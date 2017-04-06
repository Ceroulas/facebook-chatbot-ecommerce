package br.com.facebook.handler.outgoing

import br.com.facebook.handler.incoming.domain.MessageReceived
import br.com.facebook.handler.incoming.domain.Messaging
import br.com.facebook.handler.outgoing.domain.Message
import br.com.facebook.handler.outgoing.domain.OutputMessage
import br.com.facebook.handler.outgoing.domain.Recipient
import br.com.facebook.handler.outgoing.template.Attachment
import br.com.facebook.handler.outgoing.template.Button
import br.com.facebook.handler.outgoing.template.Payload
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

/**
 * Created by jonathan on 31/03/17.
 */
@Service
@Slf4j
class OutgoingHandler {

    static OutputMessage tempOutgoingMessageBuilder(MessageReceived messageReceived){
        Messaging messaging = getMessaging(messageReceived)
        String userId = messaging.sender.id
        log.info("Testing template with default messages")
        new OutputMessage(recipient: new Recipient(id: userId), message: buildMessage())
    }

    private static Message buildMessage(){
        new Message(attachment: new Attachment(payload: new Payload(template_type: 'button', text: 'Testando buttons:', buttons: buildButtons() )))
    }

    private static List<Button> buildButtons(){
        [
                new Button(type: 'web_url', url: 'www.google.com', title: 'Site do Google'),
                new Button(type: 'postback', payload: 'POSTBACK_BUTTON', title: 'Postback button')
        ]
    }

    static Messaging getMessaging(MessageReceived messageReceived){
        messageReceived.entry.first().messaging.first()
    }
}

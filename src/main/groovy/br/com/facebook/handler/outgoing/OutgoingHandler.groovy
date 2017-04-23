package br.com.facebook.handler.outgoing

import br.com.facebook.handler.incoming.domain.MessageReceived
import br.com.facebook.handler.incoming.domain.Messaging
import br.com.facebook.handler.outgoing.domain.Message
import br.com.facebook.handler.outgoing.domain.OutputMessage
import br.com.facebook.handler.outgoing.domain.Recipient
import br.com.facebook.handler.outgoing.template.Attachment
import br.com.facebook.handler.outgoing.template.Payload
import br.com.facebook.handler.outgoing.template.button.*
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

/**
 * Created by jonathan on 31/03/17.
 */
@Service
@Slf4j
class OutgoingHandler {

    static List<OutputMessage> tempOutgoingMessageBuilder(MessageReceived messageReceived){
        Messaging messaging = getMessaging(messageReceived)
        String userId = messaging.sender.id

        log.info("Testing template with default messages")
        List<Message> messages = buildMessage()
        messages.collect{ new OutputMessage(recipient: new Recipient(id: userId), message: it)}
    }

    private static List<Message> buildMessage(){
        [
            new Message(attachment: new Attachment(payload: new Payload(template_type: 'button', text: 'Testando botões:', buttons: buildButtons() ))),
            new Message(attachment: new Attachment(payload: new Payload(template_type: 'button', text: 'Testando Outros botões:', buttons: buildButtons2() )))
        ]
    }

    private static List<Button> buildButtons(){
        [
                new UrlButton(url: 'www.google.com', title: 'Site do Google'),
                new PostbackButton(title: "Postback Button", payload: "POSTBACK_PAYLOAD")
        ]
    }


    private static List<Button> buildButtons2(){
        [
                new CallButton(title: "Ligue para nós", payload: "+91318498"),
                new LogInButton(url: "www.gmail.com"),
                new LogOutButton()
        ]
    }

    static Messaging getMessaging(MessageReceived messageReceived){
        messageReceived.entry.first().messaging.first()
    }
}

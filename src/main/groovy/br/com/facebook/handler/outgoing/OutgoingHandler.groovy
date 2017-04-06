package br.com.facebook.handler.outgoing

import br.com.facebook.handler.incoming.domain.MessageReceived
import br.com.facebook.handler.incoming.domain.Messaging
import br.com.facebook.handler.outgoing.domain.Message
import br.com.facebook.handler.outgoing.domain.OutputMessage
import br.com.facebook.handler.outgoing.domain.Recipient
import org.springframework.stereotype.Service

/**
 * Created by jonathan on 31/03/17.
 */
@Service
class OutgoingHandler {

    static OutputMessage tempOutgoingMessageBuilder(MessageReceived messageReceived){
        Messaging messaging = getMessaging(messageReceived)
        String userId = messaging.sender.id
        new OutputMessage(recipient: new Recipient(id: userId), message: new Message(text: "Resposta temporária!"))
    }

    static Messaging getMessaging(MessageReceived messageReceived){
        messageReceived.entry.first().messaging.first()
    }
}

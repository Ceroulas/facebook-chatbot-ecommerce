package br.com.facebook.outgoing

import br.com.facebook.incoming.domain.MessageReceived
import br.com.facebook.outgoing.domain.Message
import br.com.facebook.outgoing.domain.OutputMessage
import br.com.facebook.outgoing.domain.Recipient
import org.springframework.stereotype.Service

/**
 * Created by jonathan on 31/03/17.
 */
@Service
class OutgoingHandler {

    OutputMessage tempOutgoingMessageBuilder(MessageReceived messageReceived){
        String userId = messageReceived.entry.first().messaging.first().sender.id
        new OutputMessage(recipient: new Recipient(id: userId), message: new Message(text: "Resposta temporária!"))
    }
}

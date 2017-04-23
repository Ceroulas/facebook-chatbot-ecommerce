package br.com.facebook.handler

import br.com.facebook.handler.incoming.IncomingHandler
import br.com.facebook.handler.incoming.domain.MessageReceived
import br.com.facebook.handler.incoming.domain.Messaging
import br.com.facebook.handler.outgoing.domain.Recipient
import br.com.facebook.handler.outgoing.domain.SenderAction
import br.com.facebook.integration.FacebookApiClient
import br.com.facebook.handler.outgoing.OutgoingHandler
import br.com.facebook.handler.outgoing.domain.OutputMessage
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by Aline on 01/04/2017.
 */
@Service
@Slf4j
class MessageHandler {

    private IncomingHandler incomingHandler
    private OutgoingHandler outgoingHandler
    private FacebookApiClient facebookApiClient

    @Autowired
    MessageHandler(IncomingHandler incomingHandler, OutgoingHandler outgoingHandler, FacebookApiClient facebookApiClient){
        this.incomingHandler = incomingHandler
        this.outgoingHandler = outgoingHandler
        this.facebookApiClient = facebookApiClient
    }

    void handleMessageFromFacebook(String message){
        MessageReceived messageReceived = incomingHandler.processIncomingMessage(message)
        Messaging messaging = outgoingHandler.getMessaging(messageReceived)
        if(!messaging.message?.isEcho && !messaging?.read) {

            markSeen(messaging)
            typingOn(messaging)

            List<OutputMessage> outputMessages = outgoingHandler.tempOutgoingMessageBuilder(messageReceived)
            outputMessages.each { facebookApiClient.sendFacebookMessage(it) }
        }
    }

    private typingOn(Messaging messaging){
        String userId = messaging.sender.id
        OutputMessage typinOnMessage = new OutputMessage(recipient: new Recipient(id: userId), senderAction: SenderAction.typing_on.name())
        facebookApiClient.sendFacebookMessage(typinOnMessage)
    }

    private markSeen(Messaging messaging){
        String userId = messaging.sender.id
        OutputMessage markSeenMessage = new OutputMessage(recipient: new Recipient(id: userId), senderAction: SenderAction.mark_seen.name())
        facebookApiClient.sendFacebookMessage(markSeenMessage)
    }
}

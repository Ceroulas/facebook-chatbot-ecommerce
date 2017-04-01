package br.com.facebook.handler

import br.com.facebook.incoming.IncomingHandler
import br.com.facebook.incoming.domain.MessageReceived
import br.com.facebook.incoming.domain.Messaging
import br.com.facebook.integration.FacebookApiClient
import br.com.facebook.outgoing.OutgoingHandler
import br.com.facebook.outgoing.domain.OutputMessage
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
            OutputMessage outputMessage = outgoingHandler.tempOutgoingMessageBuilder(messageReceived)
            facebookApiClient.sendFacebookMessage(outputMessage)
        }
    }
}
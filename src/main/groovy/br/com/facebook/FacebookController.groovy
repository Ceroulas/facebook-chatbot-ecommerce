package br.com.facebook

import br.com.facebook.incoming.IncomingHandler
import br.com.facebook.incoming.domain.MessageReceived
import br.com.facebook.integration.FacebookApiClient
import br.com.facebook.outgoing.OutgoingHandler
import br.com.facebook.outgoing.domain.OutputMessage
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by jonathan on 04/03/17.
 */
@RestController
@Slf4j
class FacebookController {

    @Value('${facebook.adapter.verify_token}')
    String token
    IncomingHandler incomingHandler
    OutgoingHandler outgoingHandler
    FacebookApiClient facebookApiClient

    @Autowired
    FacebookController(IncomingHandler incomingHandler, OutgoingHandler outgoingHandler, FacebookApiClient facebookApiClient){
        this.incomingHandler = incomingHandler
        this.outgoingHandler = outgoingHandler
        this.facebookApiClient = facebookApiClient
    }

    @RequestMapping(value  ='/webhook', method = RequestMethod.GET)
    tokenVerification(@RequestParam('hub.verify_token') String tokenToVerify, @RequestParam('hub.challenge') String challenge){
        log.info("Token received - $tokenToVerify, challenge: $challenge")
        (tokenToVerify == token)? challenge:
                new ResponseEntity<>("invalid token: ${tokenToVerify}", HttpStatus.BAD_REQUEST)
    }

    //@TODO - refatorar, remover logica de negocio para um handler
    @RequestMapping(value = '/webhook', method = RequestMethod.POST)
    receiveMessageFromFacebook(@RequestBody String message){
        log.info("Message received from Facebook: $message")

        //@TODO - tratar is_echo
        MessageReceived messageReceived = incomingHandler.processIncomingMessage(message)
        OutputMessage outputMessage = outgoingHandler.tempOutgoingMessageBuilder(messageReceived)
        facebookApiClient.sendFacebookMessage(outputMessage)

        new ResponseEntity(HttpStatus.OK)
    }
}

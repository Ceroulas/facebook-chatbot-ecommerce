package br.com.facebook

import br.com.facebook.handler.MessageHandler
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
    MessageHandler handler

    @Autowired
    FacebookController(MessageHandler handler){
        this.handler = handler
    }

    @RequestMapping(value  ='/webhook', method = RequestMethod.GET)
    tokenVerification(@RequestParam('hub.verify_token') String tokenToVerify, @RequestParam('hub.challenge') String challenge){
        log.info("Token received - $tokenToVerify, challenge: $challenge")
        (tokenToVerify == token)? challenge:
                new ResponseEntity<>("invalid token: ${tokenToVerify}", HttpStatus.BAD_REQUEST)
    }

    @RequestMapping(value = '/webhook', method = RequestMethod.POST)
    receiveMessageFromFacebook(@RequestBody String message){
        log.info("Message received from Facebook: $message")
        handler.handleMessageFromFacebook(message)
        new ResponseEntity(HttpStatus.OK)
    }
}

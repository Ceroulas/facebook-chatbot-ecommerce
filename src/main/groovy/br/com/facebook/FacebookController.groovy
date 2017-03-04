package br.com.facebook

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by jonathan on 04/03/17.
 */
@RestController
class FacebookController {

    @Value('${facebook.adapter.verify_token}')
    String token

    @RequestMapping(value  ='/webhook', method = RequestMethod.GET)
    tokenVerification(@RequestParam('hub.verify_token') String tokenToVerify, @RequestParam('hub.challenge') String challenge){
        (tokenToVerify == token)? challenge:
                new ResponseEntity<>("invalid token: ${tokenToVerify}", HttpStatus.BAD_REQUEST)
    }

    @RequestMapping(value = '/webhook', method = RequestMethod.POST)
    receiveMessageFromFacebook(@RequestBody String message){
        println message
    }
}

package br.com.facebook.incoming

import br.com.facebook.incoming.domain.MessageReceived
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

/**
 * Created by jonathan on 31/03/17.
 */
@Service
class IncomingHandler {

    MessageReceived processIncomingMessage(String message){
        new ObjectMapper().readValue(message, MessageReceived)
    }
}

package br.com.facebook.unit

import br.com.facebook.FacebookController
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by Jonathan on 05/03/2017.
 */
class FacebookControllerTest extends Specification{
    @Shared api = new FacebookController()

    def mockMvc = MockMvcBuilders.standaloneSetup(api).build()

    def setupSpec(){
        api.token = 'mysecret_token_to_verify'
    }

    def 'should call /webhook with token e challenge from facebook'(){
        given: 'endpoint accessed with parameters: #veiryToken, #challenge'

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get('/webhook')
                .param("hub.verify_token", verifyToken)
                .param("hub.challenge", challenge)
        when: 'send challenge value'
            def response = mockMvc.perform(request)
        then: 'expect response status OK and challenge value returned'
            response.andExpect(status().isOk())
                    .andExpect(content().string(challenge))
        where:
            verifyToken | challenge
            'mysecret_token_to_verify' | 'challenge_test'
    }

    def 'should return bad resquest, wrong token from facebook'(){
        given: 'endpoint accessed with parameters: #veiryToken, #challenge'

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get('/webhook')
                .param("hub.verify_token", verifyToken)
                .param("hub.challenge", challenge)
        when: 'send challenge value'
            def response = mockMvc.perform(request)
        then: 'expect response status BAD_REQUEST'
            response.andExpect(status().isBadRequest())
        where:
            verifyToken | challenge
            'wrong_token_to_verify' | 'challenge_test'
    }

    def 'should call /webhook with message from facebook'(){
        given: 'endpoint accessed with a test message'
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post('/webhook')
                .content("just a test message")
        when: 'send challenge value'
            def response = mockMvc.perform(request)

        then: 'expect response status OK and challenge value returned'
            response.andExpect(status().isOk())
    }
}

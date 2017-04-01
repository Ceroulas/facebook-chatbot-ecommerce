package br.com.facebook.integration

import br.com.facebook.outgoing.domain.OutputMessage
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 * Created by jonathan on 31/03/17.
 */
@Service
@Slf4j
class FacebookApiClient {

    @Value('${facebook.adapter.graph_url}')
    String facebookUrl
    @Value('${facebook.adapter.page_access_token}')
    String token

    void sendFacebookMessage(OutputMessage message){
        String path = "$facebookUrl$token"
        String body = new ObjectMapper().writeValueAsString(message)
        log.info("Message sent to facebook: $body")
        doPost(path, [:], String.class, body)
    }

    private <T> T doPost(String path, Map<String, Object> query, Class<T> result, String body) {
        try {
            HttpHeaders h = new HttpHeaders()
            h.put("Content-Type",["application/json"])
            HttpEntity<String> e = new HttpEntity<>(body,h)

            UriComponentsBuilder builder =  UriComponentsBuilder.fromHttpUrl("$path")
            query.each {k,v -> builder.queryParam(k, v)}
            def response = new RestTemplate().exchange(builder.build().encode().toUri(), HttpMethod.POST, e, result)

            log.debug("Retorno facebook para $path $query: $response.statusCode - $response.body")
            return response.body
        } catch (HttpStatusCodeException ex) {
            log.error("Retorno facebook para $path $query: $ex.statusCode - $ex.responseBodyAsString", ex)
            if (ex.statusCode == HttpStatus.NOT_FOUND) {
                return null
            }
            throw ex
        }
    }
}

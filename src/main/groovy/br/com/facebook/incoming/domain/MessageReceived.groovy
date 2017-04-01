package br.com.facebook.incoming.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Created by jonathan on 31/03/17.
 */
@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class MessageReceived {
    String object
    Collection<Entry> entry
}

@Canonical
class Entry {
    String id
    Long time
    Collection<Messaging> messaging
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Messaging {
    Sender sender
    br.com.facebook.outgoing.domain.Recipient recipient
    Long timestamp
    Message message
    Read read
}

@Canonical
class Sender {
    String id
}

@Canonical
class Recipient {
    String id
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Message {
    @JsonProperty("is_echo")
    Boolean isEcho
    @JsonProperty("app_id")
    Long appId
    String mid
    Long seq
    String text
}
package br.com.facebook.handler.outgoing.domain

import br.com.facebook.handler.outgoing.template.Attachment
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Created by jonathan on 31/03/17.
 */
@Canonical
class OutputMessage {
    Recipient recipient
    Message message
    @JsonProperty("sender_action")
    String senderAction
}

@Canonical
class Recipient{
    String id
}

@Canonical
class Message{
    String text
    Attachment attachment
}

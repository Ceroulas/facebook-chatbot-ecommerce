package br.com.facebook.outgoing.domain

import groovy.transform.Canonical

/**
 * Created by jonathan on 31/03/17.
 */
@Canonical
class OutputMessage {
    Recipient recipient
    Message message
}

@Canonical
class Recipient{
    String id
}

@Canonical
class Message{
    String text
}

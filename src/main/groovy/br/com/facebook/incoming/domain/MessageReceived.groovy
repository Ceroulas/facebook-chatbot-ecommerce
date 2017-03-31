package br.com.facebook.incoming.domain

import groovy.transform.Canonical

/**
 * Created by jonathan on 31/03/17.
 */
@Canonical
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
class Messaging {
    Sender sender
    br.com.facebook.outgoing.domain.Recipient recipient
    Long timestamp
    Message message
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
class Message {
    String mid
    Long seq
    String text
}
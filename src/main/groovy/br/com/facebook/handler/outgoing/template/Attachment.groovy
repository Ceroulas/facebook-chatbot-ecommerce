package br.com.facebook.handler.outgoing.template

import groovy.transform.Canonical

/**
 * Created by jonathan on 06/04/17.
 */
@Canonical
class Attachment {
    String type = 'template'
    Payload payload
}

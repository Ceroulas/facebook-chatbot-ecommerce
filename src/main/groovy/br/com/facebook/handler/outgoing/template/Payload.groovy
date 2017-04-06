package br.com.facebook.handler.outgoing.template

import groovy.transform.Canonical

/**
 * Created by jonathan on 06/04/17.
 */
@Canonical
class Payload {
    String template_type
    String text
    Collection<Button> buttons
}

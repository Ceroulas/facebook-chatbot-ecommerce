package br.com.facebook.handler.outgoing.template.button

import groovy.transform.Canonical

/**
 * Created by jonathan on 23/04/17.
 */
@Canonical
class CallButton extends Button{
    String type = "phone_number"
    String title
    String payload
}

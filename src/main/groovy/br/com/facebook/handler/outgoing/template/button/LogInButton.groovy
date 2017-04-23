package br.com.facebook.handler.outgoing.template.button

import groovy.transform.Canonical

/**
 * Created by jonathan on 23/04/17.
 */
@Canonical
class LogInButton extends Button{
    String type = "account_link"
    String url
}

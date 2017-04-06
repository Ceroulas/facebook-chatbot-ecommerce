package br.com.facebook.handler.outgoing.template

import groovy.transform.Canonical

/**
 * Created by jonathan on 06/04/17.
 */
@Canonical
//@TODO especificar melhor cada tipo de Button Template
class Button {
    String type
    String url
    String title
    String webview_height_ratio
    Boolean messenger_extensions
    String fallback_url
    def payload
    def share_contents
}

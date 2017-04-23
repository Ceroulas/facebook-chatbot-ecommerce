package br.com.facebook.handler.outgoing.template.button

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Created by jonathan on 23/04/17.
 */
@Canonical
class UrlButton extends Button{
    String type = "web_url"
    String url
    String title
    @JsonProperty("webview_height_ratio")
    String webviewHeightRatio = "compact"
    @JsonProperty("messenger_extensions")
    Boolean messengerExtension
}

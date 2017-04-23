package br.com.facebook.handler.outgoing.template.button

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Created by jonathan on 06/04/17.
 */
@Canonical
class Button {
    String type
    String url
    String title
    @JsonProperty("webview_height_ratio")
    String webviewHeightRatio
    @JsonProperty("messenger_extensions")
    Boolean messengerExtensions
    @JsonProperty("fallback_url")
    String fallbackUrl
    def payload
    @JsonProperty("share_contents")
    def shareContents
}

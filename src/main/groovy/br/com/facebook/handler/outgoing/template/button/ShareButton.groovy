package br.com.facebook.handler.outgoing.template.button

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Created by jonathan on 23/04/17.
 */
@Canonical
class ShareButton extends Button{
    String type = "element_share"
    @JsonProperty("share_contents")
    def shareContents
}

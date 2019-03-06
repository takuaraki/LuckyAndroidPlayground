package io.github.takuaraki.epoxy

import com.airbnb.epoxy.TypedEpoxyController

class TextController : TypedEpoxyController<List<String>>() {

    override fun buildModels(messages: List<String>) {
        messages.forEachIndexed { index, message ->
            cellText {
                id(index)
                message(message)
            }
        }
    }

}
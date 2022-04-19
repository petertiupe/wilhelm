package de.tiupe.handlers

import dev.fritz2.core.RootStore
import dev.fritz2.core.render
import kotlinx.coroutines.flow.flowOf

object StringStore : RootStore<String>("Peters Punkte") {

    val appendHandler = handle { model ->
        "$model +"
    }

    val removeHandlerWithoutInt = handle { model: String ->
        if (model.length > "Peters Punkte".length) {
            model.dropLast(1)
        } else {
            model
        }
    }

    val removeHandler = handle<Int> { model: String, numberToRemove: Int ->
        if (model.length > "Peters Punkte".length) {
            model.dropLast(numberToRemove)
        } else {
            model
        }
    }

    val initHandler = handle { model ->
        "Peters Punkte"
    }
}

fun renderSimpleHandler() {
    render("#simplehandler") {
        div {
            button("addButton") {
                +"Add To String"
                inlineStyle("margin: 5px")
                clicks handledBy StringStore.appendHandler
            }

            button("removeButton") {
                +"Remove from String"
                inlineStyle("margin: 5px")
                flowOf(1) handledBy StringStore.removeHandler
                clicks handledBy StringStore.removeHandlerWithoutInt
            }

            button("initButton") {
                +"Initialize"
                inlineStyle("margin: 5px")
                clicks handledBy StringStore.initHandler
            }

            StringStore.data.render {
                p { +it }
            }
        }
    }
}
package de.tiupe.handlers

import dev.fritz2.core.RootStore
import dev.fritz2.core.SimpleHandler

import dev.fritz2.core.render

 object StringStore : RootStore<String>("Peters Punkte") {

    val appendHandler = handle { model->
        "$model +"
    }

    val removeHandler = handle{ model->
        if(model.length > "Peters Punkte".length ) {
            model.dropLast(1)
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
                + "Add To String"
                inlineStyle("margin: 5px")
                clicks handledBy StringStore.appendHandler
            }

            button("removeButton") {
                + "Remove from String"
                inlineStyle("margin: 5px")
                clicks handledBy StringStore.removeHandler
            }

            button("initButton") {
                + "Initialize"
                inlineStyle("margin: 5px")
                clicks handledBy StringStore.initHandler
            }
        }
        StringStore.data.render {
            p{ + it }
        }
    }
}
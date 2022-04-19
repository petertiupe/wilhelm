package de.tiupe.handlers

import dev.fritz2.core.*

/**
 * Handler können sich gegenseitig aufrufen, aber auch selbst Events auslösen, damit
 * andere Handler sich für dieses Event eintragen können.
 *
 * Der Unterschied besteht darin, dass man einmal schon weiß,
 * welcher Store mit neuen Daten versehen werden muss, bei dem anderen Weg gibt man
 * mehreren Stores die Möglichkeit sich zu verbinden und muss nicht wissen,
 * welchen Handler man aufruft. Die Kontrolle bleibt komplett beim "Zielstore".
 *
 * Beide Stores in dem Beispiel sind vom Typ String.
 *
 * */

object SaveStore : RootStore<String>("") {
    // Der Store ersetzt einfach die Daten im Store durch die im Handler ankommenden Daten
    val save = handle<String> { _, data ->
        data
    }
}

object InputStore : RootStore<String>("") {
    // erst werden die Daten im SaveStore gesetzt, dann auch im InputStore.
    val handleInput = handle<String> { _, input ->
        SaveStore.save(input) // call other store`s handler
        input // do not forget to return the "next" store value!
    }
}

fun renderConnectingHandlers() {
    render("#connectinghandlers") {
        val inputID = "petersEingabefeld"
        label {
            `for`(inputID)
            +"Gib einen String ein, der in beide Stores soll "
        }
        input {
            width(40)
            height(30)
            changes.values() handledBy InputStore.handleInput
        }
        p {
            InputStore.data.render(into = this) {
                +"Im Input-Store steht "
                span {
                    inlineStyle("color: green")
                    +it
                }
            }
        }
        p {
            SaveStore.data.render(into = this) {
                +"Im Save-Store steht "
                span {
                    inlineStyle("color: blue")
                    +it
                }


            }
        }
        p {
            +"Der Save-Store wird vom Input-Store handler aus aufgerufen"
        }


    }
}

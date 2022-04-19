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
 * Dieser Code ist eine Kopie von den connectinghandlers, nur dass hier ein
 * Event emitted wird.
 *
 * */

object SaveStoreReceiving : RootStore<String>("") {

    // Der Store ersetzt einfach die Daten im Store durch die im Handler ankommenden Daten
    val save = handle<String> { _, data ->
        data
    }

    // In der init-Funktion muss der Handler registriert werden, der auf das Event hört, ansonsten
    // wird vom InputStoreEmitting nur "gesendet", aber niemand hört darauf.-
    init {
        InputStoreEmitting.handleAndEmitInput handledBy save
    }
}

object InputStoreEmitting : RootStore<String>("") {
    // erst werden die Daten im SaveStore gesetzt, dann auch im InputStore.
    val handleAndEmitInput = handleAndEmit<String, String> {_, newModelValue ->
        emit(newModelValue)
        newModelValue
    }
}

fun renderEmittingHandlers() {
    render("#emittinghandlers") {
        val inputID = "petersEingabefeld"
        label {
            `for`(inputID)
            +"Gib einen String ein, der in beide Stores soll "
        }
        input {
            width(40)
            height(30)
            changes.values() handledBy InputStoreEmitting.handleAndEmitInput
        }
        p {
            InputStoreEmitting.data.render(into = this) {
                +"Im Input-Store steht "
                span {
                    inlineStyle("color: green")
                    +it
                }
            }
        }
        p {
            SaveStoreReceiving.data.render(into = this) {
                +"Im Save-Store steht "
                span {
                    inlineStyle("color: blue")
                    +it
                }
            }
        }
        p {
            +"Der Save-Store empfängt das Event vom emitting Handler"
        }
    }
}

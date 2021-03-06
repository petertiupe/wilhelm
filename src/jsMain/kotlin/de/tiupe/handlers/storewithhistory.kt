package de.tiupe.handlers

import dev.fritz2.core.*
import dev.fritz2.headless.components.inputField
import dev.fritz2.history.history
import tooltip

object StoreWithHistory : RootStore<String>("Default-Entry") {
    val history = history<String>().sync(this)
 }

fun renderStoreWithHistory() {
    render("#storewithhistory") {
        val idForInput = "inputId"
        label {
            `for`(idForInput)
            + "Der folgende Wert wird in der History gespeichert"
        }

        /* Dies ist ein Input-Field aus der Komponenten-Bibliothek */
        inputField {
            value(StoreWithHistory)
            placeholder("Der Wert soll in die History ...")
            inputTextfield { }
        }.tooltip {
            + "Mein erster Tooltipp"
        }

        input {
            width(40)
            height(30)
            changes.values() handledBy StoreWithHistory.update
        }
        // Ausgabe der History, die als Flow zur Verfügung steht und eine default-Länge von 10 hat.
        var int = 0
        StoreWithHistory.history.data.renderEach {historyEntry ->
           ++int
            li{
               + "$int er Eintrag in der Historie: $historyEntry"
           }
        }
    }


}
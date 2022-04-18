package de.tiupe.mountpoints

import dev.fritz2.core.render
import dev.fritz2.core.storeOf
import kotlinx.coroutines.flow.combine

val storeVorname = storeOf<String>("Peter")
val storeNachname = storeOf<String>("Marx")

fun renderCombinedStores() {
    render("#combinedstores") {
        p {
            + "Hier werden Flows aus zwei Stores kombiniert:"
            br{}
        storeVorname.data.combine(storeNachname.data) { firstStore, secondStore ->
                "Vorname: $firstStore Nachname: $secondStore"
            }.renderText()
        }
    }
}
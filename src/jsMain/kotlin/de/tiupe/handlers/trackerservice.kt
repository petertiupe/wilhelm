package de.tiupe.handlers

import dev.fritz2.core.RootStore
import dev.fritz2.core.render
import dev.fritz2.tracking.tracker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

object TrackerStore: RootStore<String>("") {
    val tracking = tracker()

    val save = handle { model ->
        tracking.track("myTransaction") {
            delay(3500) // do something that takes a while
            "$model."
        }
    }
}
fun renderTracker() {
    render("#trackerservice") {
        button(baseClass = "button", "btn",) {
            // Dies ist ein Beispiel für CSS-Attribut, dass über einen Flow
            // gesteuert wird.
            // Die Ansicht wird via CSS gesteuert, dass ist nicht Teil dessen, was
            // ich hier betrachte, sollte aber kein Problem mehr sein....
            // Läuft sogar mit geklautem CSS :-)
            className(TrackerStore.tracking.data.map {
                console.log("Boolean ist: $it")
                if (it) "button__text" else ""
            })
            +"Save with Spinner just to show it"
            clicks handledBy TrackerStore.save
        }
    }
}
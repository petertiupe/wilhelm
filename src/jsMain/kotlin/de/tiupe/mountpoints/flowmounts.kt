package de.tiupe.mountpoints

import kotlinx.coroutines.flow.flowOf
import dev.fritz2.core.render

fun renderFlow() {
    // Man kann Extnsion-Funktionen auch innerhalb von Interfaces und
    // Klassen schreiben. Damit steht die Funktion innerhalb des Typesafe-Builders
    // zur Verfügung und kann im Render-Context genutzt werden.
    render("#flowmount") {
        p {
            + "Hier wird ein Flow gerendert:"

            flowOf(4).render {
                if(it % 2 == 0) p { +"Die Zahl $it ist gerade" }
                // Bei ungeraden Zahlen wird nichts gerendert.
            }
        }
    }
}
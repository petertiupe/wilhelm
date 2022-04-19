package de.tiupe.mountpoints

import dev.fritz2.core.render
import kotlinx.coroutines.flow.flowOf


fun renderListmounts() {
    val listFlow = flowOf(listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"))

    render("#listmounts"){
        p { + "Rendering ohne spezielle Mount-Points für kurze Listen"}
        p { + "Ändert sich ein Element der Liste wird alles neu gerendert."}
        listFlow.render { list ->
            list.forEach {wochentag ->
                li {
                    + wochentag
                }
            }
        }


        p {
            inlineStyle("color: blue")
            + "Rendering mit \"renderEach\" auf dem Flow. Für jedes Listenelement wird ein eigener Mount-Point "}
        p {
            inlineStyle("color: blue")
            + "erzeugt und nur dieser wird ersetzt, wenn sich auf dem Flow etwas ändert."}
        p {
            inlineStyle("color: blue")
            + "Dies hat noch den Vorteil, dass man im Lambda gleich den richtigen Typ zur Verfügung hat."}
        listFlow.renderEach { tagderWoche ->
            li() {
                // setzt die Farbe für die Zweite Liste
                inlineStyle("color: blue")
                + tagderWoche
            }
        }
    }

}
package de.tiupe.minitypeahead

import dev.fritz2.core.render
import dev.fritz2.core.type
import kotlinx.coroutines.flow.flowOf

fun renderTypeAhead() {
    render("#typeaheadtiupe") {
        h1{
            +"Bitte wählen Sie ein Fahrzeug aus ..."
        }
        input() {
            type("text")
            attr("list", "peterslist")
            datalist(id = "peterslist") {
                flowOf(listOf("Blaues Fahrrad", "gelber Bulli", "weißer Renaut", "Taxit", "Bus", "van")).renderEach {
                    option {
                        +  it
                    }
                }

            }
        }
    }

}
package de.tiupe.mountpoints

import dev.fritz2.core.render
import kotlinx.coroutines.flow.flowOf

fun renderFlowUsingInto() {
    render("#flowwithinto") {
        p {
            + "Hier wird ein Flow gerendert:"

            flowOf("Peter" to "Marx").render(into=this) { (key, value) ->
                + "Es wird mit into gearbeitet"
                + "Der Key ist $key, der Value ist $value"
            }
        }
    }
}
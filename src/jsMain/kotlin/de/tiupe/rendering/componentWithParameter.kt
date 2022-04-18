package de.tiupe.rendering

import dev.fritz2.core.RenderContext
import dev.fritz2.core.render

data class PersonForComponent(val vorname: String, val nachname: String )

fun RenderContext.personComponent(person: PersonForComponent) {
    p {
        + "Mein Vorname ist: ${person.vorname}"
        br {}
        + "Mein Nachname ist ${person.nachname}"
    }
}

fun renderComponentWithParameter() {
    render("#componentWithParameter") {
        val parameter = PersonForComponent("Peter", "Marx")
        personComponent(parameter)
    }
}
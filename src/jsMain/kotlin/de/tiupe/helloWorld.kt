package de.tiupe

import dev.fritz2.core.render

fun renderHelloWorld() {
    render(selector = "#helloWorld") {
        h1 { + "Hallo Welt-Beispiel" }
        div {
            p(id = "someId") {
                +"Hallo Welt!"
            }
        }
    }
}
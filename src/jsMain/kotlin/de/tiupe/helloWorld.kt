package de.tiupe

import dev.fritz2.core.render

fun renderHelloWorld() {
    render(selector = "#helloWorld") {
        h1 { +"Hallo Welt-Beispiel" }
        div("some-fix-css-class") {
            p(id = "someId") {
                +"Hallo Welt!"
            }
        }
    }
}
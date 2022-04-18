package de.tiupe

import dev.fritz2.core.RenderContext
import dev.fritz2.core.render

fun RenderContext.simplestComponent() {
    p {
        +"Dies ist die einfachste Komponente, die man sich vorstellen kann."
    }
}

// Using the Component
fun renderSimplestComponent() {
    console.log("Peter wars")
    render("#simplestComponent"){
        simplestComponent()
    }
}
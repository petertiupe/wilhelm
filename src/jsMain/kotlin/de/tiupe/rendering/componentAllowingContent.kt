package de.tiupe.rendering

import dev.fritz2.core.RenderContext
import dev.fritz2.core.render

fun RenderContext.componentAllowingContent(contentToBeRenderedInsideComponent : RenderContext.() -> Unit) {
    p{ + "Dies ist der Komponenteninhalt"}
    contentToBeRenderedInsideComponent()
}


fun renderComponentAllowingContent() {
    render("#componentAllowingContent") {
        p {
            + "Hier wird Content aufgebaut, in dem die obige Kompoenente verwendet wird"
        }
        componentAllowingContent {
            p {
                + "Dieser Teil ist Inhalt, der Unterhalb der Komponente mit Inhalt gerendert wird."
            }
        }
        p { + "Dieser Teil hat mit der Komponente nichts mehr zu tun..."}
    }
}
package de.tiupe

import de.tiupe.mountpoints.renderFlow
import de.tiupe.rendering.renderComponentAllowingContent
import de.tiupe.rendering.renderComponentWithParameter
import de.tiupe.rendering.renderHelloWorld
import de.tiupe.rendering.renderSimplestComponent

fun main(){
    renderHelloWorld()
    renderSimplestComponent()
    renderComponentWithParameter()
    renderComponentAllowingContent()
    renderFlow()
}


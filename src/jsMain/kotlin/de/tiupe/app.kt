package de.tiupe

import de.tiupe.mountpoints.renderCombinedStores
import de.tiupe.mountpoints.renderFlow
import de.tiupe.mountpoints.renderFlowUsingInto
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
    renderFlowUsingInto()
    renderCombinedStores()
}


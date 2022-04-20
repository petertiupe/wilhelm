package de.tiupe

import de.tiupe.handlers.*
import de.tiupe.mountpoints.renderCombinedStores
import de.tiupe.mountpoints.renderFlow
import de.tiupe.mountpoints.renderFlowUsingInto
import de.tiupe.mountpoints.renderListmounts
import de.tiupe.rendering.renderComponentAllowingContent
import de.tiupe.rendering.renderComponentWithParameter
import de.tiupe.rendering.renderHelloWorld
import de.tiupe.rendering.renderSimplestComponent
import de.tiupe.routing.renderRouting
import de.tiupe.validation.renderValidation

fun main(){
    renderHelloWorld()
    renderSimplestComponent()
    renderComponentWithParameter()
    renderComponentAllowingContent()
    renderFlow()
    renderFlowUsingInto()
    renderCombinedStores()
    renderListmounts()
    renderSimpleHandler()
    renderConnectingHandlers()
    renderEmittingHandlers()
    renderStoreWithHistory()
    renderTracker()
    renderValidation()
    renderRouting()
}


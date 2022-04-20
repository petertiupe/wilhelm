package de.tiupe.validation

import dev.fritz2.validation.ValidationMessage
import kotlinx.serialization.*

@Serializable
sealed class Severity(val level: Int) {

    @Serializable
    object INFO : Severity(0)

    @Serializable
    object WARNING : Severity(10)

    @Serializable
    object ERROR : Severity(20)
}

@Serializable
data class Message(override val path: String,
                   val severity: Severity,
                   val text: String): ValidationMessage {
    override val isError: Boolean = severity.level > 10
}
package de.tiupe.model

import dev.fritz2.core.Lenses

@Lenses
data class Person(val surname: String = "", val givenName: String="")


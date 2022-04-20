package de.tiupe.model

import de.tiupe.validation.Message
import de.tiupe.validation.Severity
import dev.fritz2.core.Inspector
import dev.fritz2.core.Lenses
import dev.fritz2.validation.Validation
import dev.fritz2.validation.validation

@Lenses
data class Person(val surname: String, val givenName: String, val age: Int) {
    companion object {
        // Es gehen drei Typen ein:
        //  1. zu validierender Typ (data class)
        //  2. Metadaten, die z.B. den Validierungsstep enthalten oder auch mehr
        //  3. der Ergebnistyp, also das was man zurückhaben möchte. In meinem Fall habe ich einen eigenen
        //     Nachrichtentyp definiert
        // Der Inspector ist ein Objekt für den Zugriff auf die Lenses.

        val validation: Validation<Person, Unit, Message> =
            validation<Person, Message> { inspector: Inspector<Person> ->
                val givenName   = inspector.sub(Person.givenName())
                val surname     = inspector.sub(Person.surname())
                val age         = inspector.sub(Person.age())

                if (givenName.data.trim().isBlank()) {
                    add(Message(givenName.path, Severity.ERROR, "Bitte geben Sie einen Vornamen ein"))
                }

                if (surname.data.trim().isBlank()) {
                    add(Message(surname.path, Severity.ERROR, "Bitte geben Sien einen Nachnamen ein"))
                }

                if (age.data < 0 || age.data >  99 ) {
                    add(Message(age.path, Severity.ERROR, "Bitte überprüfen Sie das Alter"))
                }
            }
    }
}


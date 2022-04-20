package de.tiupe.validation

import de.tiupe.handlers.InputStore
import de.tiupe.handlers.InputStore.invoke
import de.tiupe.handlers.SaveStore
import de.tiupe.model.Person
import dev.fritz2.core.*
import dev.fritz2.validation.ValidatingStore

// Durch das Erben von einer anderen Root-Klasse läuft die Validierung automatisch...
object PersonStore : ValidatingStore<Person, Unit, Message>(initialData = Person("", "", -1), Person.validation) {
    val handleInputSurname = handle<String> { pers, input ->
        pers.copy(surname = input)
    }
    val handleInputGivenName = handle<String> { pers, input ->
        pers.copy(givenName = input)
    }
    val handleInputAge = handle<String> { pers, input ->
        pers.copy(age = input.toInt())
    }
}



/**
 * Das Beispiel zeigt eine Validierung anhand einer Person, die wie üblich
 * im commonMain-Paket definiert wurde. Die Dokumentation ist an der Stelle sehr
 * ausführlich und gut, muss man einfach mal gemacht haben...
 */


fun renderValidation() {
    render("#validationOfPerson") {
        val inputIdGivenName = "givenName"
        label {
            `for`(inputIdGivenName)
            +"Vorname"
        }
        input(id = inputIdGivenName) {
            width(40)
            height(30)
            changes.values() handledBy PersonStore.handleInputGivenName
        }
        val inputIdSurname = "surname"
        label {
            `for`(inputIdSurname)
            +"Nachname"
        }
        input(id = inputIdSurname) {
            width(40)
            height(30)
            changes.values() handledBy PersonStore.handleInputSurname
        }
        val inputIdAge = "age"
        label {
            `for`(inputIdAge)
            +"Alter"
        }
        input(id = inputIdAge) {
            width(40)
            height(30)
            changes.values() handledBy PersonStore.handleInputAge
        }

        p {
            + "Folgende Fehler sind aufgetreten:"
        }
        PersonStore.messages.renderEach {validationMessage ->
            div {
                inlineStyle("color: red")
                + validationMessage.text
            }
        }

    }
}
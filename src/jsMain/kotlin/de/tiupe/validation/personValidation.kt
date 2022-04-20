
package de.tiupe.validation
import de.tiupe.model.Person
import dev.fritz2.core.*
import dev.fritz2.validation.ValidatingStore
import dev.fritz2.validation.valid

// Durch das Erben von einer anderen Root-Klasse läuft die Validierung automatisch...

/**
 * Man kann die automatische Validierung bei jeder Modelländerung beim Anlegen des Stores ein- bzw.
 * ausschalten. Ansonsten kann man die Steuerung der Validierung über die Metainformationen vornehmen.
 * */
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
    /**
     * Die beiden folgenden Handler dienen nur als Beispiel, falls man die Validierung in einem
     * Handler benötigt.
     * */
    @Suppress("unused")
     val handlerWithValidation = handle {pers ->

        if(validate(pers).valid) {
            // send request to server...
            pers
        } else {
            Person("","", -1)
        }
    }
    @Suppress("unused")
    val handlerWithValidationReset = handle { pers ->
        resetMessages() // empties the list of messages
        pers
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
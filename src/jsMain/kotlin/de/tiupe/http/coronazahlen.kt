package de.tiupe.http

import de.tiupe.model.RKIDatenRoot
import dev.fritz2.core.RootStore
import dev.fritz2.core.render
import dev.fritz2.remote.FetchException
import dev.fritz2.remote.http
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.math.round

/**
 * Unter der folgenden URL bekommt man die aktuellen Corona-Zahlen von Deutschland
 *
 *      https://api.corona-zahlen.org/germany
 *
 * Ich werde die Zahlen hier abfragen und auf einer Seite darstellen.
 *
 * */

// Festlegen der Basis-URL
val usersApi = http("https://api.corona-zahlen.org")
    .acceptJson()
//.contentType("application/json")

fun renderCoronazahlen() {
    render("#coronazahlen") {
        h1 {
            +"Aktuelle Corona-Zahlen in D-Land"
        }
        button(id = "ladeRkiDaten") {
            +"Lade RKI-Daten"
            inlineStyle("margin: 5px")
            clicks handledBy RKIDatenStore.loadDataHandler
        }

        div {
            RKIDatenStore.data.render(into = this) { rkiDatenRoot ->
                if(rkiDatenRoot.rKIDaten != null){
                    div {
                        +"7-Tage-Inzidenz:     ${round(rkiDatenRoot.rKIDaten.weekIncidence, )} "
                    }
                    div {
                        +"Todesfälle:          ${rkiDatenRoot.rKIDaten.delta.deaths}"
                    }
                    div {
                        +"Hospitalisierungsrate:          ${rkiDatenRoot.rKIDaten.hospitalization.incidence7Days}"
                    }
                    p {
                        +"Daten vom:          ${rkiDatenRoot.rKIDaten.meta.lastUpdateDateGerman}"
                    }
                } else {
                    div {
                        inlineStyle("color: red")
                        + "Daten wurden noch nicht vom Server gelesen oder der Server ist nicht erreichbar."
                    }
                }
            }
        }

    }
}


// Store um Zugriff auf die suspending-Functions zu haben
object RKIDatenStore : RootStore<RKIDatenRoot>(RKIDatenRoot("", null)) {
    val loadDataHandler = handle { rkiDatenRoot ->
        try {
            val response = usersApi.get("germany")
            if(response.ok) {
                val datenToSetResponse = response.json()
                // wenn man mit dem JS-Json-Objekt arbeitet, muss man mit stringify
                // erst einen String aus dem Typ Any erzeugen, dann kann man mit dem String
                // weiterarbeiten.
                val jsonString = JSON.stringify(datenToSetResponse)
                // manipulate JSON to make the resulting object simpler
                val jsonStringNew = "{\"id\":\"1\",\"rKIDaten\": $jsonString }"
                val rkiDaten = Json.decodeFromString<RKIDatenRoot>(jsonStringNew)
                rkiDaten
            } else {
                console.log("Zugriff auf die API hat nicht funktioniert")
                rkiDatenRoot
            }
        } catch (fex: FetchException) {
            console.error("Konnte RKI-Daten nicht lesen")
            rkiDatenRoot
        }
    }
}
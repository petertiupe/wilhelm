package de.tiupe.repositories

import de.tiupe.model.RKIDatenRoot
import de.tiupe.model.RKIDatenRootResource
import dev.fritz2.core.RootStore
import dev.fritz2.core.SimpleHandler
import dev.fritz2.core.render
import dev.fritz2.remote.http
import dev.fritz2.repository.ResourceNotFoundException
import dev.fritz2.repository.rest.restEntityOf
import kotlinx.coroutines.flow.map
import kotlin.math.round

val initialRKIDatenRoot = RKIDatenRoot("", null)

object RKIDatenRootEntityStore : RootStore<RKIDatenRoot>(initialRKIDatenRoot) {

    // Wollte das Beispiel über den Webpack-Proxy lösen, leider läd der nicht,
    // Der Code funktioniert, allerdings mit der Einschränkung, dass ich beim
    // Laden eine feste id 1 vergeben habe, das liegt aber daran, dass es sich
    // beim Laden der Coronazahlen nicht wirklich um Repository - Daten handelt.
    // Es fehlt noch die Fehlerbehandlung für den Fall, dass im Backend keine Daten
    // vorhanden sind. Es wird dann eine Fetch not found-Exception geworfen....
    private val rest = restEntityOf(RKIDatenRootResource, http("localhost:9000/repozugriff"), initialId = "")

    val load = handle<String> { _, id ->
        try {
            rest.load(id)
        } catch (rnfex: ResourceNotFoundException) {
            RKIDatenRoot("fehler", null)
        }

    }

    @Suppress("unused")
    val delete = handle {
        rest.delete(it)
        initialRKIDatenRoot

    }

    @Suppress("unused")
    val addOrUpdate: SimpleHandler<Unit> = handle { rkiDatenRoot ->
        if (rkiDatenRoot != initialRKIDatenRoot) {
            rest.addOrUpdate(rkiDatenRoot)
        } else {
            rkiDatenRoot
        }
    }
}

fun renderRepository() {
    render(selector = "#repository") {
        h1 {
            +"Aktuelle Corona-Zahlen in D-Land mit Repository abgefragt"
        }
        button(id = "ladeRkiDatenInRepo") {
            +"Lade RKI-Daten in Repo"
            inlineStyle("margin: 5px")
            clicks.map { ("1") } handledBy RKIDatenRootEntityStore.load
        }
        div {
            RKIDatenRootEntityStore.data.render(into = this) { rkiDatenRootES ->
                if (rkiDatenRootES.rKIDaten != null) {
                    div {
                        +"7-Tage-Inzidenz:     ${round(rkiDatenRootES.rKIDaten.weekIncidence)} "
                    }
                    div {
                        +"Todesfälle:          ${rkiDatenRootES.rKIDaten.delta.deaths}"
                    }
                    div {
                        +"Hospitalisierungsrate:          ${rkiDatenRootES.rKIDaten.hospitalization.incidence7Days}"
                    }
                    p {
                        +"Daten vom:          ${rkiDatenRootES.rKIDaten.meta.lastUpdateDateGerman}"
                    }
                } else if (rkiDatenRootES.id == "fehler") {
                    div {
                        inlineStyle("color: red")
                        +"Die Daten konnten nicht vom Server gelesen werden (Webpack-Konfiguration....)"
                    }
                } else {
                    div { }
                }
            }
        }
    }
}

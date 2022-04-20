package de.tiupe.routing

import dev.fritz2.core.render
import dev.fritz2.routing.routerOf


object Pages {
    const val home = "Home"
    const val page1 = "Page_1"
    const val page2 = "Page_2"
}

fun renderRouting() {
    // Der Einstieg in das Routing muss den Router kennen und bekommt eine Default-Seite
    // mitgegeben, die aufgerufen wird, wenn eine Route nicht gültig ist
    val mapRouter = routerOf(mapOf("page" to Pages.home))

    render("#routing") {
        h1 {
            +"Routing-Beispiel mit Map-Router"
        }

        // In dieses div schreibt der Router je nach Event...
        div(id = "routercontent") {
            // die Render-Funktion des Routers bekommt einen Key und die gesamte Map als Parameter
            // Page_1 und Page_2 zeigen unterschiedliche Möglichkeiten, wie man im Router an die Daten
            // aus der Map kommt.
            mapRouter.select("page").render { (pagekey, mp) ->
                when (pagekey) {
                    Pages.home    -> div {
                        + "Es wurde auf die HOME-Seite geroutet"
                    }
                    Pages.page1   -> div {
                        + "Es wurde auf die Seite 1 geroutet"
                        div{
                            + "Folgende Einträge stehen in der Map des Routers:"
                        }
                        mp.forEach { (key, value) ->
                            div {
                                + "Key: $key       Value: $value"
                            }
                        }
                    }
                    Pages.page2   -> div {
                        // exemplarisches Nutzen der Map
                        + "Es wurde auf die Seite 2 geroutet"
                        div{
                            + "Folgende Einträge stehen in der Map des Routers:"
                        }
                        mapRouter.data.render { map ->
                            map.forEach { (key, value) ->
                                div {
                                    + "Key: $key       Value: $value"
                                }
                            }
                        }
                    }
                    else -> div {
                        + "Die Seite konnte nicht gefunden werden"
                        + """¯\_(ツ)_/¯"""
                    }
                }

            }
        }
    }
}
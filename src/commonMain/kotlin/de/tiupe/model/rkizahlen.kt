package de.tiupe.model

import dev.fritz2.core.Lenses
import kotlinx.datetime.*
import kotlinx.serialization.*

// {"cases":23844536,"deaths":133632,"recovered":20853110,"weekIncidence":720.6058284074237,"casesPer100k":28674.796597694734,"casesPerWeek":599220,"delta":{"cases":186325,"deaths":324,"recovered":243740},"r":{"value":0.56,"rValue4Days":{"value":0.56,"date":"2022-04-16T00:00:00.000Z"},"rValue7Days":{"value":0.77,"date":"2022-04-15T00:00:00.000Z"},"lastUpdate":"2022-04-20T06:12:12.000Z"},"hospitalization":{"cases7Days":3529,"incidence7Days":4.24,"date":"2022-04-21T00:00:00.000Z","lastUpdate":"2022-04-21T03:06:00.000Z"},"meta":{"source":"Robert Koch-Institut","contact":"Marlon Lueckert (m.lueckert@me.com)","info":"https://github.com/marlon360/rki-covid-api","lastUpdate":"2022-04-20T23:00:00.000Z","lastCheckedForUpdate":"2022-04-21T12:19:44.792Z"}}

@Serializable
@Lenses
data class RKIDatenRoot(val rKIDaten: RKIDaten?){
    companion object
}

@Serializable
@Lenses
data class RKIDaten(
    val cases: Int,
    val casesPer100k: Double,
    val casesPerWeek: Int,
    val deaths: Int,
    val delta: Delta,
    val hospitalization: Hospitalization,
    val meta: Meta,
    val r: R,
    val recovered: Int,
    val weekIncidence: Double
) {
    companion object
}

@Serializable
@Lenses
data class Delta(
    val cases: Int,
    val deaths: Int,
    val recovered: Int
) {
    companion object
}

@Serializable
@Lenses
data class Hospitalization(
    val cases7Days: Int,
    val date: String,
    val incidence7Days: Double,
    val lastUpdate: String
){
    companion object
}

@Serializable
@Lenses
data class Meta(
    val contact: String,
    val info: String,
    val lastCheckedForUpdate: String,
    val lastUpdate: String,
    val source: String
) {
    companion object
    val lastUpdateDate: LocalDate
        get() {
            return lastUpdate.toInstant().toLocalDateTime(TimeZone.UTC).date
        }
    val lastUpdateDateGerman: String
        get() {
            return "${lastUpdateDate.dayOfMonth}-${lastUpdateDate.monthNumber}-${lastUpdateDate.year}"
        }
}

@Serializable
@Lenses
data class R(
    val lastUpdate: String,
    val rValue4Days: RValue4Days,
    val rValue7Days: RValue7Days,
    val value: Double
) {
    companion object
}

@Serializable
@Lenses
data class RValue4Days(
    val date: String,
    val value: Double
) {
    companion object
}

@Serializable
@Lenses
data class RValue7Days(
    val date: String,
    val value: Double
) {
    companion object
}


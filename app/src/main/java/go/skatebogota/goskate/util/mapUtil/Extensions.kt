package go.skatebogota.goskate.util.mapUtil

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import go.skatebogota.goskate.data.models.Spot
import go.skatebogota.goskate.data.persistence.LocationEntity


fun LocationEntity.toLatLgn(): LatLng {

    return LatLng(latitude, longitude)
}

fun LocationEntity.toLocation(): Location {

    val location = Location("mLocationTraces")
    location.latitude = latitude
    location.longitude = longitude

    return location
}


fun LatLng.toLocation(): Location {

    val location = Location("mLocationTraces")
    location.latitude = latitude
    location.longitude = longitude

    return location
}


fun Location.toLocationEntity(): LocationEntity {

    return LocationEntity(longitude, latitude, altitude, System.currentTimeMillis().toString())

}


//fun LatLng.toBounds(radiusInMeters: Int): LatLngBounds {
//
//    val distanceFromCenterToCorner = radiusInMeters * sqrt(2.0)
//
//    val southwestCorner =
//            SphericalUtil.computeOffset(this, distanceFromCenterToCorner, 225.0)
//    val northeastCorner =
//            SphericalUtil.computeOffset(this, distanceFromCenterToCorner, 45.0)
//    return LatLngBounds(southwestCorner, northeastCorner)
//}

/**
 * Convierte el GeocodeResponse a GeolocationInfo
 */
fun GeocodeResponse.formatToGeoLocationInfo(
    mLastLocation: LocationEntity,
    location: Location,
    pos: Int
): Spot {

    var position = pos

    val locality =
        results[pos].address_components.extractComponent(true, "sublocality", "sublocality_level_1")
    var city = results[pos].address_components.extractComponent(
        true,
        "administrative_area_level_2",
        "locality"
    )


    val depto =
        results[pos].address_components.extractComponent(true, "administrative_area_level_1")
    val country = results[pos].address_components.extractComponent(true, "country")
    val route = results[pos].address_components.extractComponent(false, "route")

    if (city == "na")
        city = depto

    val isUrban = if (route != "na") {
        route.length > 3
    } else route != "Unnamed Road"


    val locationInfo = Spot()



    locationInfo.latitude = location.latitude
    locationInfo.longitude = location.longitude
    locationInfo.latitudeInit = mLastLocation.latitude
    locationInfo.longitudeInit = mLastLocation.longitude


    return locationInfo
}


private fun List<AddressComponent>.extractComponent(
    longName: Boolean,
    vararg tipes: String
): String {

    forEachIndexed { index, addressComponent ->
        addressComponent.types.forEach {
            if (tipes.contains(it)) {
                return if (longName) {
                    addressComponent.long_name
                } else {
                    addressComponent.short_name
                }
            }
        }
    }

    return "na"
}

fun String.extractNumbers(): Int? {
    val str = this.replace("[^-?0-9]+".toRegex(), " ").split(" ")

    return if (str.isNotEmpty()) {
        if (str[0] != "")
            str[0].toInt()
        else 0
    } else {
        0
    }

}
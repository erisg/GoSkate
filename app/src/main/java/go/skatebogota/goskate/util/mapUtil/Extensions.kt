package go.skatebogota.goskate.util.mapUtil

import android.location.Location
import com.google.android.gms.maps.model.LatLng
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

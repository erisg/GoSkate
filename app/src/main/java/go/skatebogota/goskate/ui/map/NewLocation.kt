package go.skatebogota.goskate.ui.map


import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import go.skatebogota.goskate.R
import go.skatebogota.goskate.data.models.Spot
import go.skatebogota.goskate.data.persistence.LocationEntity
import go.skatebogota.goskate.util.ResourcesUtils
import go.skatebogota.goskate.util.interfaces.IMenuGone
import go.skatebogota.goskate.util.mapUtil.*
import kotlinx.android.synthetic.main.new_location.*

class NewLocation : Fragment(), GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveStartedListener, IMenuGone,
    LocationUpdatesManager.LocationManagerCallback {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    private lateinit var currentMarker: Marker
    private var IS_PAY_GEOCODE = false
    private lateinit var mLastLocation: LocationEntity
    private val locationInfo = Spot()


    companion object {
        lateinit var menuGone: IMenuGone
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapAddLocation) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync{
            mMap = it
            it.setOnCameraIdleListener(this)
            it.setOnCameraMoveStartedListener(this)
            it.setMinZoomPreference(16f)
            it.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle))
            if (::mLastLocation.isInitialized) {
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(mLastLocation.toLatLgn(), 19f))
                currentMarker = it.addMarker(
                    MarkerOptions().icon(
                        ResourcesUtils.getBitmapDescriptor(
                            R.drawable.ic_place_black_24dp,
                            context!!
                        )
                    )
                        .position(mLastLocation.toLatLgn())
                )
            }

//            initLocation?.let { latAndLong ->
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latAndLong, 19f))
//            }

        }


    }

    private fun processGeocodeResponse(geocodeResponse: GeocodeResponse) {
        if (geocodeResponse.status == "OK") {
            val geoLocationInfo: Spot = geocodeResponse.formatToGeoLocationInfo(
                mLastLocation,
                mMap.cameraPosition.target.toLocation(), 0
            )

        } else {

        }
    }


    override fun onCameraIdle() {
        user_location.animate().translationY(0F)
        val locationInfo = Spot()

        if (!IS_PAY_GEOCODE) {
            //  startIntentService(mMap.cameraPosition.target.toLocation())
        } else {
            locationInfo.latitude = mMap.cameraPosition.target.latitude
            locationInfo.longitude = mMap.cameraPosition.target.longitude
            locationInfo.latitudeInit = mLastLocation.latitude
            locationInfo.longitudeInit = mLastLocation.longitude
            currentMarker.position =
                LatLng(mMap.cameraPosition.target.latitude, mMap.cameraPosition.target.longitude)
            // onMapGeocoderView.onProcessGeocodeResponse(locationInfo, userMoveMap)
        }
    }
    var userMoveMap = false

    override fun onCameraMoveStarted(reason: Int) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            user_location.animate().translationY(-((user_location.height / 2)).toFloat())
            userMoveMap = true
        }
    }

    override fun goneMenu(navigation: BottomNavigationView) {
        navigation.visibility = View.GONE
    }

    override fun onNewLocation(location: Location) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
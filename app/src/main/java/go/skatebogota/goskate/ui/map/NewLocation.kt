package go.skatebogota.goskate.ui.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
import go.skatebogota.goskate.util.interfaces.IMenuGone
import kotlinx.android.synthetic.main.new_location.*

class NewLocation : Fragment()  , GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener ,IMenuGone {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    private lateinit var currentMarker: Marker
    private var IS_PAY_GEOCODE = false
    private lateinit var mLastLocation: LocationEntity
    val locationInfo = Spot()

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
            it.setMinZoomPreference(18f)
            it.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle))
            var zoomLevel = 10.0f
            val sydney = LatLng(4.602275, -74.115726)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney , zoomLevel))
        }
    }


    override fun onCameraIdle() {
        user_location.animate().translationY(0F)

        if (!IS_PAY_GEOCODE) {

        } else {
            locationInfo.latitude = mMap.cameraPosition.target.latitude
            locationInfo.longitude = mMap.cameraPosition.target.longitude
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

}
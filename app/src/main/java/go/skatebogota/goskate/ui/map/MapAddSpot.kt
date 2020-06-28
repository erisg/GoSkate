package go.skatebogota.goskate.ui.map

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.map_add_spot.*

class MapAddSpot : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener  , LocationListener {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    private var navController: NavController? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.map_add_spot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        getLocation()


        /**
         * se carga mapa para elegir ubicacion
         */
        mapView = view.findViewById(R.id.mapAddLocation) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)


        addButton.setOnClickListener {
            navController!!.navigate(R.id.action_mapAddSpot_to_newLocation)
        }

        cancelButton.setOnClickListener {
            navController!!.navigate(R.id.action_mapAddSpot_to_goSkateMap)
        }

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        var lating = LatLng(4.646062 , -74.123369)
        mMap = googleMap!!
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lating , 17f))


        //se le pone el estilo al mapa
        try {
            val success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this.context, R.raw.mapstyle)
            )
            if (!success) {
                Log.e("oo", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("oo", "Can't find style. Error: ", e)
        }


    }

    private fun getLocation(){
        if(ActivityCompat.checkSelfPermission(this.requireContext() , android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext()  , android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this.requireActivity() , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION) , LOCATION_PERMISSION_REQUEST_CODE)
            return
        }else{
            var location = LocationManager.NETWORK_PROVIDER


        }
    }



    override fun onCameraIdle() {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location?) {
       location.apply {

       }
    }
}
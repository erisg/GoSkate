package go.skatebogota.goskate.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.viewmodels.MapsViewModel
import go.skatebogota.goskate.util.ResourcesUtils
import kotlinx.android.synthetic.main.map_add_spot.*


private const val LOCATION_PERMISSION_REQUEST_CODE = 1

class MapAddSpot : Fragment(), OnMapReadyCallback {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    private var navController: NavController? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var viewModel: MapsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.map_add_spot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)
        navController = Navigation.findNavController(view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        getLocation()


        /**
         * se carga mapa para elegir ubicacion
         */
        mapView = view.findViewById(R.id.mapAddLocation) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)


        addButton.setOnClickListener {
            viewModel.setLatLong(lastLocation.latitude , lastLocation.longitude)
            navController!!.navigate(R.id.action_mapAddSpot_to_newLocation)
        }

        cancelButton.setOnClickListener {
           navController!!.navigate(R.id.action_mapAddSpot_to_goSkateMap)
        }

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()){
            if(it != null){
              lastLocation = it
                val currentLatLong = LatLng(it.latitude , it.longitude)
                mMap.addMarker(MarkerOptions().icon(ResourcesUtils.getBitmapDescriptor(R.drawable.ic_geo_touch_positition, this.requireContext())).position(currentLatLong))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong , 17f))

            }
        }



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
        }
    }



}
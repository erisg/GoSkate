package go.skatebogota.goskate.ui.map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import go.skatebogota.goskate.R
import go.skatebogota.goskate.data.models.Spot
import kotlinx.android.synthetic.main.maps_go_skate.*


class MapsGoSkate : Fragment() , OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private var mapView: MapView? = null
    private var navController: NavController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.maps_go_skate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        // SE CARGA MAPA
        mapView = view.findViewById(R.id.mapView) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)

        newPostFloatingActionButton.setOnClickListener {
            navController!!.navigate(R.id.action_goSkateMap_to_newLocation)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!


        try {
            val success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this.context,R.raw.mapstyle)
            )
            if (!success) {
                Log.e("oo", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("oo", "Can't find style. Error: ", e)
        }

        val spots = mutableListOf<Spot>()

        spots.add(
            Spot(
                latitude = 4.756754,
                longitude = -74.110558,
                spotTittle = "FONTANAR DEL RIO"
            )
        )
        spots.add(Spot(latitude = 4.622893, longitude = -74.172965, spotTittle = "LAS MARGARITAS"))
        spots.add(Spot(latitude = 4.571505, longitude = -74.136245, spotTittle = "TUNAL"))
        spots.add(Spot(latitude = 4.597686, longitude = -74.081089, spotTittle = "TERCER MILENIO"))

        spots.forEach {
            val place = LatLng(it.latitude, it.longitude)
            var zoomLevel = 11.1f
            mMap.addMarker(MarkerOptions().position(place).title(it.spotTittle))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoomLevel))
        }



    }


}

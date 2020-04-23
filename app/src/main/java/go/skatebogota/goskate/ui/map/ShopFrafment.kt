package go.skatebogota.goskate.ui.map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import go.skatebogota.goskate.R

class ShopFrafment : Fragment(), View.OnClickListener, OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    var navController: NavController? = null
    private lateinit var mMap: GoogleMap
    private var mapView: MapView? = null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        mapView = view.findViewById(R.id.mapView2) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)
    }

    override fun onClick(v: View?) {

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.setOnMarkerClickListener(this)

        try {
            val success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this.context, R.raw.mapstyleshop)
            )
            if (!success) {
                Log.e("oo", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("oo", "Can't find style. Error: ", e)
        }

        val place = LatLng(4.597686, -74.081089)
        val zoomLevel = 11.1f
        mMap.addMarker(MarkerOptions().position(place).title("BOGOTA"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoomLevel))
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
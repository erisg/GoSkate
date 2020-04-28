package go.skatebogota.goskate.ui.map


import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import go.skatebogota.goskate.R
import go.skatebogota.goskate.util.adapters.RecyclerImagesSpot
import go.skatebogota.goskate.util.interfaces.IMenuGone
import kotlinx.android.synthetic.main.new_location.*
import java.util.*

class NewLocation : Fragment(), IMenuGone, OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    private lateinit var adapter: RecyclerImagesSpot
    val galeryMutableList = mutableListOf<Uri>()
    val mutableData = MutableLiveData<MutableList<Uri>>()
    var uri: Uri? = null
    var place = LatLng(4.597686, -74.081089)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapAddLocation) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)

        adapter = RecyclerImagesSpot(this.context!!)
        galeryRecyclerView.layoutManager = LinearLayoutManager(this.context!!)
        galeryRecyclerView.adapter = adapter

        addPicPost.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data

            galeryMutableList.add(uri!!)
            mutableData.value = galeryMutableList

            mutableData.observe(viewLifecycleOwner, Observer {
                adapter.setListData(it)
                adapter.notifyDataSetChanged()
            })
        }
    }

    override fun goneMenu(navigation: BottomNavigationView) {
        navigation.visibility = View.GONE
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isScrollGesturesEnabled = false
        mMap.setOnMarkerClickListener(this)

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



        val zoomLevel = 13.1f
        mMap.addMarker(MarkerOptions().position(place).title("BOGOTA").draggable(true))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoomLevel))
        mMap.setOnMarkerDragListener(this)
    }


    override fun onMarkerDragEnd(marker: Marker?) {
        if (marker!!.equals(place)) {
            Toast.makeText(this.context, "START", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onMarkerDragStart(marker: Marker?) {
        if (marker!!.equals(place)) {
            Toast.makeText(this.context, "START", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onMarkerDrag(marker: Marker?) {
        if (marker!!.equals(place)) {
            val newTitle = String.format(
                Locale.getDefault(),
                getString(R.string.add_place_ubication),
                marker.position.latitude,
                marker.position.longitude
            );

            Toast.makeText(this.context, "$newTitle", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker?.title != null) {
            marker.isDraggable
        }

        return false
    }
}
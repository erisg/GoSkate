package go.skatebogota.goskate.ui.map


import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import go.skatebogota.goskate.R
import go.skatebogota.goskate.util.adapters.RecyclerImagesSpot
import go.skatebogota.goskate.util.interfaces.IMenuGone
import kotlinx.android.synthetic.main.new_location.*
import java.lang.Exception
import java.util.*
import java.util.jar.Manifest

class NewLocation : Fragment(), IMenuGone, OnMapReadyCallback,
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnCameraMoveCanceledListener,
    GoogleMap.OnCameraIdleListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    private lateinit var adapter: RecyclerImagesSpot
    val galeryMutableList = mutableListOf<Uri>()
    val mutableData = MutableLiveData<MutableList<Uri>>()
    var uri: Uri? = null
    var place = LatLng(4.597686, -74.081089)
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private lateinit var addressList: List<Address>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //MAPA
        mapView = view.findViewById(R.id.mapAddLocation) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)


        //ADAPTER DE LAS IMAGENES
        adapter = RecyclerImagesSpot(this.context!!)
        galeryRecyclerView.layoutManager = LinearLayoutManager(this.context!!)
        galeryRecyclerView.adapter = adapter


        //ABRE LA GALERIA PARA SELECCIONAR UNA IMAGEN
        addPicPost.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        newLocationMapSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var location: String = newLocationMapSearchView.query.toString()
                if (location == ""){
                    var geocoder = Geocoder(context)
                    try {
                        addressList = geocoder.getFromLocationName( location , 1 )
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    var adress : Address = addressList[0]
                    val place = LatLng(adress.latitude, adress.longitude)
                    var zoomLevel = 11.1f
                    mMap.addMarker(MarkerOptions().position(place))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoomLevel))
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
            )
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
        with(googleMap) {
            setOnCameraIdleListener(this@NewLocation)
            setOnCameraMoveStartedListener(this@NewLocation)
            setOnCameraMoveListener(this@NewLocation)
            setOnCameraMoveCanceledListener(this@NewLocation)


            uiSettings.isZoomControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = true

            // Show Sydney
            moveCamera(CameraUpdateFactory.newLatLngZoom(place, 10f))
        }

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

            Toast.makeText(this.context, newTitle, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker?.title != null) {
            marker.isDraggable
        }

        return false
    }

    override fun onCameraMoveStarted(p0: Int) {

    }

    override fun onCameraMove() {

    }

    override fun onCameraMoveCanceled() {
    }

    override fun onCameraIdle() {
    }
}
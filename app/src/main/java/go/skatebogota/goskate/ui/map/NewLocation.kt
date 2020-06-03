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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
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

class NewLocation : Fragment(), IMenuGone, OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener  {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    var uri: Uri? = null
    var place = LatLng(4.597686, -74.081089)
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private lateinit var addressList: List<Address>
    private var navController: NavController? = null
    var ratingValue = 0.0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //MAPA
        mapView = view.findViewById(R.id.mapAddLocation) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)

        chargeSpinnerData()
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingValue = rating
        }

        saveLocationBtn.setOnClickListener {
            Toast.makeText(this.context, ratingValue.toString(),Toast.LENGTH_LONG).show()
        }

        addPicPost.setOnClickListener {
            navController!!.navigate(R.id.action_newLocation_to_newPostImageVideoGallery)
        }


        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 1){
                    categoryTextView.visibility = View.VISIBLE
                    restrictionConstraintLayout.visibility = View.VISIBLE
                }else{
                    categoryTextView.visibility = View.GONE
                    restrictionConstraintLayout.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        houramSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
               when(position){
                   0 ->{
                    //   houramVTextView.text = "05:00am"
                   }
                   1 ->{
                 //      houramVTextView.text = "06:00am"
                   }
                   2 ->{
                  //     houramVTextView.text = "07:00am"
                   }
                   3 ->{
                   //    houramVTextView.text = "08:00am"
                   }
                   4 ->{
                   //    houramVTextView.text = "09:00am"
                   }
               }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        hourPmspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(position){
                    0 ->{
                    //    hourpmVTextView.text = "05:00pm"
                    }
                    1 ->{
                     //   hourpmVTextView.text = "06:00pm"
                    }
                    2 ->{
                     //   hourpmVTextView.text = "07:00am"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(position){
                    0 ->{
                     //   dayImpTextView.text = "DIA PAR SKATE"
                    }
                    1 ->{
                    //    dayImpTextView.text = "DIA IMPAR BIKE"
                    }
                    2 ->{
                    //    dayImpTextView.text = "DIA PAR BIKE"
                    }
                    3 ->{
                     //   dayImpTextView.text = "DIA IMPAR SKATE"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        newLocationMapSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val location: String = newLocationMapSearchView.query.toString()
                if (location == ""){
                    val geocoder = Geocoder(context)
                    try {
                        addressList = geocoder.getFromLocationName( location , 1 )
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    val adress : Address = addressList[0]
                    val place = LatLng(adress.latitude, adress.longitude)
                    val zoomLevel = 11.1f
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



    fun chargeSpinnerData(){


        ArrayAdapter.createFromResource(this.requireContext(),
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            categorySpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this.requireContext(),
            R.array.hour_am_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            houramSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this.requireContext(),
            R.array.hour_pm_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            hourPmspinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this.requireContext(),
            R.array.days_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            daySpinner.adapter = adapter
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data


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


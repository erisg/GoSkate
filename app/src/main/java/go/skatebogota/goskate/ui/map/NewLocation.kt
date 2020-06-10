package go.skatebogota.goskate.ui.map


import android.app.Activity
import android.app.AlertDialog
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
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import go.skatebogota.goskate.R
import go.skatebogota.goskate.util.interfaces.IMenuGone
import kotlinx.android.synthetic.main.new_location.*
import kotlinx.android.synthetic.main.search_new_message.view.*
import java.util.*

class NewLocation : Fragment(), IMenuGone, OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    var uri: Uri? = null
    var place = LatLng(4.597686, -74.081089)
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private lateinit var addressList: List<Address>
    private var navController: NavController? = null
    var ratingValue = 0.0f
    val EXTRA_LATITUD = "Latitud"
    val EXTRA_LONGITUD = "Longitud"
    var marker : Marker? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
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
            Toast.makeText(this.context, ratingValue.toString(), Toast.LENGTH_LONG).show()
        }

        addPicPost.setOnClickListener {
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.popup_gallery_cam_video, null)
            val mBuilder = AlertDialog.Builder(this.context).setView(mDialogView)
            val mAlertDialog = mBuilder.show()


        }


        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position == 1) {
                    categoryTextView.visibility = View.VISIBLE
                    restrictionConstraintLayout.visibility = View.VISIBLE
                } else {
                    categoryTextView.visibility = View.GONE
                    restrictionConstraintLayout.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        houramSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        //   houramVTextView.text = "05:00am"
                    }
                    1 -> {
                        //      houramVTextView.text = "06:00am"
                    }
                    2 -> {
                        //     houramVTextView.text = "07:00am"
                    }
                    3 -> {
                        //    houramVTextView.text = "08:00am"
                    }
                    4 -> {
                        //    houramVTextView.text = "09:00am"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        hourPmspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        //    hourpmVTextView.text = "05:00pm"
                    }
                    1 -> {
                        //   hourpmVTextView.text = "06:00pm"
                    }
                    2 -> {
                        //   hourpmVTextView.text = "07:00am"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    3 -> {
                     //   infoOtherEditText.visibility = View.VISIBLE
                    }
                    4 -> {
                      //  infoOtherEditText.visibility = View.VISIBLE
                    }
                    else -> {
                     //   infoOtherEditText.visibility = View.GONE
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
                if (location == "") {
                    val geocoder = Geocoder(context)
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    val adress: Address = addressList[0]
                    val place = LatLng(adress.latitude, adress.longitude)
                    val zoomLevel = 11.1f
                    mMap.addMarker(MarkerOptions().position(place).draggable(true))
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

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.setOnMarkerDragListener(this)

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

        var cali =LatLng(3.4383, -76.5161)
        googleMap.addMarker(MarkerOptions().position(cali).title("CALI ES CALI"))
        var cameraPosition = CameraPosition.builder()
            .target(cali)
            .zoom(10f)
            .build()

        mMap.moveCamera(CameraUpdateFactory.newLatLng(cali))

    }
    fun chargeSpinnerData() {

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            categorySpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.hour_am_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            houramSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.hour_pm_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            hourPmspinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this.requireContext(),
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



    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker == marker) {
            val intent = Intent(this.context, NewLocation::class.java)
            intent.putExtra(EXTRA_LATITUD, marker!!.position.latitude)
            intent.putExtra(EXTRA_LONGITUD, marker.position.longitude)
            startActivity(intent)
        }
        return false
    }



    override fun onMarkerDragEnd(p0: Marker?) {
        Toast.makeText(this.context , p0.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onMarkerDragStart(p0: Marker?) {
        Toast.makeText(this.context , "START", Toast.LENGTH_LONG).show()
    }

    override fun onMarkerDrag(p0: Marker?) {
        Toast.makeText(this.context , "END", Toast.LENGTH_LONG).show()
    }


}


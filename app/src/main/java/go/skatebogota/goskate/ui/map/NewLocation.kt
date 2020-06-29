package go.skatebogota.goskate.ui.map


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.viewmodels.MapsViewModel
import go.skatebogota.goskate.util.adapters.RecyclerGalerySpot
import go.skatebogota.goskate.util.interfaces.IMenuGone
import kotlinx.android.synthetic.main.new_location.*
import kotlinx.android.synthetic.main.popup_gallery_cam_video.view.*

class NewLocation : Fragment(), IMenuGone, OnMapReadyCallback {

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap
    val galeryMutableList = mutableListOf<Uri>()
    val mutableData = MutableLiveData<MutableList<Uri>>()
    var uri: Uri? = null
    private var navController: NavController? = null
    var ratingValue = 0.0f
    val VIDEO: Int = 3
    val PICK_IMAGE_CODE = 1234
    private val viewModelMaps: MapsViewModel by lazy { MapsViewModel.getGeoAccidentManagementModViewModel(fragment = this)!! }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        /**
         * se carga mapa para elegir ubicacion
         */
        mapView = view.findViewById(R.id.mapAddLocation) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)

        /**
         * calificacion del spot
         */
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingValue = rating
        }

        /**
         * se valida si esta completa la informacion
         *  se carga info al viewmodel
         */

        saveLocationBtn.setOnClickListener {
            validateInfoComplete()
        }

        /**
         * se abre popup para elegir cam , gallery , video
         */

        addPicPost.setOnClickListener {
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.popup_gallery_cam_video, null)
            val mBuilder = AlertDialog.Builder(this.context).setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            mDialogView.camImageView.setOnClickListener {
                camChooser()
                mAlertDialog.dismiss()
            }

            mDialogView.galleryImageView.setOnClickListener {
                imageFileChooser()
                mAlertDialog.dismiss()
            }
            mDialogView.videoImageView.setOnClickListener {
                videoFileChooser()
                mAlertDialog.dismiss()
            }

        }


        chargeSpinnerData()

        /**
         * listener de restricciones
         */

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
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

        /**
         * listener horario am
         */

        houramSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                          "05:00am"
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
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
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
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
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

    }


    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

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
        val spot = viewModelMaps.getLatLong()
        spot
//        val location = LatLng(viewModel.spotVO.latitude! !, viewModel.spotVO.longitude!!)
//        googleMap.addMarker(MarkerOptions().position(location).title("CALI ES CALI"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location , 17f))

    }

    private fun imageFileChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "SELECCIONA IMAGEN"), PICK_IMAGE_CODE)
    }

    private fun camChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_CAMERA_BUTTON
        startActivityForResult(Intent.createChooser(intent, "SELECCIONA IMAGEN"), PICK_IMAGE_CODE)
    }

    private fun videoFileChooser() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECCIONE VIDEO"), VIDEO)
    }


    /**
     * Carga spinners con informacion de restricciones
     */

    fun chargeSpinnerData() {

        ArrayAdapter.createFromResource(this.requireContext(), R.array.categories_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this.requireContext(), R.array.hour_am_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            houramSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this.requireContext(), R.array.hour_pm_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hourPmspinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this.requireContext(), R.array.days_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            daySpinner.adapter = adapter
        }
    }


    /**
     *  Se cargan las imagenes al recycler
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            uri = data.data
            galeryMutableList.add(uri!!)
            mutableData.value = galeryMutableList

            mutableData.observeForever {
                it?.let { photosRecyclerView.adapter = RecyclerGalerySpot(this.requireContext() , it.toList())
                }
            }
        } else
            if (requestCode == VIDEO && resultCode == Activity.RESULT_OK) {
                galeryMutableList.add(uri!!)
                mutableData.value = galeryMutableList

                mutableData.observeForever {
                    it?.let { photosRecyclerView.adapter = RecyclerGalerySpot(this.requireContext() , it.toList())
                    }
                }

            }
        photosRecyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        photosRecyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL ,false)
    }


    /**
     * Valida si la informacion esta completa
     */

    fun validateInfoComplete(){
        navController!!.navigate(R.id.action_newLocation_to_goSkateMap)
        Toast.makeText(this.context, ratingValue.toString(), Toast.LENGTH_LONG).show()
    }


    /**
     *  TODO  esconder navigation view
     */

    override fun goneMenu(navigation: BottomNavigationView) {
        navigation.visibility = View.GONE
    }



}


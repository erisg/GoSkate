package go.skatebogota.goskate.ui.map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MapStyleOptions
import go.skatebogota.goskate.R
import kotlinx.android.synthetic.main.new_location.*

class NewLocation : Fragment() , OnMapReadyCallback , GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener{

    private var mapView: MapView? = null
    private lateinit var mMap: GoogleMap

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

    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!


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



    }

    override fun onCameraIdle() {
        user_location.animate().translationY(0F)

    }

    override fun onCameraMoveStarted(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
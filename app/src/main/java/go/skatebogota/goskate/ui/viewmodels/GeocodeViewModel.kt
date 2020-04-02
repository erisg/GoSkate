package go.skatebogota.goskate.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import go.skatebogota.goskate.data.repositories.GeocodeRepository
import go.skatebogota.goskate.util.mapUtil.GeocodeResponse

class GeocodeViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<GeocodeResponse>? = null
    private var newsRepository: GeocodeRepository? = null


    fun geocode(target: LatLng): MutableLiveData<GeocodeResponse> {
        newsRepository = GeocodeRepository()
        //    mutableLiveData = newsRepository!!.getGeocode("${target.latitude},${target.longitude}")
        return mutableLiveData!!
    }

}
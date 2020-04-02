package go.skatebogota.goskate.data.repositories


import android.telecom.Call
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.Response
import go.skatebogota.goskate.util.mapUtil.GeocodeResponse
import javax.security.auth.callback.Callback


class GeocodeRepository {
//
//    private var geocodeApi: IServiceCatalog? = null
//
//    init {
//        geocodeApi = TracesRest.getRetrofitClientGoogle()?.create(IServiceCatalog::class.java)
//    }
//
//    fun getGeocode(coordinates: String): MutableLiveData<GeocodeResponse> {
//        val newsData = MutableLiveData<GeocodeResponse>()
//        geocodeApi!!.geocodeAddress(coordinates).enqueue(object : Callback<GeocodeResponse> {
//            override fun onFailure(call: Call<GeocodeResponse>, t: Throwable) {
//                newsData.value = null
//            }
//            override fun onResponse(call: Call<GeocodeResponse>, response: Response<GeocodeResponse>) {
//                if (response.isSuccessful){
//                    newsData.value = response.body()
//                }
//             }
//        })
//        return newsData
//    }


}
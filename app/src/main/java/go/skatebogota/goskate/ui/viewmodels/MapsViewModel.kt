package go.skatebogota.goskate.ui.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.model.LatLng
import go.skatebogota.goskate.data.models.SpotVO
import go.skatebogota.goskate.data.repositories.GeocodeRepository
import go.skatebogota.goskate.util.mapUtil.GeocodeResponse

class MapsViewModel(@NonNull application: Application) : AndroidViewModel(application){

     var spotVO = SpotVO()

    fun setLatLong(lat: Double, long: Double): SpotVO{
        this.spotVO.longitude = long
        this.spotVO.latitude = lat
        return this.spotVO
    }

    fun getLatLong() = this.spotVO

    companion object {

        private var INSTANCE: MapsViewModel? = null

        fun getGeoAccidentManagementModViewModel(fragment: Fragment? = null, fragmentActivity: FragmentActivity? = null): MapsViewModel? {
            if (INSTANCE == null) {
                INSTANCE = when (fragment != null) {
                    true -> ViewModelProviders.of(fragment).get(MapsViewModel::class.java)
                    false -> ViewModelProviders.of(fragmentActivity!!).get(MapsViewModel::class.java)
                }
            }
            return INSTANCE
        }
    }

}
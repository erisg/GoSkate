package go.skatebogota.goskate.ui.viewmodels

import androidx.lifecycle.*
import go.skatebogota.goskate.data.models.SpotVO
import go.skatebogota.goskate.data.models.UserVO
import go.skatebogota.goskate.data.repositories.MapRepository

class MapsViewModel : ViewModel(){

    private val mapRepository = MapRepository()
     var spotVO = SpotVO()

    fun setLatLong(lat: Double, long: Double): SpotVO{
        this.spotVO.longitude = long
        this.spotVO.latitude = lat
        return this.spotVO
    }

    fun getLatLong(): SpotVO {
        return this.spotVO
    }

    fun setInfoSpot(nameSpot:String , score:String , comment: String):  MutableLiveData<String>{
        val mutableData = MutableLiveData<String>()
        this.spotVO.spotTittle = nameSpot
        this.spotVO.score = score.toFloat()
        this.spotVO.comments = comment
        mapRepository.getInfoSpot(this.spotVO).observeForever { response ->
            mutableData.value = response
        }
        return mutableData
    }

}
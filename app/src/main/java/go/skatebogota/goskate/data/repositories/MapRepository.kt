package go.skatebogota.goskate.data.repositories


import android.net.Uri
import android.telecom.Call
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.Response
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.data.models.SpotVO
import go.skatebogota.goskate.util.mapUtil.GeocodeResponse
import java.util.*
import javax.security.auth.callback.Callback


class MapRepository {

    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var refDataBaseUserPost: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Spots")


    fun getInfoSpot(spotVO: SpotVO): MutableLiveData<String> {
        val uriList  = MutableLiveData<Uri>()
        val userMap = HashMap<String, Any>()
        spotVO.spotId =  UUID.randomUUID().toString()
        val mutableDataResponse = MutableLiveData<String>()
        val refStorage = storage.getReference(spotVO.spotTittle+"/" + UUID.randomUUID().toString())
        spotVO.gallerySpot?.forEach { it ->
            refStorage.putFile(it).addOnSuccessListener {
                refStorage.downloadUrl.addOnSuccessListener { uri->
                   uriList.value = uri } } }
        userMap["spotId"] = spotVO.spotId
        userMap["latitude"] = spotVO.latitude.toString()
        userMap["longitude"] = spotVO.longitude.toString()
        userMap["spotTittle"] = spotVO.spotTittle.toString()
        userMap["category"] = spotVO.category.toString()
        userMap["hourAm"] = spotVO.hourAm.toString()
        userMap["hourPm"] = spotVO.hourPm.toString()
        userMap["days"] = spotVO.days.toString()
        userMap["otherRestriction"] = spotVO.otherRestriction.toString()
        userMap["score"] = spotVO.score
        userMap["comments"] = spotVO.comments

        refDataBaseUserPost.child(spotVO.spotId).setValue(userMap).addOnCompleteListener { task ->
                val message = task.exception?.toString()
                if (task.isSuccessful) {
                    mutableDataResponse.value = "Successful"
                } else {
                    mutableDataResponse.value = message!!
                }
        }

        return mutableDataResponse
    }


}
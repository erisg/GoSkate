package go.skatebogota.goskate.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.data.models.UserVO
import go.skatebogota.goskate.data.repositories.RepositoryContent

class ViewModelContent : ViewModel() {

    private val repositoryContent = RepositoryContent()
    var postVO: PostVO = PostVO()
    var userVO : CharSequence? = UserVO().userName
    var currentUser = repositoryContent.auth
    val mutableData = MutableLiveData<List<PostVO>>()

    /**
     * se carga el objeto con la info del post
     */

    fun upLoadImagePost(userImagePost: Uri?, description: String, userPlace: String, filePath: String): LiveData<String> {
        postVO.imagePost = userImagePost.toString()
        postVO.description = description
        postVO.spot = userPlace
        postVO.type = filePath
        postVO.idUser = repositoryContent.auth.currentUser!!.uid
        return repositoryContent.upLoadImagePost(postVO)
    }


    /**
     * Get post from firebase
     */

    fun getInfoPost() {
        repositoryContent.getDataPost().observeForever { userPost ->
            mutableData.value = userPost
        }

    }

    /**
     * Get all users for new message
     */

    fun getAllUsersName(): MutableLiveData<List<UserVO>> {
        val mutableData = MutableLiveData<List<UserVO>>()
        repositoryContent.getAllUsersName().observeForever { userPost ->
            mutableData.value = userPost
        }
        return mutableData
    }


    /**
     * se carga info del spot
     */

    fun uploadSpot(){

    }
}
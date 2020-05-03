package go.skatebogota.goskate.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.data.repositories.RepositoryContent

class ViewModelContent : ViewModel() {

    private val repositoryContent = RepositoryContent()
    var postVO: PostVO = PostVO()
    var currentUser = repositoryContent.auth
    val mutableData = MutableLiveData<List<PostVO>>()
    val firebaseResponse : String? = null


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


}
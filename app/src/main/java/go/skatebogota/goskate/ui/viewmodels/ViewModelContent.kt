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


    fun upLoadImagePost(userImagePost: Uri? , description:String , userPlace:String) {
        postVO.imagePost = userImagePost
        postVO.description = description
        postVO.spot = userPlace
        repositoryContent.upLoadImagePost(postVO)
    }

    /**
     * Trae la respuesta
     */

    fun getFirebaseResponseImagePost() = repositoryContent.userResponse

    /**
     * Get post from firebase
     */

    fun getInfoPost(): LiveData<MutableList<PostVO>> {
        val mutableData = MutableLiveData<MutableList<PostVO>>()
        repositoryContent.getDataPost().observeForever { userPost ->
            mutableData.value = userPost
        }
        return mutableData
    }


}
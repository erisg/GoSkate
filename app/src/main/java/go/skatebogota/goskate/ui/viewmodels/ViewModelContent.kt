package go.skatebogota.goskate.ui.viewmodels

import android.app.Application
import android.net.Uri
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import go.skatebogota.goskate.data.models.PostVO
import go.skatebogota.goskate.data.repositories.RepositoryContent

class ViewModelContent : ViewModel() {

    private val repositoryContent = RepositoryContent()
    var postVO: PostVO = PostVO()
    var currentUser = repositoryContent.auth


    fun upLoadImagePost(userImagePost: Uri? , description:String , userPlace:String) {
        postVO.userFilePath = userImagePost
        postVO.description = description
        postVO.spot = userPlace
        repositoryContent.upLoadImagePost(postVO)
    }

    /**
     * Trae la respuesta
     */
    fun getFirebaseResponseImagePost() = repositoryContent.userResponse


    fun getImagePost() = repositoryContent.getImagePost().downloadUrl


}
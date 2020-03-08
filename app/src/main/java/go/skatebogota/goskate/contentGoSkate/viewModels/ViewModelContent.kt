package go.skatebogota.goskate.contentGoSkate.viewModels

import android.app.Application
import android.net.Uri
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import go.skatebogota.goskate.contentGoSkate.repository.RepositoryContent

class ViewModelContent(@NonNull application: Application) : AndroidViewModel(application) {

    private val repositoryContent = RepositoryContent(application)


    fun upLoadImagePost(filePath: Uri?){
      repositoryContent.upLoadImagePost(filePath)
    }

    fun getFirebaseResponseImagePost() = repositoryContent.response



    companion object {
        private var INSTANCE: ViewModelContent? = null
        fun getViewModelContent(fragment: Fragment? = null): ViewModelContent? {
            INSTANCE = ViewModelProvider(fragment!!).get(ViewModelContent::class.java)
            return INSTANCE
        }
    }
}
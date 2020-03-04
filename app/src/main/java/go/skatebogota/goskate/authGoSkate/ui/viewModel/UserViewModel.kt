package go.skatebogota.goskate.authGoSkate.ui.viewModel

import android.app.Application
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import go.skatebogota.goskate.authGoSkate.ui.rpository.RepositoryUser

class UserViewModel(@NonNull application: Application) : AndroidViewModel(application) {

    private val repositoryUser = RepositoryUser(application)

    fun registerUser(email: String, password: String) {
        repositoryUser.registerUser(email, password)
    }

    fun getUserRegisterResponse() = repositoryUser.response

    fun loginUser(email: String, password: String){
        repositoryUser.loginUser(email , password)
    }

    fun getUserLoginResponse() = repositoryUser.response


    companion object {

        private var INSTANCE: UserViewModel? = null

        fun getUserViewModel(CompatActivity: AppCompatActivity? = null): UserViewModel? {

            INSTANCE = ViewModelProvider(CompatActivity!!).get(UserViewModel::class.java)

            return INSTANCE
        }
    }
}
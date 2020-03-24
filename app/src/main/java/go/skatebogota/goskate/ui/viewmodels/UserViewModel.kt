package go.skatebogota.goskate.ui.ui.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import go.skatebogota.goskate.data.repositories.RepositoryUser
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister

class UserViewModel : ViewModel() {

    private val repositoryUser =
        RepositoryUser()
    var authListener : AuthListenerResponseUserRegister? = null

    //login data

    var email:String? = null
    var password:String? = null

    fun onLoginButtonClick(view : View){
        if(email.isNullOrBlank() && password.isNullOrBlank()){
            authListener?.onFailure()
            return
        }
        authListener?.onSuccess()
        repositoryUser.registerUser(this.email!!, this.password!!)
    }


    fun registerUser(email: String, password: String) {
        repositoryUser.registerUser(email, password)
    }

    fun getUserRegisterResponse() = repositoryUser.response

    fun loginUser(email: String, password: String){
        repositoryUser.loginUser(email , password)
    }

    fun getUserLoginResponse() = repositoryUser.response


    /**
     * SE GUARDA EL RESTO DE LA INFORMACION DEL PERFIL
     */

    fun saveInfoUser(userName :String , userEmail :String , userPassword :String , ageUser :String){
        repositoryUser.saveInfoUser(userName , userEmail ,userPassword ,ageUser)
    }
}
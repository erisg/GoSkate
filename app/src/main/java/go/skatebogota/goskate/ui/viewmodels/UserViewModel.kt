package go.skatebogota.goskate.ui.viewmodels


import android.net.Uri
import androidx.lifecycle.ViewModel
import go.skatebogota.goskate.data.models.UserVO
import go.skatebogota.goskate.data.repositories.RepositoryUser
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister

class UserViewModel : ViewModel() {

    private val repositoryUser = RepositoryUser()
    var authListener : AuthListenerResponseUserRegister? = null
    var userVO:UserVO = UserVO()
    var currentUser = repositoryUser.auth.currentUser

    //login data

    var email:String? = null
    var password:String? = null


    fun registerUser(
        imageProfile: Uri?,
        userName: String,
        userEmail: String,
        userPassword: String,
        ageUser: String,
        sexUser: String
    ) {
        userVO.imageProfile = imageProfile
        userVO.userName = userName
        userVO.userEmail = userEmail
        userVO.password = userPassword
        userVO.birthDate = ageUser
        userVO.sex = sexUser
        repositoryUser.registerUser(userVO)
    }

    fun getUserRegisterResponse() = repositoryUser.userResponse

    fun loginUser(email: String, password: String){
        userVO.userEmail = email
        userVO.password = password
        repositoryUser.loginUser(userVO)
    }


}
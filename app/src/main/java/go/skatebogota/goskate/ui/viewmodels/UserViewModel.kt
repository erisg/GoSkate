package go.skatebogota.goskate.ui.viewmodels


import androidx.lifecycle.ViewModel
import go.skatebogota.goskate.data.models.UserVO
import go.skatebogota.goskate.data.repositories.RepositoryUser
import go.skatebogota.goskate.util.interfaces.AuthListenerResponseUserRegister

class UserViewModel : ViewModel() {

    private val repositoryUser = RepositoryUser()
    var authListener : AuthListenerResponseUserRegister? = null
    var userVO:UserVO = UserVO()

    //login data

    var email:String? = null
    var password:String? = null


    fun registerUser(email: String, password: String) {
        userVO.userEmail = email
        userVO.password = password
        repositoryUser.registerUser(userVO)
    }

    fun getUserRegisterResponse() = repositoryUser.userResponse

    fun loginUser(email: String, password: String){
        userVO.userEmail = email
        userVO.password = password
        repositoryUser.loginUser(userVO)
    }

    fun getUserLoginResponse() = repositoryUser.response


    /**
     * SE GUARDA EL RESTO DE LA INFORMACION DEL PERFIL
     */

    fun saveInfoUser(userName :String , userEmail :String , userPassword :String , ageUser :String){
        userVO.userName = userName
        userVO.userEmail = userEmail
        userVO.password = userPassword
        userVO.birthDate = ageUser
        repositoryUser.saveInfoUser(userVO)
    }
}
package go.skatebogota.goskate.util.interfaces

interface AuthListenerResponseUserRegister {

    fun onSuccess() : String
    fun onFailure() : String
}
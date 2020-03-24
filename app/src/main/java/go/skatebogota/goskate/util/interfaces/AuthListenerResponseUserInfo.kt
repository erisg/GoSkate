package go.skatebogota.goskate.util.interfaces

interface AuthListenerResponseUserInfo {
    fun onSuccessUser(): String
    fun onFailureUser(): String
}
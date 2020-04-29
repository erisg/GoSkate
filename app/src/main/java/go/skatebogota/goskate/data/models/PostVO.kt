package go.skatebogota.goskate.data.models

import android.net.Uri


data class PostVO(
    var idPost: String? = null,
    var idUser: String? = null,
    var imagePost: String? = null,
    var description: String? = null,
    var type: String? = null,
    var datePost: String? = null,
    var likes: Int = 0,
    var spot: String? = null
)
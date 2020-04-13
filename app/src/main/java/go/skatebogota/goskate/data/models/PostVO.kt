package go.skatebogota.goskate.data.models

import android.net.Uri


data class PostVO(
    var imagePost: Uri? = null,
    var description: String? = null,
    var spot: String? = null
)
package go.skatebogota.goskate.data.models

import android.graphics.drawable.Drawable
import android.net.Uri

class PostVO(
    var postId: String? = null,
    var postFilePath: Uri? = null,
    var description: String? = null,
    var spot: String? = null,
    var spotImage: String? = null
)
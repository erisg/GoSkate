package go.skatebogota.goskate.data.models

import android.net.Uri

class SpotVO(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var spotTittle: String,
    var category : String,
    var hourAm: String? = null,
    var hourPm: String? = null,
    var days: String? = null,
    var otherRestriction:String? = null,
    var score: Float,
    var commnents : String,
    var gallerySpot : List<Uri>

)
package go.skatebogota.goskate.data.models

import android.net.Uri

class SpotVO
{
    var latitude: Double? = null
    var longitude: Double? = null
    var spotTittle: String? = null
    var category : String? = null
    var hourAm: String? = null
    var hourPm: String? = null
    var days: String? = null
    var otherRestriction:String? = null
    var score: Float = 0f
    var comments : String = ""
    lateinit var gallerySpot : List<Uri>

    constructor()

    constructor(latitude: Double , longitude : Double){
        this.latitude = latitude
        this.longitude = longitude
    }
}
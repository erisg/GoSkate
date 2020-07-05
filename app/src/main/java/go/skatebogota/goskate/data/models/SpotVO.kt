package go.skatebogota.goskate.data.models

import android.net.Uri

class SpotVO
{
    var spotId: String = ""
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
    var gallerySpot : List<Uri>? = null

    constructor()

    constructor(latitude: Double , longitude : Double){
        this.latitude = latitude
        this.longitude = longitude
    }
}
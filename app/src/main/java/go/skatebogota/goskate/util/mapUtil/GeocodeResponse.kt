package go.skatebogota.goskate.util.mapUtil

data class GeocodeResponse(
    val plus_code: PlusCode,
    val results: List<Result>,
    val status: String
) {
    override fun toString(): String {
        return "GeocodeResponse(plus_code=$plus_code, results=$results, status='$status')"
    }
}

data class PlusCode(
    val compound_code: String,
    val global_code: String
)

data class Result(
    val address_components: List<AddressComponent>,
    val formatted_address: String,
    val geometry: Geometry,
    val place_id: String,
    val types: List<String>
)

data class AddressComponent(
    val long_name: String,
    val short_name: String,
    val types: List<String>


) {
    override fun toString(): String {
        return "AddressComponent(long_name='$long_name', short_name='$short_name', types=$types)"
    }
}

data class Geometry(
    val location: Location,
    val location_type: String,
    val viewport: Viewport
)

data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest
)

data class Southwest(
    val lat: Double,
    val lng: Double
)

data class Northeast(
    val lat: Double,
    val lng: Double
)

data class Location(
    val lat: Double,
    val lng: Double
)
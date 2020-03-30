package go.skatebogota.goskate.data.persistence


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LocationEntity.TABLE_NAME)
data class LocationEntity(@ColumnInfo(name = LONGITUDE_COLUMN_NAME)
                          var longitude: Double,

                          @ColumnInfo(name = LATITUDE_COLUMN_NAME)
                          var latitude: Double,

                          @ColumnInfo(name = ALTITUDE_COLUMN_NAME)
                          var altitude: Double? = null,

                          @ColumnInfo(name = TIME_LOCATION_COLUMN_NAME)
                          var timeLocation: String) {


    //@PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @ColumnInfo(name = ID_LOCATION_COLUMN_NAME)
    var localizationID: Int = 0

    companion object {
        const val ID_LOCATION_COLUMN_NAME = "ID_LOCATION"
        const val TABLE_NAME = "LOCALIZATION"
        const val ALTITUDE_COLUMN_NAME = "ALTITUDE"
        const val LONGITUDE_COLUMN_NAME = "LONGITUDE"
        const val LATITUDE_COLUMN_NAME = "LATITUDE"
        const val TIME_LOCATION_COLUMN_NAME = "TIME_LOCATION"
    }
}
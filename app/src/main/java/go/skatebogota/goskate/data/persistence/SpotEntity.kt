package go.skatebogota.goskate.data.persistence


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SpotEntity.TABLE_NAME)
class SpotEntity(
    //@PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @ColumnInfo(name = ID_SPOT_COLUMN_NAME)
    var spotId: String = "",

    @ColumnInfo(name = LAT_SPOT_COLUMN_NAME)
    var spotLat: String,

    @ColumnInfo(name = LONG_SPOT_COLUMN_NAME)
    var spotLong: String,

    @ColumnInfo(name = SPOT_COLUMN_NAME)
    var spot: String? = null,

    @ColumnInfo(name = DESCRIPTION_SPOT_COLUMN_NAME)
    var spotImage: String
) {


    companion object {
        const val ID_SPOT_COLUMN_NAME = "ID SPOT"
        const val TABLE_NAME = "SPOT"
        const val LAT_SPOT_COLUMN_NAME = "LAT SPOT"
        const val LONG_SPOT_COLUMN_NAME = "LONG SPOT"
        const val SPOT_COLUMN_NAME = "DESCRIPTION"
        const val DESCRIPTION_SPOT_COLUMN_NAME = "SPOT IMAGE"
    }
}
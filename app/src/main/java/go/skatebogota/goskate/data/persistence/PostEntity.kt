package go.skatebogota.goskate.data.persistence

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PostEntity.TABLE_NAME)
class PostEntity(
    //@PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @ColumnInfo(name = ID_LOCATION_COLUMN_NAME)
    var postId: String = "",

    @ColumnInfo(name = FILE_POST_COLUMN_NAME)
    var postFilePath: Uri,

    @ColumnInfo(name = DESCRIPTION_COLUMN_NAME)
    var description: String,

    @ColumnInfo(name = SPOT_COLUMN_NAME)
    var spot: String? = null,

    @ColumnInfo(name = SPOT_IMAGE_COLUMN_NAME)
    var spotImage: String
) {


    companion object {
        const val ID_LOCATION_COLUMN_NAME = "ID_POST"
        const val TABLE_NAME = "POST"
        const val SPOT_COLUMN_NAME = "SPOT"
        const val FILE_POST_COLUMN_NAME = "FILE POST"
        const val DESCRIPTION_COLUMN_NAME = "DESCRIPTION"
        const val SPOT_IMAGE_COLUMN_NAME = "SPOT IMAGE"
    }
}
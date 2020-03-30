package go.skatebogota.goskate.data.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
class UserEntity(
    @ColumnInfo(name = USER_NAME_COLUMN_NAME)
    var userName: String,

    @ColumnInfo(name = USER_EMAIL_COLUMN_NAME)
    var userEmail: String,

    @ColumnInfo(name = USER_PASSWORD_COLUMN_NAME)
    var password: String? = null,

    @ColumnInfo(name = USER_BIRTH_DATE_COLUMN_NAME)
    var birthDate: String,

    @ColumnInfo(name = USER_BIRTH_SEX_COLUMN_NAME)
    var sex: String
) {


    //@PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @ColumnInfo(name = ID_USER_COLUMN_NAME)
    var userId: Int = 0

    companion object {
        const val ID_USER_COLUMN_NAME = "ID_USER"
        const val TABLE_NAME = "USERS"
        const val USER_NAME_COLUMN_NAME = "USER_NAME"
        const val USER_EMAIL_COLUMN_NAME = "USER_EMAIL"
        const val USER_PASSWORD_COLUMN_NAME = "USER_PASSWORD"
        const val USER_BIRTH_DATE_COLUMN_NAME = "USER_BIRTH_DATE"
        const val USER_BIRTH_SEX_COLUMN_NAME = "USER_BIRTH_SEX"
    }
}
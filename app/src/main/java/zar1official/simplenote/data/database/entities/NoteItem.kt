package zar1official.simplenote.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class NoteItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "note_title") val title: String,
    @ColumnInfo(name = "note_text") val text: String,
    @ColumnInfo(name = "note_date") val date: Long
) : Parcelable

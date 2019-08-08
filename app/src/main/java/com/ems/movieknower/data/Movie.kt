package com.ems.movieknower.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Results(@SerializedName("results") val moviesResult: List<Movie>) : Serializable

@Entity(tableName = "favourite_movies_table")
data class Movie(
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    val id: String,

    @ColumnInfo
    @SerializedName("poster_path")
    val poster: String?,

    @ColumnInfo
    @SerializedName("backdrop_path")
    val backdrop: String?,

    @ColumnInfo
    @SerializedName("title")
    val title: String?,

    @ColumnInfo
    @SerializedName("release_date")
    val release: String?,

    @ColumnInfo
    @SerializedName("vote_average")
    val rating: String?,

    @ColumnInfo
    @SerializedName("vote_count")
    val voteCounting: String?,

    @ColumnInfo
    @SerializedName("overview")
    val synopsis: String?,

    @ColumnInfo
    val isFavorite: Boolean

) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString()!!,
        poster = parcel.readString(),
        backdrop = parcel.readString(),
        title = parcel.readString(),
        release = parcel.readString(),
        rating = parcel.readString(),
        voteCounting = parcel.readString(),
        synopsis = parcel.readString(),
        isFavorite = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(poster)
        dest?.writeString(backdrop)
        dest?.writeString(title)
        dest?.writeString(release)
        dest?.writeString(rating)
        dest?.writeString(voteCounting)
        dest?.writeString(synopsis)
        dest?.writeByte((if (isFavorite) 1 else 0).toByte())
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
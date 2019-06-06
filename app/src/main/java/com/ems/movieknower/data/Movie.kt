package com.ems.movieknower.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Results(@SerializedName("results") val moviesResult: List<Movie>) : Serializable

data class Movie(
    @SerializedName("id") val id: String?,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("backdrop_path") val backdrop: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("release_date") val release: String?,
    @SerializedName("vote_average") val rating: String?,
    @SerializedName("vote_count") val voteCounting: String?,
    @SerializedName("overview") val synopsis: String?,
    val isFavorite: Boolean
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString(),
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
package com.moetaz.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moetaz.data.Constants.MOVIES_TABLE_NAME
import com.moetaz.domain.models.Movie

@Entity(tableName = MOVIES_TABLE_NAME)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid") val uid : Int = 0,
    @ColumnInfo(name = "id") val id : Int ,
    @ColumnInfo(name = "originalTitle") val originalTitle : String ,
    @ColumnInfo(name = "posterPath") val posterPath : String ,
    @ColumnInfo(name = "releaseDate") val releaseDate : String ,
    @ColumnInfo(name = "originalLanguage") val originalLanguage : String ,
    @ColumnInfo(name = "overview") val overview : String ,
    @ColumnInfo(name = "voteAverage") val voteAverage : Float ,
    @ColumnInfo(name = "voteCount") val voteCount : Int ,
)

fun MovieEntity.toMovie() = Movie(
    id = id,
    title = originalTitle,
    posterUrl = posterPath,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    overview = overview,
    voteAverage = voteAverage,
    voteCount = voteCount

)

fun Movie.toMovieEntity() = MovieEntity(
    id = id,
    originalTitle = title,
    posterPath = posterUrl,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    overview = overview,
    voteAverage = voteAverage,
    voteCount = voteCount
)

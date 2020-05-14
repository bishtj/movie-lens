package com.data.ana.application

import com.data.ana.common.EitherTryHandler
import com.data.ana.domain.MovieLenError
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import cats.implicits._
import scala.util.Try

case class MovieRatingsAnalytics(movieDf: DataFrame, ratingDf: DataFrame) extends EitherTryHandler {

  def transform(): Either[MovieLenError, DataFrame] = {
    for {

      movieRatingStatsDf <- computeMovieRating()
      enrichMovieStatsDf <- enrichMovieData(movieRatingStatsDf)
      responseDf <- selectColumns(enrichMovieStatsDf)

    } yield responseDf


  }


  private def computeMovieRating(): Either[MovieLenError, DataFrame] = {
    for {

      moviesRatDf <- movieRatingJoin()
      aggRatingDf <- movieRatingAgg(moviesRatDf)

    } yield aggRatingDf


  }

  private def movieRatingJoin(): Either[MovieLenError, DataFrame] = {
    eitherR(
      Try {
        val newMovieDf = movieDf.withColumnRenamed("MovieID", "MovieIDNew")
        newMovieDf
          .join(ratingDf, newMovieDf("MovieIDNew") === ratingDf("MovieID"), "right_outer")
          .drop("MovieIDNew")
      }
    )
  }

  private def movieRatingAgg(df: DataFrame): Either[MovieLenError, DataFrame] = {
    eitherR(
      Try {
        df
          .select("MovieID", "Rating")
          .groupBy("MovieID")
          .agg(
            max(col("Rating")).as("MaxRating"),
            min(col("Rating")).as("MinRating"),
            avg(col("Rating")).as("AvgRating")
          )
      }
    )
  }

  private def enrichMovieData(ratingStatsdf: DataFrame): Either[MovieLenError, DataFrame] = {
    eitherR(
      Try {
        val newMovieDf = movieDf.withColumnRenamed("MovieID", "MovieIDNew")
        newMovieDf
          .join(ratingStatsdf, newMovieDf("MovieIDNew") === ratingStatsdf("MovieID"), "left_outer")
          .drop("MovieIDNew")
      }
    )
  }

  private def selectColumns(df: DataFrame): Either[MovieLenError, DataFrame] = {
    eitherR(
      Try {
        df.select(
          "MovieID",
          "Title",
          "Genres",
          "MaxRating",
          "MinRating",
          "AvgRating"
        )
      }
    )
  }


}

package com.data.ana.application

import com.data.ana.common.EitherTryHandler
import com.data.ana.domain.MovieLenError
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, TimestampType}
import cats.implicits._
import scala.util.Try

case object ratingNormalise extends EitherTryHandler {

  def apply(dfInput: DataFrame) : Either[MovieLenError, DataFrame] = {

    for {

      ratingDf <- normaliseRating(dfInput)
      tStampDf <- normaliseTimestamp(ratingDf)

    } yield tStampDf

  }

  private def normaliseRating(df: DataFrame) : Either[MovieLenError, DataFrame] = {
    eitherR(
      Try {
        df
          .withColumn("RatingNew", col("Rating").cast(IntegerType))
          .drop("Rating")
          .withColumnRenamed("RatingNew", "Rating")
      }
    )
  }

  private def normaliseTimestamp(df: DataFrame) : Either[MovieLenError, DataFrame] = {
    eitherR(
      Try {
        df
          .withColumn("TimestampNew", from_unixtime(col("Timestamp"), "yyyy-MM-dd HH:mm:SS").cast(TimestampType))
          .drop("Timestamp")
          .withColumnRenamed("TimestampNew", "Timestamp")
      }
    )
  }

}

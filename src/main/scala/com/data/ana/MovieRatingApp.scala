package com.data.ana

import com.data.ana.application.{MovieRatingsAnalytics, movieNormalise, ratingNormalise}
import com.data.ana.common.{EitherTryHandler, MlReader}
import com.data.ana.config.MlArgParser
import com.data.ana.domain.{MovieLenError, MovieSchemaRaw, RatingSchemaRaw}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import cats.implicits._
import org.slf4j.LoggerFactory

import scala.util.Try

object MovieRatingApp extends EitherTryHandler {

  private val Delimiter = "::"
  private val logger = LoggerFactory.getLogger("Trip Analytics App")

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[2]")
      .appName("MovieLen Analytics App")
      .getOrCreate()

    implicit val spark1 = spark

   // All side effects should happen only in below section of the code
    val movieLenResult = for {

      args <- commandLineConfiguration(args)

      movieDf <- loadMovieData(args)
      rateDf <- loadRateData(args)

      movieNormDf <- movieNormalise(movieDf)
      rateNormDf <- ratingNormalise(rateDf)

      movieRateDf <- MovieRatingsAnalytics(movieNormDf, rateNormDf).transform()

      _ <- writeToFile(movieRateDf, args.movieOutputFile.get)
      _ <- writeToFile(movieRateDf, args.ratingOutputFile.get)
      _ <- writeToFile(movieRateDf, args.movieRatingOutputFile.get)

    } yield movieDf

    movieLenResult leftMap {
      case MovieLenError(err) => handleError(err)
      case _ => handleError("Failed due to unknown error")
    }

  }


  private def loadMovieData(args: MlArgParser)(implicit spark: SparkSession): Either[MovieLenError, DataFrame] = {
    MlReader(args.movieInputFile.get, Delimiter, MovieSchemaRaw)
  }

  private def loadRateData(args: MlArgParser)(implicit spark: SparkSession): Either[MovieLenError, DataFrame] = {
    MlReader(args.ratingInputFile.get, Delimiter, RatingSchemaRaw)
  }

  private def writeToFile(df: DataFrame, outputFile: String): Either[MovieLenError, Unit] = {
    eitherR(
      Try {
        df.write.parquet(outputFile)
      }
    )
  }

  private def commandLineConfiguration(args: Array[String]): Either[MovieLenError, MlArgParser] = {
    val argParser = MlArgParser(args)
    val result = argParser.verify

    result match {
      case Left(e) => Left(MovieLenError(s"Argument parse failed ${e.toString}"))
      case Right(v) => Right(argParser)
    }
  }

  private def handleError(msg: String) = {
    logger.error(s"Failed due to error $msg")
    throw new RuntimeException(s"Failed due to error $msg")
  }


}

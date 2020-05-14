package com.data.ana.config

import com.data.ana.domain.MovieLenError

case class MlArgParser(args: Array[String]) extends Serializable {

  private val keyValues = args.sliding(2, 1).toList

  val movieInputFile: Option[String] = option(keyValues.collect { case Array("--movie-input-file", v) => v })
  val ratingInputFile: Option[String] = option(keyValues.collect { case Array("--rating-input-file", v) => v })
  val movieRatingOutputFile: Option[String] = option(keyValues.collect { case Array("--movie-rating-output", v) => v })
  val movieOutputFile: Option[String] = option(keyValues.collect { case Array("--movie-output", v) => v })
  val ratingOutputFile: Option[String] = option(keyValues.collect { case Array("--rating-output", v) => v })

  def verify: Either[MovieLenError, Unit] = {

    for {
      _ <- eval("--movie-input-file", movieInputFile)
      _ <- eval("--rating-input-file", ratingInputFile)
      _ <- eval("--movie-output", movieOutputFile)
      _ <- eval("--rating-output", ratingOutputFile)
      r <- eval("--movie-rating-output", movieRatingOutputFile)
    } yield r

  }

  private def eval[T](keyName: String, optVal: Option[T]): Either[MovieLenError, Unit] = if (optVal.isEmpty) Left(MovieLenError(s"$keyName invalid")) else Right(Unit)
  private def option(values: List[String]): Option[String] = values.headOption

}

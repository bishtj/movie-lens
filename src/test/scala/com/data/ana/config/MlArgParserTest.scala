package com.data.ana.config

import org.scalatest.FunSuite

class MlArgParserTest extends FunSuite {

  test("ArgumentParser - should successfully parse the arguments given all arguments are present") {

    val args = Array("--movie-input-file", "movie/path","--rating-input-file", "rating/path", "--movie-rating-output", "movie/rating/path", "--movie-output", "movie/output/path", "--rating-output", "rating/output/path")

    val argParser = MlArgParser(args)

    assert(argParser.movieInputFile === Some("movie/path"))
    assert(argParser.ratingInputFile === Some("rating/path"))
    assert(argParser.movieRatingOutputFile === Some("movie/rating/path"))
    assert(argParser.movieOutputFile === Some("movie/output/path"))
    assert(argParser.ratingOutputFile === Some("rating/output/path"))

  }

  test("ArgumentParser - should be able parse the arguments given that some arguments are not present") {

    val args = Array("--movie-input-file", "movie/path")

    val argParser = MlArgParser(args)

    assert(argParser.movieInputFile === Some("movie/path"))
    assert(argParser.ratingInputFile === None)
    assert(argParser.movieRatingOutputFile === None)
    assert(argParser.movieOutputFile === None)
    assert(argParser.ratingOutputFile === None)
  }

  test("verify - should successfully validate the arguments") {
    val args = Array("--movie-input-file", "movie/path","--rating-input-file", "rating/path", "--movie-rating-output", "movie/rating/path", "--movie-output", "movie/output/path", "--rating-output", "rating/output/path")
    val argParser = MlArgParser(args)
    val actual = argParser.verify
    assert(actual.isRight)
  }

  test("verify - should fail on validating the arguments") {
    val args = Array("--movie-input-file", "movie/path","--rating-input-file", "rating/path")
    val argParser = MlArgParser(args)
    val actual = argParser.verify
    assert(actual.isLeft)
  }


}

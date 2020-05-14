package com.data.ana.application

import java.sql.Timestamp

import com.data.ana.MovieLensCommonTest
import com.data.ana.domain.MovieSchemaRaw
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType}

class MovieRatingTest extends MovieLensCommonTest with DataFrameSuiteBase {

  val BasePath = "src/test/resources"

  test("Should generate moview rating statistics") {
    implicit val spark1 = spark
    implicit val orderColumn = "MovieID"

    val movieRatingsAnalytics = MovieRatingsAnalytics(movieDf(), ratingsDf())

    val movieRatingsDf = movieRatingsAnalytics.transform()

    val actualDf = eitherAssert(movieRatingsDf)

    assertDataFrameEquals(orderBy(expectedDf()), orderBy(actualDf))

  }

  val ExpectedSchema = StructType(
    StructField("MovieID", StringType) ::
      StructField("Title", StringType) ::
      StructField("Genres", StringType) ::
      StructField("MaxRating", IntegerType) ::
      StructField("MinRating", IntegerType) ::
      StructField("AvgRating", DoubleType) ::
      Nil
  )

  def movieDf()(implicit spark: SparkSession) = {
    val movieRows = Seq(
      Row("1", "Toy Story (1995)", "Animation|Children's|Comedy"),
      Row("2", "Jumanji (1995)", "Adventure|Children's|Fantasy"),
      Row("3", "Grumpier Old Men (1995)", "Comedy|Romance")
    )
    rowsToDataFrame(movieRows, MovieSchemaRaw)
  }

  def ratingsDf()(implicit spark: SparkSession) = {
    val ratingRowsDf = Seq(
      Row("1", "1", 5, Timestamp.valueOf("1970-01-01 01:00:05")),
      Row("2", "1", 7, Timestamp.valueOf("1971-01-01 01:00:05")),
      Row("3", "2", 10, Timestamp.valueOf("1972-01-01 01:00:03")),
      Row("4", "2", 50, Timestamp.valueOf("1973-01-01 01:00:03")),
      Row("5", "3", 30, Timestamp.valueOf("1974-01-01 01:00:03")),
      Row("6", "3", 40, Timestamp.valueOf("1975-01-01 01:00:04"))
    )
   rowsToDataFrame(ratingRowsDf, RatingSchemaNormalised)
  }

  def expectedDf()(implicit spark: SparkSession) = {
    val expectedRows = Seq(
      Row("1", "Toy Story (1995)", "Animation|Children's|Comedy", 7, 5, 6.0),
      Row("2", "Jumanji (1995)", "Adventure|Children's|Fantasy", 50, 10, 30.0),
      Row("3", "Grumpier Old Men (1995)", "Comedy|Romance", 40, 30, 35.0),
    )
    rowsToDataFrame(expectedRows, ExpectedSchema)
  }

}

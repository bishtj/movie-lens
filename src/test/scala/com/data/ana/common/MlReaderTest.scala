package com.data.ana.common

import com.data.ana.MovieLensCommonTest
import com.data.ana.domain.{MovieSchemaRaw, RatingSchemaRaw}
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

class MlReaderTest extends MovieLensCommonTest with DataFrameSuiteBase {

  val BasePath = "src/test/resources"

  test("Should load a Movie and Rating raw data successfully") {
    implicit val spark1 = spark

    assertReadMovieLens("input_movies.dat", MovieSchemaRaw, expectedMovieDf, "MovieID")
    assertReadMovieLens("input_ratings.dat", RatingSchemaRaw, expectedRatingDf, "UserID")
  }

  private def assertReadMovieLens(filePath : String, schema: StructType, expectedDf : DataFrame, orderColumn: String) = {
    implicit val spark1 = spark
    implicit val orderCol = orderColumn

    val delimReaderDf = MlReader(s"${BasePath}/${filePath}", "::", schema)

    val actualDf = eitherAssert(delimReaderDf)

    assertDataFrameEquals(orderBy(expectedDf), orderBy(actualDf))
  }

  private def expectedMovieDf(implicit spark: SparkSession) = {
    val rows = Seq(
      Row("1", "Toy Story (1995)", "Animation|Children's|Comedy"),
      Row("2", "Jumanji (1995)", "Adventure|Children's|Fantasy"),
      Row("3", "Grumpier Old Men (1995)", "Comedy|Romance")
    )
    rowsToDataFrame(rows, MovieSchemaRaw)
  }

  private def expectedRatingDf(implicit spark: SparkSession) = {
    val rows = Seq(
      Row("1", "4", "5", "978300760"),
      Row("2", "5", "3", "978302109"),
      Row("3", "6", "4", "978301968")
    )
    rowsToDataFrame(rows, RatingSchemaRaw)
  }
}

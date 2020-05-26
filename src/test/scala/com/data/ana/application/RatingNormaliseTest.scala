package com.data.ana.application

import java.sql.Timestamp

import com.data.ana.MovieLensCommonTest
import com.data.ana.domain.RatingSchemaRaw
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.Row

class RatingNormaliseTest extends MovieLensCommonTest with DataFrameSuiteBase {

  val BasePath = "src/test/resources"

  test("Should normalise rating dataset to normalised data type") {
    implicit val spark1 = spark
    implicit val orderColumn = "UserID"
    val expectedRows = Seq(
      Row("1", "4", 5, Timestamp.valueOf("2000-12-31 22:12:00")),
      Row("2", "5", 3, Timestamp.valueOf("2000-12-31 22:35:00")),
      Row("3", "6", 4, Timestamp.valueOf("2000-12-31 22:32:00"))
    )
    val expectedDf = rowsToDataFrame(expectedRows, RatingSchemaNormalised)

    val rows = Seq(
      Row("1", "4", "5", "978300760"),
      Row("2", "5", "3", "978302109"),
      Row("3", "6", "4", "978301968")
    )
    val inputDf = rowsToDataFrame(rows, RatingSchemaRaw)
    val normalisedDf = ratingNormalise(inputDf)

    val actualDf = eitherAssert(normalisedDf)

    assertDataFrameEquals(orderBy(expectedDf), orderBy(actualDf))
  }

}

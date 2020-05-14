package com.data.ana

import com.data.ana.domain.MovieLenError
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType, TimestampType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.scalatest.FunSuite

class MovieLensCommonTest extends FunSuite {

  val RatingSchemaNormalised = StructType(
    StructField("UserID", StringType) ::
      StructField("MovieID", StringType) ::
      StructField("Rating", IntegerType) ::
      StructField("Timestamp", TimestampType) ::
      Nil
  )

  def eitherAssert(eitherResult: Either[MovieLenError, DataFrame]): DataFrame = {
    eitherResult match {
      case Right(v) => v
      case Left(e) =>
        fail(s"Failed with error ${e.msg}")
    }
  }

  def rowsToDataFrame(rows: Seq[Row], schema: StructType)(implicit spark : SparkSession) = {
    val rdds = spark.sparkContext.parallelize(rows)
    spark.createDataFrame(rdds, schema)
  }

  def orderBy(df: DataFrame) (implicit column : String) : DataFrame = {
    df.orderBy(column)
  }


}

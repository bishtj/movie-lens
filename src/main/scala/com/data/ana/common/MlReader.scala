package com.data.ana.common

import com.data.ana.domain.MovieLenError
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import scala.util.Try


case object MlReader extends EitherTryHandler {
  def apply(filePath: String, delimiter: String, schema: StructType)(implicit spark: SparkSession): Either[MovieLenError, DataFrame] = {
    eitherR(
      Try {
        // Since multi Characters delimiter ('::') are not supported by DataFrameReader below Spark 3.x,
        // so have to use delimiter using RDDs functions
        val rdds = spark
          .sparkContext
          .textFile(filePath)
          .map(_.split(delimiter))
          .map(col => Row(col: _*))
        spark.createDataFrame(rdds, schema)
      }
    )
  }
}




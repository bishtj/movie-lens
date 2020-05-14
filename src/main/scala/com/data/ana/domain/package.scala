package com.data.ana

import org.apache.spark.sql.types.{StringType, StructField, StructType}

package object domain {

  case class MovieLenError(msg: String)

  val MovieSchemaRaw = StructType(
    StructField("MovieID", StringType) ::
    StructField("Title", StringType) ::
    StructField("Genres", StringType) ::
    Nil
  )

  val RatingSchemaRaw = StructType(
    StructField("UserID", StringType) ::
    StructField("MovieID", StringType) ::
    StructField("Rating", StringType) ::
    StructField("Timestamp", StringType) ::
    Nil
  )

}

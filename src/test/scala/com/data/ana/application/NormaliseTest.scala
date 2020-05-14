package com.data.ana.application

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.FunSuite


class NormaliseTest extends FunSuite with DataFrameSuiteBase {


  test("First sbt based dataframe test") {

    val rows = Seq(
      Row("David Ray", 50),
      Row("Neil Welsh", 45)
    )

    val schema = StructType(
      StructField("name", StringType) ::
        StructField("age", IntegerType) ::
        Nil
    )

    val rdds = spark.sparkContext.parallelize(rows)

    val df1 = spark.createDataFrame(rdds, schema)
    val df2 = spark.createDataFrame(rdds, schema)

    assertDataFrameEquals(df1, df2)

  }


}

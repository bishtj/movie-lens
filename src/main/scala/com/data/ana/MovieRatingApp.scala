package com.data.ana

import org.apache.spark.sql.{DataFrame, SparkSession}

object MovieRatingApp {

  def main(args: Array[String]): Unit = {

    val df: DataFrame = SparkSession.builder().appName("").getOrCreate().read.csv("")


  }



}

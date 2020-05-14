package com.data.ana.application

import com.data.ana.common.EitherTryHandler
import com.data.ana.domain.MovieLenError
import org.apache.spark.sql.DataFrame

import scala.util.Try

case class movieRatingsAnalytics(movieDf: DataFrame, ratingDf: DataFrame) extends EitherTryHandler {

 /* def transform() : Either[MovieLenError, DataFrame] = {

    eitherR(
      Try {


      }
    )
  }
*/


}

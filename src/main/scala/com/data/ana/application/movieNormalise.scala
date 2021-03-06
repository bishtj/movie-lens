package com.data.ana.application

import com.data.ana.common.EitherTryHandler
import com.data.ana.domain.MovieLenError
import org.apache.spark.sql.DataFrame

import scala.util.Try

case object movieNormalise extends EitherTryHandler {

  def apply(dfInput: DataFrame) : Either[MovieLenError, DataFrame] = {

    eitherR(
      Try {
        // TODO : Any normalisation on movie dataset. Nothing needed for now.
        dfInput
      }
    )
  }
}

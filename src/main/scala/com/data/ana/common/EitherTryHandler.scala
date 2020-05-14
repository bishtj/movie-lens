package com.data.ana.common

import com.data.ana.domain.MovieLenError

import scala.util.{Failure, Success, Try}

trait EitherTryHandler {
  def eitherR[T](resultTry: Try[T]): Either[MovieLenError, T] = {
    resultTry match {
      case Success(v) => Right(v)
      case Failure(e) => Left(MovieLenError(e.toString))
    }
  }
}

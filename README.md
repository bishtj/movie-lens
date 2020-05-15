# MovieLens 
This is a sample project to aggregate movie ratings  

## Set up 
Follow below steps to run spark job.
Set environment variables
SPARK_HOME=<Installed Spark release 2.4.5 home>
export PATH=${SPARK_HOME}/bin:${PARH}
Navigate to project directory root and build as appropriate commands listed below.

sbt clean 

sbt package 

sbt assembly 

## Run steps 

To run saprk job externally please use one of (Makefile or spark-submit) as stated below.

### Makefile
Run below command with appropriate parameters.

```make run MASTER_URL=local --deploy-mode <deployMode> JAR_PATH=target/scala-2.11 MOVIE_INPUT_FILE=<base path>/movies.dat RATING_INPUT_FILE=<base path>/ratings.dat MOVIE_OUTPUT_DIR=<base path>/output/movie RATING_OUTPUT_DIR=<base path>/output/rating MOVIE_RATING_OUTPUT_DIR=/<base path>/movie-rating```

### Spark-submit
Run below command with appropriate parameters.

```spark-submit --master <local or remote url> --deploy-mode <client or cluster> --class com.data.ana.MovieRatingApp /basePath/movie-lens-project-assembly-1.0.jar --movie-input-file /basePath/movies.dat --rating-input-file /basePath/ratings.dat --movie-rating-output /basePath/movie-rating --movie-output /basePath/movie --rating-output /basePath/rating```















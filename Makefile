# Make file to run job
MASTER_URL ?= 'local'
DEPLOY_MODE ?= 'client'
JAR_PATH ?= 'Path/to/bin'
JAR ?= 'movie-lens-project-assembly-1.0.jar'
MOVIE_INPUT_FILE ?= 'src/test/resources/movies.dat'
RATING_INPUT_FILE ?= 'src/test/resources/ratings.dat'
MOVIE_OUTPUT_DIR ?= 'path/to/movie/output'
RATING_OUTPUT_DIR ?= 'path/to/rating/output'
MOVIE_RATING_OUTPUT_DIR ?= 'path/to/movie/rating/output'

run:
	spark-submit          									\
		--master ${MASTER_URL}								\
		--deploy-mode ${DEPLOY_MODE}                        \
		--class com.data.ana.MovieRatingApp 				\
	    ${JAR_PATH}/${JAR}                                  \
    	--movie-input-file ${MOVIE_INPUT_FILE} 				\
    	--rating-input-file ${RATING_INPUT_FILE} 			\
    	--movie-output ${MOVIE_OUTPUT_DIR} 					\
    	--rating-output ${RATING_OUTPUT_DIR} 				\
    	--movie-rating-output ${MOVIE_RATING_OUTPUT_DIR}

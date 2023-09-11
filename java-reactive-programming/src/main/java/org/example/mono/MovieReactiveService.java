package org.example.mono;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.MovieException;
import org.example.model.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class MovieReactiveService {

    private MovieInfoService movieInfoService;
    private ReviewService reviewService;
    private RevenueService revenueService;

    public MovieReactiveService(MovieInfoService movieInfoService, ReviewService reviewService){
        this.movieInfoService = movieInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Movie> getAllMovies(){
        var movieInfoFlux = movieInfoService.retrieveMoviesFlux();
        var movies = movieInfoFlux.flatMap(movieInfo -> {
            var reviewMono = reviewService.retrieveReviewsFlux(movieInfo.getMovieInfoId())
                    .collectList();
            return reviewMono.map(reviewList -> new Movie(movieInfo,reviewList));
        })
        .onErrorMap(ex-> {
            log.error("Exception: " + ex);
            throw new MovieException(ex.getMessage());
        });

        return movies.log();
    }

    public Mono<Movie> getMovieByMovieId(long movieId){
        var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        var movieReviewFlux = reviewService.retrieveReviewsFlux(movieId).collectList();

        return movieInfoMono.zipWith(movieReviewFlux,(movieInfo,reviewList)->new Movie(movieInfo,reviewList));

    }


}

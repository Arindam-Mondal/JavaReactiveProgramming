package org.example.mono;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieReactiveServiceTest {

    @Mock
    private static ReviewService reviewService;
    @Mock
    private static MovieInfoService movieInfoService;
    @InjectMocks
    private static MovieReactiveService movieReactiveService;


    @Test
    void getAllMovies() {

        when(movieInfoService.retrieveMoviesFlux()).thenCallRealMethod();
        when(reviewService.retrieveReviewsFlux(anyLong())).thenCallRealMethod();

        var movies = movieReactiveService.getAllMovies();

        StepVerifier.create(movies)
                .expectNextMatches(movie -> movie.getMovieInfo().getName().equals("Batman Begins"))
                .expectNextMatches(movie -> movie.getMovieInfo().getName().equals("The Dark Knight"))
                .expectNextMatches(movie -> movie.getMovieInfo().getName().equals("Dark Knight Rises"))
                .verifyComplete();
    }

    @Test
    void getMovieByMovieId() {

        when(movieInfoService.retrieveMovieInfoMonoUsingId(anyLong())).thenCallRealMethod();
        when(reviewService.retrieveReviewsFlux(anyLong())).thenCallRealMethod();

        var movie = movieReactiveService.getMovieByMovieId(2001);

        StepVerifier.create(movie)
                .expectNextMatches(m->m.getMovieInfo().getName().equals("Batman Begins"))
                .verifyComplete();
    }

    @Test
    void getAllMoviesException() {

        var errorMessage = "Exception occurred in Review Service";
        when(movieInfoService.retrieveMoviesFlux()).thenCallRealMethod();
        when(reviewService.retrieveReviewsFlux(anyLong())).thenThrow(new RuntimeException(errorMessage));

        var movies = movieReactiveService.getAllMovies();

        StepVerifier.create(movies)
                .expectErrorMessage(errorMessage)
                .verify();


    }
}
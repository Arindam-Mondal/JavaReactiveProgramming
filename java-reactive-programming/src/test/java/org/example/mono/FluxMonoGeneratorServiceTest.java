package org.example.mono;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class FluxMonoGeneratorServiceTest {

    FluxMonoGeneratorService fluxMonoGeneratorService = new FluxMonoGeneratorService();

    @Test
    public void nameFluxTest(){

        var stringFlux = fluxMonoGeneratorService.nameFlux();
        StepVerifier.create(stringFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void nameMonoTest(){
        var stringMono = fluxMonoGeneratorService.nameMono();

        StepVerifier.create(stringMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void nameFluxMapFilter() {
        var nameFlux = fluxMonoGeneratorService.nameFluxMapFilter();
        List<String> expectedNameList = List.of("Arindam","Subhas","Tamal","Pankaj","Suman")
                                            .stream()
                                            .filter(s->s.length()>5)
                                            .map(s->s.toUpperCase())
                                            .collect(Collectors.toList());

        StepVerifier.create(nameFlux)
                .expectNextSequence(expectedNameList)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void nameFluxFlatMapFilter() {

        List<String> stringList = List.of("Ben","Adam","Chris");
        int lengthFilter = 3;
        List<String> expectedList = stringList.stream()
                                            .filter(s->s.length() >lengthFilter)
                                            .map(s->s.toUpperCase())
                                            .flatMap(s-> Arrays.stream(s.split("")))
                                            .collect(Collectors.toList());




        var nameFlatMapFlux = fluxMonoGeneratorService.nameFluxFlatMapFilter(lengthFilter,stringList);
        StepVerifier.create(nameFlatMapFlux)
                .expectNextSequence(expectedList)
                .verifyComplete();

    }

    @Test
    void nameFlatMapMany() {
        String s = "Arindam";
        var nameFlatMapMany = fluxMonoGeneratorService.nameFlatMapMany(s);
        StepVerifier.create(nameFlatMapMany)
                .expectNextCount(7)
                .verifyComplete();
    }

    @Test
    void nameFluxTransform() {
        List<String> stringList = List.of("Ben","Adam","Chris");
        int lengthFilter = 3;
        var nameFluxTransform = fluxMonoGeneratorService.nameFluxTransform(3,stringList);

        List<String> expectedList = stringList.stream()
                .filter(s->s.length() >lengthFilter)
                .map(s->s.toUpperCase())
                .flatMap(s-> Arrays.stream(s.split("")))
                .collect(Collectors.toList());

        StepVerifier.create(nameFluxTransform)
                .expectNextSequence(expectedList)
                .verifyComplete();

    }

    @Test
    void fluxUsingConcat() {

        var fluxUsingConcat = fluxMonoGeneratorService.fluxUsingConcat();
        StepVerifier.create(fluxUsingConcat)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void fluxUsingConcatWith() {
        var fluxUsingConcatWith = fluxMonoGeneratorService.fluxUsingConcatWith();
        StepVerifier.create(fluxUsingConcatWith)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void fluxFromMono() {
        var fluxFromMono = fluxMonoGeneratorService.fluxFromMono();
        StepVerifier.create(fluxFromMono)
                .expectNext("A","D")
                .verifyComplete();
    }


    @Test
    void fluxFromZip() {
        var fluxFromZip = fluxMonoGeneratorService.fluxFromZip();
        StepVerifier.create(fluxFromZip)
                .expectNext("A-1","B-2","C-3","D-4")
                .verifyComplete();
    }


    @Test
    void nameFluxTransformAsync() {

        List<String> stringList = List.of("Ben","Adam","Chris");
        int lengthFilter = 3;
        var nameFluxTransformAsync = fluxMonoGeneratorService.nameFluxTransformAsync(3,stringList);

        List<String> expectedList = stringList.stream()
                .filter(s->s.length() >lengthFilter)
                .map(s->s.toUpperCase())
                .flatMap(s-> Arrays.stream(s.split("")))
                .collect(Collectors.toList());

        StepVerifier.create(nameFluxTransformAsync)
                .expectNextSequence(expectedList)
                .verifyComplete();
    }
}
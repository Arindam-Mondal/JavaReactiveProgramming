package org.example.mono;

import org.example.util.FakerUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FluxMonoGeneratorService {

    public static void main(String[] args) {
        FluxMonoGeneratorService fluxMonoGeneratorService = new FluxMonoGeneratorService();
        fluxMonoGeneratorService.nameFlux().subscribe(
                FakerUtil.onNext()
        );

        fluxMonoGeneratorService.nameMono()
                .subscribe(
                        FakerUtil.onNext()
                );

        System.out.println("FLUX MAP TO UPPER");

        fluxMonoGeneratorService.nameFluxMap()
                .subscribe(
                        FakerUtil.onNext()
                );
    }

    public Flux<String> nameFlux(){
        return Flux.fromIterable(getBookNames())
                .log();
    }

    public Flux<String> nameFluxMap(){
        return Flux.fromIterable(getBookNames())
                .map(bookName->bookName.toUpperCase())
                .log();
    }

    public Flux<String> nameFluxMapFilter(){
        return Flux.fromIterable(List.of("Arindam","Subhas","Tamal","Pankaj","Suman"))
                .map(name->name.toUpperCase())
                .filter(name->name.length()>5)
                .log();
    }

    public Flux<String> nameFluxFlatMapFilter(int length, List<String> stringList){
        return Flux.fromIterable(stringList)
                .map(s->s.toUpperCase())
                .filter(s->s.length()>length)
                .flatMap(this::fluxFromString)
                .log();
    }


    public Flux<String> nameFluxTransform(int length, List<String> stringList){
        return Flux.fromIterable(stringList)
                .map(s->s.toUpperCase())
                .filter(s->s.length()>length)
                .transform(transformStringFlux())
                .log();
    }


    public Flux<String> nameFluxTransformAsync(int length, List<String> stringList){
        return Flux.fromIterable(stringList)
                .map(s->s.toUpperCase())
                .publishOn(Schedulers.boundedElastic())
                .filter(s->s.length()>length)
                .transform(transformStringFlux())
                .log();
    }

    Function<Flux<String>,Flux<String>> transformStringFlux(){
        return (flux)->flux.flatMap(this::fluxFromString);
    }

    public Flux<String> nameFlatMapMany(String str){
        return Mono.just(str)
                .map(s->s.toUpperCase())
                .flatMapMany(this::fluxFromString)
                .log();
    }

    public Flux<String> fluxFromString(String str){
        var strArray = str.split("");
        return Flux.fromArray(strArray);
    }

    public Mono<String> nameMono(){
        return Mono.just(getName())
                .log();
    }

    public List<String> getBookNames(){
        return IntStream.rangeClosed(1,10)
                .mapToObj(i-> FakerUtil.FAKER.book().title())
                .collect(Collectors.toList());
    }

    public String getName(){
        return FakerUtil.FAKER.name().firstName();
    }

    public Flux<String> fluxUsingConcat(){
        var flux1 = Flux.just("A","B","C");
        var flux2 = Flux.just("D","E","F");

        return Flux.concat(flux1,flux2);

    }

    public Flux<String> fluxUsingConcatWith(){
        var flux1 = Flux.just("A","B","C");
        var flux2 = Flux.just("D","E","F");

        return flux1.concatWith(flux2);

    }

    public Flux<String> fluxFromMono(){
        var mono1 = Mono.just("A");
        var mono2 = Mono.just("D");

        return mono1.concatWith(mono2);

    }


    public Flux<String> fluxFromZip(){
        var flux1 = Flux.just("A","B","C","D");
        var flux2 = Flux.just("1","2","3","4");

        return Flux.zip(flux1,flux2,(start,second)->start+"-"+second).log();
    }

}

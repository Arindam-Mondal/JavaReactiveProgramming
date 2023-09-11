package org.example.mono;

import org.example.util.FakerUtil;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class MonoFromSupplier {
    public static void main(String[] args) {
        //This is not correct and only be used when you have the data ready
//        Mono<String> mono = Mono.just(getName());
        //Instead use Supplier
        //Supplier and Callable - Both can be used as both are of Type Supplier

        Supplier<String> stringSupplier = () -> getName();
        Mono<String> supplierMono = Mono.fromSupplier(stringSupplier);

        supplierMono.subscribe(
                FakerUtil.onNext(),
                FakerUtil.onError(),
                FakerUtil.onComplete()
        );


        Callable<String> stringCallable = () -> getName();
        Mono<String> callableMono = Mono.fromCallable(stringCallable);

        callableMono.subscribe(
                FakerUtil.onNext(),
                FakerUtil.onError(),
                FakerUtil.onComplete()
        );
    }


    public static String getName(){
        System.out.println("Generating Name");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return FakerUtil.FAKER.name().firstName();
    }
}

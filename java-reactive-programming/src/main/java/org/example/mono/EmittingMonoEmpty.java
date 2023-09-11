package org.example.mono;

import com.github.javafaker.Faker;
import org.example.util.FakerUtil;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

public class EmittingMonoEmpty {
    public static void main(String[] args) {
        IntStream.range(0,15).forEach((i)->{
            getName(i).subscribe(
                    FakerUtil.onNext(),
                    FakerUtil.onError(),
                    FakerUtil.onComplete()
            );
        });

        System.out.println("---------------------");

        getName(15)
                .subscribe(
                        FakerUtil.onNext(),
                        FakerUtil.onError(),
                        FakerUtil.onComplete()
                );

    }

    public static Mono<String> getName(int id){
        if(id < 1){
            return Mono.empty();
        }
        if(id<=10){
            return Mono.just(id+" "+Faker.instance().book().author());
        }else{
            return Mono.error(new RuntimeException("Id Cannot be Greater than 10"));
        }
    }
}

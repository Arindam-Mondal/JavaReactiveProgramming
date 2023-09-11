package org.example.mono;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import reactor.core.publisher.Mono;

public class MonoExample {
    public static void main(String[] args) {
        Mono<Integer> mono = Mono.just(1);
        //Nothing happens Until you Subscribe

        System.out.println(mono);

        mono.subscribe(i-> System.out.println(i));

        getBookFromRepository().subscribe(
                book -> System.out.println(book.author()),
                error -> System.out.println(error.getMessage()),
                () -> System.out.println("Completed")
        );
    }

    public static Mono<Book> getBookFromRepository(){
        Mono<Book> monoBook = Mono.just(new Faker().book());
        return monoBook;
    }

}

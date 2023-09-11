package org.example.util;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FakerUtil {
    public static Faker FAKER = new Faker();

    public static List<Book> bookList(){
        List<Book> bookList = IntStream.range(1,10)
                .mapToObj(i->FAKER.book())
                .collect(Collectors.toList());

        return bookList;
    }

    public static Consumer<Object> onNext(){
        return (item) -> {
            System.out.println(item);
        };
    }

    public static Consumer<Throwable> onError(){
        return (error) -> {
            System.out.println(error.getMessage());
        };
    }

    public static Runnable onComplete(){
        return ()->{
            System.out.println("Completed");
        };
    }
}

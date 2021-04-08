package com.demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PublisherExample {
	static void fluxSubscriber() {
		Flux<Integer> flux1 = Flux.range(4, 10);
		List<String> iterable = Arrays.asList("Hello", "World", "!");
		Flux<String> flux2 = Flux.fromIterable(iterable);
		// flux1.subscribe(i -> System.out.println(i));
		// flux2.subscribe(i -> System.out.println(i));
		// имитация ожидания получения каждого элемента данных в течение двух секунд (interval)
		// и соединение каждого элемента item из входного реактивного потока flux2
		// с порядковыми номерами i элементов из него же
		Flux<String> intervalFlux = Flux.interval(Duration.ofMillis(2000)).zipWith(flux2,
				(i, item) -> "item " + i + ": " + item);

		intervalFlux.subscribe(System.out::println);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void monoSubscriber() throws InterruptedException {
		Mono<String> noData = Mono.empty();
		Mono<String> noDataError = Mono.error(new Exception("Some error!"));
		Mono<String> data = Mono.just("Hello");
		// noData.subscribe();
		// noData.subscribe(System.out::println);
		/* data.subscribe(i -> System.out.println(i), error -> System.err.println("Error " + error), () -> {
			System.out.println("Done");
		});
		noDataError.subscribe(
				i -> System.out.println(i),
				error -> {
					System.err.println("Error " + error);
					fakeDispose();
				},
				() -> fakeDispose()); */
		Mono<List> listMono = Mono.just(List.of("First", "Second", "Third"));
		// имитация ожидания получения всего списка данных в течение 5 секунд
		listMono.delayElement(Duration.ofSeconds(5)).subscribe(list -> list.forEach(System.out::println));
		System.out.println("Next Code ...");
		Thread.sleep(10000);
	}

	static void fakeDispose () {
		System.out.println("Done");
	}
}

package com.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webflux.document.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemReactiveRepository extends ReactiveMongoRepository<Item, String>
{
	Mono<Item> findByDescription(String description);
}

package com.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import com.webflux.document.ItemCapped;
import reactor.core.publisher.Flux;


public interface ItemReactiveCappedRepository extends ReactiveMongoRepository<ItemCapped,String> {

	@Tailable
    Flux<ItemCapped> findItemsBy();
	
}

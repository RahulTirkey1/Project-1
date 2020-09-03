package com.webflux.initialize;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;

import com.webflux.document.Item;
import com.webflux.document.ItemCapped;
import com.webflux.repository.ItemReactiveCappedRepository;
import com.webflux.repository.ItemReactiveRepository;

import reactor.core.publisher.Flux;
@Component
@Profile("!test")
public class ItemDataInitializer implements CommandLineRunner{

	@Autowired
    ItemReactiveRepository itemReactiveRepository;
	
	@Autowired
    ItemReactiveCappedRepository itemReactiveCappedRepository;

    @Autowired
    ReactiveMongoOperations mongoOperations;
	
	public List<Item> data() {

        return Arrays.asList(new Item(null, "Samsung TV", 399.99),
                new Item(null, "LG TV", 329.99),
                new Item(null, "Apple Watch", 349.99),
                new Item("ABC", "Beats HeadPhones", 149.99));
    }
	private void initalDataSetUp() {

        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                        .flatMap(itemReactiveRepository::save)
                        .thenMany(itemReactiveRepository.findAll())
                        .subscribe((item -> {
                            System.out.println("Item inserted from CommandLineRunner : " + item);
                        }));

    }
	
	private void createCappedCollection() {
        mongoOperations.dropCollection(ItemCapped.class);
        mongoOperations.createCollection(ItemCapped.class, CollectionOptions.empty().maxDocuments(20).size(50000).capped());

    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		initalDataSetUp();
		createCappedCollection();
		dataSetUpforCappedCollection();
	}
	
	public void dataSetUpforCappedCollection(){

        Flux<ItemCapped> itemCappedFlux = Flux.interval(Duration.ofSeconds(1))
                .map(i -> new ItemCapped(null,"Random Item " + i, (100.00+i)));

        itemReactiveCappedRepository
                .insert(itemCappedFlux);
              //  .subscribe((itemCapped -> {
                //    log.info("Inserted Item is " + itemCapped);
//                }));

    }

}

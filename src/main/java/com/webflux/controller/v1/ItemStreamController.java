package com.webflux.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.document.ItemCapped;
import com.webflux.repository.ItemReactiveCappedRepository;
import static com.webflux.constants.ItemConstants.ITEM_STREAM_END_POINT_V1;
import reactor.core.publisher.Flux;
@RestController
public class ItemStreamController 
{
	 @Autowired
	    ItemReactiveCappedRepository itemReactiveCappedRepository;

	    @GetMapping(value = ITEM_STREAM_END_POINT_V1, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	    public Flux<ItemCapped> getItemsStream(){

	        return itemReactiveCappedRepository.findItemsBy();
	    }

}

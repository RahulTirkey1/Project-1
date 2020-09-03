package com.webflux.fluxandmono;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest 
{
	@Test
    public void fluxTest() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
        	  //	.map(e->e.concat("Flux"))
             //   .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("After Error"))
        	  //	.concatWith(Flux.just("Flux"))
                .log();

        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println("Exception is " + e),
                        () -> System.out.println("Completed"));
    }
	
	    @Test
	    public void fluxTestElements_WithoutError() {

	        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
	                .log();

	        StepVerifier.create(stringFlux)
	                .expectNext("Spring")
	                .expectNext("Spring Boot")
	                .expectNext("Reactive Spring")
	                .verifyComplete();


	    }
	    
	    @Test
	    public void fluxTestElements_WithError() {

	        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
	                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
	                .log();

	        StepVerifier.create(stringFlux)
	                .expectNext("Spring")
	                .expectNext("Spring Boot")
	                .expectNext("Reactive Spring")
	                //.expectError(RuntimeException.class)
	                .expectErrorMessage("Exception Occurred")
	                .verify();      //In case of Errors, verifyComplete() won't work as we expect a error and it's not a complete but a termination.


	    } 
	    
	    @Test
	    public void fluxTestElements_WithError1() {

	        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
	                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
	                .log();

	        StepVerifier.create(stringFlux)
	                .expectNext("Spring","Spring Boot","Reactive Spring")
	                .expectErrorMessage("Exception Occurred")
	                .verify();


	    }

	    @Test
	    public void fluxTestElementsCount_WithError() {

	        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
	                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
	                .log();

	        StepVerifier.create(stringFlux)
	                .expectNextCount(3)        //Instead of putting expectNext we can put the number of elements we are expecting
	                .expectErrorMessage("Exception Occurred")
	                .verify();
	    }
	    
	    @Test
	    public void monoTest(){

	        Mono<String>  stringMono = Mono.just("Spring");

	        StepVerifier.create(stringMono.log())
	                .expectNext("Spring")
	                .verifyComplete();

	    }

	    @Test
	    public void monoTest_Error(){


	        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
	                .expectError(RuntimeException.class)
	                .verify();

	    }
}

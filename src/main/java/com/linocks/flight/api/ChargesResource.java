package com.linocks.flight.api;

import com.linocks.flight.entity.Charges;
import com.linocks.flight.entity.Tax;
import com.linocks.flight.repository.ReactiveChargesReposistory;
import com.linocks.flight.repository.ReactiveTaxRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/charges")
public class ChargesResource {

    @Autowired
    ReactiveChargesReposistory reactiveChargesReposistory;
    @Autowired
    ReactiveTaxRepository reactiveTaxRepository;
    @Autowired
    ReactiveMongoTemplate mongoOps;
    

    @GetMapping("/{taxId}")
    public Mono<Charges> getPaymentCharge(@PathVariable String taxId) {
        System.out.println("getting charge amount for tax "+taxId);
        
        return reactiveChargesReposistory.findByTaxId(taxId).switchIfEmpty(Mono.just(new Charges()));
    }

    @GetMapping
    public Flux<Charges> getAllCharges(){
        System.out.println("getting all charges");
        return reactiveChargesReposistory.findAll();
    }

    @PostMapping("/save")
    public Mono<Charges> setPaymentCharge(@RequestBody Charges paymentCharge) {
        System.out.println("creating charge amount..."+paymentCharge.toString());
        paymentCharge.setId(null);
        return reactiveChargesReposistory.save(paymentCharge);
    }

    @PostMapping("/update")
    public Mono<Charges> updatePaymentCharge(@RequestBody Charges charges) {
           
        Mono<Charges> charge = reactiveChargesReposistory.findById(charges.getId());
        
        System.out.println("charge amount modified successfully...");
        return charge.flatMap(c->{
            c.setCharge(charges.getCharge());            
            return mongoOps.save(c);            
        }).map(u->{
            return u;
        });

    }

    @PostMapping("/tax")
    public Mono<Tax> setTax(@RequestBody Tax tax) {
        System.out.println("saving tax......"+tax.toString());
        return reactiveTaxRepository.save(tax);
    }

    @GetMapping("/tax")
    public Flux<Tax> getAllTaxes(){
        return reactiveTaxRepository.findAll();
    }

    @GetMapping("/tax/{id}")
    public Mono<Tax> getTax(@PathVariable String taxId){
        return reactiveTaxRepository.findById(taxId);
    }

}
package com.CustomerMs.CustomerMs.business.repository;


import com.CustomerMs.CustomerMs.Model.entity.ClienteEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClienteRepository extends
        ReactiveCrudRepository<ClienteEntity, Long> {

}

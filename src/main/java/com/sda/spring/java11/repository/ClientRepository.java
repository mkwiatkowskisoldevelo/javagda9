package com.sda.spring.java11.repository;


import com.sda.spring.java11.model.Client;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, String> {

    Optional<Client> findOneByClientId(String clientId);
}

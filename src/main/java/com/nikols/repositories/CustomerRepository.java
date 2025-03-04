package com.nikols.repositories;

import com.nikols.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {   //Hay que poner el name del entity - Customer - y luego el tipo de Id - Integer
    Optional<Customer> findByEmail(String email); // Spring genera la consulta automáticamente
    List<Customer> findByName(String name);
    List<Customer> findByAge(Integer age);

}

// El CRUD es -> Create Read Update Delete.
// Esto se llama Repository porque al extender JpaRepository con Customer e Integer lo que hacemos es hacer que
// Spring Boot genere e implemente métodos básicos para hacer el CRUD sin tener que escribir el código manualmente
// por ejemplo nos da un
// save(Customer customer) -> guarda un cliente nuevo | actualiza si existe.
// findById(Integer id)
// findAll()
// deleteById(Integer id)
// count()



//COMANDOS PARA UTILIZAR LA DB:
//docker exec -it postgres bash -> acceder a una disque terminal de la db
// psql -U <nombre_de_usuario> (user) -> acceder a mi db llamada user
// /l para ver una lista de db.
// CREATE DATABASE <nombre_de_db>; -> en nuestro caso customer
// /c <nombre_de_db>
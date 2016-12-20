package ma.scupper.spring.lazyloading.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.scupper.spring.lazyloading.model.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

}

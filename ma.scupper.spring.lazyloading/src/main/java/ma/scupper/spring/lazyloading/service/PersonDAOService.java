package ma.scupper.spring.lazyloading.service;

import java.util.List;

import ma.scupper.spring.lazyloading.model.Person;

public interface PersonDAOService {
	
	List<Person> findAll();
	
	void save(Person person);
	
	void delete(Person person);
	
	long size();
	
	List<Person> findInRange(int start, int pageSize);
	
}

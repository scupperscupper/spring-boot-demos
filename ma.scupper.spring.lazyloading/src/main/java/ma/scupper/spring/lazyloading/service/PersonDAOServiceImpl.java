package ma.scupper.spring.lazyloading.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;

import ma.scupper.spring.lazyloading.model.Person;
import ma.scupper.spring.lazyloading.repositories.PersonRepository;

@Service
public class PersonDAOServiceImpl implements PersonDAOService {

	private PersonRepository personRepository;
	
	@Autowired
	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public List<Person> findAll() {
		List<Person> results = new ArrayList<>();
		personRepository.findAll()
				.forEach(p -> results.add(p));
		return results;
	}

	@Override
	public void save(Person person) {
		personRepository.save(person);
	}

	@Override
	public void delete(Person person) {
		personRepository.delete(person);
	}

	@Override
	public long size() {
		return personRepository.count();
	}

	@Override
	public List<Person> findInRange(int start, int pageSize) {
//		int end = start + pageSize;
//		long maxPages = personRepository.count() / pageSize;
		Path<Person> person = Expressions.path(Person.class, "person");
		Path<String> personId = Expressions.path(String.class, person, "id");
		Path<String> personName = Expressions.path(String.class, person, "name");
		int pageIndex = start / pageSize;
		OrderSpecifier<?>[] orders = new OrderSpecifier[] { 
				new OrderSpecifier<>(Order.ASC, personId), 
				new OrderSpecifier<>(Order.ASC, personName) 
		};
		QPageRequest pageRequest = new QPageRequest(pageIndex, pageSize, orders);
		System.out.println("START IS = "+pageIndex+", PAGE SIZE = "+pageSize);
		Page<Person> results = personRepository.findAll(pageRequest);
		return convertPageToList(results);
	}
	
	private static List<Person> convertPageToList(Page<Person> page) {
		List<Person> results = new ArrayList<>();
		page.forEach(results::add);
		System.out.println("RESULTS SIZE = "+results.size());
		return results;
	}
	
}

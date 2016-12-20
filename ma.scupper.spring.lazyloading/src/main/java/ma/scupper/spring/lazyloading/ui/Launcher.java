package ma.scupper.spring.lazyloading.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import ma.scupper.spring.lazyloading.model.Person;
import ma.scupper.spring.lazyloading.service.PersonDAOService;

@Theme("valo")
@SpringUI(path = "")
public class Launcher extends UI {

	private static final long serialVersionUID = 3491880955373110801L;
	private static List<Person> persons;
	
	private PersonDAOService repository;
	
	@Autowired
	public void setRepository(PersonDAOService repository) {
		this.repository = repository;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		dbInit();
		persons.forEach(person -> repository.save(person));
		LazyPersonTable personTable = new LazyPersonTable(repository);
		setContent(personTable);
		setSizeFull();
	}

	private static void dbInit() {
		persons = new ArrayList<>();
		persons.addAll(IntStream.range(1, 100000).mapToObj(value -> {
			return new Person(value + "", value + "");
		}).collect(Collectors.toList()));
	}

}

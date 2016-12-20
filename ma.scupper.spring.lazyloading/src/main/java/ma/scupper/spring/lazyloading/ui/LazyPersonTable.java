package ma.scupper.spring.lazyloading.ui;

import java.util.List;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

import ma.scupper.spring.lazyloading.model.Person;
import ma.scupper.spring.lazyloading.service.PersonDAOService;

public class LazyPersonTable extends Table {

	private static final long serialVersionUID = 3096909728171124621L;

	public LazyPersonTable(PersonDAOService repository) {
		setContainerDataSource(new LazyBeanContainer(repository));
		setSizeFull();
	}

	private static class LazyBeanContainer extends BeanItemContainer<Person> {

		private static final long serialVersionUID = -8776361881666489922L;

		private final PersonDAOService repository;

		public LazyBeanContainer(PersonDAOService repository) {
			super(Person.class);
			this.repository = repository;
		}
		
		@Override
		public int size() {
			return repository.size() >= Integer.MAX_VALUE ? Integer.MAX_VALUE - 1 : (int)repository.size();
		}

		@SuppressWarnings("unchecked")
		@Override
		public BeanItem<Person> getItem(Object itemId) {
			return new BeanItem((Person) itemId);
		}

		@Override
		public List<Person> getItemIds(int startIndex, int numberOfIds) {
			List<Person> list = repository.findInRange(startIndex, numberOfIds);
			return list;
		}
	}

}

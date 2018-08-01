package aszka.dogs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DogDAO {

	private final Logger logger = LoggerFactory.getLogger(DogDAO.class);

	List<Dog> dogs = new ArrayList<>();

	public void addDog(Dog dog) throws IllegalStateException {
		dogs.add(dog);
		logger.info("Add a new dog: {}", dog);
	}

	public String dogs() {
		StringBuilder allDogs = new StringBuilder();
		Consumer<Dog> consumerDogs = dog -> allDogs.append(dog.intruduceYourself());
		dogs.forEach(consumerDogs);
		if (allDogs.toString().isEmpty()) {
			return "The list is empty\n";
		} else {
			return allDogs.toString();
		}
	}
}

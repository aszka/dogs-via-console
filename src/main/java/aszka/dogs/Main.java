package aszka.dogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.LimitExceededException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Main {

	// TODO javadoc
	// TODO asynchronimous logger

	private static final String nameRegex = "[A-Z]{1}[a-z]*( {1}[A-Z]{1}[a-z]*)*";
	private static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final int MAX_ATTEMPT = 5;
	public static final String ERROR_MAX_ATTEMPTS = "Maximum attempts is achieved";

	static final Logger logger = LoggerFactory.getLogger(Main.class);

	private static Scanner inputScanner = new Scanner(System.in);
	private static aszka.dogs.DogDAO dogDAO = new aszka.dogs.DogDAO();

	public static void main(String[] args) {
		String option;
		do {
			System.out.println("Hello! The following operations are available:");
			System.out.println("\t[1] Print the list of dogs.\n\t[2] Add a new dog to the database.\n\t[x] Close the program\nWhat is your choose? ");
			option = inputScanner.nextLine().trim();
			switch (option) {
				case ("1"):
					System.out.println(dogDAO.dogs());
					break;
				case ("2"):
					addDog();
					break;
				case ("x"):
					System.exit(0);
					break;
				default:

			}
		} while (!option.equals("x"));

		inputScanner.close();
	}

	private static void addDog() {
		Dog dog = new Dog();
		try {
			introduceDog(dog);
			dogDAO.addDog(dog);
		} catch (LimitExceededException e) {
			System.out.println(e.getMessage());
			logger.info("Max attempts to add a new dog was achieved");
		}
	}

	private static void introduceDog(Dog dog) throws LimitExceededException {
		dog.setName(getInputString("Type the dogs name: "));
		dog.setOwnersName(getInputString("Who is its keeper: "));
		dog.setBirthDate(getInputDate("Type the date of birth in yyyy-MM-dd format: ", YYYY_MM_DD));
	}

	private static String getInputString(String printMsg) throws LimitExceededException {
		int iterator = 1;
		while (iterator <= MAX_ATTEMPT) {
			iterator++;
			System.out.print(printMsg);
			String input = inputScanner.nextLine().trim();
			try {
				validateString(input, nameRegex);
				return input;
			} catch (PatternSyntaxException e) {
				System.out.println(e.getDescription());
			}
		}
		throw new LimitExceededException(ERROR_MAX_ATTEMPTS);
	}

	private static boolean validateString(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		if (matcher.matches()) {
			return true;
		} else {
			logger.info("User tried to add incorrect name");
			throw new PatternSyntaxException("The name shoud be started at big letter and doesn't contain numbers\n", regex, -1);
		}
	}

	private static LocalDate getInputDate(String printMsg, String datePattern) throws LimitExceededException {
		String input;
		int iterator = 1;
		while (iterator <= MAX_ATTEMPT) {
			iterator++;
			System.out.print(printMsg);
			input = inputScanner.nextLine().trim();
			try {
				return parseStringToDate(input, datePattern);
			} catch (DateTimeParseException e) {
				System.out.println("You typed incorrect date format\n");
				logger.info("User tried input date in a wrong format");
			}
		}
		throw new LimitExceededException(ERROR_MAX_ATTEMPTS);
	}

	private static LocalDate parseStringToDate(String dateString, String datePattern) throws DateTimeParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
		return LocalDate.parse(dateString, formatter);
	}

}

package aszka.dogs;

import java.time.LocalDate;

class Dog {

	private String name;
	private LocalDate birthDate;
	private String ownersName;

	public Dog() {
	}

	public Dog(String name, LocalDate birthDate, String ownersName) {
		this.name = name;
		this.birthDate = birthDate;
		this.ownersName = ownersName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getOwnersName() {
		return ownersName;
	}

	public void setOwnersName(String ownersName) {
		this.ownersName = ownersName;
	}

	public String intruduceYourself() {
		return "Cześć, jestem " + name + ", urodziłem się " + birthDate + ", a opiekuje się mną " + ownersName +"\n";
	}


}

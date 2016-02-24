package cz.martlin.jaxon.testings.jaxon;

public class TesingPersons {

	public TesingPersons() {
	}

	public static Person createMe() {
		Person john = new Person();

		john.setName("m@rtlin");
		john.setAge(42);
		john.setEmployed(false);

		return john;
	}
	
	public static Person createJohn() {
		Person john = new Person();

		john.setName("John");
		john.setAge(45);
		john.setEmployed(true);

		return john;
	}

}

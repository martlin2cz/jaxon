package cz.martlin.jaxon.testings.j2k.bike;

import java.util.Map;

import cz.martlin.jaxon.j2k.abstracts.JackToKlaxonSerializable;
import cz.martlin.jaxon.jack.abstracts.JackSerializable;
import cz.martlin.jaxon.testings.jaxon.person.Person;

/**
 * Data class representing some bike in some bikes-to-rent. 
 * @author martin
 *
 */
public class BikeToBorrow implements JackSerializable, JackToKlaxonSerializable {

	private int weight;
	private Integer maxSpeed;

	private double cost;
	private Person currentlyBorrowedBy;
	private Map<Person, Integer> previouslyBorrowedDays;

	public BikeToBorrow() {
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Integer getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Person getCurrentlyBorrowedBy() {
		return currentlyBorrowedBy;
	}

	public void setCurrentlyBorrowedBy(Person currentlyBorrowedBy) {
		this.currentlyBorrowedBy = currentlyBorrowedBy;
	}

	public Map<Person, Integer> getPreviouslyBorrowedDays() {
		return previouslyBorrowedDays;
	}

	public void setPreviouslyBorrowedDays(Map<Person, Integer> previouslyBorrowedDays) {
		this.previouslyBorrowedDays = previouslyBorrowedDays;
	}

	@Override
	public String jaxonDescription() {
		return "bike to borrow simple instance";
	}

}

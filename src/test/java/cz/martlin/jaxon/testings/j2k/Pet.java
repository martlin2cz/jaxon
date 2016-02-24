package cz.martlin.jaxon.testings.j2k;

import cz.martlin.jaxon.j2k.abstracts.JackToKlaxonSerializable;
import cz.martlin.jaxon.jack.abstracts.JackSerializable;

public class Pet implements JackSerializable, JackToKlaxonSerializable {

	private String name;
	private Gender gender;
	private Integer legs;
	private boolean canSwim;

	public Pet() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getLegs() {
		return legs;
	}

	public void setLegs(Integer legs) {
		this.legs = legs;
	}

	public boolean isCanSwim() {
		return canSwim;
	}

	public void setCanSwim(boolean canSwim) {
		this.canSwim = canSwim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (canSwim ? 1231 : 1237);
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((legs == null) ? 0 : legs.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		if (canSwim != other.canSwim)
			return false;
		if (gender != other.gender)
			return false;
		if (legs == null) {
			if (other.legs != null)
				return false;
		} else if (!legs.equals(other.legs))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pet [name=" + name + ", gender=" + gender + ", legs=" + legs
				+ ", canSwim=" + canSwim + "]";
	}

	@Override
	public String jaxonDescription() {
		return "Pet, what else?";
	}

}

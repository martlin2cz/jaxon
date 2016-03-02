package cz.martlin.jaxon.testings.jack.drink;

import java.io.File;
import java.util.Date;
import java.util.List;

import cz.martlin.jaxon.jack.abstracts.JackSerializable;
import cz.martlin.jaxon.testings.jaxon.person.Person;

/**
 * Represents simple dring. Drinh has name, type (hot/cold), volume optionally
 * amount of alcohol, ingredients list and menu data (photo, cost and info about
 * adding to menu (who and when)).
 * 
 * @author martin
 *
 */
public class Drink implements JackSerializable {
	private String name;
	private DrinkType type;
	private int volume;
	private Integer alcohol;

	private List<String> ingredients;
	private File image;

	private Date since;
	private Person addedBy;
	private double cost;

	public Drink() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DrinkType getType() {
		return type;
	}

	public void setType(DrinkType type) {
		this.type = type;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Integer getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(Integer alcohol) {
		this.alcohol = alcohol;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Person getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Person addedBy) {
		this.addedBy = addedBy;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addedBy == null) ? 0 : addedBy.hashCode());
		result = prime * result + ((alcohol == null) ? 0 : alcohol.hashCode());
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((since == null) ? 0 : since.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + volume;
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
		Drink other = (Drink) obj;
		if (addedBy == null) {
			if (other.addedBy != null)
				return false;
		} else if (!addedBy.equals(other.addedBy))
			return false;
		if (alcohol == null) {
			if (other.alcohol != null)
				return false;
		} else if (!alcohol.equals(other.alcohol))
			return false;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (since == null) {
			if (other.since != null)
				return false;
		} else if (!since.equals(other.since))
			return false;
		if (type != other.type)
			return false;
		if (volume != other.volume)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Drink [name=" + name + ", type=" + type + ", volume=" + volume + ", alcohol=" + alcohol
				+ ", ingredients=" + ingredients + ", image=" + image + ", since=" + since + ", addedBy=" + addedBy
				+ ", cost=" + cost + "]";
	}

}

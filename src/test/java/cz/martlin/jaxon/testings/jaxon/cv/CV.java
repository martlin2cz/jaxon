package cz.martlin.jaxon.testings.jaxon.cv;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.martlin.jaxon.jaxon.JaxonSerializable;

/**
 * Represents simple curriculum vitae. Compounds from personal informations
 * (name, birth, photo), characteristics, educations, languages and job
 * experiences.
 * 
 * @author martin
 *
 */
public class CV implements JaxonSerializable {
	private String firstName;
	private String lastName;

	private Date birth;
	private Set<String> characteristics;
	private File photo;

	private boolean drivingLicence;

	private List<Education> educations;
	private Map<String, LanguageLevel> languages;
	private List<WorkExperiece> jobs;

	public CV() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Set<String> getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(Set<String> characteristics) {
		this.characteristics = characteristics;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public boolean isDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(boolean drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public Map<String, LanguageLevel> getLanguages() {
		return languages;
	}

	public void setLanguages(Map<String, LanguageLevel> languages) {
		this.languages = languages;
	}

	public List<WorkExperiece> getJobs() {
		return jobs;
	}

	public void setJobs(List<WorkExperiece> jobs) {
		this.jobs = jobs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birth == null) ? 0 : birth.hashCode());
		result = prime * result + ((characteristics == null) ? 0 : characteristics.hashCode());
		result = prime * result + (drivingLicence ? 1231 : 1237);
		result = prime * result + ((educations == null) ? 0 : educations.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((jobs == null) ? 0 : jobs.hashCode());
		result = prime * result + ((languages == null) ? 0 : languages.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
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
		CV other = (CV) obj;
		if (birth == null) {
			if (other.birth != null)
				return false;
		} else if (!birth.equals(other.birth))
			return false;
		if (characteristics == null) {
			if (other.characteristics != null)
				return false;
		} else if (!characteristics.equals(other.characteristics))
			return false;
		if (drivingLicence != other.drivingLicence)
			return false;
		if (educations == null) {
			if (other.educations != null)
				return false;
		} else if (!educations.equals(other.educations))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (jobs == null) {
			if (other.jobs != null)
				return false;
		} else if (!jobs.equals(other.jobs))
			return false;
		if (languages == null) {
			if (other.languages != null)
				return false;
		} else if (!languages.equals(other.languages))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CV [firstName=" + firstName + ", lastName=" + lastName + ", birth=" + birth + ", characteristics="
				+ characteristics + ", photo=" + photo + ", drivingLicence=" + drivingLicence + ", educations="
				+ educations + ", languages=" + languages + ", jobs=" + jobs + "]";
	}

	@Override
	public String jaxonDescription() {
		return "A simple curriculum vitae";
	}

}

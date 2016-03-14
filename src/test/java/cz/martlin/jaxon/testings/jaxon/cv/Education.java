package cz.martlin.jaxon.testings.jaxon.cv;

import java.util.Date;

import cz.martlin.jaxon.jaxon.JaxonSerializable;

/**
 * Represents item of education. Compouds from name of school and subject and
 * (optionally) date of start and end.
 * 
 * @author martin
 *
 */
public class Education implements JaxonSerializable {

	protected String schoolName;
	protected String subject;
	protected Date since;
	protected Date until;

	public Education() {
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Date getUntil() {
		return until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schoolName == null) ? 0 : schoolName.hashCode());
		result = prime * result + ((since == null) ? 0 : since.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((until == null) ? 0 : until.hashCode());
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
		Education other = (Education) obj;
		if (schoolName == null) {
			if (other.schoolName != null)
				return false;
		} else if (!schoolName.equals(other.schoolName))
			return false;
		if (since == null) {
			if (other.since != null)
				return false;
		} else if (!since.equals(other.since))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (until == null) {
			if (other.until != null)
				return false;
		} else if (!until.equals(other.until))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Education [schoolName=" + schoolName + ", subject=" + subject + ", since=" + since + ", until=" + until
				+ "]";
	}

	@Override
	public String jaxonDescription() {
		return "Education";
	}

}

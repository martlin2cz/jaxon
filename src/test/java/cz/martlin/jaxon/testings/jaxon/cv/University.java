package cz.martlin.jaxon.testings.jaxon.cv;

import java.util.Date;

/**
 * Represents item of {@link Education} from university. Has a diploma theme and degree.
 * @author martin
 *
 */
public class University extends Education {
	private String diploma;
	private String degree;

	public University() {
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result + ((diploma == null) ? 0 : diploma.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		University other = (University) obj;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (diploma == null) {
			if (other.diploma != null)
				return false;
		} else if (!diploma.equals(other.diploma))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "University [diploma=" + diploma + ", degree=" + degree + "]";
	}

	/**
	 * Creates university instance for given data.
	 * @param name
	 * @param subject
	 * @param diploma
	 * @param degree
	 * @param since (optional) time in UTC
	 * @param until (optional) time in UTC
	 * @return
	 */
	public static University create(String name, String subject, String diploma, String degree, Long since,
			Long until) {
		University u = new University();

		u.setSchoolName(name);
		u.setSubject(subject);

		if (since != null) {
			u.setSince(new Date(since));
		}
		if (until != null) {
			u.setUntil(new Date(until));
		}

		u.setDiploma(diploma);
		u.setDegree(degree);

		return u;
	}

}

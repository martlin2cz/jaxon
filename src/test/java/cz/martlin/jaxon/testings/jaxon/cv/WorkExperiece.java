package cz.martlin.jaxon.testings.jaxon.cv;

import java.util.Date;

import cz.martlin.jaxon.jaxon.JaxonSerializable;

/**
 * Experience of work. Has name of company and profession and dates from and to.
 * 
 * @author martin
 *
 */
public class WorkExperiece implements JaxonSerializable {
	private String company;
	private String profesion;
	private Date since;
	private Date until;

	public WorkExperiece() {
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
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
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((profesion == null) ? 0 : profesion.hashCode());
		result = prime * result + ((since == null) ? 0 : since.hashCode());
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
		WorkExperiece other = (WorkExperiece) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (profesion == null) {
			if (other.profesion != null)
				return false;
		} else if (!profesion.equals(other.profesion))
			return false;
		if (since == null) {
			if (other.since != null)
				return false;
		} else if (!since.equals(other.since))
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
		return "WorkExperiece [company=" + company + ", profesion=" + profesion + ", since=" + since + ", until="
				+ until + "]";
	}

	@Override
	public String jaxonDescription() {
		return "Work experience";
	}

}

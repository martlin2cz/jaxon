package cz.martlin.jaxon.testings.jaxon.cv;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cz.martlin.jaxon.testings.tuples.JaxonTestTuple;

/**
 * Jaxon test tuple of type {@link CV}.
 * 
 * @author martin
 *
 */
public class CVsJaxonTestTuple implements JaxonTestTuple<CV> {

	private final CV object;

	public CVsJaxonTestTuple(CV object) {
		super();
		this.object = object;
	}

	public CVsJaxonTestTuple(String firstName, String lastName, Date birth, Set<String> characteristics, File photo,
			boolean drivingLicence, List<Education> educations, Map<String, LanguageLevel> languages,
			List<WorkExperiece> jobs) {

		this.object = new CV();
		object.setFirstName(firstName);
		object.setLastName(lastName);
		object.setBirth(birth);
		object.setCharacteristics(characteristics);
		object.setPhoto(photo);
		object.setDrivingLicence(drivingLicence);
		object.setEducations(educations);
		object.setLanguages(languages);
		object.setJobs(jobs);
	}

	@Override
	public CV createObject() {
		return object;
	}

	/**
	 * Gets tuple with Linus Torvald's CV.
	 * 
	 * @return
	 */
	public static CVsJaxonTestTuple getLinusTorvalds() {
		String firstName = "Linus";
		String lastName = "Torvalrd";
		Date birth = new Date(0l);
		boolean drivingLicence = true;
		File photo = new File("/home/linus/me.jpg");

		List<WorkExperiece> jobs = new ArrayList<>();
		addWork(jobs, "Transmeta Corporation", "Huh..", 854755200l, 1054425600l);
		addWork(jobs, "Open Source Development Labs", "Boss?", 1054425600l, null);

		Set<String> characteristics = createChars("Familly Type", "Open source", "Inovative", "Geeky");

		Map<String, LanguageLevel> languages = createLangs("Finish", "English", LanguageLevel.C1, "Swedish",
				LanguageLevel.B1, null, null);

		List<Education> educations = new ArrayList<>();
		educations.add(University.create("University Of Helsinki", "Computer Science",
				"Linux: A Portable Operating System", "M.S.", 1054425600l, 820454400l));

		return new CVsJaxonTestTuple(firstName, lastName, birth, characteristics, photo, drivingLicence, educations,
				languages, jobs);
	}

	private static Set<String> createChars(String... propreties) {
		Set<String> result = new TreeSet<>();

		result.addAll(Arrays.asList(propreties));

		return null;
	}

	private static Map<String, LanguageLevel> createLangs(String nativeLang, //
			String firstLang, LanguageLevel firstLevel, //
			String secondLang, LanguageLevel secondLevel, //
			String thirdLang, LanguageLevel thirdLevel) {

		Map<String, LanguageLevel> result = new TreeMap<>();

		result.put(nativeLang, LanguageLevel.NATIVE);

		if (firstLang != null) {
			result.put(firstLang, firstLevel);
		}

		if (secondLang != null) {
			result.put(secondLang, secondLevel);
		}

		if (thirdLang != null) {
			result.put(thirdLang, thirdLevel);
		}

		return result;
	}

	private static void addWork(List<WorkExperiece> jobs, String company, String profesion, Long since, Long until) {
		WorkExperiece work = new WorkExperiece();
		work.setCompany(company);
		work.setProfesion(profesion);

		if (since != null) {
			work.setSince(new Date(since));
		}
		if (until != null) {
			work.setUntil(new Date(until));
		}

		jobs.add(work);
	}
}

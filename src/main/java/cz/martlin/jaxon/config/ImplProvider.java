package cz.martlin.jaxon.config;

public class ImplProvider {

	public static Config getTestingConfig() {
		return new Config();
	}
	
	public static Config getDefaultConfig() {
		return new Config();
	}

	// TODO load config from file?

}

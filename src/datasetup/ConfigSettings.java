package datasetup;

import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigSettings {

	Properties properties = null;
	private static final Logger logger = LogManager.getLogger(ConfigSettings.class);

	public void loadPropFile(){
		properties = new Properties();
		String fileName = "resources\\settings.properties";
		InputStream inStream = null;

		try {
			inStream = getClass().getClassLoader().getResourceAsStream(fileName);
			properties.load(inStream);
			logger.info("Loading settings.properties file........");
		} catch (Exception e) {
			logger.fatal("Unable to load settings.properties file!", e);
			System.exit(0);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (Exception e) {
					logger.error("Unable to close settings.properties file!", e);
					System.exit(0);
				}
			}
		}
	}

}

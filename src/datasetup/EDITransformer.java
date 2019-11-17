package datasetup;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EDITransformer {

	private static Map<String, LineTransformer> transformers = new HashMap<>();

	private static final Logger logger = LogManager.getLogger(AppendingLineTransformerImpl.class);

	public static void addTransformer(String key, LineTransformer transformer) {
		if (!transformers.containsKey(key)) {
			transformers.put(key, transformer);
		}
	}

	public static String transform(String line, String key, String discr) {
		String transformedLine = line;
		try {
			if (line.startsWith(discr)) {
				logger.info("Matching modification phrase found!");
				transformedLine = transformers.get(key).transform(line);
			}
		} catch (Exception e) {
			logger.error("Transformation error!", e);
		}
		return transformedLine + "\n";
	}

}

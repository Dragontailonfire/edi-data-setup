package datasetup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppendingLineTransformerImpl implements LineTransformer {
	String extraData;
	private static final Logger logger = LogManager.getLogger(AppendingLineTransformerImpl.class);

	public AppendingLineTransformerImpl(String extraData) {
		super();
		this.extraData = extraData;
	}

	@Override
	public String transform(String input) {
		if (input.trim().endsWith("'")) {
			input = input.trim().substring(0, input.length() - 2);
			input = input + extraData;
			logger.info("Data appended!");
		}
		return input;
	}

	@Override
	public void setExtraData(String data) {
		this.extraData = data;
	}

}

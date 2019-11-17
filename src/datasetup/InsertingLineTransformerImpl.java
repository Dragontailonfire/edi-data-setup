package datasetup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InsertingLineTransformerImpl implements LineTransformer {
	String extraData;
	private static final Logger logger = LogManager.getLogger(InsertingLineTransformerImpl.class);

	public InsertingLineTransformerImpl(String extraData) {
		super();
		this.extraData = extraData;
	}

	@Override
	public String transform(String input) {
		input = input + "\n" + extraData;
		logger.info("Data inserted!");
		return input;
	}

	@Override
	public void setExtraData(String data) {
		this.extraData = data;
	}

}

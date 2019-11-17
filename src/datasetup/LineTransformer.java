package datasetup;

public interface LineTransformer {
	String transform(String input);
	void setExtraData(String data);
}

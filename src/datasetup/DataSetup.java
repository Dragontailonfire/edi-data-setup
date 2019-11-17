package datasetup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataSetup {

	private static final Logger logger = LogManager.getLogger(DataSetup.class);

	public static void main(String[] args) {
		String inputFileName = null;
		ConfigSettings settings = new ConfigSettings();

		try {
			settings.loadPropFile();
			logger.info("Settings loaded!");
		} catch (Exception e1) {
			logger.fatal("Unable to load settings!", e1);
			System.exit(0);
		}

		String excelPath = settings.properties.getProperty("DataFilePath")
				+ settings.properties.getProperty("DataFileName");
		logger.info("Excel Path:- " + excelPath);

		try {
			ExcelFileReader.setExcelFile(excelPath, settings.properties.getProperty("DataSheetName"));
			logger.info("Excel sheet loaded!");
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.fatal("Unable to load excel!", e1);
		}

		rowloop: for (int row = 1; true; row++) {
			logger.info("Read File name from row: " + row);
			colloop: for (int col = 2; col < 10; col++) {
				try {
					inputFileName = ExcelFileReader.getInputFileName(row);

					if (inputFileName == null || inputFileName.trim().length() == 0) {
						logger.warn("No File name in row: " + row);
						break rowloop;
					}
					logger.info("Read modification value from row: " + row + " col: " + col);
					String key = ExcelFileReader.getAppendingPhrase(row, col);

					if (key == null || key.trim().length() == 0) {
						logger.warn("No modification value in row: " + row + " col: " + col);
						continue colloop;
					}
					logger.info("Read modification Phrase from col: " + col);
					String discr = ExcelFileReader.getAppendingPhrase(0, col);
					if (col == 2) {
						logger.info("Set Appender for col: " + col);
						EDITransformer.addTransformer(key, new AppendingLineTransformerImpl(key));
					} else {
						logger.info("Set Inserter for col: " + col);
						EDITransformer.addTransformer(key, new InsertingLineTransformerImpl(key));
					}

					BufferedReader br = null;
					StringBuffer outContent = new StringBuffer();
					try {
						br = new BufferedReader(new FileReader(inputFileName));
						String originalLine = br.readLine();
						logger.info("Start file read!");
						// System.out.println("Start\n" + originalLine);
						while (originalLine != null) {
							// System.out.println(line);
							String finalLine = EDITransformer.transform(originalLine, key, discr);
							outContent.append(finalLine);
							originalLine = br.readLine();
						}
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (Exception e) {
								logger.error("Unable to close data file!", e);
								System.exit(0);
							}
						}
					}

					PrintWriter pw = null;
					try {
						logger.info("Start file write!");
						pw = new PrintWriter(new FileWriter(inputFileName));
						pw.println(outContent);
						pw.flush();
					} finally {
						if (pw != null) {
							pw.close();
						}
					}
				} catch (Exception le) {
					logger.error("Unable to read file!", le);
				}
			}
		}
	}

}

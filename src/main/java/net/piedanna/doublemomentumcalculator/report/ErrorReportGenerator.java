package net.piedanna.doublemomentumcalculator.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorReportGenerator implements ReportGenerator {

	private static final Logger LOGGER =  LoggerFactory.getLogger(ErrorReportGenerator.class);
	private final String errorMessage;

	public ErrorReportGenerator(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void generate() {
		LOGGER.error(errorMessage);
	}
}

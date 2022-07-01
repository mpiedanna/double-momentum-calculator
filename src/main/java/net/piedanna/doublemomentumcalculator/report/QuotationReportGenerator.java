package net.piedanna.doublemomentumcalculator.report;

import net.piedanna.doublemomentumcalculator.model.PriceSummary;
import net.piedanna.doublemomentumcalculator.model.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class QuotationReportGenerator implements ReportGenerator {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(QuotationReportGenerator.class);
	private final Map<Tracker, PriceSummary> quote;
	private final Tracker trackerToChoose;
	
	public QuotationReportGenerator(Tracker trackerToChoose, Map<Tracker, PriceSummary> quote) {
		this.quote = quote;
		this.trackerToChoose = trackerToChoose;
	}
	
	public void generate() {
		generatePriceSummaries();
		LOGGER.info("********************");
		LOGGER.info("Tracker to choose : {}", trackerToChoose.getSummary());
	}
	
	private void generatePriceSummaries() {
		quote.forEach((key, priceSummary) -> {
			LOGGER.info("=== {} ===", key.getSummary());
			LOGGER.info("Price 1 year ago : {}$", priceSummary.getLastYearPrice());
			LOGGER.info("Today's price : {}$", priceSummary.getTodaysPrice());
			LOGGER.info("Performance : {}%", priceSummary.getPerformance());
		});
	}
}

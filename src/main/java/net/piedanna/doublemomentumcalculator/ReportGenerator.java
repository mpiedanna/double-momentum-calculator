package net.piedanna.doublemomentumcalculator;

import net.piedanna.doublemomentumcalculator.model.PriceSummary;
import net.piedanna.doublemomentumcalculator.model.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ReportGenerator {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(ReportGenerator.class);
	private final Map<Tracker, PriceSummary> quote;
	private final Tracker trackerToChoose;
	
	public ReportGenerator(Tracker trackerToChoose, Map<Tracker, PriceSummary> quote) {
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
			LOGGER.info("Price 1 year ago : {}$", priceSummary.getLastYearPrice().toString());
			LOGGER.info("Today's price : {}$", priceSummary.getTodaysPrice().toString());
			LOGGER.info("Performance : {}%", priceSummary.getPerformance().toString());
		});
	}
}

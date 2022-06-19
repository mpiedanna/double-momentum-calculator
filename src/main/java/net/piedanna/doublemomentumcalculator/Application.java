package net.piedanna.doublemomentumcalculator;

import net.piedanna.doublemomentumcalculator.model.PriceSummary;
import net.piedanna.doublemomentumcalculator.model.Tracker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class Application {

	public static void main(String... args) throws IOException, InterruptedException, URISyntaxException {
		
		final FinanceQuotationClient financeQuote = new FinanceQuotationClient();
		final Map<Tracker, PriceSummary> quote = financeQuote.getQuote();
		
		final DoubleMomentumCalculator doubleMomentumCalculator = new DoubleMomentumCalculator(quote);
		final Tracker trackerToChoose = doubleMomentumCalculator.calculate();
		
		final ReportGenerator reportGenerator = new ReportGenerator(trackerToChoose, quote);
		reportGenerator.generate();
	}
}

package net.piedanna.doublemomentumcalculator;

import net.piedanna.doublemomentumcalculator.exception.DoubleMomentumCalculatorException;
import net.piedanna.doublemomentumcalculator.exception.FinanceQuotationClientException;
import net.piedanna.doublemomentumcalculator.model.PriceSummary;
import net.piedanna.doublemomentumcalculator.model.Tracker;
import net.piedanna.doublemomentumcalculator.report.ErrorReportGenerator;
import net.piedanna.doublemomentumcalculator.report.QuotationReportGenerator;
import net.piedanna.doublemomentumcalculator.report.ReportGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class Application {

	public static void main(String... args) {
		try {
			final FinanceQuotationClient financeQuote = new FinanceQuotationClient();
			final Map<Tracker, PriceSummary> quote = financeQuote.getQuote();

			final DoubleMomentumCalculator doubleMomentumCalculator = new DoubleMomentumCalculator(quote);
			final Tracker trackerToChoose = doubleMomentumCalculator.calculate();

			final ReportGenerator quotationReportGenerator = new QuotationReportGenerator(trackerToChoose, quote);
			quotationReportGenerator.generate();
		} catch (DoubleMomentumCalculatorException | FinanceQuotationClientException | IOException | URISyntaxException e) {
			final String errorMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			final ReportGenerator errorReportGenerator = new ErrorReportGenerator(errorMessage);
			errorReportGenerator.generate();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}

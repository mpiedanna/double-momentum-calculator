package net.piedanna.doublemomentumcalculator;

import lombok.RequiredArgsConstructor;
import net.piedanna.doublemomentumcalculator.exception.DoubleMomentumCalculatorException;
import net.piedanna.doublemomentumcalculator.model.PriceSummary;
import net.piedanna.doublemomentumcalculator.model.Tracker;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DoubleMomentumCalculator {
	
	private final Map<Tracker, PriceSummary> prices;
	
	public Tracker calculate() {
		final Tracker bestTrackerPerformanceSinceLastTwelveMonth = getBestTrackerPerformanceSinceLastTwelveMonth();
		
		if (!List.of(Tracker.CSPX_L, Tracker.IXUS).contains(bestTrackerPerformanceSinceLastTwelveMonth)) {
			throw new DoubleMomentumCalculatorException("Tracker not found.");
		}
		
		Tracker trackerToChoice = Tracker.BIL;
		
		// S&P 500 
		// ACWI EX-US
		if (Tracker.CSPX_L.equals(bestTrackerPerformanceSinceLastTwelveMonth) &&
				calculateAbsoluteMomentum(prices.get(Tracker.CSPX_L), prices.get(Tracker.BIL))) {
			trackerToChoice = Tracker.CSPX_L;
		} else if (Tracker.IXUS.equals(bestTrackerPerformanceSinceLastTwelveMonth)
				&& calculateAbsoluteMomentum(prices.get(Tracker.IXUS), prices.get(Tracker.BIL))){
			trackerToChoice = Tracker.IXUS;
		}
		
		return trackerToChoice;
	}
	
	private Tracker getBestTrackerPerformanceSinceLastTwelveMonth() {
		return prices
				.entrySet()
				.stream()
				.filter(price -> List.of(Tracker.CSPX_L, Tracker.IXUS).contains(price.getKey()))
				.max(Map.Entry.comparingByValue(Comparator.comparing(PriceSummary::getPerformance)))
				.map(Map.Entry::getKey)
				.orElseThrow(() -> new DoubleMomentumCalculatorException("Problem with retrieving the best tracker."));
	}
	
	private boolean calculateAbsoluteMomentum(final PriceSummary firstTracker, final PriceSummary secondTracker) {
		return firstTracker.getPerformance().subtract(secondTracker.getPerformance()).compareTo(BigDecimal.ZERO) > 0;
	}
}

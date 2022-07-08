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
		
		if (!List.of(Tracker.VOO, Tracker.VXUS).contains(bestTrackerPerformanceSinceLastTwelveMonth)) {
			throw new DoubleMomentumCalculatorException("Tracker not found.");
		}
		
		Tracker trackerToChoice = Tracker.VGIT;
		
		// S&P 500 
		// ACWI EX-US
		if (Tracker.VOO.equals(bestTrackerPerformanceSinceLastTwelveMonth) &&
				calculateAbsoluteMomentum(prices.get(Tracker.VOO), prices.get(Tracker.VGIT))) {
			trackerToChoice = Tracker.VOO;
		} else if (Tracker.VXUS.equals(bestTrackerPerformanceSinceLastTwelveMonth)
				&& calculateAbsoluteMomentum(prices.get(Tracker.VXUS), prices.get(Tracker.VGIT))){
			trackerToChoice = Tracker.VXUS;
		}
		
		return trackerToChoice;
	}
	
	private Tracker getBestTrackerPerformanceSinceLastTwelveMonth() {
		return prices
				.entrySet()
				.stream()
				.filter(price -> List.of(Tracker.VOO, Tracker.VXUS).contains(price.getKey()))
				.max(Map.Entry.comparingByValue(Comparator.comparing(PriceSummary::getPerformance)))
				.map(Map.Entry::getKey)
				.orElseThrow(() -> new DoubleMomentumCalculatorException("Problem with retrieving the best tracker."));
	}
	
	private boolean calculateAbsoluteMomentum(final PriceSummary firstTracker, final PriceSummary secondTracker) {
		return firstTracker.getPerformance().subtract(secondTracker.getPerformance()).compareTo(BigDecimal.ZERO) > 0;
	}
}

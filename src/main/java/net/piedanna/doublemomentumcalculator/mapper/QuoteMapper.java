package net.piedanna.doublemomentumcalculator.mapper;

import net.piedanna.doublemomentumcalculator.model.*;

import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum QuoteMapper {

	INSTANCE;
	
	public Map<Tracker, PriceSummary> mapQuoteToPriceSummary(final Quote quote) {
		final LongSummaryStatistics summaryTimestamp = getSummaryTimestamp(quote);
		final Map<Tracker, PriceSummary> comparisons = getComparisons(quote, summaryTimestamp);
		final Map<Tracker, PriceSummary> indicator = getIndicator(quote, summaryTimestamp);
	
		return Stream.concat(
				comparisons.entrySet().stream(), 
				indicator.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private Map<Tracker, PriceSummary> getIndicator(final Quote quote, final LongSummaryStatistics summaryTimestamp) {
		return quote
				.getChart()
				.getResult()
				.stream()
				.map(Result::getIndicators)
				.collect(
					Collectors.toMap(i -> Tracker.CSPX_L, 
						i -> new PriceSummary(
							i.getAdjclose().get(0).getAdjclose().get(getIndex(quote, summaryTimestamp.getMin()).intValue()),
							i.getAdjclose().get(0).getAdjclose().get(getIndex(quote, summaryTimestamp.getMax()).intValue())
						)
					)
		);
	}

	private Map<Tracker, PriceSummary> getComparisons(final Quote quote, final LongSummaryStatistics summaryTimestamp) {
		return quote
				.getChart()
				.getResult()
				.stream()
				.flatMap(result -> result.getComparisons().stream())
				.collect(
					Collectors.toMap(Comparison::getSymbol,
							comparison -> new PriceSummary(
									comparison.getClose().get(getIndex(quote, summaryTimestamp.getMin()).intValue()),
									comparison.getClose().get(getIndex(quote, summaryTimestamp.getMax()).intValue())
							)
					)
		);
	}
	
	private LongSummaryStatistics getSummaryTimestamp(final Quote quote) {
		return getTimestampStream(quote)
				.mapToLong(timestamp -> timestamp)
				.summaryStatistics();
	}
	
	private Long getIndex(final Quote quote, final Long timestamp) {
		return getTimestampStream(quote)
				.takeWhile(t -> !t.equals(timestamp))
				.count();
	}
	
	private Stream<Long> getTimestampStream(final Quote quote) {
		return quote
				.getChart()
				.getResult()
				.stream()
				.flatMap(result -> result.getTimestamp().stream());
	}
}

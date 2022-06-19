package net.piedanna.doublemomentumcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor
public class PriceSummary {

	private BigDecimal lastYear;
	private BigDecimal today;

	public BigDecimal getLastYearPrice() {
		return this.lastYear.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getTodaysPrice() {
		return this.today.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getPerformance() {
		return this.today.divide(this.lastYear, 2, RoundingMode.HALF_UP)
				.subtract(new BigDecimal("1"))
				.multiply(new BigDecimal("100"));
	}
}

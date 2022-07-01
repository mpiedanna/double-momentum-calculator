package net.piedanna.doublemomentumcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.piedanna.doublemomentumcalculator.exception.DoubleMomentumCalculatorException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor
public class PriceSummary {

	private BigDecimal lastYear;
	private BigDecimal today;

	public BigDecimal getLastYearPrice() {
		return this.lastYear != null ? this.lastYear.setScale(2, RoundingMode.HALF_UP) : null;
	}

	public BigDecimal getTodaysPrice() {
		return this.today != null ? this.today.setScale(2, RoundingMode.HALF_UP) : null;
	}

	public BigDecimal getPerformance() {
		if (this.today == null || this.lastYear == null) {
			throw new DoubleMomentumCalculatorException("Cannot compute tracker performance.");
		}

		return this.today.divide(this.lastYear, 2, RoundingMode.HALF_UP)
				.subtract(new BigDecimal("1"))
				.multiply(new BigDecimal("100"));
	}
}

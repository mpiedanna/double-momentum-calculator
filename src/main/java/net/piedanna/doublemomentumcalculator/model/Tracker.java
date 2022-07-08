package net.piedanna.doublemomentumcalculator.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Tracker {

	VGIT("VGIT", "Vanguard Intermediate-Term Treasury Index Fund", "Treasury Bonds"),
	VOO("VOO", "Vanguard 500 Index Fund", "S&P 500"),
	VXUS("VXUS", "Vanguard Total Intl Stock Idx Fund", "ACWI EX-US");

	@JsonValue
	private final String ticker;
	private final String name;
	private final String summary;
	
	Tracker(String ticker, String name, String summary) {
		this.ticker = ticker;
		this.name = name;
		this.summary = summary;
	}
}

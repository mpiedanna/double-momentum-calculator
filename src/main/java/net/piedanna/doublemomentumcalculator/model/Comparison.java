package net.piedanna.doublemomentumcalculator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Comparison {
	
	private Tracker symbol;
	private List<BigDecimal> close;
}

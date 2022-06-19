package net.piedanna.doublemomentumcalculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class AdjClose {

	@JsonProperty("adjclose")
	private List<BigDecimal> close;
}

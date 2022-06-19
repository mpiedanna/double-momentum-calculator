package net.piedanna.doublemomentumcalculator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Chart {
	private List<Result> result;
}

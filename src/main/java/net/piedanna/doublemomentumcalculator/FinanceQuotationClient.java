package net.piedanna.doublemomentumcalculator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.piedanna.doublemomentumcalculator.constant.ApiConstants;
import net.piedanna.doublemomentumcalculator.exception.FinanceQuotationClientException;
import net.piedanna.doublemomentumcalculator.mapper.QuoteMapper;
import net.piedanna.doublemomentumcalculator.model.PriceSummary;
import net.piedanna.doublemomentumcalculator.model.Quote;
import net.piedanna.doublemomentumcalculator.model.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

public class FinanceQuotationClient {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(FinanceQuotationClient.class);
	
	private final HttpClient client;
	private final ObjectMapper objectMapper;
	
	public FinanceQuotationClient() {
		client = HttpClient.newHttpClient();
		objectMapper = new ObjectMapper();
	}
	
	private HttpRequest createRequest() throws URISyntaxException {
		return HttpRequest.newBuilder()
				  .uri(new URI(
						  ApiConstants.BASE_URL + 
						  Tracker.CSPX_L.getTicker() +
						  "?comparisons=" + Tracker.BIL.getTicker() +
						  "%2C" + Tracker.IXUS.getTicker() + "&range=1y&region=US&interval=1mo&lang=en"))
				  .version(HttpClient.Version.HTTP_2)
				  .header("Content-Type", "application/json")
				  .header("X-API-KEY", ApiConstants.TOKEN)
				  .GET()
				  .build();
	}
	
	public Map<Tracker, PriceSummary> getQuote() throws IOException, InterruptedException, URISyntaxException {
		LOGGER.info("Request execution...");
		final HttpResponse<String> response = client.send(createRequest(), BodyHandlers.ofString());
		final Integer statusCode = response.statusCode();
		LOGGER.info("Status code {}", statusCode);

		if (!"200".equals(statusCode.toString())) {
			throw new FinanceQuotationClientException("Error during the execution of the request.");
		}
		
		final Quote quote = objectMapper.readValue(response.body(), new TypeReference<>() {});
		
		return QuoteMapper.INSTANCE.mapQuoteToPriceSummary(quote);
	}
}

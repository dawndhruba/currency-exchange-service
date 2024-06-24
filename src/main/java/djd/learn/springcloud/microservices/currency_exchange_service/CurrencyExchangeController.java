package djd.learn.springcloud.microservices.currency_exchange_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@GetMapping(path = "/currency-exchange/from/{fromCurrency}/to/{toCurrency}")
	public ResponseEntity<CurrencyExchange> getExchangeRate(@PathVariable String fromCurrency, @PathVariable String toCurrency) {
		CurrencyExchange exchange = repository.findByFromAndTo(fromCurrency, toCurrency);
		
		if(exchange == null) {
			throw new RuntimeException("Unable to find conversion rate");
		}
		
		exchange.setEnvironment(environment.getProperty("local.server.port"));
		
		
		
		return new ResponseEntity<CurrencyExchange>(exchange, HttpStatus.OK);
	}
}

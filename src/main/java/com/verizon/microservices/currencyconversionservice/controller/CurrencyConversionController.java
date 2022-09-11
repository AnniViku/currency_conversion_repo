package com.verizon.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.verizon.microservices.currencyconversionservice.bean.CurrencyConversion;
import com.verizon.microservices.currencyconversionservice.feignProxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCuurenConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		HashMap<String, String> uriValiable = new HashMap<>();
		uriValiable.put("from", from);
		uriValiable.put("to", to);
		ResponseEntity<CurrencyConversion> resEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriValiable);
		CurrencyConversion resbody = resEntity.getBody();

		return new CurrencyConversion(resbody.getId(), from, to, quantity, resbody.getCoversationMultile(),
				quantity.multiply(resbody.getCoversationMultile()), resbody.getEnvironment());
		// return new CurrencyConversion(1000L, from, to, quantity, BigDecimal.ONE,
		// BigDecimal.ONE, "");

	}

	
	  //feign method
	  
	  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}") 
	  public CurrencyConversion calculateCuurenConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
	  
	  CurrencyConversion resbody = proxy.retrieveExchangeValue(from, to);
	  
	  return new CurrencyConversion(resbody.getId(), from, to, quantity,resbody.getCoversationMultile(),quantity.multiply(resbody.getCoversationMultile()),
			  resbody.getEnvironment()); 
	  //return new CurrencyConversion(1000L, from, to, * quantity, BigDecimal.ONE, BigDecimal.ONE, "");
	  
	  }
	 
}

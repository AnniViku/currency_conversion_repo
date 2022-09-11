
package com.verizon.microservices.currencyconversionservice.feignProxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.verizon.microservices.currencyconversionservice.bean.CurrencyConversion;

   @FeignClient(name="currency-exchange-service", url ="http://localhost:8000/")

//Instead of using above@FeignClient we will use below one to get the   load-balancing from eureka naming service
  
  //@FeignClient(name="currency-exchange-service") => here all we want feginClient to talk to eureka server and load balancing between them
  
  //@FeignClient(name="currency-exchange-service") 
  public interface CurrencyExchangeProxy {
  
  
  @GetMapping("/currency-exchange/from/{from}/to/{to}") public
  CurrencyConversion retrieveExchangeValue(@PathVariable String
  from, @PathVariable String to);
  
  
  }

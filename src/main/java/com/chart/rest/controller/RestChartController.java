package com.chart.rest.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chart.rest.entities.IndexPrice;
import com.chart.rest.entities.IndexPriceNotFoundException;
import com.chart.rest.repositories.IndexPriceRepository;

@RestController
public class RestChartController {

	private final IndexPriceRepository repository;

	RestChartController(IndexPriceRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/")
	String hello() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			String hostname = InetAddress.getLocalHost().getHostName();
			return "[ip:"+ip+", hostname:"+hostname+"] helloWorld02";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}		
		
	}
	
	@GetMapping("/IndexPrices")
	List<IndexPrice> all() {
		return repository.findAll();
	}
	// end::get-aggregate-root[]

	@PostMapping("/indexPrice")
	IndexPrice newEmployee(@RequestBody IndexPrice newIndexPrice) {
		return repository.save(newIndexPrice);
	}

	// Single item

	@GetMapping("/indexPrice/{startAt}")
	IndexPrice one(@PathVariable String startAt) {

		return repository.findByStartAt(startAt).orElseThrow(() -> new IndexPriceNotFoundException(startAt));

	}

	@PutMapping("/employees/{id}")
	IndexPrice replaceEmployee(@RequestBody IndexPrice newIndexPrice, @PathVariable String startAt) {

		return repository.findByStartAt(startAt) //
				.map(indexPrice -> {
					indexPrice.replace(newIndexPrice);
					return repository.save(indexPrice);
				}) //
				.orElseGet(() -> {
					newIndexPrice.setStartAt(startAt);
					return repository.save(newIndexPrice);
				});
	}

	@DeleteMapping("/indexPrice/{StartAt}")
	void deleteEmployee(@PathVariable String StartAt) {
		repository.deleteByStartAt(StartAt);
	}
}

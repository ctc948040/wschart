package com.chart.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.chart.rest.repositories.IndexPriceRepository;
import com.chart.util.FakeDate;

@Configuration
public class RestChartConfig {
	
	@Autowired
	IndexPriceRepository indexPriceRepository;
	
    @Bean
    public FakeDate getFakeDate() {
    	long minStartAt = Long.parseLong(indexPriceRepository.findByMinStartAt()); 
        return new FakeDate(minStartAt);
    }
}
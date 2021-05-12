package com.chart.rest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chart.rest.entities.IndexPrice;

@Repository
public interface IndexPriceRepository extends JpaRepository<IndexPrice, String > {

	Optional<IndexPrice> findByStartAt(String startAt);

	void deleteByStartAt(String startAt);
	
	@Query("SELECT MIN(startAt) FROM IndexPrice")
    String findByMinStartAt();
	
	//List<Tradingbtcusd> findBySymbol(String symbol);
	//IndexPrice findByStartAt(String startAt);
}
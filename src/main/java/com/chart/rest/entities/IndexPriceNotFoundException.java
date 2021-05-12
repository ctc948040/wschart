package com.chart.rest.entities;

public class IndexPriceNotFoundException extends RuntimeException {

	public IndexPriceNotFoundException(String startAt) {
	    super("Could not find IndexPrice " + startAt);
	  }

}

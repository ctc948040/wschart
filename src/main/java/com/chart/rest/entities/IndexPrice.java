package com.chart.rest.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "index_price"
//,
//	uniqueConstraints={
//		@UniqueConstraint(
//			columnNames={"id","startAt"}
//		)
//	}	
)
//@IdClass(IndexPricePK.class)
public class IndexPrice {

//	@Id 
//	@SequenceGenerator(name="seq",sequenceName="index_price_seq")        
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
////	@GeneratedValue(strategy=GenerationType.AUTO)
//    private long id;

	@Id
	private String startAt;
	private String symbol;
	private String period;
	private String open;
	private String high;
	private String low;
	private String close;

	public IndexPrice() {
	}

	public IndexPrice(String startAt, String symbol, String period, String open, String high, String low,
			String close) {

		this.setStartAt(startAt);
		this.symbol = symbol;
		this.period = period;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof IndexPrice))
			return false;
		IndexPrice indexPrice = (IndexPrice) o;
		return Objects.equals(this.startAt, indexPrice.startAt) && Objects.equals(this.symbol, indexPrice.symbol)
				&& Objects.equals(this.period, indexPrice.period) && Objects.equals(this.open, indexPrice.open)
				&& Objects.equals(this.high, indexPrice.high) && Objects.equals(this.low, indexPrice.low)
				&& Objects.equals(this.close, indexPrice.close);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.startAt, this.symbol, this.period, this.open, this.high, this.low, this.close);
	}

	@Override
	public String toString() {
		return "IndexPrice{" + "startAt=" + startAt + ", symbol=" + symbol + ", period=" + period + ", open=" + open
				+ ", high=" + high + ", low=" + low + ", close=" + close + '}';
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getStartAt() {
		return startAt;
	}

	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}

	public void replace(IndexPrice newIndexPrice) {
		this.close = newIndexPrice.close;
		this.high = newIndexPrice.high;
		this.low = newIndexPrice.low;
		this.open = newIndexPrice.open;
		this.period = newIndexPrice.period;
		this.symbol = newIndexPrice.symbol;
	}
}

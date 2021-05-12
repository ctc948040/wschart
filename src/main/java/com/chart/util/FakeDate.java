package com.chart.util;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class FakeDate {
	long minStartAt;
	long addMonthEpoch;
	long currtEpoch;
	long termEpoch;

	private static int cnt = 0;

	public FakeDate(long minStartAt) {
		this.minStartAt = minStartAt;
		Date addMonthDate = DateUtils.addMonths(new Date(minStartAt * 1000), 1);
		this.addMonthEpoch = addMonthDate.getTime() / 1000;
		this.currtEpoch = System.currentTimeMillis() / 1000;
		this.termEpoch = this.currtEpoch - this.addMonthEpoch;
		setCnt(getCnt() + 1);
	}

	/**
	 * 가상 현재 타임스탬프(동적)
	 * 
	 * @return
	 */
	public long getSimulEpoch() {
		return (System.currentTimeMillis() / 1000) - this.termEpoch;
	}

	/**
	 * 실제 가상 시작점(한달지난 시점)
	 * 
	 * @return
	 */
	public long getSimulAddMonthEpoch() {
		return this.addMonthEpoch;
	}

	/**
	 * 실제 타임스탬프
	 * 
	 * @return
	 */
	public long getCurrtEpoch() {
		return this.currtEpoch;
	}

	public static int getCnt() {
		return cnt;
	}

	public static void setCnt(int cnt) {
		FakeDate.cnt = cnt;
	}

}

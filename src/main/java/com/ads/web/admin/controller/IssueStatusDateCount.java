package com.ads.web.admin.controller;

import java.util.Date;

public class IssueStatusDateCount {
	private String status;
	private long count;
	private Date date;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

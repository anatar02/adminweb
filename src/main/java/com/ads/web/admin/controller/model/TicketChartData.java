package com.ads.web.admin.controller.model;

public class TicketChartData {
	private String day;
	private int open;
	private int closed;
	private int esclating;
	private int inprogress;
	
	public TicketChartData(String day, int open, int closed, int esclating, int inprogress) {
		super();
		this.day = day;
		this.open = open;
		this.closed = closed;
		this.esclating = esclating;
		this.inprogress = inprogress;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getOpen() {
		return open;
	}
	public void setOpen(int open) {
		this.open = open;
	}
	public int getClosed() {
		return closed;
	}
	public void setClosed(int closed) {
		this.closed = closed;
	}
	public int getEsclating() {
		return esclating;
	}
	public void setEsclating(int esclating) {
		this.esclating = esclating;
	}
	public int getInprogress() {
		return inprogress;
	}
	public void setInprogress(int inprogress) {
		this.inprogress = inprogress;
	}
	
}

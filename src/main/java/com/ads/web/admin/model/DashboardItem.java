package com.ads.web.admin.model;

public class DashboardItem {
	private String id;
	private String value;
	private String text;
	private String panelClass;
	
	public DashboardItem(String id, String value, String text, String panelClass) {
		super();
		this.id = id;
		this.value = value;
		this.text = text;
		this.panelClass = panelClass;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPanelClass() {
		return panelClass;
	}
	public void setPanelClass(String panelClass) {
		this.panelClass = panelClass;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}

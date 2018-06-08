package com.ads.web.admin.model;

public class StatusModel {
	private String id;
	private String displayName;
	
	public StatusModel(String id, String displayName) {
		super();
		this.id = id;
		this.displayName = displayName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}

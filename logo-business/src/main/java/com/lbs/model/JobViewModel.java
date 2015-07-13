package com.lbs.model;

public class JobViewModel {

	private String name;
	private String description;
	private String teamname;
	private String username;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTeamname() {
		return teamname;
	}
	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public JobViewModel(String name, String description, String teamname, String username) {
		super();
		this.name = name;
		this.description = description;
		this.teamname = teamname;
		this.username = username;
	}
	
	public JobViewModel() {
		super();
	}
	
}

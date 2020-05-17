package com.Medhanialem.utils;

public class RestJsonAnswer {
	
	private final String result;
	private final String description;

	public RestJsonAnswer(String result, String description) {
		this.result = result;
		this.description = description;
	}

	public String getResult() {
		return result;
	}

	public String getDescription() {
		return description;
	}


}

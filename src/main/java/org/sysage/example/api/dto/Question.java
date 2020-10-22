package org.sysage.example.api.dto;

import javax.validation.constraints.NotBlank;

public class Question {

	@NotBlank(message = "q must not be blank!")
	private String q;

	public Question() {
	}

	public Question(String q) {
		this.q = q;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
}

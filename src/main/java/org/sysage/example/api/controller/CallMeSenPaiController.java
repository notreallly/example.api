package org.sysage.example.api.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.sysage.example.api.dto.Question;

import com.fasterxml.jackson.annotation.JsonView;

@Controller
public class CallMeSenPaiController {

	@RequestMapping(value = "/CallMeSenPai", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(SenPai.Public.class)
	@ResponseBody
	public SenPai noticeMe(@Valid @RequestBody Question question) {
		return new SenPai(question.getQ());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationError handleException(MethodArgumentNotValidException exception) {
		return ValidationErrorBuilder.fromBindingErrors(exception.getBindingResult());
	}

	public static class SenPai {

		public interface Public {
		};

		private final String defaultAnswer = "SenPai noticed you just now!";
		private String question;

		public SenPai() {
		}

		public SenPai(String question) {
			this.question = question;
		}

		@JsonView(SenPai.Public.class)
		public String getAnswer() {
			if (question.toLowerCase().contains("senpai"))
				return defaultAnswer;
			else
				return "SenPai responsed you \"" + question + "\" just now!";
		}
	}
}

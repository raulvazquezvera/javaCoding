package com.jct.rvv.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = 5013653965090414042L;

	@JsonProperty(value = "id")
	private Integer id;

	@JsonProperty(value = "userId")
	private Integer userId;

	@JsonProperty(value = "title")
	private String title;

	@JsonProperty(value = "body")
	private String body;

}

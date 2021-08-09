package com.jct.rvv.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilJson {

	public static String mapperToJson(Object obj) throws JsonProcessingException {
		return (new ObjectMapper()).writeValueAsString(obj);
	}

	public static String mapperToJsonFormatted(Object obj) throws JsonProcessingException {
		return (new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}

}

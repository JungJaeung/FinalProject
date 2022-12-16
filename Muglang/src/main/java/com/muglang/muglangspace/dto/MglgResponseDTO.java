package com.muglang.muglangspace.dto;

import java.util.List;

import lombok.Data;

@Data
public class MglgResponseDTO<T> {
	private List<T> items;
	
	private T item;
	
	private String errorMessage;
	
	private int statusCode;
}

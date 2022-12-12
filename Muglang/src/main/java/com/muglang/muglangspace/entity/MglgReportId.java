package com.muglang.muglangspace.entity;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

@Data
public class MglgReportId implements Serializable{
	private int reportId;
	private int reportType;
}

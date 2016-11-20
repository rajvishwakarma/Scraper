package com.example.model;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TinDetails {

	private String oldRcNo;
	private String tinNo;
	private String dealerName;
	private String status;


	public TinDetails(String oldRcNo, String tinNo, String dealerName, String status){
		this.oldRcNo = oldRcNo;
		this.tinNo = tinNo;
		this.dealerName = dealerName;
		this.status = status;
	}

}
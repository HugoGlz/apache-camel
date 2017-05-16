package com.hugo.camel.tests.bindy.sample;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",", crlf = "UNIX")
public class Data {

	@DataField(pos = 1)
	private String name;
	
	@DataField(pos = 2)
	private String name2;
	
	@DataField(pos = 3)
	private Integer amount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public void print(){
		System.out.println("============HELLO=============");
	}
}

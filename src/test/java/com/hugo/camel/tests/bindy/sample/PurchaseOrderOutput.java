package com.hugo.camel.tests.bindy.sample;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",", crlf = "UNIX")
public class PurchaseOrderOutput {

	@DataField(pos = 2)
	private String name;
	
	@DataField(pos = 3, precision = 2)
	private Double price;
	
	@DataField(pos = 1)
	private Integer amount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public String toString(){
		return String.format(
				"The response is %s", this.getName()
				);
	}
}

package com.hugo.camel.tests.bindy.sample;

import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;

@Converter
public class PurchaseOrderConverter implements TypeConverters{

	@Converter 
	public static PurchaseOrderOutput toPurchaseOrderOutput(String content){
		String values[] = content.split(",");
		
		PurchaseOrderOutput output = new PurchaseOrderOutput();
		
		output.setName(values[0]);
		
		return output;
	}
}

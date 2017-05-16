package com.hugo.camel.tests.csv.sample;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.csv.writer.CSVConfig;
import org.apache.commons.csv.writer.CSVField;

public class CSVTransform {

	public static void main(String... args) throws Exception{
		try{
			CamelContext context = new DefaultCamelContext();

			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					CSVConfig custom = new CSVConfig();
					custom.setDelimiter(',');
					custom.setEndTrimmed(true);
					custom.addField(new CSVField("id"));
					custom.addField(new CSVField("customerId"));
					custom.addField(new CSVField("date"));
					custom.addField(new CSVField("item"));
					custom.addField(new CSVField("amount"));
					custom.addField(new CSVField("description"));
					
					CsvDataFormat myCSV = new CsvDataFormat();
					myCSV.setConfig(custom);
					
					from("file:data/csv")
						.marshal(myCSV)
						.to("file:data/outbox");
				}
			});
			
			context.start();
			
			Thread.sleep(5000);
			
			context.stop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

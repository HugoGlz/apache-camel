package com.hugo.camel.tests.excel.sample;

import java.util.ArrayList;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ExcelExcec {

	public static void main(String... args) throws Exception{
		try{
			CamelContext context = new DefaultCamelContext();
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("file:data/xls?noop=true")
					.bean(new ExcelConverterBean())
					.process(new Processor() {
						
						@Override
						public void process(Exchange exchange) throws Exception {
							CustomerOutput content = (CustomerOutput) exchange.getIn().getBody();
							CustomerData output = content.getCustomers().get(0);
                            System.out.println(output);
                            System.out.println(output.getName());
                            System.out.println(output.getDate().toString());
                            System.out.println(output.getId().toString());
                            System.out.println(output.getPrice().toString());
                            System.out.println(output.getQuantity().toString());
                            System.out.println(output.getTotal().toString());
//                            exchange.getIn().setBody(output);
							
                            String fileName = exchange.getIn().getHeader("CamelFileName").toString();
                            
                            
                            fileName = fileName.replace(".xls", ".csv");
                            exchange.getIn().setHeader("CamelFileName", fileName);
						}
					})
					.to("file:data/outbox");
				}
			});
			
			context.start();
			
			Thread.sleep(15000);
			
			context.stop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

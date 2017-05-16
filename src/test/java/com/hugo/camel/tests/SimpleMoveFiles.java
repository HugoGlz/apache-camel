package com.hugo.camel.tests;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;

public class SimpleMoveFiles {

	private Logger logger = Logger.getLogger(SimpleMoveFiles.class);
	
	public static void main(String... args) throws Exception{
		try{
			CamelContext context = new DefaultCamelContext();
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("file:data/inbox?noop=true")
					.process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							System.out.println(exchange);
							System.out.println(exchange.getExchangeId());
							System.out.println(exchange.getIn().getHeaders());
							System.out.println(exchange.getIn().getHeader("CamelFileName"));
							System.out.println(exchange.getIn().getBody());
							System.out.println(exchange.getIn().getMessageId());
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

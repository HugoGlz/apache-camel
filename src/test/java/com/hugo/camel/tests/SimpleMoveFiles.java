package com.hugo.camel.tests;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class SimpleMoveFiles {

	public static void main(String... args) throws Exception{
		try{
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
			
			CamelContext context = new DefaultCamelContext();
			context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					/*
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
					*/
					
					/*
					from("file:data/inbox?noop=true")
					.choice().when(header("CamelFileName").endsWith(".csv"))
					.to("file:data/alternative");
					*/
					
					System.out.println("reading");
					
					from("file:data/inbox?noop=true")
					.to("jms:incomingOrders");
					
					from("jms:incomingOrders")
					.choice().when(header("CamelFileName").endsWith(".txt"))
						.to("jms:txtOrders")
					.choice().when(header("CamelFileName").endsWith(".csv"))
						.to("jms:csvOrders");
					
					from("jms:txtOrders")
						.process(new Processor() {
							@Override
							public void process(Exchange exchange) throws Exception {
								System.out.println(
										String.format("TXT processing FILE %s", 
												exchange.getIn().getHeader("CamelFileName")));
							}
						});
					
					from("jms:csvOrders")
					.process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							System.out.println(
									String.format("CSV processing FILE %s", 
											exchange.getIn().getHeader("CamelFileName")));
						}
					});
					
					System.out.println("finish");
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

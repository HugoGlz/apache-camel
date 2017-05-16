package com.hugo.camel.tests.transform.sample;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class CSVMain {

	public static void main(String... args) throws Exception{
		try{
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
			
			CamelContext context = new DefaultCamelContext();
			context.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("file:data/inbox-csv?noop=true")
					.unmarshal().csv()
					.split(body())
					.to("activemq:queue.csv.record");
					
					from("activemq:queue.csv.record").process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							System.out.println("============MESSAGE============");
							System.out.println(exchange.getIn().getBody(String.class));
							
						}
					});
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

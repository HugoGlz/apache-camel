package com.hugo.camel.tests.bindy.sample;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class TransformMain {

	public static void main(String... args) throws Exception{
		try{
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
			
			CamelContext context = new DefaultCamelContext();
			context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("file:data/inbox?noop=true")
					.transform(new TransformImplCSV())
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

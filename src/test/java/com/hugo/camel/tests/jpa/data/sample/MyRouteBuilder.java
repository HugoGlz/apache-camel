package com.hugo.camel.tests.jpa.data.sample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import com.hugo.camel.tests.jpa.sample.Person;

public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {
    	org.apache.camel.converter.jaxb.JaxbDataFormat jxb = new JaxbDataFormat("com.hugo.camel.tests.jpa.sample");
        
    	from("file:data/xml?noop=true")
        .unmarshal(jxb)
        .process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				Person content = (Person) exchange.getIn().getBody();
                System.out.println(content);
                System.out.println(content.getName());
                System.out.println(content.getSurname());
                //System.out.println(content.getId().toString());
			}
		})
        //.to("jpa:com.hugo.camel.tests.jpa.sample.Person");
        .to("file:data/outbox");
        
    }

}

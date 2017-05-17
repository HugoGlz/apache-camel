package com.hugo.camel.tests.bindy.sample;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;

import com.hugo.camel.tests.ways.transform.ProccessorImplCSV;
import com.sun.istack.logging.Logger;

public class PurchaseOrderBindyTest{

	private static final Logger logger = Logger.getLogger(ProccessorImplCSV.class);
	
	public void testBindy() throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(createRoute());
		context.start();
		
		MockEndpoint mock = context.getEndpoint("mock:result", 
												MockEndpoint.class);
		
		mock.expectedBodiesReceived("Camel in Action,49.95,1\n");
		
		PurchaseOrderInput order = new PurchaseOrderInput();
		order.setAmount(1);
		order.setName("Came in Action");
		order.setPrice(49.95D);
		
		ProducerTemplate template = context.createProducerTemplate();
		template.sendBody("direct:toCsv", order);
		
		logger.info("testing");
		mock.assertIsSatisfied();
	}
	
	public RouteBuilder createRoute() {
		return new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("direct:toCsv")
					.marshal().bindy(BindyType.Csv, PurchaseOrderInput.class)
					.to("mock:result");
			}
		};
	}
	
	public static void main(String...strings) throws Exception{
		new PurchaseOrderBindyTest().testBindy();
	}
}

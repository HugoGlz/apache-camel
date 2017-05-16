package com.hugo.camel.tests.bindy.sample;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;

import com.hugo.camel.tests.ways.transform.ProccessorImplCSV;
import com.sun.istack.logging.Logger;

public class BindyReadingFileTest{

	private static final Logger logger = Logger.getLogger(ProccessorImplCSV.class);
	
	public void testBindy() throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(createRoute());
		context.start();
		Thread.sleep(5000);
	}
	
	public RouteBuilder createRoute() {
		return new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("file:data/csv?noop=true")
					.unmarshal().bindy(BindyType.Csv, PurchaseOrder.class)
					.process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							PurchaseOrder purchaseOrder = exchange.getIn().getBody(PurchaseOrder.class);
							logger.info(purchaseOrder.getAmount().toString());
							logger.info(purchaseOrder.getName());
							logger.info(purchaseOrder.getPrice().toString());
						}
					})
					.to("file:data/outbox");
			}
		};
	}
	
	public static void main(String...strings) throws Exception{
		new BindyReadingFileTest().testBindy();
	}
}

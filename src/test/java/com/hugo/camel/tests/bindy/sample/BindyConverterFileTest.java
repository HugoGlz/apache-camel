package com.hugo.camel.tests.bindy.sample;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.DataFormat;

import com.hugo.camel.tests.ways.transform.ProccessorImplCSV;
import com.sun.istack.logging.Logger;

public class BindyConverterFileTest{

	private static final Logger logger = Logger.getLogger(ProccessorImplCSV.class);
	
	public void testBindy(){
		try{
			CamelContext context = new DefaultCamelContext();
			context.addRoutes(createRoute());
			context.start();
			Thread.sleep(7000);
			context.stop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public RouteBuilder createRoute() {
		return new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {

				DataFormat bindy = new BindyCsvDataFormat(com.hugo.camel.tests.bindy.sample.PurchaseOrderInput.class);
				
				DataFormat bindyOut = new BindyCsvDataFormat(com.hugo.camel.tests.bindy.sample.PurchaseOrderOutput.class);
				
				from("file:data/csv?noop=true")
					.unmarshal(bindy)
					.process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							logger.info("HERE 0");
							PurchaseOrderInput object = (PurchaseOrderInput) exchange.getIn().getBody();
                            System.out.println(object);
                            PurchaseOrderOutput output = new PurchaseOrderOutput();
                            output.setName(object.getName());
                            output.setAmount(object.getAmount());
                            output.setPrice(object.getPrice());
                            
                            exchange.getIn().setBody(output);
						}
					})
					.to("direct:handleOrders");
				
				from("direct:handleOrders")
					.marshal(bindyOut)
					.process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							logger.info("HERE");
							String order = exchange.getIn().getBody(String.class);
							logger.info(order);
						}
					})
					.to("file:data/outbox");
			}
		};
	}
	
	public static void main(String...strings) throws Exception{
		new BindyConverterFileTest().testBindy();
	}
}

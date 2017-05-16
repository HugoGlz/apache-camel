package com.hugo.camel.tests.processor.sample;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class ProccessorImplCSV implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		String content = message.getBody(String.class);
		
	}

}

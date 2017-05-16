package com.hugo.camel.tests.ways.transform;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.sun.istack.logging.Logger;

public class ProccessorImplCSV implements Processor{

	private static final Logger logger = Logger.getLogger(ProccessorImplCSV.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		String content = message.getBody(String.class);
		
		logger.info(String.format("===========BODY======== \n %s", content));
		
		String values[] = content.split("\n");
		StringBuilder builder = new StringBuilder();
		
		for(String value : values){
			if(builder.length() > 0)
				builder.append(String.format(",%s",value));
			else
				builder.append(String.format("%s",value));
		}
		
		logger.info(String.format("===========BODY OUTPUT======== \n %s", builder.toString()));
		
		message.setBody(builder.toString());
	}

}

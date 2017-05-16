package com.hugo.camel.tests.ways.transform;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Message;

import com.sun.istack.logging.Logger;

public class TransformImplCSV implements Expression{

	private static final Logger logger = Logger.getLogger(TransformImplCSV.class);
	
	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
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
		
		return (T) builder.toString();
	}

}

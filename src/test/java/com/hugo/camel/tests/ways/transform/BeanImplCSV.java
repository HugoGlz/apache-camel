package com.hugo.camel.tests.ways.transform;

import com.sun.istack.logging.Logger;

public class BeanImplCSV{

	private static final Logger logger = Logger.getLogger(BeanImplCSV.class);
	
	public String map(String content) throws Exception {
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
		
		return builder.toString();
	}

}

package com.hugo.camel.tests.jpa.data.sample;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class MainApp {

    public static void main(String... args) throws Exception {
       
    	try{
			CamelContext context = new DefaultCamelContext();
			context.addRoutes(new MyRouteBuilder());
			context.start();
			Thread.sleep(15000);
			context.stop();
		}catch(Exception e){
			e.printStackTrace();
		}
    }

}

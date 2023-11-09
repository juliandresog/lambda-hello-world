package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App implements RequestHandler<Map<String,String>, String> {
    
//    @Override
//    public String handleRequest(Object input, Context context) {
//        return "Hello, World! "+input+"  :: "+context;
//    }
    
    @Override
    public String handleRequest(Map<String,String> event, Context context) {
        String response = "Hello " + event.get("key1") + "! "+context.getFunctionName();
        return response;
    }
    
    
//    public static void main( String[] args )
//    {
//        System.out.println( "Hello World!" );
//    }
}

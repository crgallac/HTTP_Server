package client;
import java.io.IOException;
import java.util.Scanner;


import echo.*;
import time.BlockingTimeClient; 

public class HTTPcClientSetup {

	
	
	public static void clientSetup(String args[]) throws IOException {
		
		HttpcConsole httpc = new HttpcConsole(); 

		
	}
	
	
	
	public static void testConnect(HTTP_RequestMessage request, HTTP_API http ) throws IOException {
	
    	
    	request.method = "GET"; 
    	request.requestTarget = "/status/418"; 
//    	request.requestTarget = "/get?course=networking&assignment=1";
    	request.version = "1.0"; 
    	request.port = 80; 
    	request.header.AddHeaderLine("HOST", "httpbin.org");
    	request.isVerbose = true; 
    	
    	
//    	request.header.AddHeaderLine("Content-Type", "application/x-www-form-urlencoded");
//    	request.header.AddHeaderLine("Content-Length" , 13);
    	
    	
		
		http.setupConnection(request.header.headerList.get("HOST"), request.port);
//		http.setupConnection("httpbin.org", request.port);

		http.httpGet(request);
//		http.httpPost("/status/418", "httpbin.org", "1.0");
	}
	
	
	public static void testEcho(String[] args) throws IOException {
		
//		BlockingEchoClient bec = new BlockingEchoClient(); 
//		BlockingEchoServer bes = new BlockingEchoServer(); 
//		BlockingTimeClient btc = new BlockingTimeClient(); 
		
		
//		System.out.println("need args");
		
		
		// Make sure to setup server first 
		
//		bes.mainBlockingEchoServer(args);
//		bec.mainBlockingEchoClient(args); 
//		btc.mainBlockingTimeClient(args);
	}
	
}

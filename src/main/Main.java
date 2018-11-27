package main;


import java.io.IOException;
import java.util.Scanner;

import echo.*;
import server.HTTPfs;
import time.BlockingTimeClient;
import time.BlockingTimeServer; 

public class Main {

	
	
	public static void main(String args[]) throws IOException {
		
		boolean executeCommand = true;
		
		System.out.println("Start server");
		Scanner scanner = new Scanner(System.in);
		args = scanner.nextLine().split(" ");
		
//		for (String s : args) {
//		  
//			System.out.println(s); 
//		}
		
		HTTPfs server = new HTTPfs(args); 
		
		
//		    ServerStartingParams httpObject = createHTTPObject(arguments);
//		    SocketConnectionFromServer server = new SocketConnectionFromServer(httpObject);
//		    server.initiateServer();
		
		
		

		
	}
	
	
	
	
	
	public static void testEcho(String[] args) throws IOException {
		
		
		
//		BlockingEchoClient bec = new BlockingEchoClient(); 
//		BlockingEchoServer bes = new BlockingEchoServer(); 
		BlockingTimeClient btc = new BlockingTimeClient(); 
//		BlockingTimeServer bts = new BlockingTimeServer(); 
		
		
//		System.out.println("need args");
		
		
		// Make sure to setup server first 
		
//		bes.mainBlockingEchoServer(args);
//		bec.mainBlockingEchoClient(args); 
	
//		bts.mainBlockingTimeServer(args);
		btc.mainBlockingTimeClient(args);
		
	}
	
}



//boolean executeCommand = true;
//System.out.println("Please enter command to start server...");
//Scanner scanner = new Scanner(System.in);
//String arguments[] = scanner.nextLine().split(" ");
//for (String s : arguments) {
//    if (s.toLowerCase().equals("exit")) {
//        executeCommand = false;
//    }
//}
//if (executeCommand) {
//    ServerStartingParams httpObject = createHTTPObject(arguments);
//    SocketConnectionFromServer server = new SocketConnectionFromServer(httpObject);
//    server.initiateServer();
//}
//}
//
//public static ServerStartingParams createHTTPObject(String ar[]) {
//ServerStartingParams http = new ServerStartingParams();
//for (int i = 0; i < ar.length; i++) {
//    if (ar[i].equals("-v")) {
//        http.printDebugMessage = true;
//    }
//    if (ar[i].equals("-p")) {
//        http.port = Integer.parseInt(ar[i + 1]);
//    }
//    if (ar[i].equals("-d")) {
//        http.filePath = ar[i + 1].replace("'", "");
//    }
//}
//return http;
//}
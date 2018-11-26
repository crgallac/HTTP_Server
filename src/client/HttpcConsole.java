package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.swing.text.AbstractDocument.Content;

import java.util.*;

public class HttpcConsole {

	private boolean isDataInline;
	
	HTTP_API httpAPI; 
	HTTP_RequestMessage request; 
	
	
	public HttpcConsole() throws IOException, FileNotFoundException {
		
		this.isDataInline = true; 
		
    	httpAPI = new HTTP_API(); 
    	
		
    	
        System.out.println("httpc is initalized \n" 
        		+ "it is a curl-like application but supports HTTP protocol only.\n" 
                + "Use \"httpc help [command]\" for more information about a command.\n");
		this.readEchoAndRepeat();
    	
	}
	
	
	
    private void readEchoAndRepeat() throws IOException, FileNotFoundException {
    	
        Charset utf8 = StandardCharsets.UTF_8;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
        	
        	request = new HTTP_RequestMessage(); 
        	request.port = 80; 
        	request.version = "1.0"; 
        	
            String line = scanner.nextLine();
          
            String[] arguments = line.split(" ");
            
            String callAPI = arguments[0]; 
            
          
            
          
            if(isHttpcCommand(callAPI)) {
            
            	
            
            	  
            	if(arguments.length > 1 && isHttpcMethod(arguments[1])) {
            		
            		switch (arguments[1].toLowerCase()) {
            		
            			case "get": 
            				
            				request.method = "GET"; 
            				
            				httpRequest(arguments); 
            				
            				
            				httpAPI.setupConnection(request.header.headerList.get("HOST"), request.port);
            				httpAPI.httpGet(request);
            				
            				
//            		    	request.requestTarget = "/post"; 
//            		    	request.version = "1.0"; 
//            		    	request.port = 80; 
//            		    	request.header.AddHeaderLine("HOST", "httpbin.org");
//            		    	request.header.AddHeaderLine("Accept", "application/json");
////            		    	request.header.AddHeaderLine("Content-Type", "application/x-www-form-urlencoded");
////            		    	request.header.AddHeaderLine("Content-Length" , 13);
//            		    	
//            		    	
//            				
//            				http.setupConnection(request.header.header.get("HOST"), request.port);
//            				http.httpPost(request);
            				
            
            				
            				break; 
            				
            			case "post": 
            				
            				request.method = "POST"; 
            				
            				httpRequest(arguments); 
            				
            				httpAPI.setupConnection(request.header.headerList.get("HOST"), request.port);
            				httpAPI.httpPost(request);
            				
            				break; 
            				
            		
            		
            		}
            		
            		
            		
            	}
            	else if (arguments.length > 1 && isHttpcHelp(arguments[1])) {
            		
            		if(arguments.length > 2) { 
            			
            			switch (arguments[2].toLowerCase()) {
            			
            			case "get": 
            				
             	            System.out.println(
             	                    "usage: httpc get [-v] [-h key:value] URL\n"
             	                    		+ "Get executes a HTTP GET request for a given URL.\n"
             	                            + "   -v            Prints the detail of the response such as protocol, status,\n"
             	                            + "and headers.\n" + "   -h key:value  Associates headers to HTTP Request with the format\n"
             	                            + "'key:value'.");
            				
            				break; 
            				
            			case "post": 
            				
             	            System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\n"
             	                    + "Post executes a HTTP POST request for a given URL with inline data or from\n" + "file.\n"
             	                    + "   -v             Prints the detail of the response such as protocol, status,\n"
             	                    + "and headers.\n" + "   -h key:value   Associates headers to HTTP Request with the format\n"
             	                    + "'key:value'.\n" + "   -d string      Associates an inline data to the body HTTP POST request.\n"
             	                    + "   -f file        Associates the content of a file to the body HTTP POST\n" + "request.\n"
             	                    + "Either [-d] or [-f] can be used but not both.");
             	        
            				
            				break; 
            				
            			default: 
            				System.out.println("Not a valid input");
            				
            				break;
            				
            			}

            			
            		}
            		else {
            	            System.out.println("httpc is a curl-like application but supports HTTP protocol only.\n" 
            	            		+ "Usage: \n"
            	                    + "   httpc command [arguments]\n " + "The commands are:\n"
            	                    + "   get executes a HTTP GET request and prints the response.\n"
            	                    + "   post executes a HTTP POST request and prints the response. \n"
            	                    + "   help prints this screen. \n"
            	                    + "Use \"httpc help [command]\" for more information about a command.\n");
            			
            		}
          	
            	}
            	else {
            		System.out.println("Not a valid input"); 
            		
            	}
            	
            	
            	
            	
            }
            else {
            		System.out.println("Not a valid input"); 
//            		throw new IOException(); 
            }
            
         
        }
    }
    
    

    
    
    private boolean isHttpcCommand(String arg) {
    	
    	return arg.toLowerCase().equals("httpc");
    	
    }
    
    private boolean isHttpcMethod(String arg) {
    	
    	
    	return arg.toLowerCase().equals("get") || arg.toLowerCase().equals("post"); 
    	
    }
    private boolean isHttpcHelp(String arg) {
    	
    	
    	return arg.toLowerCase().equals("help"); 
    	
    	
    	
    }
	private void httpRequest(String[] args) throws IOException, FileNotFoundException {


		
		String[] inputArguments = Arrays.copyOfRange(args, 2, args.length); 
		
		
		
		int index = 0; 
		int fileFlag = 0; 
		
		while ( index < inputArguments.length) {   
			
			String tmpInput = new String("");
			 
			
			if(inputArguments[index].contains("http://")) {
				
				
	
				tmpInput = inputArguments[index].replaceAll("http://", "");
				tmpInput = tmpInput.replaceAll("\'", "");
				request.url = tmpInput; 
				String[] tmpStrings = tmpInput.split("/"); 
				
				request.header.AddHeaderLine("HOST", tmpStrings[0]);
				
				tmpInput = ""; 
				for(int i = 1; i < tmpStrings.length; i++) {
					
					tmpInput = tmpInput.concat("/");
					tmpInput =  tmpInput.concat(tmpStrings[i]); 
					
				}
				
				request.requestTarget = tmpInput; 
				
				index++; 
				
			}else {
			
			switch (inputArguments[index]) {
			
			case "-v": 
				
					this.request.isVerbose = true; 
					index++; 
					
				break; 
				
			case "-d": 
					
				if(args[1].toLowerCase() == "get" || fileFlag > 0) {
						
						System.out.println("invalid use of arguments");
						throw new IOException(); 
						
					}
					else {
						
					
					this.isDataInline = true; 
					
					index ++; 
					
					tmpInput = inputArguments[index].replace("\'", "");
					this.request.body.data = tmpInput; 
					
					
					this.request.header.AddHeaderLine("Content-length", request.body.data.length() );
				
					index ++; 
					
					
					}
				
				fileFlag++; 
				break; 
				
			case "-f": 
				
				if(args[1].toLowerCase() == "get" || fileFlag > 0) {
					
					System.out.println("invalid use of arguments ");
					throw new IOException(); 

					
				}
				else {
				
					this.isDataInline = false; 
					
					index ++; 
					
					
					tmpInput = inputArguments[index].replaceAll("\'", "");

				      try {
				        
				    	  	File text = new File(tmpInput); 

		
					        //Creating Scanner instance to read File in Java
					        Scanner scnr = new Scanner(text);
					      
					        //Reading each line of file using Scanner class
					       
					        while(scnr.hasNextLine()){
					            this.request.body.data = this. request.body.data.concat(scnr.nextLine());
					        }       
					        
					        this.request.header.AddHeaderLine("Content-length", request.body.data.length() );
							
						
							index ++; 
				         
				      } catch (Exception e) {
				         // if any I/O error occurs
				         e.printStackTrace();
				      }
				      
				      
				      
	
				}
				
				fileFlag++; 
				break; 
				
			case "-h": 
				
					index ++; 
					
					tmpInput = inputArguments[index].replace("\"", "");
					
					String[] headerInput = inputArguments[index].split(":");
					
				
					this.request.header.AddHeaderLine(headerInput[0], headerInput[1]);
				
					index ++; 
					
				
				break; 
				
			case "-o": 
				
				this.request.isOutputToFile = true; 
				index++; 
				
				String fileName = inputArguments[index].replace("\'", "");
				this.request.outputFile = fileName; 
				
				
				index++; 
				
			
			break; 
				
			
		
				
			default : 
				
				break; 
				
			
			
			}
			      		
			
		}
		

	}
	}
    
	
}



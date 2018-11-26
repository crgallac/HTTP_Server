package client;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import joptsimple.OptionParser;

public class HTTP_API {
	
	
	OptionParser parser = new OptionParser();
	SocketAddress endpoint; 

	
	
	
	public void setupConnection(String host, int port) throws IOException {
        
        endpoint = new InetSocketAddress(host, port);
        
    }
	
	
	
    
        public void httpGet(HTTP_RequestMessage http) throws IOException {
           
        	try (SocketChannel s = SocketChannel.open()) {
                
            	System.out.println(s.connect(endpoint));
//                System.out.println("Type any thing then ENTER. Press Ctrl+C to terminate");
//                readEchoAndRepeat(socket);
            	
            	
            	 PrintWriter wtr = new PrintWriter(s.socket().getOutputStream());
            	 

                 //Prints the request string to the output stream
                 wtr.println(http.method + " " + http.requestTarget + " " + "HTTP/" + http.version );
                 wtr.println(http.header.HeaderToString());
                 
                 wtr.println("");
                 wtr.flush();
                 



                 //Creates a BufferedReader that contains the server response
                 BufferedReader bufRead = new BufferedReader(new InputStreamReader(s.socket().getInputStream()));
               

                 
                 if(http.isVerbose) {
                     //Prints each line of the response 
                	 if(!http.isOutputToFile) {
                	 printRequest(http); 
                	 printVerboseResponse(bufRead); 
                	 }else {
                	 FileWriter fw = new FileWriter(http.outputFile); 
                	 
                	 outputRequest(http, fw);
                	 outputVerboseResponse(bufRead, fw);
                	 
                	 fw.close();
                	 }
    	
                     }else {
                    	 
                    	 if(!http.isOutputToFile) {
                       
                    	 printResponse(bufRead);
                    	 }else {
                    		 
                           	 FileWriter fw = new FileWriter(http.outputFile); 
                        	 
                        	 outputResponse(bufRead, fw);
                        	 
                        	 fw.close();
                    		 
                    	 }
                    
                     }


                 //Closes out buffer and writer
                 bufRead.close();
                 wtr.close();
            	
            }
        	
        	
       
        }
        
        public void httpPost(HTTP_RequestMessage http) throws IOException {
            
        	try (SocketChannel s = SocketChannel.open()) {
                
            	System.out.println(s.connect(endpoint));
//                System.out.println("Type any thing then ENTER. Press Ctrl+C to terminate");
//                readEchoAndRepeat(socket);
            	
            	
            	 PrintWriter wtr = new PrintWriter(s.socket().getOutputStream());


                 //Prints the request string to the output stream
                 wtr.println(http.method + " " + http.requestTarget + " " + "HTTP/" + http.version );
                 wtr.println(http.header.HeaderToString());
                 
                 wtr.print(http.body.data);
                 wtr.println(); 
                 
                 
                 
                 wtr.flush();
                 

                 //Creates a BufferedReader that contains the server response
                 BufferedReader bufRead = new BufferedReader(new InputStreamReader(s.socket().getInputStream()));
               

                 
                 if(http.isVerbose) {
                     //Prints each line of the response 
                	 if(!http.isOutputToFile) {
                	 printRequest(http); 
                	 printVerboseResponse(bufRead); 
                	 }else {
                	 FileWriter fw = new FileWriter(http.outputFile); 
                	 
                	 outputRequest(http, fw);
                	 outputVerboseResponse(bufRead, fw);
                	 
                	 fw.close();
                	 }
    	
                     }else {
                    	 
                    	 if(!http.isOutputToFile) {
                       
                    	 printResponse(bufRead);
                    	 }else {
                    		 
                           	 FileWriter fw = new FileWriter(http.outputFile); 
                        	 
                        	 outputResponse(bufRead, fw);
                        	 
                        	 fw.close();
                    		 
                    	 }
                    
                     }


                 //Closes out buffer and writer
                 bufRead.close();
                 wtr.close();
            	
            }
        	
        	
        	
        	
       
        }
        
        
      private void printVerboseResponse(BufferedReader bufRead) throws IOException {
         
    	  String outStr;
    	  
    	  while((outStr = bufRead.readLine()) != null){
              System.out.println(outStr);
          }
      }
      
      private void printResponse(BufferedReader bufRead) throws IOException {
          
    	  String outStr;
    	  boolean printFlag = false; 
    	  
    	  while((outStr = bufRead.readLine()) != null){
    		  
    		  if(outStr.equals("{")) {
    			  printFlag = true; 
    		  }; 
    		  
    		  if(printFlag) { 
    			  System.out.println(outStr);
    		  }
          }
      }
      
      private void printRequest(HTTP_RequestMessage http ) {
    	  
          System.out.println();; 
          System.out.println(http.method + " " + http.requestTarget + " " + "HTTP/" + http.version );
          System.out.println(http.header.HeaderToString());
          
          System.out.print(http.body.data);
          System.out.println();; 
          System.out.println();; 
      }
      
      
      private void outputVerboseResponse(BufferedReader bufRead, FileWriter fw) throws IOException {
          
    	  String outStr;
    	
    	  
    	  
    	  while((outStr = bufRead.readLine()) != null){
              
    		  fw.write(outStr + "\r\n");
    		  
          }
    	  
    	  
      }
      
      private void outputResponse(BufferedReader bufRead, FileWriter fw) throws IOException {
          
    	  String outStr;
    	 
    	  boolean printFlag = false; 
    	  
    	  while((outStr = bufRead.readLine()) != null){
    		  
    		  if(outStr.equals("{")) {
    			  printFlag = true; 
    		  }; 
    		  
    		  if(printFlag) { 
    			  fw.write(outStr + "\r\n");
    		  }
          }
    	  
    	
      }
      
      
      
      private void outputRequest(HTTP_RequestMessage http, FileWriter fw) throws IOException {
    	  
    	 
    	  
    	  fw.write(http.method + " " + http.requestTarget + " " + "HTTP/" + http.version);
    	  fw.write("\r\n");
    	  fw.write(http.header.HeaderToString());
    	  fw.write("\r\n");
    	  fw.write(http.body.data);
    	  fw.write("\r\n");
    	 
          
    	  

      }
      
        
        
}

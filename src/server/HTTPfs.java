package server;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

import client.HTTP_RequestMessage;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class HTTPfs {
	

	HTTPfs_Members httpfs_members; 
	Charset utf8 = StandardCharsets.UTF_8;
	
	
	

	public HTTPfs(String[] args){
		
		try{
		httpfs_members = new HTTPfs_Members(); 
		this.serverSetup(args);
		
		}
		catch(IOException e) {
			System.out.println("Warning: Could not setup the Server. The listening port may already be in use. ");
		}
		
	}
	

    private void readAndReplyToClient(SocketChannel socket) {
    	
    		
    	 
	        try (SocketChannel client = socket) {
	      
	            ByteBuffer buf = ByteBuffer.allocate(1024);
	            
	            for (; ; ) {
	                int nr = client.read(buf);

	                if (nr == -1)
	                    break;

	                if (nr > 0) {
	                	
	                	this.inputParser(client, buf, nr);
	                }
		    }
	        }catch (IOException e) {
		        
		    }
    	
    }

    public void listenAndServe(int port) throws IOException {
    	System.out.println("Server Listening on port: " + port); 
    
    	
        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.bind(new InetSocketAddress(port));
            
            for (; ; ) {
            	//accepts incoming requests and puts them in a thread to be handled by the server
                SocketChannel client = server.accept();
                // We may use a custom Executor instead of ForkJoinPool in a real-world application
                ForkJoinPool.commonPool().submit(() -> readAndReplyToClient(client));
            }
        }
    }

	
	  public void serverSetup(String[] args) throws IOException {
	      
		  if(args[0].equals("httpfs")){
		  
			  OptionParser parser = new OptionParser();
		        
		        
		        parser.acceptsAll(asList("port", "p"), "Listening port")
		                .withOptionalArg()
		                .defaultsTo("8080");
		        
		        parser.acceptsAll(asList("dir", "d"))
	       				.withRequiredArg(); 
		        parser.acceptsAll(asList("debug", "v"));     
	    
	    
		       OptionSet opts = parser.parse(args);
	
		       if(opts.has("d")) httpfs_members.dir = (String) opts.valueOf("dir");		
		  
		  		
		  	   System.out.println(httpfs_members.dir); 
	    
		       httpfs_members.isDebugging = opts.has("v");
	
		       System.out.println(httpfs_members.isDebugging);
		        
		        
		       
		        httpfs_members.port = Integer.parseInt((String) opts.valueOf("port"));
		        
		       
		        this.listenAndServe(httpfs_members.port); }
		 
		  
		  
		  
	
	    }
	  
	    private void readFully(SocketChannel socket, ByteBuffer buf, int size) throws IOException {
	        while (buf.position() < size) {
	            int n = socket.read(buf);
	            if (n == -1) {
	                break;
	            }
	        }
	        if (buf.position() != size) {
	            throw new EOFException();
	        }
	    }

	  
	    private void inputParser(SocketChannel client, ByteBuffer buf, int nr) throws IOException{
	    	 // Receive all what we have sent
            System.out.println("Recieved client request: ");
        	
            HTTP_RequestMessage rm = new HTTP_RequestMessage(); 
            
            
            readFully(client, buf, nr);
            buf.flip();
            
           String testStr = utf8.decode(buf).toString(); 
            
            String[] strArr = utf8.decode(buf).toString().split(" ");
            
            this.inputToRequestMessage(testStr, rm); 
            
            switch (rm.method.toLowerCase()){
            
            case("get"):
            		
            		if(rm.requestTarget.equals("/")){
            			
            			
            			this.getFilesInDirectory(httpfs_members.dir, client);
            			//needs to send individual file 
            				
            			
            		}else{
            			//default 
            			String tgt = rm.requestTarget.replace("/", "\\");
            			this.returnFile(httpfs_members.dir + tgt, client);            			
            		}
            	
            	break; 
            
            case("post"): 	
            			
            			//needs to send individual file 
            			String tgt = rm.requestTarget.replace("/", "\\");
            		
            			this.createFile(httpfs_members.dir + tgt, rm.body.data, client ); 
            			
            		
            			
            		
            	
            	
            	
            	break; 
            
            default: 
            	
            	if(httpfs_members.isDebugging){
	            	System.out.println("invalid input to server"); 
	            	this.returnFile("Invalid Input to Server", client);
            	}
            	break; 
            
            }
            
            
            
            
            
            
            
//            this.httpfsAPI.serverCommandParse(strArr);            
            buf.clear();
	    }
	    
	    
	    private void inputToRequestMessage(String input, HTTP_RequestMessage rm) throws IOException {
			
	    	BufferedReader reader = new BufferedReader(new StringReader(input));
	    	
	    	String request_line = reader.readLine(); 
	    	
	    	String[] rl = request_line.split(" "); 
	    	
	    	rm.method = rl[0]; 
	    	rm.requestTarget = rl[1]; 
	    	rm.version = rl[2]; 
	    	
	    	
	    	String header; 
	    	
	    	while(((header = reader.readLine()) != null) && !("".equals(header)) ){
	    	
	    		String[] headers = header.split(":");
	    		
	    		rm.header.AddHeaderLine(headers[0], headers[1]);
	    		 
	    	}
	    	
	    	String body = ""; 
	    	String in; 
	    	
			while(( in = reader.readLine()) != null ){
	    		
	    		body = body.concat(in); 
	    		
	    	}
			
			rm.body.data = body; 
	    	
	    	reader.close();
	    	
			
		}


		private void createFile(String fileName, String body, SocketChannel client) throws IOException {
			// TODO Auto-generated method stub
	    	
	        //Creates a BufferedReader that contains the server response
            BufferedReader bufRead = new BufferedReader(new StringReader(body));
          
	    	PrintWriter writer;
			try {
				writer = new PrintWriter(fileName, "UTF-8");
				String outStr;
				
				 while((outStr = bufRead.readLine()) != null){
					 writer.println(outStr); 
					 writer.close();
				 }
				 writer.println(); 
				 writer.close();
				 
				 
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
	    	bufRead.close();
	    	
	    	this.returnFile(fileName, client);
			
		}


		private void getFilesInDirectory(String filePath, SocketChannel socket){
	    	
	    	
	    	try(BufferedWriter bufOut = new BufferedWriter(new OutputStreamWriter( socket.socket().getOutputStream() ) )){
		    		
	    		File folder; 
		    		
		    		try{
		    			 folder = new File(System.getProperty("user.dir"));
		    		
		    		File[] listOfFiles = folder.listFiles();
		    		
		    		
			    	
		    		
	
		    	for (int i = 0; i < listOfFiles.length; i++) {
		    	  if (listOfFiles[i].isFile()) {
		    	    bufOut.write( "File " + listOfFiles[i].getName() );
	                bufOut.newLine();
	                bufOut.flush();
		    	  } else if (listOfFiles[i].isDirectory()) {
		    		    bufOut.write( "Directory " + listOfFiles[i].getName() );
		                bufOut.newLine(); //HERE!!!!!!
		                bufOut.flush();
		    	  }	    	  
		    	}
		    	
		    		} catch(Exception e){
		    			
		    			if(httpfs_members.isDebugging){
			    			System.out.println("Couldn't access file");
			    		    bufOut.write( "Error Couldn't access file" );
			                bufOut.newLine(); //HERE!!!!!!
			                bufOut.flush();
			    		}
		    			
		    		}
		    	
	 
	    	}catch(Exception e) {
	    		System.out.println("IOException: Stream Buffer not opened");
	    	}
	    	
	    	
	    }
	    
	    
	    private void returnFile(String filePath, SocketChannel socket) throws IOException {
	    	
	    	File file = new File(filePath); 
	    	
	    	 
	    	
	    try( BufferedWriter bufOut = new BufferedWriter(new OutputStreamWriter( socket.socket().getOutputStream() ) )){
	    	
		try(Scanner scanner = new Scanner(file) ) {
				
	
	    	while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            bufOut.write( line );
                bufOut.newLine(); //HERE!!!!!!
                bufOut.flush();
	    }
	    	
	    	scanner.close();

	    }  catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
	    	
	    	if(this.httpfs_members.isDebugging){
				System.out.println("File Not Found" );
				bufOut.write( "HTTP 404: Not Found" );
	            bufOut.newLine(); //HERE!!!!!!
	            bufOut.flush();
	    	}
			
		} 
	    } catch (IOException e)
	    {
	    	System.out.println("Stream Error"); 
	    	
	    }
		
	    

	    }
}

//OptionParser parser = new OptionParser();
//parser.acceptsAll(asList("host", "h"), "EchoServer hostname")
//        .withOptionalArg()
//        .defaultsTo("localhost");
//
//parser.acceptsAll(asList("port", "p"), "EchoServer listening port")
//        .withOptionalArg()
//        .defaultsTo("8007");
//
//OptionSet opts = parser.parse(args);
//
//String host = (String) opts.valueOf("host");
//int port = Integer.parseInt((String) opts.valueOf("port"));
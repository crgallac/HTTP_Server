package server;

import static java.util.Arrays.asList;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;



import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class HTTPfsServerSetup {
	
	HTTPfs_API httpfsAPI; 
	
	


	public HTTPfsServerSetup(String[] args){
		
		try{
		this.serverSetup(args);
		this.httpfsAPI = new HTTPfs_API(); 
		}
		catch(IOException e) {
			System.out.println("Warning: Could not setup the Server. The listening port may already be in use. ");
		}
		
	}
	

    private void readAndReplyToClient(SocketChannel socket) {
    	
    		Charset utf8 = StandardCharsets.UTF_8;
    	 
	        try (SocketChannel client = socket) {
	      
	            ByteBuffer buf = ByteBuffer.allocate(1024);
	            
	            for (; ; ) {
	                int nr = client.read(buf);

	                if (nr == -1)
	                    break;

	                if (nr > 0) {
			            // Receive all what we have sent
	                    System.out.println("Recieved client request: ");
	                	
			            readFully(client, buf, nr);
			            buf.flip();
			            
			            String[] strArr = utf8.decode(buf).toString().split(" "); 
			            
//			            this.httpfsAPI.serverCommandParse(strArr);
			        	
			            OptionParser parser = new OptionParser();
			            parser.acceptsAll(asList("dir", "d"))
			               		.withRequiredArg(); 
			            parser.acceptsAll(asList("debug", "v"));     
			            
			            
			            OptionSet opts = parser.parse(strArr);

			            String dir = (String) opts.valueOf("dir");
			            System.out.println(dir); 
			            
			            boolean isDebugging = opts.has("v");

			            System.out.println(isDebugging);
			        	
			           
			            
			            buf.clear();
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
	        OptionParser parser = new OptionParser();
	        parser.acceptsAll(asList("port", "p"), "Listening port")
	                .withOptionalArg()
	                .defaultsTo("8080");
	        
	        
	        OptionSet opts = parser.parse(args);
	        int port = Integer.parseInt((String) opts.valueOf("port"));
	        
	       
	        this.listenAndServe(port);
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
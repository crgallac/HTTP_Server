package client;



public class HTTP_RequestMessage {
	

		String method; 
		String requestTarget; 
		String version; 
		
		
		String url;
		
		
		Integer port; 
		
		HTTP_Header header; 
		HTTP_Body body; 
	    
		boolean isVerbose = false; 
	    boolean isOutputToFile = false; 
	    
	    String outputFile; 
	    
	    public HTTP_RequestMessage(){
	    	
	    	header = new HTTP_Header(); 
	    	body = new HTTP_Body(); 
	    	
	    	outputFile = new String(); 
	    }

	    
	  
	    
	    
	}
	
	
	
	
	


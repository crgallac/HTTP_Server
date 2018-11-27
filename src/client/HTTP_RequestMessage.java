package client;



public class HTTP_RequestMessage {
	

		public String method; 
		public String requestTarget; 
		public String version; 
		
		
		String url;
		
		
		Integer port; 
		
		public HTTP_Header header; 
		public HTTP_Body body; 
	    
		boolean isVerbose = false; 
	    boolean isOutputToFile = false; 
	    
	    String outputFile; 
	    
	    public HTTP_RequestMessage(){
	    	
	    	header = new HTTP_Header(); 
	    	body = new HTTP_Body(); 
	    	
	    	outputFile = new String(); 
	    }

	    
	  
	    
	    
	}
	
	
	
	
	


package server;

public class HTTPfs_Members {

	public String dir; 
	public boolean isDebugging; 
	public Integer port; 
	
	
	public HTTPfs_Members(){
		
		this.dir = System.getProperty("user.dir");
		System.out.println(dir); 
		this.isDebugging = false; 
		this.port = 8080; 
		
	}
	
}

package client;

import java.util.HashMap;
import java.util.Map;

public class HTTP_Header {
	
    Map<String, String> headerList; 

    
    public HTTP_Header(){
	headerList = new HashMap<String, String>();
	
		
    }
		
    
    public String HeaderToString() {
    	
		String tmpString = new String(); 
    	
    	
		for (Map.Entry<String, String> entry : headerList.entrySet()) {
		    
	
			
			tmpString = tmpString.concat(entry.getKey()); 
			tmpString = tmpString.concat(":");
			tmpString = tmpString.concat(entry.getValue()); 
			tmpString = tmpString.concat("\r\n"); 
			
		}
    	
    	
    	return tmpString; 
    }
    
    public void AddHeaderLine(String fieldName, String value) {
    	
    	headerList.put(fieldName, value);
    	
    	
    }
    
    public void AddHeaderLine(String fieldName, Integer value) {
    	
    	headerList.put(fieldName, value.toString());
    	
    	
    }
    
    
    
    public void RemoveHeaderLine(String fieldName) {
    	
    	headerList.remove(fieldName);
    	
    }
    
    
		
	}
	
	
	
	

	
	


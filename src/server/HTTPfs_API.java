package server;

import static java.util.Arrays.asList;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class HTTPfs_API {
	
	
	  //check to see if get or 
    
    public void serverCommandParse(String[] args){

 	OptionParser parser = new OptionParser();
    parser.acceptsAll(asList("dir", "d"))
       		.withRequiredArg(); 
    parser.acceptsAll(asList("debug", "v"));     
    
    
    OptionSet opts = parser.parse(args);

    String dir = (String) opts.valueOf("dir");
    System.out.println(dir); 
    
    boolean isDebugging = opts.has("v");

    System.out.println(isDebugging);
	
    }
	

}

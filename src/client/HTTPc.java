package client;

public class HTTPc {
//
//	 public HTTP_Object createHTTPObject(String ar[]) {
//	        HTTP_Object http = new HTTP_Object();
//	        for (int i = 0; i < ar.length; i++) {
//
//	            // System.out.println("arguments : " + args[i]);
//	            if (i == 1) {
//	                if (ar[i].toLowerCase().equals("get")) {
//	                    http.typeRequest = "GET";
//	                } else if (ar[i].toLowerCase().equals("post")) {
//	                    http.typeRequest = "POST";
//	                } else {
//	                    System.out.println("Un-Supported Type");
//	                }
//	            }
////	            if (ar[i].equals("-v")) {
////	                http.isVerbose = true;
////	            }
////	            if (ar[i].equals("-h")) {
////	                http.headers.add(ar[i + 1]);
////	            }
////	            if (ar[i].equals("-d")) {
////	                http.isInline = true;
////	                http.data = ar[i + 1].replace("'", "");
////	            }
////	            if (ar[i].equals("-f")) {
////	                http.readFromFile = true;
////	                http.fileName = ar[i + 1].replace("'", "");
////	            }
////	            if (ar[i].startsWith("'http://")) {
////	                http.url = ar[i].replace("'", "");
////	                http.host = http.url.split("/")[2];
////	                http.urlParamaters = http.url.split("/")[3];
////	            }
////	            if (ar[i].equals("-o")) {
////	                http.writeTofile = true;
////	                http.fileName = ar[i + 1];
////	            }
//	        }
//	        return http;
//	    }
//
//	    public static String combineData(String args[]) {
//	        String results = "";
//	        int startIndex = 0, endIndex = 0;
//	        for (int i = 0; i < args.length; i++) {
//	            if (args[i].startsWith("'")) {
//	                startIndex = i;
//	            }
//	            if (args[i].endsWith("'")) {
//	                endIndex = i;
//	            }
//
//	        }
//	        for (int j = startIndex; j <= endIndex; j++) {
//	            results += args[j];
//	        }
//
//	        return results;
//	    }
//
//	    public void helpMethod(String str[]) {
//	        if (str.length == 2 & str[0].equals("httpc") & str[1].equals("help")) {
//	            System.out.println("httpc is a curl-like application but supports HTTP protocol only.\n" + "Usage: \n"
//	                    + "   httpc command [arguments]\n " + "The commands are:\n"
//	                    + "   get executes a HTTP GET request and prints the response.\n"
//	                    + "   post executes a HTTP POST request and prints the response. \n"
//	                    + "   help prints this screen. \n"
//	                    + "Use \"httpc help [command]\" for more information about a command.\n");
//
//	        } else if (str.length == 3 & str[0].equals("httpc") & str[1].equals("help") & str[2].equals("get")) {
//	            System.out.println(
//	                    "usage: httpc get [-v] [-h key:value] URL\n" + "Get executes a HTTP GET request for a given URL.\n"
//	                            + "   -v            Prints the detail of the response such as protocol, status,\n"
//	                            + "and headers.\n" + "   -h key:value  Associates headers to HTTP Request with the format\n"
//	                            + "'key:value'.");
//
//	        } else if (str.length == 3 & str[0].equals("httpc") & str[1].equals("help") & str[2].equals("post")) {
//	            System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\n"
//	                    + "Post executes a HTTP POST request for a given URL with inline data or from\n" + "file.\n"
//	                    + "   -v             Prints the detail of the response such as protocol, status,\n"
//	                    + "and headers.\n" + "   -h key:value   Associates headers to HTTP Request with the format\n"
//	                    + "'key:value'.\n" + "   -d string      Associates an inline data to the body HTTP POST request.\n"
//	                    + "   -f file        Associates the content of a file to the body HTTP POST\n" + "request.\n"
//	                    + "Either [-d] or [-f] can be used but not both.");
//	        }
//	    }
//	
//	
}

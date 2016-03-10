package PA2;

import java.util.concurrent.ConcurrentHashMap;


public class ThreadProcess extends Thread {

	private ConcurrentHashMap<String, Integer> cHashMap;
	private String line;

	ThreadProcess(String line,ConcurrentHashMap<String, Integer> hashMap){
		
		/*
		 SortControl initializes the Thread Process constructor
		 This constructor gives its values here 
		 */
		
		this.line = line;
		this.cHashMap = hashMap;
	}

	private void addToHashMap(String[] tokens){
		Integer value=null;
		
		for(String key: tokens){ //For each key in tokens, same as (for int key=0; key<token.length(); key++)
			if(key.trim().length() == 0){ //remove leading and trailing white spaces
				continue;
			}
			
			value = this.cHashMap.get(key); //get value corresponding to the given key
			if(value == null){
				this.cHashMap.put(key, 1); //associates key with the value 1
			}else{
				
				this.cHashMap.put(key, value+1);
			}
		
		}
	}
	private void processText(){	
		String[] tokens = line.split("\\r?\\n"); //splits the string based on newline and saves them into a list based structure
		this.addToHashMap(tokens); 
	}
	
	public void run(){
		this.processText();
	}
	
}

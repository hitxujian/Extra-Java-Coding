package PA2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ThreadRead extends Thread {

	private ConcurrentLinkedQueue<String> jobQueue;
	private File file;
	private int chunkSize =1000; //1048576;
	private long processingTime = 0;
	
	ThreadRead(File file,ConcurrentLinkedQueue<String> jobQueue,int chunkSize){
		
		/*
		 SortControl initializes the Thread Read constructor
		 This constructor gives its values here 
		 */
		
		this.file = file;
		this.jobQueue = jobQueue;
		this.chunkSize = chunkSize;
		
	}
	

	private void readFileChunk() {
		
		/*
		 * Reads one chunk of the given file at a time
		 */
		
		long startTime = 0;
		boolean readFlag = false;
		try {
			FileInputStream fis = new FileInputStream(file); //initiailize FileInputStream
		    byte[] chunk = new byte[chunkSize]; 
		    while ( fis.read(chunk) != -1) { //== -1 indicates end of file, this specifies op when not end of file
		    	System.out.println("\nReading the file and Sorting...\n");
		    	String s = new String(chunk);
		    	while(this.jobQueue.size()>20){
		    		if (!readFlag){ //if haven't started reading yet
		    			startTime = System.currentTimeMillis();
		    			readFlag = true;
		    		}
		    		try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	}
		    	if(readFlag){
		    		processingTime = processingTime +(System.currentTimeMillis()-startTime);
		    		readFlag = false;
		    	}
		    	jobQueue.add(s);
		    	
		    }
		    fis.close();
		}
		
		catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public long getProcessingTime() {
		return processingTime;
	}
	
	public void run(){
		this.readFileChunk();
	}
}

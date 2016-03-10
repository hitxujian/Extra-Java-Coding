package PA2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SortControl {

	private ConcurrentHashMap<String, Integer> cHashMap;
	private long timeElapsed;
	private int maxThreadCount = 4;
	private long readingTime = 0;
	private long processingTime = 0;
	private int chunkSize = 1000;

	SortControl(int maxThreadCount, int chunkSize) {
		/*
		main() initializes the SortControl constructor and the constructor
		gives it these values
		*/
		
		cHashMap = new ConcurrentHashMap<String, Integer>(61176, 0.7f, 4);
		this.maxThreadCount = maxThreadCount;
		this.chunkSize = chunkSize;
	}

	public void startProcess(File file) {
		
		/*
		 * Initializes and executes the thread operations
		 * Namely ThreadRead and ThreadProcess
		 */
		long processStart = System.currentTimeMillis();
		ConcurrentLinkedQueue<String> jobQueue = new ConcurrentLinkedQueue<String>();
		ThreadRead rt = new ThreadRead(file, jobQueue, chunkSize); //initialize ThreadRead
		ThreadProcess t = null; //initialize ThreadProcess
		boolean processFlag = false; //Indicates whether thread processing is taking place
		long startTime = 0;
		ThreadProcess[] thread = new ThreadProcess[this.maxThreadCount];
		for (int i = 0; i < thread.length; i++) {
			thread[i] = null;
		}
		rt.start(); //start ThreadRead thread
		while (rt.isAlive() || jobQueue.size() > 0) {
			while (jobQueue.size() == 0) {
				if (!processFlag) { //indicates no thread is being processed
					startTime = System.currentTimeMillis();
					processFlag = true;
				}
			}
			if (processFlag) { //indicates thread is being processed
				readingTime = readingTime
						+ (System.currentTimeMillis() - startTime);
				processFlag = false;
			}
			for (int i = 0; i < maxThreadCount && i < jobQueue.size(); i++) {
				t = thread[i];
				if (t == null || !t.isAlive()) {
					t = new ThreadProcess(jobQueue.element(), this.cHashMap); //returns element at the head of the queue
					t.start(); 
					jobQueue.remove();
					thread[i] = t;

				}
			}
		}
		this.timeElapsed = (long) ((System.currentTimeMillis() - processStart) * 0.001);
		this.processingTime = rt.getProcessingTime();
		this.writeResultToFile();
	}

	private void writeResultToFile() {
		
		/*
		 * Performs sorting operation on the read data
		 * Writes sorting result to file
		 */

		FileWriter f1, f2;
		BufferedWriter br = null;
		try {
			ArrayList<String> list = new ArrayList<String>();
			for (Entry<String, Integer> entry : this.cHashMap.entrySet()) {
				list.add(entry.getKey());
			}
			

			System.out.println("\nTime elapsed: "+ this.timeElapsed + " seconds\n");
			System.out.println("Processing time: " + (this.processingTime * 0.001) + " seconds\n");
			System.out.println("Reading time: " + (this.readingTime * 0.001) + " seconds\n");

			
			SortList sl = new SortList(list);
			sl.sortGivenArray();
			
			f1 = new FileWriter("Sorted_Result_10_mb.txt");
			f2 = new FileWriter("Sorted_Result_100_mb.txt");
			
			if (list.size() <= 200000)
				br = new BufferedWriter(f1);
			else if (list.size() >= 900000)
				br = new BufferedWriter(f2);

			br.write("Time elapsed: " + this.timeElapsed + " seconds\n");
			br.write("Processing Time: " + (this.processingTime * 0.001) + " seconds\n");
			br.write("Reading time: " + (this.readingTime * 0.001) +" seconds\n");
			br.write("===================================================================================================\n\n\n");
	
			
			for (int i = 0; i < list.size(); i++) {
				if (i < 10 || i >= list.size()-10){
					br.write((String) list.get(i).toString() + " ");
					br.write("\n");
				}
			}

			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

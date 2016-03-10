package PA2;

import java.io.File;

public class SortMain {

	/**
	 * @param args
	 * 
	 */
	
	public static void main(String[] args) {
		
		
		if(args.length !=1){
			System.out.println("\nPlease enter the file to be sorted in the command line as:  "
					+ "\n\tjava -jar <jar file name> <input file path>");
			return;
		}
		String filePath = args[0];
		int maxthread = 4;
		int chunkSize = 1048576;
		SortControl mc = new SortControl(maxthread,chunkSize);
		
		long startTime = 0;
		long endTime = 0;
		long totalTime = 0;
		startTime=System.currentTimeMillis();
		mc.startProcess(new File(filePath)); //Initialize startProcess method of SortControl class
		endTime=System.currentTimeMillis();
		totalTime=endTime-startTime;
		System.out.println("\nTotal Operation Time: "+(totalTime * 0.001)+" seconds");
		
	}

}




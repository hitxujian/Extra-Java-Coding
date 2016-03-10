package PA2;

import java.util.ArrayList;

public class SortList {
	
	//Performs merge sort operation on the given array list
	
    private ArrayList<String> inputArray;
     
    public ArrayList<String> getSortedArray() {
        return inputArray;
    }
 
    public SortList(ArrayList<String> inputArray){
        this.inputArray = inputArray;
    }
     
    public void sortGivenArray(){       
        divide(0, this.inputArray.size()-1);
    }
     
    public void divide(int startIndex,int endIndex){
         
        //Divide list, split it down the middle
        if(startIndex<endIndex && (endIndex-startIndex)>=1){
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid); //Apply the method recursively
            divide(mid+1, endIndex);        
             
            //merging Sorted array produce above into one sorted array
            merger(startIndex,mid,endIndex);            
        }       
    }   
     
    public void merger(int startIndex,int midIndex,int endIndex){
         
        //Sorting the merged array list
        ArrayList<String> mergedSortedArray = new ArrayList<String>();
         
        int leftIndex = startIndex;
        int rightIndex = midIndex+1;
         
        while(leftIndex<=midIndex && rightIndex<=endIndex){
            if(inputArray.get(leftIndex).compareTo(inputArray.get(rightIndex)) <= 0){ //sorting the elements alphabetically
                mergedSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;
            }
        }       
         
        while(leftIndex<=midIndex){
            mergedSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }
         
        while(rightIndex<=endIndex){
            mergedSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }
         
        int i = 0;
        int j = startIndex;
        //Merging individual array lists into a single output array list
        while(i<mergedSortedArray.size()){
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }
}

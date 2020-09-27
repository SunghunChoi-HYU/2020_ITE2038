package bptree;

import java.io.*;
import java.util.HashMap;

public class main {

	public static void main(String[] args) {
		bptree<Integer, Integer> BplusTree = new bptree<Integer, Integer>(3);
		switch(args[0]) {
		case "-c":
			PrintWriter pwc = null;
    		try { 	
    			pwc = new PrintWriter(new FileWriter(args[1]));
    			pwc.println(args[2]);
    		} catch (Exception e) {
    			System.out.println(e);
    		} finally {
    			try {
    				pwc.close();
    			} catch(Exception e) {}
    		}
		break;
		
		case "-i" :
			FileReader fri = null;
			FileReader fri2 = null;
			FileWriter fwi = null;
			BufferedReader bri = null;
			BufferedReader bri2 = null;
			BufferedWriter bwi = null;
			int degree = 0;
			 try {
				BplusTree = BplusTree.FileLoad(args[1], BplusTree);
			 	fri = new FileReader(args[2]);
			 	bri = new BufferedReader(fri);
			 	fwi = new FileWriter(args[1]);
			 	bwi = new BufferedWriter(fwi);
			
			 	String keyNvalue;
			 	while((keyNvalue = bri.readLine())!= null) {
			 		String[] keyNvaluesplit = keyNvalue.split(",");
			 		int key = Integer.parseInt(keyNvaluesplit[0]);
			 		int value = Integer.parseInt(keyNvaluesplit[1]);
					BplusTree.insert(key, value);
			 	}
			 		BplusTree.FileSave(args[1], BplusTree);
			 }catch(Exception e) {
			 	e.printStackTrace();
			 }finally {
			 	if(bri!= null) try {bri.close();}catch(IOException e){}
			 	if(fri!= null) try {fri.close();}catch(IOException e) {}
			 	if(bwi!= null) try {bri.close();}catch(IOException e){}
			 	if(fwi!= null) try {fri.close();}catch(IOException e) {}
			 }
			 break;
			 
		 case "-d" :
    		FileReader frd = null;
	    	FileReader frd2 = null;
	   		BufferedReader brd = null;
	   		BufferedReader brd2 = null;
    		try {
	    		frd = new FileReader(args[2]);
	    		brd = new BufferedReader(frd);
	   			BplusTree = BplusTree.FileLoad(args[1], BplusTree);
	    			
	    		String delKey;
	    		while((delKey = brd.readLine()) != null) {
	    			BplusTree.delete(Integer.parseInt(delKey));
	   			}
	    		BplusTree.FileSave(args[1], BplusTree);

	   		} catch(Exception e) {
    			e.printStackTrace();
	    	} finally {
	    		if(frd!= null) try{frd.close();} catch(IOException e) {}
	    		if(brd!= null) try{brd.close();} catch(IOException e) {}
	    	}
	    	break;
	    		
	   	case "-s" :
	   		BplusTree = BplusTree.FileLoad(args[1], BplusTree);
	   		int searchKey = Integer.parseInt(args[2]);
	   		System.out.println(BplusTree.search(searchKey));
	   	break;	
	    	
	   	case "-r" : 
	   		BplusTree = BplusTree.FileLoad(args[1], BplusTree);
	   		int searchStartKey = Integer.parseInt(args[2]);
	   		int searchEndKey = Integer.parseInt(args[3]);
	   		BplusTree.searchRange(searchStartKey, searchEndKey);
	    break;
	    		
	   	default :
	   		System.out.println("Wrong input");
	   		break;
	   	}
	}
}
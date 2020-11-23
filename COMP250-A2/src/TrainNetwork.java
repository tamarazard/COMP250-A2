public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
    	this.networkLines = new TrainLine[nLines];
    }
    
    public void addLines(TrainLine[] lines) {
    	this.networkLines = lines;
    }
    
    public TrainLine[] getLines() {
    	return this.networkLines;
    }
    
    public void dance() {
    	System.out.println("The tracks are moving!");
    	//YOUR CODE GOES HERE
    	for (int i=0; i<networkLines.length; i++) {
    		networkLines[i].shuffleLine();
    	}
    }
    
    public void undance() {
    	//YOUR CODE GOES HERE
    	for (int i=0; i<networkLines.length; i++) {
    		networkLines[i].sortLine();
    	}
    }
    
    public int travel(String startStation, String startLine, String endStation, String endLine) {
    	
    	TrainLine curLine = null; //use this variable to store the current line.
    	TrainStation curStation = null;  //use this variable to store the current station. 
    	
    	curLine = getLineByName(startLine);
    	curStation= curLine.findStation(startStation);
    	
    	int hoursCount = 0;
    	System.out.println("Departing from "+startStation);
    	
    	//YOUR CODE GOES HERE
    	TrainStation previous = null;
    	TrainStation tmp;
    	int stationVisited = 0;
    	
    		
    	
    	while(!curStation.getName().equalsIgnoreCase(endStation)) {
	        	
	    //	for(int k=0; k<networkLines.length; k++) {
	    	//	if(networkLines[k].equals(curLine)) {
	    			//for(int i=k; i<networkLines.length; i++) {
		    		//	curLine = networkLines[i];
		    			    			
		    			//prints an update on your current location in the network.
			    	System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
			    	System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
			    	System.out.println("=============================================");
			    	
			    	if(hoursCount == 168 || hoursCount>168) {
			    		System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
			    			return hoursCount;
			    	}
			    	
			    	if(previous==null) {
			    		previous = curStation;
		    			if(!curStation.hasConnection) {
		    					curStation = curLine.getNext(curStation);
		    				}
		    			else {
		    				curStation = curStation.getTransferStation();
		    			}
			    	}
			    	else {
			    		tmp = curStation;
			    		curStation = curLine.travelOneStation(curStation, previous);
			    		previous = tmp;
			    	}
		    			
		    			//curStation=curStation.getRight();
		    		
			    		curLine=curStation.getLine();
		    			
		    			stationVisited++;
		    			hoursCount++;
		    			
		    			if(hoursCount%2==0) dance();  	    			    			
	    			
    	
		    			
    	}	
    	System.out.println("Arrived at destination after "+hoursCount+" hours!");
		return hoursCount;
    //		} 
    //	}	   
	    		
	    	//break; //remove this! this break is only there so the template compiles.
    		}
	    	
	    	
//    }
    
    
    //you can extend the method header if needed to include an exception. You cannot make any other change to the header.
    public TrainLine getLineByName(String lineName){
    	//YOUR CODE GOES HERE
    	
    	for (int i=0; i<networkLines.length; i++) {
    		
    		if(this.networkLines[i].getName().equals(lineName)){
    			return networkLines[i];
    		}
    		
    	}
    			throw new LineNotFoundException("No line with that name exists");
    		
    	//return null; //change this
    }
    
  //prints a plan of the network for you.
    public void printPlan() {
    	System.out.println("CURRENT TRAIN NETWORK PLAN");
    	System.out.println("----------------------------");
    	for(int i=0;i<this.networkLines.length;i++) {
    		System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
    		}
    	System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	   String name;

	   public LineNotFoundException(String n) {
	      name = n;
	   }

	   public String toString() {
	      return "LineNotFoundException[" + name + "]";
	   }
	}
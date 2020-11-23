import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		// YOUR CODE GOES HERE 
		int size=0;		
//		TrainStation tStation;
//		for(tStation = leftTerminus; tStation!=null; tStation=tStation.getRight()) {
//			size++;
//		}                                                                           
		TrainStation tStation = this.leftTerminus;
		
		while(tStation!=null) {
			
			size++;
			tStation= tStation.getRight();
			
		}
		return size;		     
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	
	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		// YOUR CODE GOES HERE
		TrainStation tStation;
	
		this.findStation(current.getName());
		
			if(current.hasConnection) {										//see if its on the line
					if(current.getTransferStation().equals(previous)) {
						tStation =getNext(current);
						return tStation;
						}
					else {
						tStation = current.getTransferStation();
						return tStation;
				}
					
			}else {
				tStation = getNext(current);
				return tStation;
			}
//		if(getNext(current).equals(previous)) {
//			tStation = current;
//		}
//		else {
//			current = getNext(current);
//			previous = current;
//			tStation = current;
//		}
	//	return tStation;
}
	

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) {
		// YOUR CODE GOES HERE
	if(findStation(station.getName()).equals(station)){										//not sure at all
		if(station.isRightTerminal()) {
				if(goingRight) reverseDirection();
				return station.getLeft();
				
			} else if(station.isLeftTerminal()){
				if (!goingRight)reverseDirection();
				return station.getRight();
		}
		else if(goingRight) {
				return station.getRight();
			}
		else {
			return station.getLeft();
		} 
														//maybe add isRighTerminal and left
	}
	else {
		throw new StationNotFoundException("The station is not in the line");
	}
		}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) {
		// YOUR CODE GOES HERE 
		TrainStation station;
		leftTerminus.setLeftTerminal();
		station= leftTerminus;
		
		    while(station!=null) {
				    if(station.getName().equals(name)){
				    	return station;
				    }
			
				   	station=station.getRight();
				    
		    }
		  // if(station.getRight().equals(null)) {
		  		throw new StationNotFoundException("This station is not found"); 
		  
	}

	public void sortLine() {

		// YOUR CODE GOES HERE
		int N = getSize();
	//	int k;
		TrainStation[] list = getLineArray();
		TrainStation element;
		//list = getLineArray();
		
//		for (int i=0; i<N; i++) {
//			for(int k=0; k<N-i-1; k++) {
//				if(list[k].getName().compareToIgnoreCase(list[k+1].getName())>0) {
//					swap(list[k],list[k+1]);
//					
//				}
//			}
				
//			element = list[i];
//			k=i;
//				while(k>0 && list[k].getName().compareToIgnoreCase(list[k-1].getName())<0) {
//					list[k]=list[k-1];
//					k = k-1;
//				}
//			list[k] = element;
		

			
			boolean ct=true;
			TrainStation tStation=this.getLeftTerminus();
			int k=0;
			while (ct) {
				ct=false;
				tStation=this.getLeftTerminus();
				
				for (int i=0; i<getSize()-k;i++) {
					if (tStation.getName().compareTo(tStation.getRight().getName())>0) {
					swap (tStation,tStation.getRight());
					
					if (tStation.getLeft()==null) {
						this.leftTerminus=tStation;
						tStation.setNonTerminal();
						tStation.setLeftTerminal();
					}
					else if (tStation.getRight()==null) 	{	
						this.rightTerminus=tStation;
						tStation.setNonTerminal();
						tStation.setRightTerminal();
					}
				 }
					k++;
		        }
				this.lineMap=this.getLineArray();
			for(int i = 0;i<getSize()-1;i++) {
					if(i==0) {
						lineMap[i].setNonTerminal();
						lineMap[i].setLeftTerminal();
						lineMap[i].setLeft(null);
						lineMap[i].setRight(lineMap[i+1]);
					leftTerminus = lineMap[i];
					}
					else if(i==getSize()-1) {
						lineMap[i].setNonTerminal();
					lineMap[i].setRightTerminal();
						lineMap[i].setRight(null);
						lineMap[i].setLeft(lineMap[i-1]);
						rightTerminus = lineMap[i];
					}
					else {
						lineMap[i].setNonTerminal();
						lineMap[i].setRight(lineMap[i+1]);
						lineMap[i].setLeft(lineMap[i-1]);
					}
					}
					}
				
	
		/*lineMap[0].setNonTerminal();
		lineMap[0].setLeftTerminal();
		
		lineMap[0].setLeft(null);
		lineMap[0].setRight(lineMap[1]);
		leftTerminus = lineMap[0];
		
		lineMap[lineMap.length-1].setNonTerminal();
		lineMap[lineMap.length-1].setRightTerminal();
		
		lineMap[lineMap.length-1].setLeft(lineMap[lineMap.length-2]);
		lineMap[lineMap.length-1].setRight(null);
		rightTerminus = lineMap[lineMap.length-1];
		
		for(int j=1; j<list.length-2; j++) {
			lineMap[j].setLeft(lineMap[j-1]);
			lineMap[j].setRight(lineMap[j+1]);
			lineMap[j].setNonTerminal();
		}*/
		
		
//		this.lineMap[0] = this.leftTerminus;
//			for(int t=0; t<lineMap.length-1; t++) {
//				lineMap[t+1] = lineMap[t].getRight();
//			}
		
		
		
//		lineMap = list;
//		leftTerminus = lineMap[0];
//		rightTerminus = lineMap[getSize()-1];
	}
		
//		TrainStation tStation;
//		
//		for(tStation = leftTerminus; tStation!=null; tStation=tStation.getRight()) {
//			if(tStation.getName().compareToIgnoreCase(tStation.getRight().getName())>0) {
//				swap(tStation,tStation.getRight());
//			}
//		}
//	}
	private void swap (TrainStation A,TrainStation B) {
		TrainStation tmp= A;
		A.setRight(B.getRight());
		A.setLeft(B);
		B.setRight(A);
		B.setRight(tmp.getLeft());
	}

		
	

	public TrainStation[] getLineArray() {
		// YOUR CODE GOES HERE
		leftTerminus.setLeftTerminal();
		TrainStation[] lineArray = new TrainStation[getSize()];
		TrainStation cur = leftTerminus;
		
		
		for (int i=0; i<getSize(); i++) {
			lineArray[i] = cur;
			cur=cur.getRight();
		}
	   		
		return lineArray; // change this
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
			
		rand.setSeed(11);
		
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		
		// YOUR CODE GOES HERE
		//this.lineMap = shuffledArray;
//		lineMap[0].setLeftTerminal();
//		lineMap[getSize()-1].setRightTerminal();
//		this.leftTerminus = shuffledArray[0];
//		this.leftTerminus.setLeftTerminal();
//		
//				
//		this.rightTerminus = shuffledArray[shuffledArray.length-1];
//		this.rightTerminus.setRightTerminal();
//		
//		
//		shuffledArray[0].setRight(shuffledArray[shuffledArray.length-1]);
//		shuffledArray[0].setNonTerminal();
//		shuffledArray[shuffledArray.length-1].setLeft(shuffledArray[0]);
//		shuffledArray[shuffledArray.length-1].setNonTerminal();
//		
//		for(int k =1; k<shuffledArray.length-1; k++) {
//			
//			
//			shuffledArray[k].setNonTerminal();
//			addStation(shuffledArray[k]);
//						}
		shuffledArray[0].setNonTerminal();
		shuffledArray[0].setLeftTerminal();
		
		shuffledArray[0].setLeft(null);
		shuffledArray[0].setRight(shuffledArray[1]);
		leftTerminus = shuffledArray[0];
		
		shuffledArray[shuffledArray.length-1].setNonTerminal();
		shuffledArray[shuffledArray.length-1].setRightTerminal();
		
		shuffledArray[shuffledArray.length-1].setLeft(shuffledArray[shuffledArray.length-2]);
		shuffledArray[shuffledArray.length-1].setRight(null);
		rightTerminus = shuffledArray[shuffledArray.length-1];
		
		for(int i=1; i<shuffledArray.length-1; i++) {
			shuffledArray[i].setLeft(shuffledArray[i-1]);
			shuffledArray[i].setNonTerminal();
			shuffledArray[i].setRight(shuffledArray[i+1]);
		//	lineMap[i] = shuffledArray[i];
			
		}
		//lineMap = shuffledArray;
			}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}

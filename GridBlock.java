//M. M. Kuttel 2024 mkuttel@gmail.com
// GridBlock class to represent a block in the grid.
// only one thread at a time "owns" a GridBlock - this must be enforced

package medleySimulation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GridBlock {
	
	private int isOccupied; 
	private final Lock lock = new ReentrantLock();
	private final boolean isStart;  //is this a starting block?
	private int [] coords; // the coordinate of the block.
	
	GridBlock(boolean startBlock) throws InterruptedException {
		isStart=startBlock;
		isOccupied= -1;
	}
	
	GridBlock(int x, int y, boolean startBlock) throws InterruptedException {
		this(startBlock);
		coords = new int [] {x,y};
	}
	
	public   int getX() {return coords[0];}  
	
	public   int getY() {return coords[1];}
	
	
	
	//Get a block
	public  boolean get(int threadID) throws InterruptedException {
		lock.lock();
		try {
		if (isOccupied==threadID) return true; //thread Already in this block
		if (isOccupied>=0) return false; //space is occupied
		isOccupied= threadID;  //set ID to thread that had block
		return true;}
		finally{
			lock.unlock();
		}

	}
		
	
	//release a block
	public  void release() {
		lock.lock();
		try{
		isOccupied= -1;
		}
		finally{
			lock.unlock();
		}
	}
	

	//is a bloc already occupied?
	public  boolean occupied() {
		lock.lock();
		try{
		if(isOccupied==-1) return false;
		return true; }
		finally{
			lock.unlock();
		}
	}
	
	
	//is a start block
	public  boolean isStart() {
		return isStart;	
	}

}

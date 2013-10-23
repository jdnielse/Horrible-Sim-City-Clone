package objects;
/*
 * This class is the base for all displayed objects in the game
 */
public class GameTile {

	int posX = -1, posY = -1;	//the position of the object
	int sizeX = -1, sizeY = -1; //the size of the object
	int airPollution = 0, waterPollution = 0;
	int ownerid = 0; // 0 is unowned
	public TType type = TType.NONE;
	
	public enum TType{ //tile type
		COMM,
		IND,
		RES,
		ROAD,
		NONE
	}
	
	public GameTile(int x, int y){
		posX = x;
		posY = y;
		
	}
	
	public GameTile(){
		
	}
	
	
}

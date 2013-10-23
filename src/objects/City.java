package objects;

import java.util.ArrayList;

import objects.GameTile.TType;

/**
 * Stores info about the city
 * @author Jon
 *
 */
public class City {
	
	//how to store the map? GameObject[][], ArrayList<ArrayList<GameObject>>?
	public GameTile[][] map;
	public ArrayList<GameTile> dirty = new ArrayList<GameTile>(); //when a tile is updated, mark it dirty
	//public boolean allDirty = true;
	public City(int x,int y){

		map = new GameTile[x][y];
		
		

		for(int i =0;i < x;i++){
			for(int j =0; j < y;j++){
				//read map file here

				map[i][j] = new GameTile(i,j);
				
//				double rand = Math.random();
//				int gen = (int) (rand * 3);
//				if(i%2==gen){
//					map[i][j].type = TType.RES;
//				}
//				if(j%2==gen) map[i][j].type = TType.COMM;
				
			}
		}
	}
}

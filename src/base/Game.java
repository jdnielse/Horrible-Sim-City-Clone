package base;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import objects.*;
import objects.GameTile.TType;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame{

	float zoom = 2f; //remember how zoomed out we are
	City c;
	int offX, offY;
	Image yell, blue, green, black, grey, err;
	HashMap<TType, Image> TTypeMap;
	boolean lrMove = false;
	boolean udMove = false;
	int lrTime = 0, udTime = 0;
	TType selected;
	long cash;

	public Game() {
		super("City Sim");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//g.drawString("Dis gonna be a good game", 100, 100);

		//go through our grid and draw it
		//each tile is 100px, zoom 0
		//calculate steps
		int stepX = (int) (gc.getWidth()/(zoom*100));
		int stepY = (int) (gc.getHeight()/(zoom*100));


		//if(c.allDirty){
		//c.allDirty = false;
		//need to change i and j to move map, not offset
		for(int i =0; i <= stepX; i++){
			for(int j = 0; j <= stepY; j++){
				//make sure its not outside the city
				if(i+offX>c.map.length-1 || j+offY > c.map[0].length-1||i<0||j<0){
					err.draw(i*zoom*100, j*zoom*100, zoom);
				}else{
					//based on our zoom level, decide how much to show
					//add offset x and y to each tile
					TTypeMap.get(c.map[i+offX][j+offY].type).draw(i*zoom*100, j*zoom*100, zoom);
					//System.out.println("i*stepX: "+i*zoom*100);
				}
			}
		}
		//}

		//		for(int i =0; i < c.map[0].length; i +=1){
		//			for(int j =0; j < c.map.length; j +=1){
		//				TTypeMap.get(c.map[i][j].type).draw(i*zoom*100, j*zoom*100, zoom);
		//			}
		//		}
		g.setColor(Color.black);
		g.fillRect(0, gc.getHeight()-50, 200, 100);
		//type user what is selected
		g.setColor(Color.white);
		g.drawString(selected.name(), 0, gc.getHeight()-15);
		g.drawString("$"+NumberFormat.getNumberInstance(Locale.US).format(cash), 0, gc.getHeight()-30);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		c = new City(10,10); //create our new city
		selected = TType.RES;
		cash = 100000;
		offX = 0;
		offY = 0;

		//load our textures
		yell = new Image("assets/yell.png");
		blue = new Image("assets/blue.png");
		green = new Image("assets/green.png");
		black = new Image("assets/black.png");
		grey = new Image("assets/grey.png");
		err = new Image("assets/err.png");

		//setup our type enum to texture map
		TTypeMap = new HashMap<TType, Image>();
		TTypeMap.put(TType.COMM, blue);
		TTypeMap.put(TType.NONE, grey);
		TTypeMap.put(TType.IND, yell);
		TTypeMap.put(TType.RES, green);
		TTypeMap.put(TType.ROAD, black);

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input i = gc.getInput();
		if(udMove) udTime+=delta;
		if(lrMove) lrTime +=delta;
		if(udTime> 200){
			udTime = 0;
			udMove = false;
		}
		if(lrTime > 200){
			lrTime = 0;
			lrMove = false;
		}
		//camera up and down
		if(i.isKeyDown(Input.KEY_S) ){
			if(!udMove){
				if(offY<c.map[0].length){
					offY+=1;
					udMove = true;
					//c.allDirty = true;
				}
			}
		}
		if(i.isKeyDown(Input.KEY_W)){
			if(!udMove){
				if(offY>0){
					udMove = true;
					offY-=1;
					//c.allDirty = true;
				}
			}
		}
		//camera right and left
		if(i.isKeyDown(Input.KEY_D)){
			if(!lrMove){
				if(offX<c.map.length)
				{
					lrMove = true;
					offX+=1;
					//c.allDirty = true;
				}
			}
		}
		if(i.isKeyDown(Input.KEY_A)){
			if(!lrMove){
				if(offX>0)
				{
					offX-=1;
					lrMove = true;
					//c.allDirty = true;
				}

			}
		}
		if(i.isKeyDown(Input.KEY_1)){
			selected = TType.RES;
		}
		if(i.isKeyDown(Input.KEY_2)){
			selected = TType.COMM;
		}
		if(i.isKeyDown(Input.KEY_3)){
			selected = TType.IND;
		}
		if(i.isKeyDown(Input.KEY_R)){
			selected = TType.ROAD;
		}


	}

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new Game());

		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(60);
		app.start();
	}

	@Override
	public void mouseWheelMoved(int change) {
		//System.out.println(change);

		if(change >0){
			zoom +=.05;
		}else if(change < 0){
			//System.out.println(zoom);
			if(!(zoom<0.15)){
				zoom -=.05;
			}
		}

	}

	@Override
	public void mousePressed(int button, int x, int y){
		if(button == 0){
			GameTile t = getTile(x,y);
			if(t!=null)
				t.type = selected;
		}
	}

	private GameTile getTile(int x, int y){
		//System.out.println(x/(zoom*100)+" "+y/(zoom*100));
		int newX, newY;
		newX = (int) (x/(zoom*100)+offX);
		newY = (int) (y/(zoom*100)+offY);
		//System.out.println(newX+" "+newY);
		if(newX>c.map[0].length||newY>c.map.length)return null;
		return c.map[newX][newY];
	}

}

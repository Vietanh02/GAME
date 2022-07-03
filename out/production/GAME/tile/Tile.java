package tile;

import java.awt.image.BufferedImage;

public class Tile {
	public BufferedImage image[];
	public Tile(){
		image = new BufferedImage[4];
	}
	public boolean collision = false;
}

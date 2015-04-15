
public class ImageCoord {
	private  int width = 352; 
	private  int height = 288; 
	public Coord[][] rgb = new Coord[height][width];
	public ImageCoord() {
		for(int row=0;row<height;row++){
			for(int col =0;col<width;col++){
				Coord tmpCoord = new Coord();
				rgb[row][col] = tmpCoord;
			}
		}
	}
	
}

class Coord {
	public int int_rgb[] = new int[3];
	public void Coord() {
		int_rgb[0] = 0;
		int_rgb[1] = 0;
		int_rgb[2] = 0;
		
	}	

}

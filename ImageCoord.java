
public class ImageCoord {
	private  int width = 352; 
	private  int height = 288; 
	public int[][] rgb = new int[height][width];
	public ImageCoord() {
		for(int row=0;row<height;row++){
			for(int col =0;col<width;col++){
				rgb[row][col] = 0;
			}
		}
	}
	
}

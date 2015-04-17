import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;

public class MotionIndexer {
	
    int height = 288;
    int width = 352;
    int numFrames;
    BufferedImage[] buffImage;
	
	public MotionIndexer(int numFrames,BufferedImage[] buffImage,String MotionIndexPath) {
		this.numFrames = numFrames;
		this.buffImage = buffImage;
		
		
		String fileType = getParentPath(MotionIndexPath);
		
		
		try{
			FileWriter fw = new FileWriter(fileType+".motionindex",true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			ArrayList<Macroblock> macroBlocks = new ArrayList<Macroblock>();
			bw.write("" + getcurrentName(MotionIndexPath) + " ");
				
			
			
			for (int frameidx = 0;frameidx<buffImage.length;frameidx++){
				
				BufferedImage img = buffImage[frameidx];
				
				for (int y = 16; y < height; y+=16) {
					for (int x = 16; x < width; x+=16) {
						Macroblock mb = new Macroblock(img, x, y);
						macroBlocks.add(mb);
					}
				}
				
			}
				
				
			
			int mbOffset = ((width/16)-1) * ((height/16)-1);
	    	double maxDiff = 0;
	    	double diffSum = 0;
	    	double maxAverage = 0;
			
	    	
	    	System.out.println("num macroBlocks: " + macroBlocks.size());
	    	
	    	for (int i = 0; i < macroBlocks.size(); i++) {
	    		if (mbOffset+i < macroBlocks.size()-1) {
	    			double compare = macroBlocks.get(i).compareTo(macroBlocks.get(mbOffset+i));
	    			//System.out.println(compare);
	    			diffSum += compare;
	    			
	    			if (compare > maxDiff) {
	    				maxDiff = compare;
	    			}
	    		}
	    		if (i % mbOffset == 0) {
	    			if (diffSum/mbOffset > maxAverage)
	    				maxAverage = diffSum/mbOffset;
	    			//System.out.println((int)(diffSum/mbOffset/62));
	    			bw.write(""+ (int)((diffSum / mbOffset)/64) + " ");
	    			diffSum = 0;
	    		}
	    	}
	    	System.out.println("maxAverage: " + maxAverage);
	    	//System.out.println("maxDiff: " + maxDiff);
	    	bw.write("\n");
	    	System.out.println("Finished " + getcurrentName(MotionIndexPath));
	    	maxAverage = 0;
	    	bw.close();
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getParentPath(String pathName) {
	    String name = pathName;
	    int lastIndexOf = name.lastIndexOf("\\");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(0,lastIndexOf);
	}
	private String getcurrentName(String pathName) {
	    String name = pathName;
	    int lastIndexOf = name.lastIndexOf("\\");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf+1);
	}
}
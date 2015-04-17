import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;


public class ColorIndexer {


    int height = 288;
    int width = 352;
    int numFrames;

	
	public ColorIndexer(int numFrames,BufferedImage[] buffImage,String colorIndexPath){
		this.numFrames = numFrames;
		
		
		try{
			
			String fileType = getParentPath(colorIndexPath);
			
			FileWriter fw = new FileWriter(fileType+".colorindex",true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			//File file = new File(filenames[j]);
			String[] colorindex = new String[numFrames];
			
			bw.write("" + getcurrentName(colorIndexPath) + " ");
		    /*InputStream is = new FileInputStream(file);
		    long len = file.length();
		    byte[] bytes = new byte[(int)len];
		    
		    int offset = 0;
	        int numRead = 0;
	        
	        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
		    */
		   // int ind;
	    	// maxInd = height * width;
	    	
			
	    	for(int i = 0; i < numFrames; i++){
	    		//ind = maxInd * i * 3;
	    		
	    		BufferedImage img = buffImage[i];

			    HashMap<String, Integer> histogram = new HashMap<String, Integer>();
				for(int y = 0; y < height; y++){
			
					for(int x = 0; x < width; x++){
						
						int rgb = img.getRGB(x, y);
						
						
						/*
						int r = (int) bytes[ind];
						int g = (int) bytes[ind+height*width];
						int b = (int) bytes[ind+height*width*2]; 
						*/
						
						int r = ColorSpaceConverter.getRedFromRGB(rgb); 
				        int g = ColorSpaceConverter.getGreenFromRGB(rgb); 
				        int b = ColorSpaceConverter.getBlueFromRGB(rgb); 
				        

						if (r < 0){
							r+=256;
						}
						if (g < 0){
							g+=256;
						}
						
						if (b < 0){
							b+=256;
						}
						
				
						
					
						MyHSV hsv = new MyHSV(r, g, b);
						if (histogram.get(hsv.toString()) == null){
							histogram.put(hsv.toString(), 0);
						}else{
							//add value to existed key
							histogram.put(hsv.toString(), histogram.get(hsv.toString()) + 1);
						}
						
						
						//ind++;
					}
				}
				
				
				
				String maxHSV = "";
				for (String s : histogram.keySet()	){
					if (maxHSV.equals("")){
						maxHSV = s;
					}else{
						if (histogram.get(s) > histogram.get(maxHSV)){
							maxHSV = s;
						}
					}
				}
				colorindex[i] = maxHSV;
				//System.out.println("colorindex[" + i + "] = " + colorindex[i]);
				bw.write("" + maxHSV + " ");
				//ind = (ind * 3) / (i + 1);
	    	}
	    	//[HHSV] H>>[0,16] value S>>[0,8] v>>[0,8]
	    	bw.write("\n");
	    	
	    	System.out.println("Finished " + getcurrentName(colorIndexPath) + "colorIdx");	
				
	    	
			
			
			bw.close();
		}catch(Exception e){
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
	

	
	public class MyHSV{
		int h, s, v;
		
		/*
		 * H is normalized to 16 levels:  0-15. -1 = undefined
		 * S is normalized to 4 levels: 0-3
		 * V is normalized to 4 levels: 0-3
		 */
		
		
		public MyHSV(int ri, int gi, int bi){
			//normalize rgb to [0,1]
			float r = (float) (ri / 256.0);
			float g = (float) (gi / 256.0);
			float b = (float) (bi / 256.0);
			float  min, max, c;
			float vtemp, stemp, htemp;
			max = Math.max(r, Math.max(g, b));
			min = Math.min(r, Math.min(g, b));
			c = max - min;
			vtemp = max;
			
			if (max != 0){
				stemp = (int) Math.round((float) c / (float) max);
			}else{
				stemp = 0;
				htemp = -1;
			}
			
			if( r == max ){
				htemp = ( g - b ) / c;
			}else if( g == max ){
				htemp = 2 + ( b - r ) / c;
			}else{
				htemp = 4 + ( r - g ) / c;
			}
			htemp = htemp *  60;
			if( htemp < 0 )
				htemp += 360;
			htemp = htemp / (float)360.0;
			
			//System.out.println("htemp: " + htemp);
			
			/*
			 * H is quantized to 16 levels:  0-15. -1 = undefined
			 * S is quantized to 4 levels: 0-3
			 * V is quantized to 4 levels: 0-3
			 */
			
			if (htemp != -1){
				h = (int) Math.round(htemp * 16);
			}
			s = (int) Math.round(stemp * 8);
			v = (int) Math.round(vtemp * 8);

		}
		
		public String toString(){
			if (h < 10){
				return "0" + h + "" + s + "" + v;
			}
			return "" + h + "" + s + "" + v;
		}
	}
	
	
}
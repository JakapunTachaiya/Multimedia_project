import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.MalformedURLException;

import javax.media.MediaLocator;
import javax.swing.*;

import java.util.Arrays;
import java.util.Vector;

public class MainProject {

	private static int width = 352; 
	private static int height = 288; 
	
	
	
	
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		String fileName = args[0];
		
		
		
		String[] nameOfVdo = new String[]{"flowers","interview","movie","musicvideo","sports","starcraft","traffic"}; 
		ImageProcessing[] imagePObj = new ImageProcessing [nameOfVdo.length];
		MotionIndexer[] motionObj = new MotionIndexer[nameOfVdo.length];
		AudioIndexer[] audioObj = new AudioIndexer[nameOfVdo.length];
		ColorIndexer[] colorobj = new ColorIndexer[nameOfVdo.length];
		
		for(int i = 0;i<nameOfVdo.length;i++){
			imagePObj[i] = new ImageProcessing(nameOfVdo[i]);
			//motionObj[i] = new MotionIndexer(imagePObj[i].getNumberOfFrame(),imagePObj[i].getBufferedImage(),nameOfVdo[i]);
			//audioObj[i] = new AudioIndexer(imagePObj[i].getNumberOfFrame(),nameOfVdo[i]);
			colorobj[i] = new ColorIndexer(imagePObj[i].getNumberOfFrame(),imagePObj[i].getBufferedImage(),nameOfVdo[i]);
			
			//BufferViewer BufferViewerObj = new BufferViewer(imagePObj[i].getBufferedImage());
			//BufferViewerObj.playBufferViewer();
		}
		
		
	

	}

}

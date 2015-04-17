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
		
		
		File[] dbfiles = new File("dataBase").listFiles();
		File[] qfiles = new File("query").listFiles();
		
		String[] dbpath = new String[dbfiles.length];
		String[] qpath = new String[qfiles.length];
		
		for (int i = 0;i<dbfiles.length;i++) {
	
		    	dbpath[i] = dbfiles[i].getPath();
		    	System.out.println(dbpath[i]);
		    
		}
		
		for (int i = 0;i<qpath.length;i++) {
		  
		    	qpath[i] = qfiles[i].getPath();
		    	System.out.println(qpath[i]);
		    
		}
		
		
		
		//dataBase
		
		ImageProcessing[] imagePObj = new ImageProcessing [dbpath.length];
		MotionIndexer[] motionObj = new MotionIndexer[dbpath.length];
		AudioIndexer[] audioObj = new AudioIndexer[dbpath.length];
		ColorIndexer[] colorobj = new ColorIndexer[dbpath.length];
		
		for(int i = 0;i<dbpath.length;i++){
			imagePObj[i] = new ImageProcessing(dbpath[i]);
			motionObj[i] = new MotionIndexer(imagePObj[i].getNumberOfFrame(),imagePObj[i].getBufferedImage(),dbpath[i]);
			audioObj[i] = new AudioIndexer(imagePObj[i].getNumberOfFrame(),dbpath[i]);
			colorobj[i] = new ColorIndexer(imagePObj[i].getNumberOfFrame(),imagePObj[i].getBufferedImage(),dbpath[i]);
			
			//BufferViewer BufferViewerObj = new BufferViewer(imagePObj[i].getBufferedImage());
			//BufferViewerObj.playBufferViewer();
		}
		
		
		//query
		
		ImageProcessing[] queryimagePObj = new ImageProcessing [qpath.length];
		MotionIndexer[] querymotionObj = new MotionIndexer[qpath.length];
		AudioIndexer[] queryaudioObj = new AudioIndexer[qpath.length];
		ColorIndexer[] querycolorobj = new ColorIndexer[qpath.length];
		
		for(int i = 0;i<qpath.length;i++){
			queryimagePObj[i] = new ImageProcessing(qpath[i]);
			querymotionObj[i] = new MotionIndexer(queryimagePObj[i].getNumberOfFrame(),queryimagePObj[i].getBufferedImage(),qpath[i]);
			queryaudioObj[i] = new AudioIndexer(queryimagePObj[i].getNumberOfFrame(),qpath[i]);
			querycolorobj[i] = new ColorIndexer(queryimagePObj[i].getNumberOfFrame(),queryimagePObj[i].getBufferedImage(),qpath[i]);
		
			//BufferViewer BufferViewerObj = new BufferViewer(queryimagePObj[i].getBufferedImage());
			//BufferViewerObj.playBufferViewer();
		}
		
		
	

	}

}

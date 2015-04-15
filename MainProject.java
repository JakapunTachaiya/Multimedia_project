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
		
		ImageProcessing imagePObj = new ImageProcessing("interview");

	    BufferedImage[] bufferImglist = imagePObj.getBufferedImage();
	
	    BufferViewer BufferViewerObj = new BufferViewer(bufferImglist);
	  
	    BufferViewerObj.playBufferViewer();
	   

	}

}

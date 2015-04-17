import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class BufferViewer extends JFrame {
	
	private BufferedImage[] BufferedImageObj;
	
	/*
	private Timer animationTimer;	
	private int animationDelay = 33;// 33 millisecond delay
	*/
	
	public BufferViewer(BufferedImage[] bufferImglist) {
		// TODO Auto-generated constructor stub
		BufferedImageObj = bufferImglist;
	}


	public void playBufferViewer(){
		  	
			JPanel  panel = new JPanel ();
		    panel.add (new JLabel (new ImageIcon (BufferedImageObj[0])));
		    
		    
		    JFrame frame = new JFrame("Display images");
		    frame.getContentPane().add (panel);
		    frame.pack();
		    frame.setVisible(true);
		    
		    for(int i = 0; i < BufferedImageObj.length; i++){

		    panel.removeAll();
		    panel.add (new JLabel (new ImageIcon (BufferedImageObj[i])));
	
		    panel.repaint();
		    frame.setVisible(true);
		            try {
		                Thread.sleep(33);
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		    }
		    
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

		
	}


	/*
	public void startAnimation(){
	    if ( animationTimer == null ) {
	         currentImage = 0;  
	         animationTimer = new Timer( animationDelay, this );
	         animationTimer.start();
	      }
	      else  // continue from last image displayed
	         if ( ! animationTimer.isRunning() )
	            animationTimer.restart();
	   }

	public void stopAnimation()
	   {
	      animationTimer.stop();
	   }
	*/
	
}

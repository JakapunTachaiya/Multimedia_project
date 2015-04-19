import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class IndexDescriptor {
	ArrayList<int[]> motionDBidx,audioDBidx;
	ArrayList<int[]> motionQidx,audioQidx;
	ArrayList<String[]> colorQidx,colorDBidx;
	ArrayList<double[]> motionDescript,colorDescript,audioDescript;
	ArrayList<String> dbName,qName;
	
	int maxMotionValue,maxAudioValue;

	public IndexDescriptor() {
		maxMotionValue=0;
		maxAudioValue=0;
		
		dbName = new ArrayList<String>();
		qName = new ArrayList<String>();
		motionDBidx = new ArrayList<int[]>();
		colorDBidx = new ArrayList<String[]>();
		audioDBidx = new ArrayList<int[]>();
		motionQidx = new ArrayList<int[]>();
		colorQidx = new ArrayList<String[]>();
		audioQidx = new ArrayList<int[]>();
		motionDescript = new ArrayList<double[]>();
		colorDescript = new ArrayList<double[]>();
		audioDescript = new ArrayList<double[]>();
		
		
		
		
		readAllFile();
	}
	
	
	public void readAllFile(){
		File[] files = new File(".").listFiles();
		
		for(int i =0;i<files.length;i++){
			if(!files[i].isFile()) {continue;}
			
			String fileName = files[i].getName();
			String fileType = getParent(fileName);
			String mediaType = getcurrentType(fileName);
			
			if(fileType.equalsIgnoreCase("dataBase")||fileType.equalsIgnoreCase("query")){
				
				try (BufferedReader br = new BufferedReader(new FileReader(files[i].getPath())))
				{
		 
					String sCurrentLine;
		 
					while ((sCurrentLine = br.readLine()) != null) {
						//System.out.println(sCurrentLine);
						
						initialArray(fileType,mediaType,sCurrentLine);
						
					}
		 
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	
	
	private void initialArray(String fileType, String mediaType,String sCurrentLine) {
		String[] str= sCurrentLine.split(" ");
		
		int[] tmpValue = new int[str.length-1];
		String[] tmpColVal = new String[str.length-1];
		
		for(int i=0;i<tmpValue.length;i++){
			
			if(mediaType.equalsIgnoreCase("colorindex")){
				tmpColVal[i] = str[i+1];
				
			}
			
			if(mediaType.equalsIgnoreCase("audioindex")){
				tmpValue[i] = Integer.parseInt(str[i+1]);
				if(maxAudioValue<tmpValue[i]) maxAudioValue=tmpValue[i];
				
			}
			else if(mediaType.equalsIgnoreCase("motionindex")){
				tmpValue[i] = Integer.parseInt(str[i+1]);
				if(maxMotionValue<tmpValue[i]) maxMotionValue=tmpValue[i];
				
			}
			
		}
		
		if(fileType.equalsIgnoreCase("dataBase")){
			dbName.add(str[0]);
			if(mediaType.equalsIgnoreCase("audioindex")) audioDBidx.add(tmpValue);
			if(mediaType.equalsIgnoreCase("colorindex")) colorDBidx.add(tmpColVal);
			if(mediaType.equalsIgnoreCase("motionindex")) motionDBidx.add(tmpValue);
		}
		else if(fileType.equalsIgnoreCase("query")){
			qName.add(str[0]);
			if(mediaType.equalsIgnoreCase("audioindex")) audioQidx.add(tmpValue);
			if(mediaType.equalsIgnoreCase("colorindex")) colorQidx.add(tmpColVal);
			if(mediaType.equalsIgnoreCase("motionindex")) motionQidx.add(tmpValue);
		}
		
		
	}
	
	public DescriptorInfo getDescriptorInfo(String source, String query) {
		// TODO Auto-generated method stub
		double[] motion = getDiffMotion(source,query);
		double[] color  = getDiffColor(source, query);
		double[] audio = getDiffAudio(source, query);
		int motionPercent = getPercent(motion);
		int colorPercent = getPercent(color);
		int audioPercent = getPercent(audio);
		int avgPercent = (int) ((motionPercent+colorPercent+audioPercent)/3.0);
		DescriptorInfo newDescriptor = new DescriptorInfo(source, query, motion, audio, color, motionPercent, audioPercent, colorPercent, avgPercent);
		return newDescriptor;
	}
	

	
	private int getPercent(double[] array){
		int percent=0;
		double sum=0;
		
		for(int i = 0;i<array.length;i++){
			sum+= array[i];
		}
			
		percent = (int) ((sum/150.0)*100);
		return percent;
	}
	
	
	
	
	public double[] getDiffMotion(String source,String query){
		int srcIdx =dbName.indexOf(source);
		int qIdx = qName.indexOf(query);
		
		int srcSize = motionDBidx.get(srcIdx).length;
		int qSize = motionQidx.get(qIdx).length;
		
		int[] srcAry = motionDBidx.get(srcIdx);
		int[] qAry = motionQidx.get(qIdx);
		
		int[] diffSumWindow = new int[srcSize-qSize];
		
		for(int i = 0;i<diffSumWindow.length;i++){
			
			diffSumWindow[i] = getdiffSumAtIndex(i,srcAry,qAry);
			
		}
		int minIndex = getMinIndex(diffSumWindow);
		
		int[] diffArray = getdiffArrayAtIndex(minIndex,srcAry,qAry);
		double[] diffMotion = new double[srcSize];
		
		for(int i=0;i<diffMotion.length;i++){diffMotion[i] = 0;}
		
		for(int i=0;i<diffArray.length;i++){
			int idx=minIndex+i;
			int tmpValue = diffArray[i];
			diffMotion[idx] = (maxMotionValue-tmpValue)/(double)maxMotionValue;
		}
	
		return diffMotion;	
	}
	
	public double[] getDiffAudio(String source,String query){
		
		int srcIdx =dbName.indexOf(source);
		int qIdx = qName.indexOf(query);
		
		int srcSize = audioDBidx.get(srcIdx).length;
		int qSize = audioQidx.get(qIdx).length;
		
		int[] srcAry = audioDBidx.get(srcIdx);
		int[] qAry = audioQidx.get(qIdx);
		
		int[] diffSumWindow = new int[srcSize-qSize];
		
		for(int i = 0;i<diffSumWindow.length;i++){
			
			diffSumWindow[i] = getdiffSumAtIndex(i,srcAry,qAry);
			
		}
		int minIndex = getMinIndex(diffSumWindow);
		
		int[] diffArray = getdiffArrayAtIndex(minIndex,srcAry,qAry);
		double[] diffAudio = new double[srcSize];
		
		for(int i=0;i<diffAudio.length;i++){diffAudio[i] = 0;}
		
		for(int i=0;i<diffArray.length;i++){
			int idx=minIndex+i;
			int tmpValue = diffArray[i];
			diffAudio[idx] = (maxAudioValue-tmpValue)/(double)maxAudioValue;
		}
	
		return diffAudio;	
	}
	public double[] getDiffColor(String source,String query){
		int srcIdx =dbName.indexOf(source);
		int qIdx = qName.indexOf(query);
		
		int srcSize = colorDBidx.get(srcIdx).length;
		int qSize = colorQidx.get(qIdx).length;
		
		String[] srcAry = colorDBidx.get(srcIdx);
		String[] qAry = colorQidx.get(qIdx);
		
		int[] diffSumWindow = new int[srcSize-qSize];
		
		for(int i = 0;i<diffSumWindow.length;i++){
			
			diffSumWindow[i] = getdiffSumColorAtIndex(i,srcAry,qAry);
			
		}
		int minIndex = getMinIndex(diffSumWindow);
		
		int[] diffArray = getdiffColorArrayAtIndex(minIndex,srcAry,qAry);
		double[] diffAudio = new double[srcSize];
		
		for(int i=0;i<diffAudio.length;i++){diffAudio[i] = 0;}
		
		for(int i=0;i<diffArray.length;i++){
			int idx=minIndex+i;
			int tmpValue = diffArray[i];
			diffAudio[idx] = (48-tmpValue)/48.0;
		}
	
		return diffAudio;	
		
		
	}
	
	

	public int getHsvDifference(String hsv1, String hsv2){
		
		int h1 = Integer.parseInt(hsv1.substring(0, 2));
		int s1 = Integer.parseInt(hsv1.substring(2, 3));
		int v1 = Integer.parseInt(hsv1.substring(3));
		int h2 = Integer.parseInt(hsv2.substring(0, 2));
		int s2 = Integer.parseInt(hsv2.substring(2, 3));
		int v2 = Integer.parseInt(hsv2.substring(3));
		
		int h = Math.abs(h1 - h2);
		if (h > 8){
			int diff = h - 8;
			h = h - (2 * diff);
		}
		
		int s = Math.abs(s1 - s2);
		int v = Math.abs(v1 - v2);
		
		return (h * 2) + s + v;
	}
	
	
	private int getdiffSumColorAtIndex(int i, String[] srcAry, String[] qAry) {
		// TODO Auto-generated method stub
		
		int diffSum = 0;
		
		for (int j =0;j<qAry.length;j++){
			int tmpdif = getHsvDifference(srcAry[j+i],qAry[j]);
			diffSum+= Math.abs(tmpdif);
		}
		
		return diffSum;
	}
	private int[] getdiffColorArrayAtIndex(int i, String[] srcAry, String[] qAry) {
		// TODO Auto-generated method stub
		
		int[] diff = new int[qAry.length];
		
		for (int j =0;j<qAry.length;j++){
			int tmpdif = getHsvDifference(srcAry[j+i],qAry[j]);
			
			diff[j]= Math.abs(tmpdif);
		}
		
		return diff;
	}
	
	private int getdiffSumAtIndex(int i, int[] srcAry, int[] qAry) {
		// TODO Auto-generated method stub
		
		int diffSum = 0;
		
		for (int j =0;j<qAry.length;j++){
			int tmpdif = srcAry[j+i]-qAry[j];
			diffSum+= Math.abs(tmpdif);
		}
		
		return diffSum;
	}

	private int[] getdiffArrayAtIndex(int i, int[] srcAry, int[] qAry) {
		// TODO Auto-generated method stub
		
		int[] diff = new int[qAry.length];
		
		for (int j =0;j<qAry.length;j++){
			int tmpdif = srcAry[j+i]-qAry[j];
			
			diff[j]= Math.abs(tmpdif);
		}
		
		return diff;
	}


	
	/*
	public void testArry(){
		System.out.println("TEST>>>>>>>>>>>>>>>>>>>>>>>");
		for(int i =0;i<motionDBidx.size();i++){
			for(int j=0;j<motionDBidx.get(i).length;j++){
				System.out.print(motionDBidx.get(i)[j]+" ");
			}
			System.out.println();
		}
		for(int i =0;i<motionDBidx.size();i++){
			for(int j=0;j<motionDBidx.get(i).length;j++){
				System.out.print(colorDBidx.get(i)[j]+" ");
			}
			System.out.println();
		}
		for(int i =0;i<motionDBidx.size();i++){
			for(int j=0;j<motionDBidx.get(i).length;j++){
				System.out.print(audioDBidx.get(i)[j]+" ");
			}
			System.out.println();
		}
		
		for(int i =0;i<audioQidx.size();i++){
			for(int j=0;j<audioQidx.get(i).length;j++){
				System.out.print(audioQidx.get(i)[j]+" ");
			}
			System.out.println();
		}

	}
	*/
	

	public int getMinValue(int[] numbers){  
	    int minValue = numbers[0];  
	    for(int i=1;i<numbers.length;i++){  
	        if(numbers[i] < minValue){  
	            minValue = numbers[i];  
	        }  
	    }  
	    return minValue;  
	}  

	    
	public int getMinIndex(int[] numbers){  
	    int minValue = numbers[0];
	    int minIndex = 0;
	    for(int i=1;i<numbers.length;i++){  
	        if(numbers[i] < minValue){  
	            minValue = numbers[i];
	            minIndex = i;
	        }  
	    }  
	    return minIndex;  
	}  


	private String getParent(String pathName) {
	    String name = pathName;
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(0,lastIndexOf);
	}
	private String getcurrentType(String pathName) {
	    String name = pathName;
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf+1);
	}





	

}

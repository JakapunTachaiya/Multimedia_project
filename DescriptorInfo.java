	public class DescriptorInfo{
		String dbName;
		String qName;
		double[] motionVal; //[0-1]
		double[] audioVal;//[0-1]
		double[] ColorVal;//[0-1]
		int motionPercent;
		int audioPercent;
		int colorPercent;
		int avgPercent;
		public DescriptorInfo(String dbName, String qName, double[] motionVal,
				double[] audioVal, double[] colorVal, int motionPercent,
				int audioPercent, int colorPercent, int avgPercent) {
			super();
			this.dbName = dbName;
			this.qName = qName;
			this.motionVal = motionVal;
			this.audioVal = audioVal;
			ColorVal = colorVal;
			this.motionPercent = motionPercent;
			this.audioPercent = audioPercent;
			this.colorPercent = colorPercent;
			this.avgPercent = avgPercent;
		}

		
		
	}
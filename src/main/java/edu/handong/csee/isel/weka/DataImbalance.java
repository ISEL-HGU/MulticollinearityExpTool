package edu.handong.csee.isel.weka;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.supervised.instance.SpreadSubsample;

public class DataImbalance {
	public Instances spreadSubsampling(Instances trainData) throws Exception {
		// training data undersampling
		final SpreadSubsample spreadsubsample = new SpreadSubsample();
		spreadsubsample.setInputFormat(trainData);
		spreadsubsample.setDistributionSpread(1.0);
		trainData = Filter.useFilter(trainData, spreadsubsample);
		return trainData;
	}
	
	public Instances smote(Instances trainData) throws Exception {
		// smote 
		final SMOTE smote = new SMOTE();
		smote.setInputFormat(trainData);
		smote.setNearestNeighbors(1);
		trainData = Filter.useFilter(trainData, smote);
		return trainData;
	}
}

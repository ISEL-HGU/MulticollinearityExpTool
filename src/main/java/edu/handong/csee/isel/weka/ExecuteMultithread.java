package edu.handong.csee.isel.weka;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.PrincipalComponents;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.J48;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;

import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.attributeSelection.AttributeSelection;

import weka.filters.unsupervised.attribute.Remove;
import weka.filters.supervised.instance.SpreadSubsample;
import weka.filters.supervised.instance.SMOTE;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.FileWriter;
import java.util.Arrays;

import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ExecuteMultithread{
	static String sourcePath;
	static String dataUnbalancingMode;
	static String type;
	static String csvPath;
	static String mlModel;
	static String tuningFlag;
	static String tuningMode;
	static String multisearchEvaluationName;
	String poolSize;
	String iter;
	String fold;

	public ExecuteMultithread(String sourcePath, String dataUnbalancingMode, String type, String csvPath, String mlModel, String iter, String fold, String poolSize, String tuningFlag, String tuningMode, String multisearchEvaluationName) {
		this.sourcePath = sourcePath;
		this.dataUnbalancingMode = dataUnbalancingMode;
		this.type = type;
		this.csvPath = csvPath;
		this.mlModel = mlModel;
		this.iter = iter;
		this.fold = fold;
		this.poolSize = poolSize;
		this.tuningFlag = tuningFlag;
		this.tuningMode	= tuningMode;
		this.multisearchEvaluationName = multisearchEvaluationName;
	}
	
	public void run() {
		try {
    			ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(poolSize));
	    		
			String path;
			// if sourcePath contain ".arff", delete extensions
			System.out.println("sourcePath is : " + sourcePath); //
			System.out.println("mlModel is : " + mlModel); //
			int index = sourcePath.lastIndexOf(".");
	        if (index != -1) {
	        		sourcePath = sourcePath.substring(0, index);
	        		System.out.println("file name is : " + sourcePath);
	        }
	        for(int i = 0; i < Integer.parseInt(iter); i++){
				ArrayList<String> filePathList = new ArrayList<String>(); 
				for(int n = 0; n < Integer.parseInt(fold); n++){
					path = sourcePath + "_" + i + "_" + n + ".arff";
					filePathList.add(path);							
				}
				if(type.equals("1") || type.equals("4")) { // unsupervised (1 is original, pac, and vif. 4 is vc and rr)
					for(int idx = 0; idx < Integer.parseInt(fold); idx++) {
			    		    Runnable CV = new CrossValidation(idx, filePathList, sourcePath, dataUnbalancingMode, type, csvPath, mlModel, tuningFlag, tuningMode, multisearchEvaluationName);
					    executor.execute(CV);
					}
				}
				else if(type.equals("2") || type.equals("3")) { // supervised (2 is CFS, 3 is WFS)
					for(int idx = 0; idx < Integer.parseInt(fold); idx++) {
						Runnable CVFS = new CrossValidationFS(idx, filePathList, sourcePath, dataUnbalancingMode, type, csvPath, mlModel, tuningFlag, tuningMode, multisearchEvaluationName);
		    		    		executor.execute(CVFS);
					}
				}
				else {
		    			System.exit(-1);
	    			}
	
			}
	        executor.shutdown();
	    		while (!executor.isTerminated()) {
	    		}	    		
	    		System.out.println("Finished all threads");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
package edu.handong.csee.isel.weka;

import weka.attributeSelection.WrapperSubsetEval;
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
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.meta.GridSearch;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.attributeSelection.AttributeSelection;

import weka.filters.unsupervised.attribute.Remove;
import weka.filters.supervised.instance.SpreadSubsample;
import weka.filters.supervised.instance.SMOTE;

import java.util.Random;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import weka.core.converters.CSVSaver;

public class CrossValidationFS implements Runnable {
	int idx;
	ArrayList<String> filePathList;
	String sourcePath;
	String dataUnbalancingMode;
	String type;
	String csvPath;
	String mlModel;

	Evaluation eval_case = null;
	Instances trainData = null;
	String testPath = null;
	String approach_name;

	String tuningFlag;
	String tuningMode;
	
	public static final int EVAL_DEFAULT = 1;
	public static final int EVAL_ACCURACY = 2;
	public static final int EVAL_RMSE = 3;
	public static final int EVAL_MAE = 4;
	public static final int EVAL_FMEASURE = 5;
	public static final int EVAL_AUC = 6;
	public static final int EVAL_AUPRC = 7;

	public static final Tag[] TAGS_EVALUATION = {
			new Tag(EVAL_DEFAULT, "Default: accuracy (discrete class); RMSE (numeric class)"),
			new Tag(EVAL_ACCURACY, "Accuracy (discrete class only)"),
			new Tag(EVAL_RMSE, "RMSE (of the class probabilities for discrete class)"),
			new Tag(EVAL_MAE, "MAE (of the class probabilities for discrete class)"),
			new Tag(EVAL_FMEASURE, "F-measure (discrete class only)"),
			new Tag(EVAL_AUC, "AUC (area under the ROC curve - discrete class only)"),
			new Tag(EVAL_AUPRC, "AUPRC (area under the precision-recall curve - discrete class only)") };

	public CrossValidationFS(int idx, ArrayList<String> filePathList, String sourcePath, String dataUnbalancingMode,
			String type, String csvPath, String mlModel, String tuningFlag, String tuningMode) {
		this.idx = idx;
		this.filePathList = filePathList;
		this.sourcePath = sourcePath;
		this.dataUnbalancingMode = dataUnbalancingMode;
		this.type = type;
		this.csvPath = csvPath;
		this.mlModel = mlModel;
		this.tuningFlag = tuningFlag;
		this.tuningMode	= tuningMode;
	}


	@Override
	public void run() {
		String isMulticollinearity = "";
		String multicollinearity_vif_10 = "thres: 10/ not issue";
		String multicollinearity_vif_5 = "thres: 5/ not issue";
		String multicollinearity_vif_4 = "thres: 4/ not issue";
		String multicollinearity_vif_2_5 = "thres: 2.5/ not issue";

		try {
			Instances testData = null, temp = null;
			for (int i = 0; i < filePathList.size(); i++) {
				if (i == idx) {
					BufferedReader reader = new BufferedReader(new FileReader(filePathList.get(i)));
					testData = new Instances(reader);
					reader.close();
					testPath = filePathList.get(i);
					continue;
				}
				BufferedReader reader = new BufferedReader(new FileReader(filePathList.get(i)));
				temp = new Instances(reader);
				reader.close();
				if (trainData == null) {
					trainData = temp;
				} else {
					trainData.addAll(temp);
				}

			}

			
			trainData.setClassIndex(trainData.numAttributes()-1);
			testData.setClassIndex(testData.numAttributes()-1);

			DataImbalance DI = new DataImbalance();
			if (dataUnbalancingMode.equals("1")) {
				// no handling unbalancing data problem
			} else if (dataUnbalancingMode.equals("2")) {
				trainData = DI.spreadSubsampling(trainData);
			} else if (dataUnbalancingMode.equals("3")) {
				trainData = DI.smote(trainData);
			} else {
				System.out.println("Check your Data unbalancing mode option!");
				System.exit(-1);
			}

			

//			Classifier myModel = (Classifier) weka.core.Utils.forName(Classifier.class, mlModel, null);
			// feature selection -> using only trainData
			AttributeSelection attsel = new AttributeSelection(); // package weka.attributeSelection!
			BestFirst search = new BestFirst();
			if (type.equals("2")) { // CFS
				CfsSubsetEval eval = new CfsSubsetEval();
				attsel.setEvaluator(eval);
			} 
//			else if (type.equals("3")) { // WFS
//				WrapperSubsetEval wrapperEval = new WrapperSubsetEval();
//				wrapperEval.setClassifier(myModel); // It is the same ML model as the prediction ML model
//				wrapperEval.setEvaluationMeasure(new SelectedTag(EVAL_AUC, TAGS_EVALUATION));
//				attsel.setEvaluator(wrapperEval);
//			}

			attsel.setSearch(search);
			attsel.SelectAttributes(trainData);
			// System.out.println(attsel.toResultsString());

			int[] indices = attsel.selectedAttributes(); // to find feature selected index
			// trainData and testData dimension reduction
			Remove removeFilter = new Remove();
			removeFilter.setInvertSelection(true); // If true, leaves only the index of the array.
			removeFilter.setAttributeIndicesArray(indices);
			removeFilter.setInputFormat(trainData);
			removeFilter.setInputFormat(testData);
			trainData = Filter.useFilter(trainData, removeFilter);
			testData = Filter.useFilter(testData, removeFilter);
			// System.out.println("test " + testData.numAttributes());
			// System.out.println("train " + trainData.numAttributes());
			// System.out.println("--------------------");

			// Save the CFS, WFS features number
			// saveFeaturesNumber(mlModel, csvPath, type,
			// testPath,trainData.numAttributes());

			isMulticollinearity = checkMulticollinearity(trainData, 10.0);
			if (isMulticollinearity.equals("Y"))
				multicollinearity_vif_10 = "10.0";
			isMulticollinearity = checkMulticollinearity(trainData, 5.0);
			if (isMulticollinearity.equals("Y"))
				multicollinearity_vif_5 = "5.0";
			isMulticollinearity = checkMulticollinearity(trainData, 4.0);
			if (isMulticollinearity.equals("Y"))
				multicollinearity_vif_4 = "4.0";
			isMulticollinearity = checkMulticollinearity(trainData, 2.5);
			if (isMulticollinearity.equals("Y"))
				multicollinearity_vif_2_5 = "2.5";

			if (type.equals("2"))
				approach_name = "CFS-BestFirst";
//			else if (type.equals("3"))
//				approach_name = "WFS-BestFirst";

			
			if(!tuningFlag.equals("false")) {
				if(tuningMode.equals("false")) {
					System.out.println("Check your tuning mode option!");
					System.exit(-1);
				}
				else if(tuningMode.equals("1")) {
					// parameter tuning (1. GridSearch)
					GridSearch grid_search = new GridSearch();
					grid_search.setClassifier((Classifier) weka.core.Utils.forName(Classifier.class, mlModel, null));
					grid_search.buildClassifier(trainData);
					eval_case = new Evaluation(trainData);
					eval_case.evaluateModel(grid_search, testData);
				}
				else if(tuningMode.equals("2")) {
					// parameter tuning (2. CVParameterSelection)
					CVParameterSelection cv_ps = new CVParameterSelection();
					cv_ps.setClassifier((Classifier) weka.core.Utils.forName(Classifier.class, mlModel, null));
					cv_ps.buildClassifier(trainData);
					eval_case = new Evaluation(trainData);
					eval_case.evaluateModel(cv_ps, testData);
				}

			}
			else { // tuningFlag.equals("false")
				// no parameter tuning
				final Classifier myModel = (Classifier) weka.core.Utils.forName(Classifier.class, mlModel, null);
				myModel.buildClassifier(trainData);
				eval_case = new Evaluation(trainData);
				eval_case.evaluateModel(myModel, testData);
			}
			
			showSummary(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name, multicollinearity_vif_10,
					multicollinearity_vif_5, multicollinearity_vif_4, multicollinearity_vif_2_5);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void saveFeaturesNumber(String modelName, String csvPath, String type, String srcPath,
			int numAttributes) throws IOException {
		FileWriter writer = new FileWriter(csvPath, true);
		CSVUtils.writeLine(writer, Arrays.asList(modelName, type, srcPath, String.valueOf(numAttributes)));
		writer.flush();
		writer.close();
	}

	public static void showSummary(Evaluation eval, Instances instances, String modelName, String csvPath, String type,
			String srcPath, String approach_name, String multicollinearity_vif_10, String multicollinearity_vif_5,
			String multicollinearity_vif_4, String multicollinearity_vif_2_5) throws Exception {
		FileWriter writer = new FileWriter(csvPath, true);
		if (eval == null)
			System.out.println("showSummary - eval is null");
		else {
			for (int i = 0; i < instances.classAttribute().numValues() - 1; i++) {
 				CSVUtils.writeLine(writer, Arrays.asList(modelName, String.valueOf(eval.precision(i)), String.valueOf(eval.recall(i)), String.valueOf(eval.fMeasure(i)), String.valueOf(eval.areaUnderROC(i)), String.valueOf(eval.matthewsCorrelationCoefficient(i)), String.valueOf(eval.areaUnderPRC(i)), String.valueOf(eval.falseNegativeRate(i)), String.valueOf(eval.falsePositiveRate(i)), String.valueOf(eval.numFalseNegatives(i)), String.valueOf(eval.numFalsePositives(i)), String.valueOf(eval.numTrueNegatives(i)), String.valueOf(eval.numFalsePositives(i)), String.valueOf(eval.numTrueNegatives(i)), String.valueOf(eval.numTruePositives(i)), String.valueOf(eval.trueNegativeRate(i)), String.valueOf(eval.truePositiveRate(i)), type, srcPath, approach_name, multicollinearity_vif_10, multicollinearity_vif_5, multicollinearity_vif_4, multicollinearity_vif_2_5));
 			}
		}
		writer.flush();
		writer.close();
	}
	

	public String checkMulticollinearity(Instances instances, double VIFThresholdValue) throws Exception {
		String isMulticollinearity = "";
		Instances forVIFData = null;
		Remove rm = new Remove();
		rm.setAttributeIndices("last");
		rm.setInputFormat(instances);
		forVIFData = Filter.useFilter(instances, rm);
		int n = forVIFData.numAttributes();
		double[] vifs = new double[n];
		for (int i = 0; i < vifs.length; i++) {
			forVIFData.setClassIndex(i);
			// Using Weka Linear Regression
			AccessibleLinearRegression regressor = new AccessibleLinearRegression();
			regressor.setAttributeSelectionMethod(new SelectedTag(1, LinearRegression.TAGS_SELECTION));
			regressor.setEliminateColinearAttributes(false);
			regressor.buildClassifier(forVIFData);
			double r2 = regressor.getRSquared(forVIFData);
			vifs[i] = 1d / (1d - r2);
			if (vifs[i] >= VIFThresholdValue) {
				isMulticollinearity = "Y"; // Occur multicollinearity
				break;
			} else {
				isMulticollinearity = "N";
			}
		}
		return isMulticollinearity;
	}

//	public static Instances spreadSubsampling(Instances trainData) throws Exception {
//		// training data undersampling
//		SpreadSubsample spreadsubsample = new SpreadSubsample();
//		spreadsubsample.setInputFormat(trainData);
//		spreadsubsample.setDistributionSpread(1.0);
//		trainData = Filter.useFilter(trainData, spreadsubsample);
//		return trainData;
//	}
//
//	public static Instances smote(Instances trainData) throws Exception {
//		// smote
//		SMOTE smote = new SMOTE();
//		smote.setInputFormat(trainData);
//		smote.setNearestNeighbors(1);
//		trainData = Filter.useFilter(trainData, smote);
//		return trainData;
//	}

}

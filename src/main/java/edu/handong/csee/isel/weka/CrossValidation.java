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

import weka.classifiers.meta.CVParameterSelection; // parameter tuning
import weka.classifiers.meta.GridSearch; // parameter tuning
import weka.classifiers.meta.MultiSearch; // parameter tuning
import weka.classifiers.meta.multisearch.DefaultEvaluationMetrics;
import weka.classifiers.meta.multisearch.DefaultSearch;
import weka.core.Instances;
import weka.core.SelectedTag;
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

import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CrossValidation implements Runnable{
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
	String multisearchEvaluationName;

	public CrossValidation(final int idx, final ArrayList<String> filePathList, final String sourcePath, final String dataUnbalancingMode,
			final String type, final String csvPath, final String mlModel, String tuningFlag, String tuningMode, String multisearchEvaluationName) {
		this.idx = idx;
		this.filePathList = filePathList;
		this.sourcePath = sourcePath;
		this.dataUnbalancingMode = dataUnbalancingMode;
		this.type = type;
		this.csvPath = csvPath;
		this.mlModel = mlModel;
		this.tuningFlag = tuningFlag;
		this.tuningMode	= tuningMode;
		this.multisearchEvaluationName = multisearchEvaluationName;
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
					final BufferedReader reader = new BufferedReader(new FileReader(filePathList.get(i)));
					testData = new Instances(reader);
					reader.close();
					testPath = filePathList.get(i);
					continue;
				}
				final BufferedReader reader = new BufferedReader(new FileReader(filePathList.get(i)));
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
			
			
			
			if(!tuningFlag.equals("false")) {
				if(tuningMode.equals("false")) {
					System.out.println("Check your tuning mode option!");
					System.exit(-1);
				}
				else if(tuningMode.equals("1")) {
					// parameter tuning (1. GridSearch)
					GridSearch grid_search = new GridSearch();
					grid_search.setClassifier((Classifier) weka.core.Utils.forName(Classifier.class, mlModel, null));
					
					grid_search.setEvaluation(new SelectedTag(GridSearch.EVALUATION_WAUC, GridSearch.TAGS_EVALUATION));
					grid_search.setXProperty("classifier.confidenceFactor");
					grid_search.setXMin(0.05);
					grid_search.setXMax(0.5);
					grid_search.setXStep(0.05);
					grid_search.setXBase(10);
					grid_search.setXExpression("I");

					grid_search.setYProperty("classifier.minNumObj");
					grid_search.setYMin(2);
					grid_search.setYMax(10);
					grid_search.setYStep(1);
					grid_search.setYBase(10);
					grid_search.setYExpression("I");

					grid_search.setDebug(true);
			        
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
				else if(tuningMode.equals("3")) {
					MultiSearch multi_search = new MultiSearch();
					SelectedTag tag = null;
					if(multisearchEvaluationName.equals("AUC")) {
						tag = new SelectedTag(DefaultEvaluationMetrics.EVALUATION_AUC, new DefaultEvaluationMetrics().getTags());
					}
					else if(multisearchEvaluationName.equals("Fmeasure")) {
						tag = new SelectedTag(DefaultEvaluationMetrics.EVALUATION_FMEASURE, new DefaultEvaluationMetrics().getTags());
					}
					else if(multisearchEvaluationName.equals("MCC")) {
						tag = new SelectedTag(DefaultEvaluationMetrics.EVALUATION_MATTHEWS_CC, new DefaultEvaluationMetrics().getTags());
					}
					else if(multisearchEvaluationName.equals("Precision")) {
						tag = new SelectedTag(DefaultEvaluationMetrics.EVALUATION_PRECISION, new DefaultEvaluationMetrics().getTags());
					}
					else if(multisearchEvaluationName.equals("Recall")) {
						tag = new SelectedTag(DefaultEvaluationMetrics.EVALUATION_RECALL, new DefaultEvaluationMetrics().getTags());
					}
					multi_search.setEvaluation(tag);
					multi_search.setAlgorithm(new DefaultSearch());
					multi_search.setClassifier((Classifier) weka.core.Utils.forName(Classifier.class, mlModel, null));
					multi_search.buildClassifier(trainData);
					eval_case = new Evaluation(trainData);
					eval_case.evaluateModel(multi_search, testData);
				}

			}
			else { // tuningFlag.equals("false")
				// no parameter tuning
				final Classifier myModel = (Classifier) weka.core.Utils.forName(Classifier.class, mlModel, null);
				myModel.buildClassifier(trainData);
				eval_case = new Evaluation(trainData);
				eval_case.evaluateModel(myModel, testData);
			}
		



			if (type.equals("4")) {
				approach_name = "VCRR";
				showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
			} else { // type.equals("1")  
				// For checking multicollinearity using VIF with various threshold values when
				// the case is original dataset
				if (sourcePath.contains("_PCA")) {
					approach_name = "Default-PCA";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else if (sourcePath.contains("_NONSTEPWISE_10")) {
					approach_name = "NSVIF10";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else if (sourcePath.contains("_NONSTEPWISE_5")) {
					approach_name = "NSVIF5";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else if (sourcePath.contains("_NONSTEPWISE_4")) {
					approach_name = "NSVIF4";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else if (sourcePath.contains("_NONSTEPWISE_2_5")) {
					approach_name = "NSVIF2.5";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else if (sourcePath.contains("_STEPWISE_10")) {
					approach_name = "SVIF10";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else if (sourcePath.contains("_STEPWISE_5")) {
					approach_name = "SVIF5";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else if (sourcePath.contains("_STEPWISE_4")) {
					approach_name = "SVIF4";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else if (sourcePath.contains("_STEPWISE_2_5")) {
					approach_name = "SVIF2.5";
					showSummaryForPCAVIFVC(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name);
				} else { // original dataset
					approach_name = "None";
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
					showSummaryForOrigin(eval_case, trainData, mlModel, csvPath, type, testPath, approach_name,
							multicollinearity_vif_10, multicollinearity_vif_5, multicollinearity_vif_4, multicollinearity_vif_2_5);
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public String checkMulticollinearity(final Instances instances, final double VIFThresholdValue) throws Exception {
		String isMulticollinearity = "";
		Instances forVIFData = null;
		final Remove rm = new Remove();
		rm.setAttributeIndices("last");
		rm.setInputFormat(instances);
		forVIFData = Filter.useFilter(instances, rm);
		final int n = forVIFData.numAttributes();
		final double[] vifs = new double[n];
		for (int i = 0; i < vifs.length; i++) {
			forVIFData.setClassIndex(i);
			// Using Weka Linear Regression
			final AccessibleLinearRegression regressor = new AccessibleLinearRegression();
			regressor.setAttributeSelectionMethod(new SelectedTag(1, LinearRegression.TAGS_SELECTION));
			regressor.setEliminateColinearAttributes(false);
			regressor.buildClassifier(forVIFData);
			final double r2 = regressor.getRSquared(forVIFData);
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

	
	
	public static void showSummaryForPCAVIFVC(final Evaluation eval,final Instances instances, final String modelName, final String csvPath, final String type, final String srcPath, final String approach_name) throws Exception {
		final FileWriter writer =  new FileWriter(csvPath, true);
		if(eval == null) System.out.println("showSummary - eval is null");
		else
 			for (int i = 0; i < instances.classAttribute().numValues() - 1; i++) {
 				CSVUtils.writeLine(writer, Arrays.asList(modelName, String.valueOf(eval.precision(i)), String.valueOf(eval.recall(i)), String.valueOf(eval.fMeasure(i)), String.valueOf(eval.areaUnderROC(i)), String.valueOf(eval.matthewsCorrelationCoefficient(i)), String.valueOf(eval.areaUnderPRC(i)), String.valueOf(eval.falseNegativeRate(i)), String.valueOf(eval.falsePositiveRate(i)), String.valueOf(eval.numFalseNegatives(i)), String.valueOf(eval.numFalsePositives(i)), String.valueOf(eval.numTrueNegatives(i)), String.valueOf(eval.numFalsePositives(i)), String.valueOf(eval.numTrueNegatives(i)), String.valueOf(eval.numTruePositives(i)), String.valueOf(eval.trueNegativeRate(i)), String.valueOf(eval.truePositiveRate(i)), type, srcPath, approach_name));
 			}

		writer.flush();
		writer.close();
	}
	
	public static void showSummaryForOrigin(final Evaluation eval, final Instances instances, final String modelName, final String csvPath, final String type, final String srcPath, final String approach_name, final String multicollinearity_vif_10, final String multicollinearity_vif_5, final String multicollinearity_vif_4, final String multicollinearity_vif_2_5) throws Exception {
		final FileWriter writer = new FileWriter(csvPath, true);
		if (eval == null)
			System.out.println("showSummary - eval is null");
		else
 			for (int i = 0; i < instances.classAttribute().numValues() - 1; i++) {
 				CSVUtils.writeLine(writer, Arrays.asList(modelName, String.valueOf(eval.precision(i)), String.valueOf(eval.recall(i)), String.valueOf(eval.fMeasure(i)), String.valueOf(eval.areaUnderROC(i)), String.valueOf(eval.matthewsCorrelationCoefficient(i)), String.valueOf(eval.areaUnderPRC(i)), String.valueOf(eval.falseNegativeRate(i)), String.valueOf(eval.falsePositiveRate(i)), String.valueOf(eval.numFalseNegatives(i)), String.valueOf(eval.numFalsePositives(i)), String.valueOf(eval.numTrueNegatives(i)), String.valueOf(eval.numFalsePositives(i)), String.valueOf(eval.numTrueNegatives(i)), String.valueOf(eval.numTruePositives(i)), String.valueOf(eval.trueNegativeRate(i)), String.valueOf(eval.truePositiveRate(i)), type, srcPath, approach_name, multicollinearity_vif_10, multicollinearity_vif_5, multicollinearity_vif_4, multicollinearity_vif_2_5));
 			}
		writer.flush();
		writer.close();
	}

	
//	public static Instances spreadSubsampling(Instances trainData) throws Exception {
//		// training data undersampling
//		final SpreadSubsample spreadsubsample = new SpreadSubsample();
//		spreadsubsample.setInputFormat(trainData);
//		spreadsubsample.setDistributionSpread(1.0);
//		trainData = Filter.useFilter(trainData, spreadsubsample);
//		return trainData;
//	}
//	
//	public static Instances smote(Instances trainData) throws Exception {
//		// smote 
//		final SMOTE smote = new SMOTE();
//		smote.setInputFormat(trainData);
//		smote.setNearestNeighbors(1);
//		trainData = Filter.useFilter(trainData, smote);
//		return trainData;
//	}
	
}

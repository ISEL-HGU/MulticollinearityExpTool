package edu.handong.csee.isel.weka;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
	static String sourcePath;
	static String dataUnbalancingMode;
	static String type;
	static String csvPath;
	static String mlModel;
	static String iter;
	static String fold;
	static String poolSize;
	static String beforeCrossValidationDataPath;
	static String parameterTuningFlag = "false";
	static String parameterTuningMode = "false";
	static String multisearchEvaluationName = "AUC";
	static boolean help = false;
	
    public static void main(String[] args) throws Exception {
    		Options options = createOptions();

		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}

	    		File searchDir = new File(beforeCrossValidationDataPath);
	    		File []fileList = searchDir.listFiles();
	    		String copySourcePath = sourcePath;
	    		for(File tempFile : fileList) {
	    		  if(tempFile.isFile()) {
	    		    String tempFileName = tempFile.getName();
	    		    sourcePath = sourcePath + tempFileName;
	    		    ExecuteMultithread myExecuteMultithread = new ExecuteMultithread(sourcePath, dataUnbalancingMode, type, csvPath, mlModel, iter, fold, poolSize, parameterTuningFlag, parameterTuningMode, multisearchEvaluationName);
	    		    myExecuteMultithread.run();  
	    		    sourcePath = copySourcePath;

	    		  }
	    		}
	    }
	}
	
	static Options createOptions(){
	
		// create Options object
		Options options = new Options();
	
		// add options
		options.addOption(Option.builder("s").longOpt("source")
				.desc("source arff file path to train a prediction model")
				.hasArg()
				.argName("file")
				.required()
				.build());
		
		options.addOption(Option.builder("d").longOpt("dataUnbalancingMode")
				.desc("1 is noHandling data unbalance or 2 is applying spread subsampling or 3 is applying smote")
				.hasArg()
				.argName("data unbalancing mode")
				.required()
				.build());
	
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());
	
		options.addOption(Option.builder("t").longOpt("type")
				.desc("1 is a original dataset or applying PCA or VIF to remove multicollinearity or 2 is applying Correlation-based feature selection or 3 is applying Wrapper-based feature selection or 4 is applying Variable clustering and removing redundant metrics.")
				.hasArg()
				.required()
				.argName("attribute value")
				.build());
	
		options.addOption(Option.builder("c").longOpt("csv")
				.desc("file path of output to output file.")
				.hasArg()
				.required()
				.argName("csv file location")
				.build());
		
		options.addOption(Option.builder("m").longOpt("model")
				.desc("machine learning model")
				.hasArg()
				.required()
				.argName("machine learning model")
				.build());
		
		options.addOption(Option.builder("i").longOpt("iter")
				.desc("number of cross-validation iterations")
				.hasArg()
				.required()
				.argName("number of cross-validation iterations")
				.build());
		
		options.addOption(Option.builder("f").longOpt("fold")
				.desc("the number of cross-validation folds")
				.hasArg()
				.required()
				.argName("the number of cross-validation folds")
				.build());
		
		options.addOption(Option.builder("p").longOpt("pool")
				.desc("thread pool size")
				.hasArg()
				.required()
				.argName("thread pool size")
				.build());
		
		options.addOption(Option.builder("o").longOpt("originaldata")
				.desc("path to original data before creating cross-validation data")
				.hasArg()
				.required()
				.argName("path")
				.build());
		
		options.addOption(Option.builder("v").longOpt("tuningflag")
				.desc("true or false")
				.hasArg()
				.argName("flag of parameter tuning")
				.build());
		
		options.addOption(Option.builder("u").longOpt("tuning")
				.desc("parameter tuning option. 1 is GridSearch or 2 is CVParameterSelection or 3 is MultiSearch")
				.hasArg()
				.argName("parameter tuning option")
				.build());
		
		options.addOption(Option.builder("e").longOpt("evaluation")
				.desc("1 is AUC or 2 is Fmeasure or 3 is MCC or 4 is Precision or 5 is Recall")
				.hasArg()
				.argName("MultiSearch Evaluation option")
				.build());
		
		return options;
	}
	
	static boolean parseOptions(Options options,String[] args){
	
		CommandLineParser parser = new DefaultParser();
	
		try {
	
			CommandLine cmd = parser.parse(options, args);
	
			sourcePath = cmd.getOptionValue("s");
			dataUnbalancingMode = cmd.getOptionValue("d");
			help = cmd.hasOption("h");
			type = cmd.getOptionValue("t"); 
			csvPath = cmd.getOptionValue("c"); 
			mlModel = cmd.getOptionValue("m");
			iter = cmd.getOptionValue("i");
			fold = cmd.getOptionValue("f");
			poolSize = cmd.getOptionValue("p");
			beforeCrossValidationDataPath = cmd.getOptionValue("o");
			
			if(cmd.hasOption("v")) {
				parameterTuningFlag = cmd.getOptionValue("v");
			}
			if(cmd.hasOption("u")) {
				parameterTuningMode = cmd.getOptionValue("u");
			}
			if(cmd.hasOption("e")) {
				multisearchEvaluationName = cmd.getOptionValue("e");
			}
			

	
		} catch (Exception e) {
			printHelp(options);
			return false;
		}
	
		return true;
	}
	
	// Need to update
	private static void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "Multicollineaity paper experiment tool";
		String footer ="\nPlease report issues at https://github.com/HGUISEL/EJTool/issues";
		formatter.printHelp("EJTool", header, options, footer, true);
	}
}




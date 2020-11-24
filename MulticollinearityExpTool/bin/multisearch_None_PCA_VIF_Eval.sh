#!/bin/sh
# type1

# AUC
#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LR_result_AUC.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25 -v true -u 3 -e AUC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_RF_result_AUC.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25 -v true -u 3 -e AUC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_DT_result_AUC.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25 -v true -u 3 -e AUC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LMT_result_AUC.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25 -v true -u 3 -e AUC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_BN_result_AUC.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25 -v true -u 3 -e AUC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_NB_result_AUC.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25 -v true -u 3 -e AUC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

# Fmeasure
#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LR_result_Fmeasure.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25 -v true -u 3 -e Fmeasure -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_RF_result_Fmeasure.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25 -v true -u 3 -e Fmeasure -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_DT_result_Fmeasure.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25 -v true -u 3 -e Fmeasure -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LMT_result_Fmeasure.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25 -v true -u 3 -e Fmeasure -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_BN_result_Fmeasure.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25 -v true -u 3 -e Fmeasure -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_NB_result_Fmeasure.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25 -v true -u 3 -e Fmeasure -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

# MCC
#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LR_result_MCC.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25 -v true -u 3 -e MCC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_RF_result_MCC.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25 -v true -u 3 -e MCC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_DT_result_MCC.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25 -v true -u 3 -e MCC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LMT_result_MCC.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25 -v true -u 3 -e MCC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_BN_result_MCC.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25 -v true -u 3 -e MCC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_NB_result_MCC.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25 -v true -u 3 -e MCC -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

# Precision
#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LR_result_Precision.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25 -v true -u 3 -e Precision -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_RF_result_Precision.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25 -v true -u 3 -e Precision -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_DT_result_Precision.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25 -v true -u 3 -e Precision -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LMT_result_Precision.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25 -v true -u 3 -e Precision -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_BN_result_Precision.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25 -v true -u 3 -e Precision -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_NB_result_Precision.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25 -v true -u 3 -e Precision -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

# Recall
#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LR_result_Recall.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25 -v true -u 3 -e Recall -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_RF_result_Recall.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25 -v true -u 3 -e Recall -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_DT_result_Recall.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25 -v true -u 3 -e Recall -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LMT_result_Recall.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25 -v true -u 3 -e Recall -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_BN_result_Recall.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25 -v true -u 3 -e Recall -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_NB_result_Recall.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25 -v true -u 3 -e Recall -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

#!/bin/sh
# type1

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LR_result.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_RF_result.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_DT_result.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_LMT_result.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_BN_result.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/cross_validation_data/ -t 1 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_None_PCA_VIF_NB_result.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_pca_vif_data

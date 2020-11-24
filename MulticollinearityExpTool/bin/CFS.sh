#!/bin/sh
# type2 
./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 2 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/CFS_LR_result.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25  -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 2 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/CFS_RF_result.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25  -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 2 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/CFS_DT_result.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25  -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 2 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/CFS_LMT_result.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25  -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 2 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/CFS_BN_result.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25  -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 2 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/CFS_NB_result.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25  -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data


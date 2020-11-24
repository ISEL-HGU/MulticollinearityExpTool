#!/bin/sh
# type3 
./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 3 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/WFS_LR_result.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 3 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/WFS_RF_result.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 3 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/WFS_DT_result.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 3 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/WFS_LMT_result.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 3 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/WFS_BN_result.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/fs_cross_validation_data/ -t 3 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/WFS_NB_result.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25 -o /home/eunjiwon/Git/MulticollinearityExpTool/origin_data

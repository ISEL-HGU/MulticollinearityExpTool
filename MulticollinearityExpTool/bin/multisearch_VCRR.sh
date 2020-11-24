#!/bin/sh
# type4
./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/vc_rr_cross_validation_data/ -t 4 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_VCRR_LR_result.csv -i 10 -f 10 -m weka.classifiers.functions.Logistic -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/VC_RR_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/vc_rr_cross_validation_data/ -t 4 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_VCRR_DT_result.csv -i 10 -f 10 -m weka.classifiers.trees.J48 -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/VC_RR_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/vc_rr_cross_validation_data/ -t 4 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_VCRR_BN_result.csv -i 10 -f 10 -m weka.classifiers.bayes.BayesNet -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/VC_RR_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/vc_rr_cross_validation_data/ -t 4 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_VCRR_NB_result.csv -i 10 -f 10 -m weka.classifiers.bayes.NaiveBayes -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/VC_RR_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/vc_rr_cross_validation_data/ -t 4 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_VCRR_RF_result.csv -i 10 -f 10 -m weka.classifiers.trees.RandomForest -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/VC_RR_data

./MulticollinearityExpTool -s /home/eunjiwon/Git/MulticollinearityExpTool/vc_rr_cross_validation_data/ -t 4 -c /home/eunjiwon/Git/MulticollinearityExpTool/multi_results/MultiSearch_VCRR_LMT_result.csv -i 10 -f 10 -m weka.classifiers.trees.LMT -d 1 -p 25 -v true -u 3 -o /home/eunjiwon/Git/MulticollinearityExpTool/VC_RR_data


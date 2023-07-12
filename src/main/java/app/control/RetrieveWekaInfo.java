package app.control;

import app.model.AllEvaluationLists;
import app.model.ClassifierEvaluation;
import app.utils.ClassifierEvaluationUtil;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.supervised.instance.SpreadSubsample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class RetrieveWekaInfo {
	private static final Logger LOGGER = Logger.getLogger(RetrieveWekaInfo.class.getName());
	private static final String RANDOM_FOREST = "Random Forest";
	private static final String NAIVE_BAYES = "Naive Bayes";
	private static final String IBK = "IBk";
	private static final String NO_SAMPLING = "No Sampling";
	private static final String OVERSAMPLING = "Oversampling";
	private static final String UNDERSAMPLING = "Undersampling";
	private static final String SMOTE = "SMOTE";
	private final String path;
	private final Integer releaseIndex;

	public RetrieveWekaInfo(String path, int releaseIndex) {
		this.path = path;
		this.releaseIndex = releaseIndex;
	}

	public AllEvaluationLists retrieveClassifiersEvaluation(String projectName) throws Exception {

		List<ClassifierEvaluation> simpleRandomForestList = new ArrayList<>();
		List<ClassifierEvaluation> simpleNaiveBayesList = new ArrayList<>();
		List<ClassifierEvaluation> simpleIBkList = new ArrayList<>();
		List<ClassifierEvaluation> oversamplingRandomForestList = new ArrayList<>();
		List<ClassifierEvaluation> oversamplingNaiveBayesList = new ArrayList<>();
		List<ClassifierEvaluation> oversamplingIBkList = new ArrayList<>();
		List<ClassifierEvaluation> undersamplingRandomForestList = new ArrayList<>();
		List<ClassifierEvaluation> undersamplingNaiveBayesList = new ArrayList<>();
		List<ClassifierEvaluation> undersamplingIBkList = new ArrayList<>();
		List<ClassifierEvaluation> smoteRandomForestList = new ArrayList<>();
		List<ClassifierEvaluation> smoteNaiveBayesList = new ArrayList<>();
		List<ClassifierEvaluation> smoteIBkList = new ArrayList<>();


		for(int index = 2; index < this.releaseIndex+1; index++){
			String completePath = this.path + index;
			DataSource source1 = new DataSource(completePath + "/Train.arff");
			DataSource source2 = new DataSource(completePath + "/Test.arff");
			Instances training = source1.getDataSet();
			Instances testing = source2.getDataSet();

			List<Integer> countYN = getNumberOfYN(training);
			int majoritySize = Collections.max(countYN);
			int minoritySize = Collections.min(countYN);

			RandomForest randomForestClassifier = new RandomForest();
			NaiveBayes naiveBayesClassifier = new NaiveBayes();
			IBk ibkClassifier = new IBk();

			// VALIDATION WITHOUT SAMPLING
			int numAttr = training.numAttributes();
			training.setClassIndex(numAttr - 1);
			testing.setClassIndex(numAttr - 1);

			Evaluation eval = new Evaluation(testing);

			randomForestClassifier.buildClassifier(training);
			eval.evaluateModel(randomForestClassifier, testing);
			ClassifierEvaluation simpleRandomForest = new ClassifierEvaluation(projectName, index, RANDOM_FOREST, NO_SAMPLING);
			simpleRandomForest.setTrainingPercent(100.0*training.numInstances()/(training.numInstances()+testing.numInstances()));
			simpleRandomForest.setPrecision(eval.precision(0));
			simpleRandomForest.setRecall(eval.recall(0));
			simpleRandomForest.setAuc(eval.areaUnderROC(0));
			simpleRandomForest.setKappa(eval.kappa());
			simpleRandomForest.setTp(eval.numTruePositives(0));
			simpleRandomForest.setFp(eval.numFalsePositives(0));
			simpleRandomForest.setTn(eval.numTrueNegatives(0));
			simpleRandomForest.setFn(eval.numFalseNegatives(0));
			simpleRandomForestList.add(simpleRandomForest);

			naiveBayesClassifier.buildClassifier(training);
			eval.evaluateModel(naiveBayesClassifier, testing);
			ClassifierEvaluation simpleNaiveBayes = new ClassifierEvaluation(projectName, index, NAIVE_BAYES, NO_SAMPLING);
			simpleNaiveBayes.setTrainingPercent(100.0*training.numInstances()/(training.numInstances()+testing.numInstances()));
			simpleNaiveBayes.setPrecision(eval.precision(0));
			simpleNaiveBayes.setRecall(eval.recall(0));
			simpleNaiveBayes.setAuc(eval.areaUnderROC(0));
			simpleNaiveBayes.setKappa(eval.kappa());
			simpleNaiveBayes.setTp(eval.numTruePositives(0));
			simpleNaiveBayes.setFp(eval.numFalsePositives(0));
			simpleNaiveBayes.setTn(eval.numTrueNegatives(0));
			simpleNaiveBayes.setFn(eval.numFalseNegatives(0));
			simpleNaiveBayesList.add(simpleNaiveBayes);

			ibkClassifier.buildClassifier(training);
			eval.evaluateModel(ibkClassifier, testing);
			ClassifierEvaluation simpleIBk = new ClassifierEvaluation(projectName, index, IBK, NO_SAMPLING);
			simpleIBk.setTrainingPercent(100.0*training.numInstances()/(training.numInstances()+testing.numInstances()));
			simpleIBk.setPrecision(eval.precision(0));
			simpleIBk.setRecall(eval.recall(0));
			simpleIBk.setAuc(eval.areaUnderROC(0));
			simpleIBk.setKappa(eval.kappa());
			simpleIBk.setTp(eval.numTruePositives(0));
			simpleIBk.setFp(eval.numFalsePositives(0));
			simpleIBk.setTn(eval.numTrueNegatives(0));
			simpleIBk.setFn(eval.numFalseNegatives(0));
			simpleIBkList.add(simpleIBk);


			// VALIDATION WITH OVERSAMPLING
			Resample resample = new Resample();
			String z = Double.toString(2 * ((double) majoritySize / training.size()) * 100);
			resample.setOptions(new String[]{"-B", "1.0", "-Z", z});
			resample.setInputFormat(training);

			Instances filteredTraining = Filter.useFilter(training, resample);
			Instances filteredTesting = Filter.useFilter(testing, resample);

			numAttr = filteredTraining.numAttributes();
			filteredTraining.setClassIndex(numAttr - 1);

			randomForestClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(randomForestClassifier, filteredTesting);
			ClassifierEvaluation oversamplingRandomForest = new ClassifierEvaluation(projectName, index, RANDOM_FOREST, OVERSAMPLING);
			oversamplingRandomForest.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			oversamplingRandomForest.setPrecision(eval.precision(0));
			oversamplingRandomForest.setRecall(eval.recall(0));
			oversamplingRandomForest.setAuc(eval.areaUnderROC(0));
			oversamplingRandomForest.setKappa(eval.kappa());
			oversamplingRandomForest.setTp(eval.numTruePositives(0));
			oversamplingRandomForest.setFp(eval.numFalsePositives(0));
			oversamplingRandomForest.setTn(eval.numTrueNegatives(0));
			oversamplingRandomForest.setFn(eval.numFalseNegatives(0));
			oversamplingRandomForestList.add(oversamplingRandomForest);

			naiveBayesClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(naiveBayesClassifier, filteredTesting);
			ClassifierEvaluation oversamplingNaiveBayes = new ClassifierEvaluation(projectName, index, NAIVE_BAYES, OVERSAMPLING);
			oversamplingNaiveBayes.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			oversamplingNaiveBayes.setPrecision(eval.precision(0));
			oversamplingNaiveBayes.setRecall(eval.recall(0));
			oversamplingNaiveBayes.setAuc(eval.areaUnderROC(0));
			oversamplingNaiveBayes.setKappa(eval.kappa());
			oversamplingNaiveBayes.setTp(eval.numTruePositives(0));
			oversamplingNaiveBayes.setFp(eval.numFalsePositives(0));
			oversamplingNaiveBayes.setTn(eval.numTrueNegatives(0));
			oversamplingNaiveBayes.setFn(eval.numFalseNegatives(0));
			oversamplingNaiveBayesList.add(oversamplingNaiveBayes);

			ibkClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(ibkClassifier, filteredTesting);
			ClassifierEvaluation oversamplingIBk = new ClassifierEvaluation(projectName, index, IBK, OVERSAMPLING);
			oversamplingIBk.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			oversamplingIBk.setPrecision(eval.precision(0));
			oversamplingIBk.setRecall(eval.recall(0));
			oversamplingIBk.setAuc(eval.areaUnderROC(0));
			oversamplingIBk.setKappa(eval.kappa());
			oversamplingIBk.setTp(eval.numTruePositives(0));
			oversamplingIBk.setFp(eval.numFalsePositives(0));
			oversamplingIBk.setTn(eval.numTrueNegatives(0));
			oversamplingIBk.setFn(eval.numFalseNegatives(0));
			oversamplingIBkList.add(oversamplingIBk);


			// VALIDATION WITH UNDERSAMPLING
			SpreadSubsample spreadSubsample = new SpreadSubsample();
			spreadSubsample.setInputFormat(training);
			spreadSubsample.setOptions(new String[] {"-M", "1.0"});

			filteredTraining = Filter.useFilter(training, spreadSubsample);
			filteredTesting = Filter.useFilter(testing, spreadSubsample);

			numAttr = filteredTraining.numAttributes();
			filteredTraining.setClassIndex(numAttr - 1);

			randomForestClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(randomForestClassifier, filteredTesting);
			ClassifierEvaluation undersamplingRandomForest = new ClassifierEvaluation(projectName, index, RANDOM_FOREST, UNDERSAMPLING);
			undersamplingRandomForest.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			undersamplingRandomForest.setPrecision(eval.precision(0));
			undersamplingRandomForest.setRecall(eval.recall(0));
			undersamplingRandomForest.setAuc(eval.areaUnderROC(0));
			undersamplingRandomForest.setKappa(eval.kappa());
			undersamplingRandomForest.setTp(eval.numTruePositives(0));
			undersamplingRandomForest.setFp(eval.numFalsePositives(0));
			undersamplingRandomForest.setTn(eval.numTrueNegatives(0));
			undersamplingRandomForest.setFn(eval.numFalseNegatives(0));
			undersamplingRandomForestList.add(undersamplingRandomForest);

			naiveBayesClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(naiveBayesClassifier, filteredTesting);
			ClassifierEvaluation undersamplingNaiveBayes = new ClassifierEvaluation(projectName, index, NAIVE_BAYES, UNDERSAMPLING);
			undersamplingNaiveBayes.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			undersamplingNaiveBayes.setPrecision(eval.precision(0));
			undersamplingNaiveBayes.setRecall(eval.recall(0));
			undersamplingNaiveBayes.setAuc(eval.areaUnderROC(0));
			undersamplingNaiveBayes.setKappa(eval.kappa());
			undersamplingNaiveBayes.setTp(eval.numTruePositives(0));
			undersamplingNaiveBayes.setFp(eval.numFalsePositives(0));
			undersamplingNaiveBayes.setTn(eval.numTrueNegatives(0));
			undersamplingNaiveBayes.setFn(eval.numFalseNegatives(0));
			undersamplingNaiveBayesList.add(undersamplingNaiveBayes);

			ibkClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(ibkClassifier, filteredTesting);
			ClassifierEvaluation undersamplingIBk = new ClassifierEvaluation(projectName, index, IBK, UNDERSAMPLING);
			undersamplingIBk.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			undersamplingIBk.setPrecision(eval.precision(0));
			undersamplingIBk.setRecall(eval.recall(0));
			undersamplingIBk.setAuc(eval.areaUnderROC(0));
			undersamplingIBk.setKappa(eval.kappa());
			undersamplingIBk.setTp(eval.numTruePositives(0));
			undersamplingIBk.setFp(eval.numFalsePositives(0));
			undersamplingIBk.setTn(eval.numTrueNegatives(0));
			undersamplingIBk.setFn(eval.numFalseNegatives(0));
			undersamplingIBkList.add(undersamplingIBk);


			// VALIDATION WITH SMOTE
			SMOTE smote = new SMOTE();
			String p = (minoritySize > 0) ? Double.toString(100.0 * (majoritySize - minoritySize) / minoritySize) : "100.0";
			smote.setOptions(new String[] {"-P", p});
			smote.setInputFormat(training);
			try {
				filteredTraining = Filter.useFilter(training, smote);
			} catch (Exception e){
				LOGGER.info("");
			}
			filteredTesting = Filter.useFilter(testing, smote);

			numAttr = filteredTraining.numAttributes();
			filteredTraining.setClassIndex(numAttr - 1);

			randomForestClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(randomForestClassifier, filteredTesting);
			ClassifierEvaluation smoteRandomForest = new ClassifierEvaluation(projectName, index, RANDOM_FOREST, SMOTE);
			smoteRandomForest.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			smoteRandomForest.setPrecision(eval.precision(0));
			smoteRandomForest.setRecall(eval.recall(0));
			smoteRandomForest.setAuc(eval.areaUnderROC(0));
			smoteRandomForest.setKappa(eval.kappa());
			smoteRandomForest.setTp(eval.numTruePositives(0));
			smoteRandomForest.setFp(eval.numFalsePositives(0));
			smoteRandomForest.setTn(eval.numTrueNegatives(0));
			smoteRandomForest.setFn(eval.numFalseNegatives(0));
			smoteRandomForestList.add(smoteRandomForest);

			naiveBayesClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(naiveBayesClassifier, filteredTesting);
			ClassifierEvaluation smoteNaiveBayes = new ClassifierEvaluation(projectName, index, NAIVE_BAYES, SMOTE);
			smoteNaiveBayes.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			smoteNaiveBayes.setPrecision(eval.precision(0));
			smoteNaiveBayes.setRecall(eval.recall(0));
			smoteNaiveBayes.setAuc(eval.areaUnderROC(0));
			smoteNaiveBayes.setKappa(eval.kappa());
			smoteNaiveBayes.setTp(eval.numTruePositives(0));
			smoteNaiveBayes.setFp(eval.numFalsePositives(0));
			smoteNaiveBayes.setTn(eval.numTrueNegatives(0));
			smoteNaiveBayes.setFn(eval.numFalseNegatives(0));
			smoteNaiveBayesList.add(smoteNaiveBayes);

			ibkClassifier.buildClassifier(filteredTraining);
			eval.evaluateModel(ibkClassifier, filteredTesting);
			ClassifierEvaluation smoteIBk = new ClassifierEvaluation(projectName, index, IBK, SMOTE);
			smoteIBk.setTrainingPercent(100.0*filteredTraining.numInstances()/(filteredTraining.numInstances()+filteredTesting.numInstances()));
			smoteIBk.setPrecision(eval.precision(0));
			smoteIBk.setRecall(eval.recall(0));
			smoteIBk.setAuc(eval.areaUnderROC(0));
			smoteIBk.setKappa(eval.kappa());
			smoteIBk.setTp(eval.numTruePositives(0));
			smoteIBk.setFp(eval.numFalsePositives(0));
			smoteIBk.setTn(eval.numTrueNegatives(0));
			smoteIBk.setFn(eval.numFalseNegatives(0));
			smoteIBkList.add(smoteIBk);
		}

		List<ClassifierEvaluation> avgEvaluationsList = new ArrayList<>();

		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(simpleRandomForestList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(simpleNaiveBayesList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(simpleIBkList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(undersamplingRandomForestList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(undersamplingNaiveBayesList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(undersamplingIBkList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(oversamplingRandomForestList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(oversamplingNaiveBayesList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(oversamplingIBkList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(smoteRandomForestList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(smoteNaiveBayesList));
		avgEvaluationsList.add(ClassifierEvaluationUtil.getAvgEvaluation(smoteIBkList));

		AllEvaluationLists allLists = new AllEvaluationLists();

		allLists.setAvgEvaluationsList(avgEvaluationsList);
		allLists.setSmoteIBkList(smoteIBkList);
		allLists.setSmoteNaiveBayesList(smoteNaiveBayesList);
		allLists.setSmoteRandomForestList(smoteRandomForestList);
		allLists.setUndersamplingIBkList(undersamplingIBkList);
		allLists.setUndersamplingNaiveBayesList(undersamplingNaiveBayesList);
		allLists.setUndersamplingRandomForestList(undersamplingRandomForestList);
		allLists.setOversamplingIBkList(oversamplingIBkList);
		allLists.setOversamplingNaiveBayesList(oversamplingNaiveBayesList);
		allLists.setOversamplingRandomForestList(oversamplingRandomForestList);
		allLists.setSimpleIBkList(simpleIBkList);
		allLists.setSimpleNaiveBayesList(simpleNaiveBayesList);
		allLists.setSimpleRandomForestList(simpleRandomForestList);
		allLists.mergeAll();

		return allLists;
	}

	private List<Integer> getNumberOfYN(Instances training) {
		int countYes = 0;
		int countNo = 0;
		for (Instance instance : training) {
			if (instance.stringValue(instance.numAttributes()-1).equals("true"))
				countYes++;
			else
				countNo++;
		}
		return Arrays.asList(countNo, countYes);
	}
}

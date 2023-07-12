package app.model;

import java.util.ArrayList;
import java.util.List;

public class AllEvaluationLists {
	
	private List<ClassifierEvaluation> simpleRandomForestList;
	private List<ClassifierEvaluation> simpleNaiveBayesList;
	private List<ClassifierEvaluation> simpleIBkList;
	private List<ClassifierEvaluation> undersamplingRandomForestList;
	private List<ClassifierEvaluation> undersamplingNaiveBayesList;
	private List<ClassifierEvaluation> undersamplingIBkList;
	private List<ClassifierEvaluation> oversamplingRandomForestList;
	private List<ClassifierEvaluation> oversamplingNaiveBayesList;
	private List<ClassifierEvaluation> oversamplingIBkList;
	private List<ClassifierEvaluation> smoteRandomForestList;
	private List<ClassifierEvaluation> smoteNaiveBayesList;
	private List<ClassifierEvaluation> smoteIBkList;
	
	private List<ClassifierEvaluation> avgEvaluationsList;
	private List<ClassifierEvaluation> mergeEvaluationsList = new ArrayList<>();
	
	public void mergeAll() {
		this.mergeEvaluationsList.addAll(simpleRandomForestList);
		this.mergeEvaluationsList.addAll(simpleNaiveBayesList);
		this.mergeEvaluationsList.addAll(simpleIBkList);
		this.mergeEvaluationsList.addAll(undersamplingRandomForestList);
		this.mergeEvaluationsList.addAll(undersamplingNaiveBayesList);
		this.mergeEvaluationsList.addAll(undersamplingIBkList);
		this.mergeEvaluationsList.addAll(oversamplingRandomForestList);
		this.mergeEvaluationsList.addAll(oversamplingNaiveBayesList);
		this.mergeEvaluationsList.addAll(oversamplingIBkList);
		this.mergeEvaluationsList.addAll(smoteRandomForestList);
		this.mergeEvaluationsList.addAll(smoteNaiveBayesList);
		this.mergeEvaluationsList.addAll(smoteIBkList);
		
	}
	
	/**
	 * @return the simpleRandomForestList
	 */
	public List<ClassifierEvaluation> getSimpleRandomForestList() {
		return simpleRandomForestList;
	}
	/**
	 * @param simpleRandomForestList the simpleRandomForestList to set
	 */
	public void setSimpleRandomForestList(List<ClassifierEvaluation> simpleRandomForestList) {
		this.simpleRandomForestList = simpleRandomForestList;
	}
	/**
	 * @return the simpleNaiveBayesList
	 */
	public List<ClassifierEvaluation> getSimpleNaiveBayesList() {
		return simpleNaiveBayesList;
	}
	/**
	 * @param simpleNaiveBayesList the simpleNaiveBayesList to set
	 */
	public void setSimpleNaiveBayesList(List<ClassifierEvaluation> simpleNaiveBayesList) {
		this.simpleNaiveBayesList = simpleNaiveBayesList;
	}
	/**
	 * @return the simpleIBkList
	 */
	public List<ClassifierEvaluation> getSimpleIBkList() {
		return simpleIBkList;
	}
	/**
	 * @param simpleIBkList the simpleIBkList to set
	 */
	public void setSimpleIBkList(List<ClassifierEvaluation> simpleIBkList) {
		this.simpleIBkList = simpleIBkList;
	}
	/**
	 * @return the featureSelRandomForestList
	 */
	public List<ClassifierEvaluation> getUndersamplingRandomForestList() {
		return undersamplingRandomForestList;
	}
	/**
	 * @param undersamplingRandomForestList the featureSelRandomForestList to set
	 */
	public void setUndersamplingRandomForestList(List<ClassifierEvaluation> undersamplingRandomForestList) {
		this.undersamplingRandomForestList = undersamplingRandomForestList;
	}
	/**
	 * @return the featureSelNaiveBayesList
	 */
	public List<ClassifierEvaluation> getUndersamplingNaiveBayesList() {
		return undersamplingNaiveBayesList;
	}
	/**
	 * @param undersamplingNaiveBayesList the featureSelNaiveBayesList to set
	 */
	public void setUndersamplingNaiveBayesList(List<ClassifierEvaluation> undersamplingNaiveBayesList) {
		this.undersamplingNaiveBayesList = undersamplingNaiveBayesList;
	}
	/**
	 * @return the featureSelIBkList
	 */
	public List<ClassifierEvaluation> getUndersamplingIBkList() {
		return undersamplingIBkList;
	}
	/**
	 * @param undersamplingIBkList the featureSelIBkList to set
	 */
	public void setUndersamplingIBkList(List<ClassifierEvaluation> undersamplingIBkList) {
		this.undersamplingIBkList = undersamplingIBkList;
	}
	/**
	 * @return the samplingRandomForestList
	 */
	public List<ClassifierEvaluation> getOversamplingRandomForestList() {
		return oversamplingRandomForestList;
	}
	/**
	 * @param oversamplingRandomForestList the samplingRandomForestList to set
	 */
	public void setOversamplingRandomForestList(List<ClassifierEvaluation> oversamplingRandomForestList) {
		this.oversamplingRandomForestList = oversamplingRandomForestList;
	}
	/**
	 * @return the samplingNaiveBayesList
	 */
	public List<ClassifierEvaluation> getOversamplingNaiveBayesList() {
		return oversamplingNaiveBayesList;
	}
	/**
	 * @param oversamplingNaiveBayesList the samplingNaiveBayesList to set
	 */
	public void setOversamplingNaiveBayesList(List<ClassifierEvaluation> oversamplingNaiveBayesList) {
		this.oversamplingNaiveBayesList = oversamplingNaiveBayesList;
	}
	/**
	 * @return the samplingIBkList
	 */
	public List<ClassifierEvaluation> getOversamplingIBkList() {
		return oversamplingIBkList;
	}
	/**
	 * @param oversamplingIBkList the samplingIBkList to set
	 */
	public void setOversamplingIBkList(List<ClassifierEvaluation> oversamplingIBkList) {
		this.oversamplingIBkList = oversamplingIBkList;
	}
	/**
	 * @return the costSensRandomForestList
	 */
	public List<ClassifierEvaluation> getSmoteRandomForestList() {
		return smoteRandomForestList;
	}
	/**
	 * @param smoteRandomForestList the costSensRandomForestList to set
	 */
	public void setSmoteRandomForestList(List<ClassifierEvaluation> smoteRandomForestList) {
		this.smoteRandomForestList = smoteRandomForestList;
	}
	/**
	 * @return the costSensNaiveBayesList
	 */
	public List<ClassifierEvaluation> getSmoteNaiveBayesList() {
		return smoteNaiveBayesList;
	}
	/**
	 * @param smoteNaiveBayesList the costSensNaiveBayesList to set
	 */
	public void setSmoteNaiveBayesList(List<ClassifierEvaluation> smoteNaiveBayesList) {
		this.smoteNaiveBayesList = smoteNaiveBayesList;
	}
	/**
	 * @return the costSensIBkList
	 */
	public List<ClassifierEvaluation> getSmoteIBkList() {
		return smoteIBkList;
	}
	/**
	 * @param smoteIBkList the costSensIBkList to set
	 */
	public void setSmoteIBkList(List<ClassifierEvaluation> smoteIBkList) {
		this.smoteIBkList = smoteIBkList;
	}
	/**
	 * @return the avgEvaluationsList
	 */
	public List<ClassifierEvaluation> getAvgEvaluationsList() {
		return avgEvaluationsList;
	}
	/**
	 * @param avgEvaluationsList the avgEvaluationsList to set
	 */
	public void setAvgEvaluationsList(List<ClassifierEvaluation> avgEvaluationsList) {
		this.avgEvaluationsList = avgEvaluationsList;
	}

	/**
	 * @return the mergeEvaluationsList
	 */
	public List<ClassifierEvaluation> getMergeEvaluationsList() {
		return mergeEvaluationsList;
	}

	/**
	 * @param mergeEvaluationsList the mergeEvaluationsList to set
	 */
	public void setMergeEvaluationsList(List<ClassifierEvaluation> mergeEvaluationsList) {
		this.mergeEvaluationsList = mergeEvaluationsList;
	}
	

}

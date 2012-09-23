package org.modeldriven.alf.uml;


public interface CreateObjectAction extends Action {
	public OutputPin getResult();

	public void setResult(OutputPin result);

	public Classifier getClassifier();

	public void setClassifier(Classifier classifier);
}

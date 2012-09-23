package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.uml.Classifier;

public interface CreateObjectAction extends Action {
	public OutputPin getResult();

	public void setResult(OutputPin result);

	public Classifier getClassifier();

	public void setClassifier(Classifier classifier);
}

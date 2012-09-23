package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.uml.Classifier;

public interface ReadIsClassifiedObjectAction extends Action {
	public boolean getIsDirect();

	public void setIsDirect(boolean isDirect);

	public Classifier getClassifier();

	public void setClassifier(Classifier classifier);

	public OutputPin getResult();

	public void setResult(OutputPin result);

	public InputPin getObject();

	public void setObject(InputPin object);
}

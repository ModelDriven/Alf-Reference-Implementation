package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.uml.Classifier;

public interface ReclassifyObjectAction extends Action {
	public boolean getIsReplaceAll();

	public void setIsReplaceAll(boolean isReplaceAll);

	public List<Classifier> getOldClassifier();

	public void addOldClassifier(Classifier oldClassifier);

	public InputPin getObject();

	public void setObject(InputPin object);

	public List<Classifier> getNewClassifier();

	public void addNewClassifier(Classifier newClassifier);
}

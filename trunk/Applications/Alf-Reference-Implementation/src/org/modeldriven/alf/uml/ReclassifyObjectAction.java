package org.modeldriven.alf.uml;

import java.util.List;

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

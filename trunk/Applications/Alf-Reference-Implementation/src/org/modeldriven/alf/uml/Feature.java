package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.RedefinableElement;

public interface Feature extends RedefinableElement {
	public boolean getIsStatic();

	public void setIsStatic(boolean isStatic);

	public List<Classifier> getFeaturingClassifier();
}

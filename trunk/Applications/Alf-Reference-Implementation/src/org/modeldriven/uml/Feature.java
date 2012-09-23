package org.modeldriven.uml;

import java.util.List;

public interface Feature extends RedefinableElement {
	public boolean getIsStatic();

	public void setIsStatic(boolean isStatic);

	public List<Classifier> getFeaturingClassifier();
}

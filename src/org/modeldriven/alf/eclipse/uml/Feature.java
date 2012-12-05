package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Feature extends RedefinableElement implements
		org.modeldriven.alf.uml.Feature {

	public Feature(org.eclipse.uml2.uml.Feature base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Feature getBase() {
		return (org.eclipse.uml2.uml.Feature) this.base;
	}

	public boolean getIsStatic() {
		return this.getBase().getIsStatic();
	}

	public void setIsStatic(boolean isStatic) {
		this.getBase().setIsStatic(isStatic);
	}

	public List<org.modeldriven.alf.uml.Classifier> getFeaturingClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier element : this.getBase()
				.getFeaturingClassifiers()) {
			list.add(new Classifier(element));
		}
		return list;
	}

}

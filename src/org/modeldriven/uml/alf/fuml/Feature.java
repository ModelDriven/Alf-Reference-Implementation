package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class Feature extends RedefinableElement implements
		org.modeldriven.alf.uml.Feature {

	public Feature(fUML.Syntax.Classes.Kernel.Feature base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Feature getBase() {
		return (fUML.Syntax.Classes.Kernel.Feature) this.base;
	}

	public boolean getIsStatic() {
		return this.getBase().isStatic;
	}

	public void setIsStatic(boolean isStatic) {
		this.getBase().isStatic = isStatic;
	}

	public List<org.modeldriven.alf.uml.Classifier> getFeaturingClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().featuringClassifier) {
			list.add(new Classifier(element));
		}
		return list;
	}

}

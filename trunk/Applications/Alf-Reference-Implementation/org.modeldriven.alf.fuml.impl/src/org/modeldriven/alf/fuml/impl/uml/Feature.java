/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

import java.util.ArrayList;
import java.util.List;

public abstract class Feature extends RedefinableElement implements
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
			list.add((Classifier)this.wrap(element));
		}
		return list;
	}

}

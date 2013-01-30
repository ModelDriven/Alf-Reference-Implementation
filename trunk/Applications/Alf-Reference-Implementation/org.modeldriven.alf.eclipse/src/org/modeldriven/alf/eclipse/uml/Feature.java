/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
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
		return this.getBase().isStatic();
	}

	public void setIsStatic(boolean isStatic) {
		this.getBase().setIsStatic(isStatic);
	}

	public List<org.modeldriven.alf.uml.Classifier> getFeaturingClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier element : this.getBase()
				.getFeaturingClassifiers()) {
			list.add((org.modeldriven.alf.uml.Classifier) wrap(element));
		}
		return list;
	}

}

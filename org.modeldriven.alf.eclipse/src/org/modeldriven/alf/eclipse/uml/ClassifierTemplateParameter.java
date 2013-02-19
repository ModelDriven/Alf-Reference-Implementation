/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.uml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.UMLFactory;

public class ClassifierTemplateParameter extends TemplateParameter implements
	org.modeldriven.alf.uml.ClassifierTemplateParameter {
	
	public ClassifierTemplateParameter() {
		this(UMLFactory.eINSTANCE.createClassifierTemplateParameter());
	}

	public ClassifierTemplateParameter(
			org.eclipse.uml2.uml.TemplateParameter base) {
		super(base);
	}
	
	public org.eclipse.uml2.uml.ClassifierTemplateParameter getBase() {
		return (org.eclipse.uml2.uml.ClassifierTemplateParameter)this.base;
	}

	@Override
    public boolean getAllowSubstitutable() {
		return this.getBase().isAllowSubstitutable();
	}
    
	@Override
    public void setAllowSubstitutable(boolean allowSubstitutable) {
    	this.getBase().setAllowSubstitutable(allowSubstitutable);
    }

	@Override
	public List<org.modeldriven.alf.uml.Classifier> getConstrainingClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = 
				new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier classifier: this.getBase().getConstrainingClassifiers()) {
			list.add((org.modeldriven.alf.uml.Classifier)wrap(classifier));
		}
		return list;
	}

	@Override
	public void addConstrainingClassifier(org.modeldriven.alf.uml.Classifier constrainingClassifier) {
		this.getBase().getConstrainingClassifiers().add(
				constrainingClassifier == null? null: ((Classifier)constrainingClassifier).getBase());		
	}
	
	@Override
	public org.modeldriven.alf.uml.Classifier getParameteredElement() {
		return (org.modeldriven.alf.uml.Classifier)wrap(this.getBase().getParameteredElement());
	}

}

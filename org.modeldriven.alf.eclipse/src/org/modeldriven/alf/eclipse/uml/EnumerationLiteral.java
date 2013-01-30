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

public class EnumerationLiteral extends InstanceSpecification implements
		org.modeldriven.alf.uml.EnumerationLiteral {
	public EnumerationLiteral() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createEnumerationLiteral());
	}

	public EnumerationLiteral(org.eclipse.uml2.uml.EnumerationLiteral base) {
		super(base);
	}

	public org.eclipse.uml2.uml.EnumerationLiteral getBase() {
		return (org.eclipse.uml2.uml.EnumerationLiteral) this.base;
	}

	public org.modeldriven.alf.uml.Enumeration getEnumeration() {
		return (org.modeldriven.alf.uml.Enumeration) wrap(this.getBase()
				.getEnumeration());
	}

    @Override
    public void setEnumeration(org.modeldriven.alf.uml.Enumeration enumeration) {
        this.getBase().setEnumeration(((Enumeration)enumeration).getBase());
    }
    
    @Override
    public List<org.modeldriven.alf.uml.Classifier> getClassifier() {
        List<org.modeldriven.alf.uml.Classifier> classifiers = 
                new ArrayList<org.modeldriven.alf.uml.Classifier>();
        classifiers.add(this.getEnumeration());
        return classifiers;
    }

}

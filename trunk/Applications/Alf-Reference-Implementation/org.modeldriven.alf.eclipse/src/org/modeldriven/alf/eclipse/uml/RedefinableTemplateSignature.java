/*******************************************************************************
 * Copyright 2011-2015 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.eclipse.uml;

import org.eclipse.uml2.uml.UMLFactory;

public class RedefinableTemplateSignature extends TemplateSignature 
	implements org.modeldriven.alf.uml.RedefinableTemplateSignature {

	public RedefinableTemplateSignature() {
		super(UMLFactory.eINSTANCE.createRedefinableTemplateSignature());
	}
	
	public RedefinableTemplateSignature(org.eclipse.uml2.uml.RedefinableTemplateSignature base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.RedefinableTemplateSignature getBase() {
		return (org.eclipse.uml2.uml.RedefinableTemplateSignature)this.base;
	}

}

/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms,
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import org.eclipse.uml2.uml.UMLFactory;

public class TemplateParameterSubstitution extends Element implements
	org.modeldriven.alf.uml.TemplateParameterSubstitution {
	
	public TemplateParameterSubstitution() {
		this(UMLFactory.eINSTANCE.createTemplateParameterSubstitution());
	}

	public TemplateParameterSubstitution(
			org.eclipse.uml2.uml.TemplateParameterSubstitution base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.TemplateParameterSubstitution getBase() {
		return (org.eclipse.uml2.uml.TemplateParameterSubstitution)this.base;
	}

	@Override
	public org.modeldriven.alf.uml.TemplateParameter getFormal() {
		return (org.modeldriven.alf.uml.TemplateParameter)wrap(
				this.getBase().getFormal());
	}

	@Override
	public void setFormal(org.modeldriven.alf.uml.TemplateParameter formal) {
		this.getBase().setFormal(formal == null? null: 
			((TemplateParameter)formal).getBase());
	}

	@Override
	public org.modeldriven.alf.uml.ParameterableElement getActual() {
		return (org.modeldriven.alf.uml.ParameterableElement)wrap(
				this.getBase().getActual());
	}

	@Override
	public void setActual(org.modeldriven.alf.uml.ParameterableElement actual) {
		this.getBase().setActual(actual == null? null: 
			(org.eclipse.uml2.uml.ParameterableElement)((Element)actual).getBase());
	}

}

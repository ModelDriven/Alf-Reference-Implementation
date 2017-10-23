/*******************************************************************************
 *  Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class TemplateParameter extends Element implements org.modeldriven.alf.uml.TemplateParameter {

	public TemplateParameter(org.eclipse.uml2.uml.TemplateParameter base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.TemplateParameter getBase() {
		return (org.eclipse.uml2.uml.TemplateParameter)this.base;
	}

	@Override
	public org.modeldriven.alf.uml.ParameterableElement getParameteredElement() {
		return (org.modeldriven.alf.uml.ParameterableElement)wrap(this.getBase().getParameteredElement());
	}

	@Override
	public void setParameteredElement(org.modeldriven.alf.uml.ParameterableElement parameteredElement) {
		this.getBase().setParameteredElement(parameteredElement == null? null:
				(org.eclipse.uml2.uml.ParameterableElement)((Element)parameteredElement).getBase());
	}

	@Override
	public org.modeldriven.alf.uml.ParameterableElement getOwnedParameteredElement() {
		return (org.modeldriven.alf.uml.ParameterableElement)wrap(this.getBase().getOwnedParameteredElement());
	}

	@Override
	public void setOwnedParameteredElement(
			org.modeldriven.alf.uml.ParameterableElement parameteredElement) {
		this.getBase().setOwnedParameteredElement(parameteredElement == null? null:
				(org.eclipse.uml2.uml.ParameterableElement)((Element)parameteredElement).getBase());
	}

	@Override
	public org.modeldriven.alf.uml.TemplateSignature getSignature() {
		return (org.modeldriven.alf.uml.TemplateSignature)wrap(this.getBase().getSignature());
	}

}

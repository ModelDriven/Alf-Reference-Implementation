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
import java.util.Collection;
import java.util.List;

import org.eclipse.uml2.uml.UMLFactory;

public class TemplateBinding extends Element implements org.modeldriven.alf.uml.TemplateBinding {
	
	public TemplateBinding() {
		this(UMLFactory.eINSTANCE.createTemplateBinding());
	}

	public TemplateBinding(org.eclipse.uml2.uml.TemplateBinding base) {
		super(base);
	}

	@Override
	public org.eclipse.uml2.uml.TemplateBinding getBase() {
		return (org.eclipse.uml2.uml.TemplateBinding)this.base;
	}

	@Override
	public org.modeldriven.alf.uml.TemplateSignature getSignature() {
		return (org.modeldriven.alf.uml.TemplateSignature)wrap(this.getBase().getSignature());
	}
	
	@Override
	public void setSignature(org.modeldriven.alf.uml.TemplateSignature signature) {
		this.getBase().setSignature(
				signature == null? null: ((TemplateSignature)signature).getBase());
	}

	@Override
	public Collection<org.modeldriven.alf.uml.TemplateParameterSubstitution> getParameterSubstitution() {
		List<org.modeldriven.alf.uml.TemplateParameterSubstitution> list = new 
				ArrayList<org.modeldriven.alf.uml.TemplateParameterSubstitution>();
		for (org.eclipse.uml2.uml.TemplateParameterSubstitution substitution: 
			this.getBase().getParameterSubstitutions()) {
			list.add(((org.modeldriven.alf.uml.TemplateParameterSubstitution)
					wrap(substitution)));
		}
		return list;
	}

	@Override
	public void addParameterSubstitution(
			org.modeldriven.alf.uml.TemplateParameterSubstitution parameterSubstitution) {
		this.getBase().getParameterSubstitutions().add(
				parameterSubstitution == null? null: 
					((TemplateParameterSubstitution)parameterSubstitution).getBase());
	}

}

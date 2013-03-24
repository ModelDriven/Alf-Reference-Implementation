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

public class TemplateSignature extends Element 
	implements org.modeldriven.alf.uml.TemplateSignature {
	
	public TemplateSignature(org.eclipse.uml2.uml.TemplateSignature base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.TemplateSignature getBase() {
		return (org.eclipse.uml2.uml.TemplateSignature)this.base;
	}

	@Override
    public List<org.modeldriven.alf.uml.TemplateParameter> getParameter() {
    	List<org.modeldriven.alf.uml.TemplateParameter> list = 
    			new ArrayList<org.modeldriven.alf.uml.TemplateParameter>();
    	for (org.eclipse.uml2.uml.TemplateParameter parameter: this.getBase().getParameters()) {
    		list.add((org.modeldriven.alf.uml.TemplateParameter)wrap(parameter));
    	}
    	return list;
    }
    
	@Override
    public List<org.modeldriven.alf.uml.TemplateParameter> getOwnedParameter() {
    	List<org.modeldriven.alf.uml.TemplateParameter> list = 
    			new ArrayList<org.modeldriven.alf.uml.TemplateParameter>();
    	for (org.eclipse.uml2.uml.TemplateParameter parameter: this.getBase().getOwnedParameters()) {
    		list.add((org.modeldriven.alf.uml.TemplateParameter)wrap(parameter));
    	}
    	return list;
    }
    
	@Override
    public void addOwnedParameter(org.modeldriven.alf.uml.TemplateParameter templateParameter) {
    	this.getBase().getOwnedParameters().add(((TemplateParameter)templateParameter).getBase());
    }

	@Override
    public org.modeldriven.alf.uml.TemplateableElement getTemplate() {
    	return (org.modeldriven.alf.uml.TemplateableElement)wrap(this.getBase().getTemplate());
    }

	@Override
	public void removeOwnedParameter(
			org.modeldriven.alf.uml.TemplateParameter ownedParameter) {
		if (this.getOwnedParameter().contains(ownedParameter)) {
			org.eclipse.uml2.uml.TemplateSignature base =
					this.getBase();
			org.eclipse.uml2.uml.TemplateParameter templateParameter = 
					((TemplateParameter)ownedParameter).getBase();
			base.getOwnedParameters().remove(templateParameter);
			base.getParameters().remove(templateParameter);
		}
	}

}

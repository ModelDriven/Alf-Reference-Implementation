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

public class OpaqueExpression extends ValueSpecification implements
		org.modeldriven.alf.uml.OpaqueExpression {
	
	public OpaqueExpression() {
		this(UMLFactory.eINSTANCE.createOpaqueExpression());
	}

	public OpaqueExpression(org.eclipse.uml2.uml.OpaqueExpression base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.OpaqueExpression getBase() {
		return (org.eclipse.uml2.uml.OpaqueExpression)this.getBase();
	}

	@Override
	public List<String> getLanguage() {
		List<String> list = new ArrayList<String>();
		for (String language: this.getBase().getLanguages()) {
			list.add(language);
		}
		return list;
	}

	@Override
	public List<String> getBody() {
		List<String> list = new ArrayList<String>();
		for (String language: this.getBase().getBodies()) {
			list.add(language);
		}
		return list;
	}

}

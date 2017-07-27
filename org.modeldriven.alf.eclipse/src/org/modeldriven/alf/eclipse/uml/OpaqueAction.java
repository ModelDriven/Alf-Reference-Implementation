/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class OpaqueAction extends Action implements
		org.modeldriven.alf.uml.OpaqueAction {
	
	public OpaqueAction() {
		this(UMLFactory.eINSTANCE.createOpaqueAction());
	}

	public OpaqueAction(org.eclipse.uml2.uml.OpaqueAction base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.OpaqueAction getBase() {
		return (org.eclipse.uml2.uml.OpaqueAction)this.base;
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

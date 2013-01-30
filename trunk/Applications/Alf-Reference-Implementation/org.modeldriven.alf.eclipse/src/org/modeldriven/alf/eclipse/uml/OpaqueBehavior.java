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

public class OpaqueBehavior extends Behavior implements
		org.modeldriven.alf.uml.OpaqueBehavior {
	public OpaqueBehavior() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createOpaqueBehavior());
	}

	public OpaqueBehavior(org.eclipse.uml2.uml.OpaqueBehavior base) {
		super(base);
	}

	public org.eclipse.uml2.uml.OpaqueBehavior getBase() {
		return (org.eclipse.uml2.uml.OpaqueBehavior) this.base;
	}

	public List<String> getBody() {
		List<String> list = new ArrayList<String>();
		for (String element : this.getBase().getBodies()) {
			list.add(element);
		}
		return list;
	}

	public void addBody(String body) {
		this.getBase().getBodies().add(body);
	}

	public List<String> getLanguage() {
		List<String> list = new ArrayList<String>();
		for (String element : this.getBase().getLanguages()) {
			list.add(element);
		}
		return list;
	}

	public void addLanguage(String language) {
		this.getBase().getLanguages().add(language);
	}

}

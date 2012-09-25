/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fumlri;

import java.util.ArrayList;
import java.util.List;

public class OpaqueBehavior extends Behavior implements
		org.modeldriven.alf.uml.OpaqueBehavior {
	public OpaqueBehavior() {
		this(new fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior());
	}

	public OpaqueBehavior(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior getBase() {
		return (fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior) this.base;
	}

	public List<String> getBody() {
		List<String> list = new ArrayList<String>();
		for (String element : this.getBase().body) {
			list.add(element);
		}
		return list;
	}

	public void addBody(String body) {
		this.getBase().body.add(body);
	}

	public List<String> getLanguage() {
		List<String> list = new ArrayList<String>();
		for (String element : this.getBase().language) {
			list.add(element);
		}
		return list;
	}

	public void addLanguage(String language) {
		this.getBase().language.add(language);
	}

}

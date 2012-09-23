/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public abstract class Action extends ExecutableNode implements
		org.modeldriven.alf.uml.Action {

	public Action(fUML.Syntax.Actions.BasicActions.Action base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.Action getBase() {
		return (fUML.Syntax.Actions.BasicActions.Action) this.base;
	}

	public List<org.modeldriven.alf.uml.OutputPin> getOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().output) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public org.modeldriven.alf.uml.Classifier getContext() {
		return (Classifier)this.wrap(this.getBase().context);
	}

	public List<org.modeldriven.alf.uml.InputPin> getInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (fUML.Syntax.Actions.BasicActions.InputPin element : this.getBase().input) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public boolean getIsLocallyReentrant() {
		return this.getBase().isLocallyReentrant;
	}

	public void setIsLocallyReentrant(boolean isLocallyReentrant) {
		this.getBase().setIsLocallyReentrant(isLocallyReentrant);
	}

}

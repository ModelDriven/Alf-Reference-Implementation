/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

import java.util.ArrayList;
import java.util.List;

public class Clause extends Element implements org.modeldriven.alf.uml.Clause {
	public Clause() {
		this(new fUML.Syntax.Activities.CompleteStructuredActivities.Clause());
	}

	public Clause(
			fUML.Syntax.Activities.CompleteStructuredActivities.Clause base) {
		super(base);
	}

	public fUML.Syntax.Activities.CompleteStructuredActivities.Clause getBase() {
		return (fUML.Syntax.Activities.CompleteStructuredActivities.Clause) this.base;
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getTest() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode element : this
				.getBase().test) {
			list.add((ExecutableNode)this.wrap(element));
		}
		return list;
	}

	public void addTest(org.modeldriven.alf.uml.ExecutableNode test) {
		this.getBase().addTest(test==null? null: ((ExecutableNode) test).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getBody() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode element : this
				.getBase().body) {
			list.add((ExecutableNode)this.wrap(element));
		}
		return list;
	}

	public void addBody(org.modeldriven.alf.uml.ExecutableNode body) {
		this.getBase().addBody(body==null? null: ((ExecutableNode) body).getBase());
	}

	public List<org.modeldriven.alf.uml.Clause> getPredecessorClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.Clause element : this
				.getBase().predecessorClause) {
			list.add(new Clause(element));
		}
		return list;
	}

	public void addPredecessorClause(
			org.modeldriven.alf.uml.Clause predecessorClause) {
		this.getBase().addPredecessorClause(
				((Clause) predecessorClause).getBase());
	}

	public List<org.modeldriven.alf.uml.Clause> getSuccessorClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.Clause element : this
				.getBase().successorClause) {
			list.add(new Clause(element));
		}
		return list;
	}

	public void addSuccessorClause(org.modeldriven.alf.uml.Clause successorClause) {
		successorClause.addPredecessorClause(this);
	}

	public org.modeldriven.alf.uml.OutputPin getDecider() {
		return (OutputPin)this.wrap(this.getBase().decider);
	}

	public void setDecider(org.modeldriven.alf.uml.OutputPin decider) {
		this.getBase().setDecider(decider==null? null: ((OutputPin) decider).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getBodyOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().bodyOutput) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addBodyOutput(org.modeldriven.alf.uml.OutputPin bodyOutput) {
		this.getBase().addBodyOutput(bodyOutput==null? null: ((OutputPin) bodyOutput).getBase());
	}

}

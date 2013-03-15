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

public class Clause extends Element implements org.modeldriven.alf.uml.Clause {
	public Clause() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createClause());
	}

	public Clause(org.eclipse.uml2.uml.Clause base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Clause getBase() {
		return (org.eclipse.uml2.uml.Clause) this.base;
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getTest() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode element : this.getBase()
				.getTests()) {
			list.add((org.modeldriven.alf.uml.ExecutableNode) wrap(element));
		}
		return list;
	}

	public void addTest(org.modeldriven.alf.uml.ExecutableNode test) {
		this.getBase().getTests().add(
				test == null ? null : ((ExecutableNode) test).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getBody() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode element : this.getBase()
				.getBodies()) {
			list.add((org.modeldriven.alf.uml.ExecutableNode) wrap(element));
		}
		return list;
	}

	public void addBody(org.modeldriven.alf.uml.ExecutableNode body) {
		this.getBase().getBodies().add(
				body == null ? null : ((ExecutableNode) body).getBase());
	}

	public List<org.modeldriven.alf.uml.Clause> getPredecessorClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (org.eclipse.uml2.uml.Clause element : this.getBase()
				.getPredecessorClauses()) {
			list.add((org.modeldriven.alf.uml.Clause) wrap(element));
		}
		return list;
	}

	public void addPredecessorClause(
			org.modeldriven.alf.uml.Clause predecessorClause) {
		this.getBase().getPredecessorClauses().add(
				predecessorClause == null ? null : ((Clause) predecessorClause)
						.getBase());
	}

	public List<org.modeldriven.alf.uml.Clause> getSuccessorClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (org.eclipse.uml2.uml.Clause element : this.getBase()
				.getSuccessorClauses()) {
			list.add((org.modeldriven.alf.uml.Clause) wrap(element));
		}
		return list;
	}

	public void addSuccessorClause(
			org.modeldriven.alf.uml.Clause successorClause) {
		this.getBase().getSuccessorClauses().add(
				successorClause == null ? null : ((Clause) successorClause)
						.getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getDecider() {
		return (org.modeldriven.alf.uml.OutputPin) wrap(this.getBase()
				.getDecider());
	}

	public void setDecider(org.modeldriven.alf.uml.OutputPin decider) {
		this.getBase().setDecider(
				decider == null ? null : ((OutputPin) decider).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getBodyOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getBodyOutputs()) {
			list.add((org.modeldriven.alf.uml.OutputPin) wrap(element));
		}
		return list;
	}

	public void addBodyOutput(org.modeldriven.alf.uml.OutputPin bodyOutput) {
		this.getBase().getBodyOutputs().add(
				bodyOutput == null ? null : ((OutputPin) bodyOutput).getBase());
	}

}

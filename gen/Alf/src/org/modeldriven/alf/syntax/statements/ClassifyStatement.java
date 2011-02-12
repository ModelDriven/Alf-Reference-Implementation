
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.ClassifyStatementImpl;

/**
 * A statement that changes the classification of an object.
 **/

public class ClassifyStatement extends Statement {

	private Expression expression = null;
	private QualifiedNameList fromList = null;
	private QualifiedNameList toList = null;
	private ArrayList<ElementReference> fromClass = null; // DERIVED
	private ArrayList<ElementReference> toClass = null; // DERIVED
	private Boolean isReclassifyAll = false;

	public ClassifyStatement() {
		this.impl = new ClassifyStatementImpl(this);
	}

	public ClassifyStatementImpl getImpl() {
		return (ClassifyStatementImpl) this.impl;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public QualifiedNameList getFromList() {
		return this.fromList;
	}

	public void setFromList(QualifiedNameList fromList) {
		this.fromList = fromList;
	}

	public QualifiedNameList getToList() {
		return this.toList;
	}

	public void setToList(QualifiedNameList toList) {
		this.toList = toList;
	}

	public ArrayList<ElementReference> getFromClass() {
		if (this.fromClass == null) {
			this.fromClass = this.getImpl().deriveFromClass();
		}
		return this.fromClass;
	}

	public ArrayList<ElementReference> getToClass() {
		if (this.toClass == null) {
			this.toClass = this.getImpl().deriveToClass();
		}
		return this.toClass;
	}

	public Boolean getIsReclassifyAll() {
		return this.isReclassifyAll;
	}

	public void setIsReclassifyAll(Boolean isReclassifyAll) {
		this.isReclassifyAll = isReclassifyAll;
	}

	/**
	 * The expression in a classify statement must have a class as its type and
	 * multiplicity upper bound of 1.
	 **/
	public boolean classifyStatementExpression() {
		return this.getImpl().classifyStatementExpression();
	}

	/**
	 * All qualified names listed in the from or to lists of a classify
	 * statement must resolve to classes.
	 **/
	public boolean classifyStatementClassNames() {
		return this.getImpl().classifyStatementClassNames();
	}

	/**
	 * All the from and to classes of a classify statement must be subclasses of
	 * the type of the target expression and none of them may have a common
	 * superclass that is a subclass of the type of the target expression (that
	 * is, they must be disjoint subclasses).
	 **/
	public boolean classifyStatementClasses() {
		return this.getImpl().classifyStatementClasses();
	}

	/**
	 * The assignments before the expression of a classify statement are the
	 * same as the assignments before the statement.
	 **/
	public boolean classifyStatementAssignmentsBefore() {
		return this.getImpl().classifyStatementAssignmentsBefore();
	}

	/**
	 * The assignments after a classify statement are the same as the
	 * assignments after its expression.
	 **/
	public boolean classifyStatementAssignmentsAfter() {
		return this.getImpl().classifyStatementAssignmentsAfter();
	}

	/**
	 * The from classes of a classify statement are the class referents of the
	 * qualified names in the from list for the statement.
	 **/
	public boolean classifyStatementFromClassDerivation() {
		return this.getImpl().classifyStatementFromClassDerivation();
	}

	/**
	 * The to classes of a classify statement are the class referents of the
	 * qualified names in the to list for the statement.
	 **/
	public boolean classifyStatementToClassDerivation() {
		return this.getImpl().classifyStatementToClassDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isReclassifyAll:");
		s.append(this.getIsReclassifyAll());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
		QualifiedNameList fromList = this.getFromList();
		if (fromList != null) {
			fromList.print(prefix + " ");
		}
		QualifiedNameList toList = this.getToList();
		if (toList != null) {
			toList.print(prefix + " ");
		}
		ArrayList<ElementReference> fromClass = this.getFromClass();
		if (fromClass != null) {
			for (ElementReference item : this.getFromClass()) {
				System.out.println(prefix + " /" + item);
			}
		}
		ArrayList<ElementReference> toClass = this.getToClass();
		if (toClass != null) {
			for (ElementReference item : this.getToClass()) {
				System.out.println(prefix + " /" + item);
			}
		}
	}
} // ClassifyStatement

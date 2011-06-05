
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A statement that changes the classification of an object.
 **/

public class ClassifyStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private Expression expression = null;
	private QualifiedNameList fromList = null;
	private QualifiedNameList toList = null;
	private Collection<ElementReference> fromClass = null; // DERIVED
	private Collection<ElementReference> toClass = null; // DERIVED
	private Boolean isReclassifyAll = false;

	public ClassifyStatementImpl(ClassifyStatement self) {
		super(self);
	}

	public ClassifyStatement getSelf() {
		return (ClassifyStatement) this.self;
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

	public Collection<ElementReference> getFromClass() {
		if (this.fromClass == null) {
			this.setFromClass(this.deriveFromClass());
		}
		return this.fromClass;
	}

	public void setFromClass(Collection<ElementReference> fromClass) {
		this.fromClass = fromClass;
	}

	public void addFromClass(ElementReference fromClass) {
		this.fromClass.add(fromClass);
	}

	public Collection<ElementReference> getToClass() {
		if (this.toClass == null) {
			this.setToClass(this.deriveToClass());
		}
		return this.toClass;
	}

	public void setToClass(Collection<ElementReference> toClass) {
		this.toClass = toClass;
	}

	public void addToClass(ElementReference toClass) {
		this.toClass.add(toClass);
	}

	public Boolean getIsReclassifyAll() {
		return this.isReclassifyAll;
	}

	public void setIsReclassifyAll(Boolean isReclassifyAll) {
		this.isReclassifyAll = isReclassifyAll;
	}

	protected Collection<ElementReference> deriveFromClass() {
		return null; // STUB
	}

	protected Collection<ElementReference> deriveToClass() {
		return null; // STUB
	}

	/**
	 * The expression in a classify statement must have a class as its type and
	 * multiplicity upper bound of 1.
	 **/
	public boolean classifyStatementExpression() {
		return true;
	}

	/**
	 * All qualified names listed in the from or to lists of a classify
	 * statement must resolve to classes.
	 **/
	public boolean classifyStatementClassNames() {
		return true;
	}

	/**
	 * All the from and to classes of a classify statement must be subclasses of
	 * the type of the target expression and none of them may have a common
	 * superclass that is a subclass of the type of the target expression (that
	 * is, they must be disjoint subclasses).
	 **/
	public boolean classifyStatementClasses() {
		return true;
	}

	/**
	 * The assignments before the expression of a classify statement are the
	 * same as the assignments before the statement.
	 **/
	public boolean classifyStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after a classify statement are the same as the
	 * assignments after its expression.
	 **/
	public boolean classifyStatementAssignmentsAfter() {
		return true;
	}

	/**
	 * The from classes of a classify statement are the class referents of the
	 * qualified names in the from list for the statement.
	 **/
	public boolean classifyStatementFromClassDerivation() {
		this.getSelf().getFromClass();
		return true;
	}

	/**
	 * The to classes of a classify statement are the class referents of the
	 * qualified names in the to list for the statement.
	 **/
	public boolean classifyStatementToClassDerivation() {
		this.getSelf().getToClass();
		return true;
	}

} // ClassifyStatementImpl

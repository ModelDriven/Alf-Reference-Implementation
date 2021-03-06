/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.ClassifyStatementImpl;

/**
 * A statement that changes the classification of an object.
 **/

public class ClassifyStatement extends Statement {

	public ClassifyStatement() {
		this.impl = new ClassifyStatementImpl(this);
	}

	public ClassifyStatement(Parser parser) {
		this();
		this.init(parser);
	}

	public ClassifyStatement(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ClassifyStatementImpl getImpl() {
		return (ClassifyStatementImpl) this.impl;
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public QualifiedNameList getFromList() {
		return this.getImpl().getFromList();
	}

	public void setFromList(QualifiedNameList fromList) {
		this.getImpl().setFromList(fromList);
	}

	public QualifiedNameList getToList() {
		return this.getImpl().getToList();
	}

	public void setToList(QualifiedNameList toList) {
		this.getImpl().setToList(toList);
	}

	public Collection<ElementReference> getFromClass() {
		return this.getImpl().getFromClass();
	}

	public void setFromClass(Collection<ElementReference> fromClass) {
		this.getImpl().setFromClass(fromClass);
	}

	public void addFromClass(ElementReference fromClass) {
		this.getImpl().addFromClass(fromClass);
	}

	public Collection<ElementReference> getToClass() {
		return this.getImpl().getToClass();
	}

	public void setToClass(Collection<ElementReference> toClass) {
		this.getImpl().setToClass(toClass);
	}

	public void addToClass(ElementReference toClass) {
		this.getImpl().addToClass(toClass);
	}

	public Boolean getIsReclassifyAll() {
		return this.getImpl().getIsReclassifyAll();
	}

	public void setIsReclassifyAll(Boolean isReclassifyAll) {
		this.getImpl().setIsReclassifyAll(isReclassifyAll);
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
     * the declared type of the target expression and none of them may have a
     * common superclass that is a subclass of the declared type of the target
     * expression (that is, they must be disjoint subclasses).
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

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getExpression());
        addExternalReferencesFor(references, this.getFromList());
        addExternalReferencesFor(references, this.getToList());
    }

	@Override
    public void _deriveAll() {
		this.getFromClass();
		this.getToClass();
		super._deriveAll();
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
		QualifiedNameList fromList = this.getFromList();
		if (fromList != null) {
			fromList.deriveAll();
		}
		QualifiedNameList toList = this.getToList();
		if (toList != null) {
			toList.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.classifyStatementExpression()) {
			violations.add(new ConstraintViolation(
					"classifyStatementExpression", this));
		}
		if (!this.classifyStatementClassNames()) {
			violations.add(new ConstraintViolation(
					"classifyStatementClassNames", this));
		}
		if (!this.classifyStatementClasses()) {
			violations.add(new ConstraintViolation("classifyStatementClasses",
					this));
		}
		if (!this.classifyStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"classifyStatementAssignmentsBefore", this));
		}
		if (!this.classifyStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"classifyStatementAssignmentsAfter", this));
		}
		if (!this.classifyStatementFromClassDerivation()) {
			violations.add(new ConstraintViolation(
					"classifyStatementFromClassDerivation", this));
		}
		if (!this.classifyStatementToClassDerivation()) {
			violations.add(new ConstraintViolation(
					"classifyStatementToClassDerivation", this));
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		QualifiedNameList fromList = this.getFromList();
		if (fromList != null) {
			fromList.checkConstraints(violations);
		}
		QualifiedNameList toList = this.getToList();
		if (toList != null) {
			toList.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" isReclassifyAll:");
		s.append(this.getIsReclassifyAll());
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
		QualifiedNameList fromList = this.getFromList();
		if (fromList != null) {
			System.out.println(prefix + " fromList:");
			fromList.print(prefix + "  ", includeDerived);
		}
		QualifiedNameList toList = this.getToList();
		if (toList != null) {
			System.out.println(prefix + " toList:");
			toList.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			Collection<ElementReference> fromClass = this.getFromClass();
			if (fromClass != null && fromClass.size() > 0) {
				System.out.println(prefix + " /fromClass:");
				for (Object _object : fromClass.toArray()) {
					ElementReference _fromClass = (ElementReference) _object;
					System.out.println(prefix + "  "
							+ _fromClass.toString(includeDerived));
				}
			}
		}
		if (includeDerived) {
			Collection<ElementReference> toClass = this.getToClass();
			if (toClass != null && toClass.size() > 0) {
				System.out.println(prefix + " /toClass:");
				for (Object _object : toClass.toArray()) {
					ElementReference _toClass = (ElementReference) _object;
					System.out.println(prefix + "  "
							+ _toClass.toString(includeDerived));
				}
			}
		}
	}
} // ClassifyStatement

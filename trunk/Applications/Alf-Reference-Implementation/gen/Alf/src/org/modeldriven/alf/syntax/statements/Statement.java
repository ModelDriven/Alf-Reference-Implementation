
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.StatementImpl;

/**
 * A model of an Alf statement.
 **/

public abstract class Statement extends DocumentedElement {

	public Statement() {
	}

	public Statement(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public Statement(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public StatementImpl getImpl() {
		return (StatementImpl) this.impl;
	}

	public Collection<Annotation> getAnnotation() {
		return this.getImpl().getAnnotation();
	}

	public void setAnnotation(Collection<Annotation> annotation) {
		this.getImpl().setAnnotation(annotation);
	}

	public void addAnnotation(Annotation annotation) {
		this.getImpl().addAnnotation(annotation);
	}

	public Collection<AssignedSource> getAssignmentBefore() {
		return this.getImpl().getAssignmentBefore();
	}

	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
		this.getImpl().setAssignmentBefore(assignmentBefore);
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.getImpl().addAssignmentBefore(assignmentBefore);
	}

	public Collection<AssignedSource> getAssignmentAfter() {
		return this.getImpl().getAssignmentAfter();
	}

	public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
		this.getImpl().setAssignmentAfter(assignmentAfter);
	}

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
		this.getImpl().addAssignmentAfter(assignmentAfter);
	}

	public Statement getEnclosingStatement() {
		return this.getImpl().getEnclosingStatement();
	}

	public void setEnclosingStatement(Statement enclosingStatement) {
		this.getImpl().setEnclosingStatement(enclosingStatement);
	}

	public Boolean getIsIsolated() {
		return this.getImpl().getIsIsolated();
	}

	public void setIsIsolated(Boolean isIsolated) {
		this.getImpl().setIsIsolated(isIsolated);
	}

	/**
	 * All the annotations of a statement must be allowed, as given by the
	 * annotationAllowed operation for the statement.
	 **/
	public boolean statementAnnotationsAllowed() {
		return this.getImpl().statementAnnotationsAllowed();
	}

	/**
	 * No name may be assigned more than once before or after a statement.
	 **/
	public boolean statementUniqueAssignments() {
		return this.getImpl().statementUniqueAssignments();
	}

	/**
	 * A statement is isolated if it has an @isolated annotation.
	 **/
	public boolean statementIsIsolatedDerivation() {
		return this.getImpl().statementIsIsolatedDerivation();
	}

	/**
	 * Returns true if the given annotation is allowed for this kind of
	 * statement. By default, only an @isolated annotation is allowed, with no
	 * arguments. This operation is redefined only in subclasses of Statement
	 * for kinds of statements that allow different annotations than this
	 * default.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public void _deriveAll() {
		this.getAssignmentBefore();
		this.getAssignmentAfter();
		this.getEnclosingStatement();
		this.getIsIsolated();
		super._deriveAll();
		Collection<Annotation> annotation = this.getAnnotation();
		if (annotation != null) {
			for (Object _annotation : annotation.toArray()) {
				((Annotation) _annotation).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.statementAnnotationsAllowed()) {
			violations.add(new ConstraintViolation(
					"statementAnnotationsAllowed", this));
		}
		if (!this.statementUniqueAssignments()) {
			violations.add(new ConstraintViolation(
					"statementUniqueAssignments", this));
		}
		if (!this.statementIsIsolatedDerivation()) {
			violations.add(new ConstraintViolation(
					"statementIsIsolatedDerivation", this));
		}
		Collection<Annotation> annotation = this.getAnnotation();
		if (annotation != null) {
			for (Object _annotation : annotation.toArray()) {
				((Annotation) _annotation).checkConstraints(violations);
			}
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isIsolated:");
			s.append(this.getIsIsolated());
		}
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Collection<Annotation> annotation = this.getAnnotation();
		if (annotation != null && annotation.size() > 0) {
			System.out.println(prefix + " annotation:");
			for (Object _object : annotation.toArray()) {
				Annotation _annotation = (Annotation) _object;
				if (_annotation != null) {
					_annotation.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		if (includeDerived) {
			Collection<AssignedSource> assignmentBefore = this
					.getAssignmentBefore();
			if (assignmentBefore != null && assignmentBefore.size() > 0) {
				System.out.println(prefix + " /assignmentBefore:");
				for (Object _object : assignmentBefore.toArray()) {
					AssignedSource _assignmentBefore = (AssignedSource) _object;
					System.out.println(prefix + "  "
							+ _assignmentBefore.toString(includeDerived));
				}
			}
		}
		if (includeDerived) {
			Collection<AssignedSource> assignmentAfter = this
					.getAssignmentAfter();
			if (assignmentAfter != null && assignmentAfter.size() > 0) {
				System.out.println(prefix + " /assignmentAfter:");
				for (Object _object : assignmentAfter.toArray()) {
					AssignedSource _assignmentAfter = (AssignedSource) _object;
					System.out.println(prefix + "  "
							+ _assignmentAfter.toString(includeDerived));
				}
			}
		}
		if (includeDerived) {
			Statement enclosingStatement = this.getEnclosingStatement();
			if (enclosingStatement != null) {
				System.out.println(prefix + " /enclosingStatement:"
						+ enclosingStatement.toString(includeDerived));
			}
		}
	}
} // Statement

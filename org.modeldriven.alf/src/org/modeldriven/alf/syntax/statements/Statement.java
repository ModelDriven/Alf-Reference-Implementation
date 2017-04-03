/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.StatementImpl;

/**
 * A model of an Alf statement.
 **/

public abstract class Statement extends DocumentedElement {

	@Override
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

    public Boolean getIsIndexFrom0() {
        return this.getImpl().getIsIndexFrom0();
    }
    
    public void setIsIndexFrom0(Boolean isIndexFrom0) {
        this.getImpl().setIsIndexFrom0(isIndexFrom0);
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
     * A statement has indexing from 0 if it has an @indexFrom0 annotation, or
     * it is contained in a statement with indexing from 0 and it does not have
     * an @indexFrom1 annotation applied.
     */
    public boolean statementIsIndexFrom0Derivation() {
        return this.getImpl().statementIsIndexFrom0Derivation();
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
	
	/**
     * Returns true if this statement is assured to generate a return value. By
     * default, a statement does not have a return value.
	 */
	public Boolean hasReturnValue() {
	    return this.getImpl().hasReturnValue();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getAnnotation());
    }

	@Override
    public void _deriveAll() {
		this.getAssignmentBefore();
		this.getAssignmentAfter();
		this.getEnclosingStatement();
		this.getIsIsolated();
		this.getIsIndexFrom0();
		super._deriveAll();
		Collection<Annotation> annotation = this.getAnnotation();
		if (annotation != null) {
			for (Object _annotation : annotation.toArray()) {
				((Annotation) _annotation).deriveAll();
			}
		}
	}

	@Override
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
        if (!this.statementIsIndexFrom0Derivation()) {
            violations.add(new ConstraintViolation(
                    "statementIsIndexFrom0Derivation", this));
        }
		Collection<Annotation> annotation = this.getAnnotation();
		if (annotation != null) {
			for (Object _annotation : annotation.toArray()) {
				((Annotation) _annotation).checkConstraints(violations);
			}
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isIsolated:");
			s.append(this.getIsIsolated());
			s.append(" /isIndexFrom0:");
			s.append(this.getIsIndexFrom0());
		}
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

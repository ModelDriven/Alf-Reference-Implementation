/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import java.util.List;
import org.modeldriven.alf.syntax.expressions.impl.InvocationExpressionImpl;

/**
 * An expression denoting the invocation of a behavior or operation, or the
 * sending of a signal.
 **/

public abstract class InvocationExpression extends Expression {

    public InvocationExpression() {
    }

    public InvocationExpression(Parser parser) {
        this();
        Token token = parser.getToken(0);
        if (token.next != null) {
            token = token.next;
        }
        this.setParserInfo(parser.getFileName(), token.beginLine, token.beginColumn);
    }

    public InvocationExpression(ParsedElement element) {
        this();
        this.setParserInfo(element.getFileName(), element.getLine(), element.getColumn());
    }

    public InvocationExpressionImpl getImpl() {
        return (InvocationExpressionImpl) this.impl;
    }

    public Boolean getIsBehavior() {
        return this.getImpl().getIsBehavior();
    }

    public void setIsBehavior(Boolean isBehavior) {
        this.getImpl().setIsBehavior(isBehavior);
    }

    public Boolean getIsAssociationEnd() {
        return this.getImpl().getIsAssociationEnd();
    }

    public void setIsAssociationEnd(Boolean isAssociationEnd) {
        this.getImpl().setIsAssociationEnd(isAssociationEnd);
    }

    public FeatureReference getFeature() {
        return this.getImpl().getFeature();
    }

    public void setFeature(FeatureReference feature) {
        this.getImpl().setFeature(feature);
    }

    public Tuple getTuple() {
        return this.getImpl().getTuple();
    }

    public void setTuple(Tuple tuple) {
        this.getImpl().setTuple(tuple);
    }

    public Boolean getIsOperation() {
        return this.getImpl().getIsOperation();
    }

    public void setIsOperation(Boolean isOperation) {
        this.getImpl().setIsOperation(isOperation);
    }

    public Boolean getIsDestructor() {
        return this.getImpl().getIsDestructor();
    }

    public void setIsDestructor(Boolean isDestructor) {
        this.getImpl().setIsDestructor(isDestructor);
    }

    public Boolean getIsImplicit() {
        return this.getImpl().getIsImplicit();
    }

    public void setIsImplicit(Boolean isImplicit) {
        this.getImpl().setIsImplicit(isImplicit);
    }

    public ElementReference getReferent() {
        return this.getImpl().getReferent();
    }

    public void setReferent(ElementReference referent) {
        this.getImpl().setReferent(referent);
    }

    public List<ElementReference> getParameter() {
        return this.getImpl().getParameter();
    }

    public void setParameter(List<ElementReference> parameter) {
        this.getImpl().setParameter(parameter);
    }

    public void addParameter(ElementReference parameter) {
        this.getImpl().addParameter(parameter);
    }

    public Boolean getIsSignal() {
        return this.getImpl().getIsSignal();
    }

    public void setIsSignal(Boolean isSignal) {
        this.getImpl().setIsSignal(isSignal);
    }

    public ElementReference getBoundReferent() {
        return this.getImpl().getBoundReferent();
    }

    public void setBoundReferent(ElementReference boundReferent) {
        this.getImpl().setBoundReferent(boundReferent);
    }

    /**
     * An invocation expression is a behavior invocation if its referent is a
     * behavior.
     **/
    public boolean invocationExpressionIsBehaviorDerivation() {
        return this.getImpl().invocationExpressionIsBehaviorDerivation();
    }

    /**
     * An invocation expression is an association end read if its referent is an
     * association end.
     **/
    public boolean invocationExpressionIsAssociationEndDerivation() {
        return this.getImpl().invocationExpressionIsAssociationEndDerivation();
    }

    /**
     * An invocation expression is an operation call if its referent is an
     * operation.
     **/
    public boolean invocationExpressionIsOperationDerivation() {
        return this.getImpl().invocationExpressionIsOperationDerivation();
    }

    /**
     * An invocation expression is a destructor call either implicitly or if it
     * is an explicit operation call to a destructor operation.
     **/
    public boolean invocationExpressionIsDestructorDerivation() {
        return this.getImpl().invocationExpressionIsDestructorDerivation();
    }

    /**
     * An invocation expression is an implicit object destruction if it has a
     * feature with the name "destroy" and no explicit referents.
     **/
    public boolean invocationExpressionIsImplicitDerivation() {
        return this.getImpl().invocationExpressionIsImplicitDerivation();
    }

    /**
     * An invocation expression is a signal send if its referent is a signal.
     **/
    public boolean invocationExpressionIsSignalDerivation() {
        return this.getImpl().invocationExpressionIsSignalDerivation();
    }

    /**
     * The parameters of an invocation expression are given by the result of the
     * parameterElements helper operation.
     **/
    public boolean invocationExpressionParameterDerivation() {
        return this.getImpl().invocationExpressionParameterDerivation();
    }

    /**
     * If the referent of an invocationExpression is an operation or behavior
     * with a return parameter, then the type of the expression is that of the
     * return parameter (if any). If the referent is a classifier, then the type
     * is the referent. If the referent is a property, then the type is that of
     * the property. Otherwise the expression has no type.
     **/
    public boolean invocationExpressionTypeDerivation() {
        return this.getImpl().invocationExpressionTypeDerivation();
    }

    /**
     * If the referent of an invocationExpression is an operation or behavior
     * with a return parameter, then the upper bound of the expression is that
     * of the return parameter. If the referent is a classifier, then the upper
     * bound is 1. If the referent is a property, then the upper bound is that
     * of the property. Otherwise the upper bound is 0.
     **/
    public boolean invocationExpressionUpperDerivation() {
        return this.getImpl().invocationExpressionUpperDerivation();
    }

    /**
     * If the referent of an invocationExpression is an operation or behavior
     * with a return parameter, then the lower bound of the expression is that
     * of the return parameter. If the referent is a classifier, then the lower
     * bound is 1. If the referent is a property, then the lower bound is that
     * of the property. Otherwise the lower bound is 0.
     **/
    public boolean invocationExpressionLowerDerivation() {
        return this.getImpl().invocationExpressionLowerDerivation();
    }

    /**
     * If the referent of an invocation expression is a template behavior, then
     * the bound referent is the implicit template binding of this template;
     * otherwise it is the same as the referent. For an implicit template
     * binding, the type arguments of for the template are inferred from the
     * types of the arguments for in and inout parameters of the template
     * behavior. If the resulting implicit template binding would not be a legal
     * binding of the template behavior, then the invocation expression has no
     * bound referent.
     */
    public boolean invocationExpressionBoundReferentDerivation() {
        return this.getImpl().invocationExpressionBoundReferentDerivation();
    }

    /**
     * The assignments before the target expression of the feature reference of
     * an invocation expression (if any) are the same as the assignments before
     * the invocation expression.
     **/
    public boolean invocationExpressionAssignmentsBefore() {
        return this.getImpl().invocationExpressionAssignmentsBefore();
    }
    
    /**
     * If the referent of the invocation expression is a template, then all of
     * its template parameters must be classifier template parameters. Note:
     * This allows for the possibility that the referent is not an Alf unit, in
     * which case it could have non-classifier template parameters.
     */
    public boolean invocationExpressionTemplateParameters() {
        return this.getImpl().invocationExpressionTemplateParameters();
    }
    
    /**
     * If invocation is a sequence feature invocation, then the assignments
     * after the tuple of the invocation expression must be the same as the
     * assignments before.
     */
    public boolean invocationExpressionAssignmentsAfter() {
        return this.getImpl().invocationExpressionAssignmentsAfter();
    }

    /**
     * Returns references to the elements that act as the parameters of the
     * referent. If the referent is a behavior or operation, these are the owned
     * parameters, in order. If the referent is an association end, then the
     * parameters are the other association ends of the association of the
     * referent end, which are treated as if they were in parameters. Otherwise
     * (by default), they are any properties of the referent (e.g., signal
     * attributes), which are treated as if they were in parameters. (This is
     * defined as a helper operation, so that it can be overridden by subclasses
     * of InvocationExpression, if necessary.)
     **/
    public List<ElementReference> parameterElements() {
        return this.getImpl().parameterElements();
    }

    /**
     * The assignments after an invocation expression are the same as those
     * after the tuple of the expression.
     **/
    public Collection<AssignedSource> updateAssignments() {
        return this.getImpl().updateAssignments();
    }

    public void _deriveAll() {
        this.getIsBehavior();
        this.getIsAssociationEnd();
        this.getFeature();
        this.getIsOperation();
        this.getIsDestructor();
        this.getIsImplicit();
        this.getReferent();
        this.getParameter();
        this.getIsSignal();
        this.getBoundReferent();
        super._deriveAll();
        Tuple tuple = this.getTuple();
        if (tuple != null) {
            tuple.deriveAll();
        }
    }

    public void checkConstraints(Collection<ConstraintViolation> violations) {
        super.checkConstraints(violations);
        if (!this.invocationExpressionIsBehaviorDerivation()) {
            violations
                    .add(new ConstraintViolation("invocationExpressionIsBehaviorDerivation", this));
        }
        if (!this.invocationExpressionIsAssociationEndDerivation()) {
            violations.add(new ConstraintViolation("invocationExpressionIsAssociationEndDerivation",
                    this));
        }
        if (!this.invocationExpressionIsOperationDerivation()) {
            violations.add(
                    new ConstraintViolation("invocationExpressionIsOperationDerivation", this));
        }
        if (!this.invocationExpressionIsDestructorDerivation()) {
            violations.add(
                    new ConstraintViolation("invocationExpressionIsDestructorDerivation", this));
        }
        if (!this.invocationExpressionIsImplicitDerivation()) {
            violations
                    .add(new ConstraintViolation("invocationExpressionIsImplicitDerivation", this));
        }
        if (!this.invocationExpressionIsSignalDerivation()) {
            violations.add(new ConstraintViolation("invocationExpressionIsSignalDerivation", this));
        }
        if (!this.invocationExpressionParameterDerivation()) {
            violations
                    .add(new ConstraintViolation("invocationExpressionParameterDerivation", this));
        }
        if (!this.invocationExpressionTypeDerivation()) {
            violations.add(new ConstraintViolation("invocationExpressionTypeDerivation", this));
        }
        if (!this.invocationExpressionUpperDerivation()) {
            violations.add(new ConstraintViolation("invocationExpressionUpperDerivation", this));
        }
        if (!this.invocationExpressionLowerDerivation()) {
            violations.add(new ConstraintViolation("invocationExpressionLowerDerivation", this));
        }
        if (!this.invocationExpressionBoundReferentDerivation()) {
            violations.add(new ConstraintViolation("invocationExpressionBoundReferentDerivation", this));
        }
        if (!this.invocationExpressionAssignmentsBefore()) {
            violations.add(new ConstraintViolation("invocationExpressionAssignmentsBefore", this));
        }
        if (!this.invocationExpressionTemplateParameters()) {
            violations.add(new ConstraintViolation("invocationExpressionTemplateParameters", this));
        }
        if (!this.invocationExpressionAssignmentsAfter()) {
            violations.add(new ConstraintViolation("invocationExpressionAssignmentsAfter", this));
        }
        Tuple tuple = this.getTuple();
        if (tuple != null) {
            tuple.checkConstraints(violations);
        }
    }

    public String _toString(boolean includeDerived) {
        StringBuffer s = new StringBuffer(super._toString(includeDerived));
        if (includeDerived) {
            s.append(" /isBehavior:");
            s.append(this.getIsBehavior());
        }
        if (includeDerived) {
            s.append(" /isAssociationEnd:");
            s.append(this.getIsAssociationEnd());
        }
        if (includeDerived) {
            s.append(" /isOperation:");
            s.append(this.getIsOperation());
        }
        if (includeDerived) {
            s.append(" /isDestructor:");
            s.append(this.getIsDestructor());
        }
        if (includeDerived) {
            s.append(" /isImplicit:");
            s.append(this.getIsImplicit());
        }
        if (includeDerived) {
            s.append(" /isSignal:");
            s.append(this.getIsSignal());
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
        if (includeDerived) {
            FeatureReference feature = this.getFeature();
            if (feature != null) {
                System.out.println(prefix + " /feature:" + feature.toString(includeDerived));
            }
        }
        Tuple tuple = this.getTuple();
        if (tuple != null) {
            System.out.println(prefix + " tuple:");
            tuple.print(prefix + "  ", includeDerived);
        }
        if (includeDerived) {
            ElementReference boundReferent = this.getBoundReferent();
            if (boundReferent != null) {
                System.out.println(prefix + " /boundReferent:" + boundReferent.toString(includeDerived));
            }
        }
        if (includeDerived) {
            List<ElementReference> parameter = this.getParameter();
            if (parameter != null && parameter.size() > 0) {
                System.out.println(prefix + " /parameter:");
                for (Object _object : parameter.toArray()) {
                    ElementReference _parameter = (ElementReference) _object;
                    System.out.println(prefix + "  " + _parameter.toString(includeDerived));
                }
            }
        }
    }
} // InvocationExpression

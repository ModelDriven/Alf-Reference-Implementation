
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A statement that declares the type of a local name and assigns it an initial
 * value.
 **/

public class LocalNameDeclarationStatementImpl extends StatementImpl {

	private String name = "";
	private Expression expression = null;
	private Boolean hasMultiplicity = false;
	private QualifiedName typeName = null;
	private ElementReference type = null; // DERIVED

	public LocalNameDeclarationStatementImpl(LocalNameDeclarationStatement self) {
		super(self);
	}

	public LocalNameDeclarationStatement getSelf() {
		return (LocalNameDeclarationStatement) this.self;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;		
        
        // Note: The following accounts for short form instance and sequence 
        // initialization expressions. It requires that the type name and
        // multiplicity be set before the initializer expression is set.
        LocalNameDeclarationStatement self = this.getSelf();
        if (this.expression instanceof InstanceCreationExpression) {
            InstanceCreationExpression initializer =
                (InstanceCreationExpression)this.expression;
            if (initializer.getConstructor() == null) {
                initializer.setConstructor(self.getTypeName());
            }
        } else if (this.expression instanceof SequenceConstructionExpression) {
            SequenceConstructionExpression initializer =
                (SequenceConstructionExpression)this.expression;
            if (initializer.getTypeName() == null) {
                initializer.setTypeName(self.getTypeName());
                initializer.setHasMultiplicity(self.getHasMultiplicity());
            }
        }
	}

	public Boolean getHasMultiplicity() {
		return this.hasMultiplicity;
	}

	public void setHasMultiplicity(Boolean hasMultiplicity) {
		this.hasMultiplicity = hasMultiplicity;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	public ElementReference getType() {
		if (this.type == null) {
			this.setType(this.deriveType());
		}
		return this.type;
	}

	public void setType(ElementReference type) {
		this.type = type;
	}

    /**
     * The type of a local name declaration statement with a type name is the
     * single classifier referent of the type name. Otherwise it is empty.
     **/
	protected ElementReference deriveType() {
	    LocalNameDeclarationStatement self = this.getSelf();
	    QualifiedName typeName = self.getTypeName();
	    if (typeName != null) {
	        return typeName.getImpl().getNonTemplateClassifierReferent();
	    } else {
	        return null;
	    }
	}
	
    /**
     * The assignments before the expression of a local name declaration
     * statement are the same as the assignments before the statement.
     *
     * The assignments after a local name declaration statement are the
     * assignments after the expression plus a new assignment for the local name
     * defined by the statement. The assigned source for the local name is the
     * local name declaration statement. The local name has the type denoted by
     * the type name if this is not empty and is untyped otherwise. If the
     * statement has multiplicity, then the multiplicity of the local name is
     * [0..*], otherwise it is [0..1].
     **/
	@Override
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
	    LocalNameDeclarationStatement self = this.getSelf();
	    Map<String, AssignedSource> assignmentsAfter = new HashMap<String, AssignedSource>();
	    Expression expression = self.getExpression();
	    if (expression == null) {
	        assignmentsAfter.putAll(super.deriveAssignmentAfter());
	    } else {
	        expression.getImpl().setAssignmentBefore(self.getImpl().getAssignmentBeforeMap());
	        assignmentsAfter.putAll(expression.getImpl().getAssignmentAfterMap());
	    }
	    String name = self.getName();
	    if (name != null) {
	        assignmentsAfter.put(name, 
	                AssignedSourceImpl.makeAssignment(
	                        name, self, self.getType(), 
	                        0, self.getHasMultiplicity()? -1: 1));
	    }
	    return assignmentsAfter;
	}
	
	/*
	 * Derivations
	 */

    public boolean localNameDeclarationStatementTypeDerivation() {
        this.getSelf().getType();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * The assignments before the expression of a local name declaration
	 * statement are the same as the assignments before the statement.
	 **/
	public boolean localNameDeclarationStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * If the type name in a local name declaration statement is not empty, then
	 * it must resolve to a non-template classifier and the expression must be
	 * assignable to that classifier.
	 **/
	public boolean localNameDeclarationStatementType() {
	    LocalNameDeclarationStatement self = this.getSelf();
	    Expression expression = self.getExpression();
	    if (expression == null) {
	        return false;
	    } else {
	        return self.getTypeName() == null || 
	            new AssignableLocalNameImpl(this).isAssignableFrom(expression);
	    }
	}

	/**
	 * The local name in a local name declaration statement must be unassigned
	 * before the statement and before the expression in the statement. It must
	 * remain unassigned after the expression.
	 **/
	public boolean localNameDeclarationStatementLocalName() {
	    LocalNameDeclarationStatement self = this.getSelf();
	    this.getAssignmentAfterMap();
	    String name = self.getName();
	    Expression expression = self.getExpression();
		return name != null && expression != null &&
		            this.getAssignmentBefore(name) == null &&
		            expression.getImpl().getAssignmentAfter(name) == null;
	}

	/**
	 * The assignments after a local name declaration statement are the
	 * assignments before the statement plus a new assignment for the local name
	 * defined by the statement. The assigned source for the local name is the
	 * local name declaration statement. The local name has the type denoted by
	 * the type name if this is not empty and is untyped otherwise. If the
	 * statement has multiplicity, then the multiplicity of the local name is
	 * [0..*], otherwise it is [0..1].
	 **/
	public boolean localNameDeclarationStatementAssignmentsAfter() {
        // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}

	/**
	 * If a local name declaration statement does not have multiplicity, then
	 * the multiplicity of upper bound of the assigned expression must not be
	 * greater than 1.
	 **/
	public boolean localNameDeclarationStatementExpressionMultiplicity() {
	    LocalNameDeclarationStatement self = this.getSelf();
	    Expression expression = self.getExpression();
	    return self.getHasMultiplicity() || expression == null || 
	            expression.getUpper() <= 1;
	}

	/*
	 * Helper Methods
	 */
	
	/**
	 * Return the assignment expression equivalent to this local name
	 * declaration statement.
	 */
    public AssignmentExpression getAssignmentExpression() {
        LocalNameDeclarationStatement self = this.getSelf();
        String name = self.getName();
        Expression expression = self.getExpression();
        
        QualifiedName target = new QualifiedName();
        target.getImpl().addName(name);
        
        NameLeftHandSide lhs = new NameLeftHandSide();
        lhs.setTarget(target);
        
        AssignmentExpression assignmentExpression = new AssignmentExpression();
        assignmentExpression.setOperator("=");
        assignmentExpression.setLeftHandSide(lhs);
        assignmentExpression.setRightHandSide(expression);
        
        assignmentExpression.getImpl().setAssignmentBefore(
                this.getAssignmentBeforeMap());
        assignmentExpression.deriveAll();
        
        return assignmentExpression;
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
        LocalNameDeclarationStatement self = this.getSelf();
        QualifiedName typeName = self.getTypeName();
        Expression expression = self.getExpression();
        if (typeName != null) {
            typeName.getImpl().setCurrentScope(currentScope);
        }
        if (expression != null) {
            expression.getImpl().setCurrentScope(currentScope);
        }
    }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof LocalNameDeclarationStatement) {
            LocalNameDeclarationStatement self = this.getSelf();
            LocalNameDeclarationStatement baseStatement = 
                (LocalNameDeclarationStatement)base;
            Expression expression = baseStatement.getExpression();
            QualifiedName typeName = baseStatement.getTypeName();
            self.setName(baseStatement.getName());
            if (expression != null) {
                self.setExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
            self.setHasMultiplicity(baseStatement.getHasMultiplicity());
            self.setTypeName(typeName.getImpl().
                    updateForBinding(templateParameters, templateArguments));
        }
    }
    
} // LocalNameDeclarationStatementImpl

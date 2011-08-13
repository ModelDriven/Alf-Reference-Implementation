
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
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The definition of a loop variable in a for statement.
 **/

public class LoopVariableDefinitionImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	private String variable = "";
	private Expression expression1 = null;
	private Expression expression2 = null;
	private QualifiedName typeName = null;
	private Boolean typeIsInferred = true;
	private Boolean isCollectionConversion = null; // DERIVED
	private ElementReference type = null; // DERIVED
	private Boolean isFirst = null; // DERIVED
	private Map<String, AssignedSource> assignmentBefore = null; // DERIVED
	private Map<String, AssignedSource> assignmentAfter = null; // DERIVED

	public LoopVariableDefinitionImpl(LoopVariableDefinition self) {
		super(self);
	}

	public LoopVariableDefinition getSelf() {
		return (LoopVariableDefinition) this.self;
	}

	public String getVariable() {
		return this.variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public Expression getExpression1() {
		return this.expression1;
	}

	public void setExpression1(Expression expression1) {
		this.expression1 = expression1;
	}

	public Expression getExpression2() {
		return this.expression2;
	}

	public void setExpression2(Expression expression2) {
		this.expression2 = expression2;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	public Boolean getTypeIsInferred() {
		return this.typeIsInferred;
	}

	public void setTypeIsInferred(Boolean typeIsInferred) {
		this.typeIsInferred = typeIsInferred;
	}

	public Boolean getIsCollectionConversion() {
		if (this.isCollectionConversion == null) {
			this.setIsCollectionConversion(this.deriveIsCollectionConversion());
		}
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
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

	public Boolean getIsFirst() {
		if (this.isFirst == null) {
			this.setIsFirst(this.deriveIsFirst());
		}
		return this.isFirst;
	}

	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

    public Collection<AssignedSource> getAssignmentBefore() {
        return this.getAssignmentBeforeMap().values();
    }
    
    public Map<String, AssignedSource> getAssignmentBeforeMap() {
        if (this.assignmentBefore == null) {
            this.setAssignmentBefore(this.deriveAssignmentBefore());
        }
        return this.assignmentBefore;
    }
    
    public AssignedSource getAssignmentBefore(String name) {
        this.getAssignmentBefore();
        return this.assignmentBefore.get(name);
    }

    public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
        this.setAssignmentBefore(new HashMap<String, AssignedSource>());
        for (AssignedSource assignment: assignmentBefore) {
            this.addAssignmentBefore(assignment);
        }
    }
    
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
        this.assignmentBefore = assignmentBefore;
    }

    public void addAssignmentBefore(AssignedSource assignmentBefore) {
        this.getAssignmentBefore();
        this.assignmentBefore.put(assignmentBefore.getName(), assignmentBefore);
    }

    public Collection<AssignedSource> getAssignmentAfter() {
        return this.getAssignmentAfterMap().values();
    }

    public Map<String, AssignedSource> getAssignmentAfterMap() {
        if (this.assignmentAfter == null) {
            this.setAssignmentAfter(this.deriveAssignmentAfter());
        }
        return this.assignmentAfter;
    }

    public AssignedSource getAssignmentAfter(String name) {
        this.getAssignmentAfter();
        return this.assignmentAfter.get(name);
    }

    public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
        this.assignmentAfter.clear();
        for (AssignedSource assignment: assignmentAfter) {
            this.addAssignmentBefore(assignment);
        }
    }

    public void setAssignmentAfter(Map<String, AssignedSource> assignmentAfter) {
        this.assignmentAfter = assignmentAfter;
    }

    public void addAssignmentAfter(AssignedSource assignmentAfter) {
        this.getAssignmentAfter();
        this.assignmentAfter.put(assignmentAfter.getName(), assignmentAfter);
    }

    /**
     * Collection conversion is required for a loop variable definition if the
     * type for the definition is the instantiation of a collection class and
     * the multiplicity upper bound of the first expression is no greater than
     * 1.
     **/
	protected Boolean deriveIsCollectionConversion() {
	    LoopVariableDefinition self = this.getSelf();
	    ElementReference type = self.getType();
	    Expression expression1 = self.getExpression1();
		return type != null && type.getImpl().isCollectionClass() &&
		            expression1 != null && expression1.getUpper() <= 1;
	}

    /**
     * If the type of a loop variable is not inferred, then the variable has the
     * type denoted by the type name, if it is not empty, and is untyped
     * otherwise. If the type is inferred, them the variable has the same as the
     * type of the expression in its definition.
     **/
	protected ElementReference deriveType() {
	    LoopVariableDefinition self = this.getSelf();
	    if (self.getTypeIsInferred()) {
	        Expression expression = self.getExpression1();
	        return expression == null? null: expression.getType();
	    } else {
	        return typeName == null? null: typeName.getImpl().getClassifierReferent();
	    }
	}

	/**
	 * isFirst defaults to false unless explicitly set otherwise.
	 */
	protected Boolean deriveIsFirst() {
		return false;
	}

	protected Map<String, AssignedSource> deriveAssignmentBefore() {
	    // Note: This should always be set externally.
		return new HashMap<String, AssignedSource>();
	}

    /**
     * The assignments before the expressions of a loop variable definition are
     * the assignments before the loop variable definition.
     *
	 * The assignments after a loop variable definition include the assignments
	 * after the expression (or expressions) of the definition plus a new
	 * assigned source for the loop variable itself. The assigned source for the
	 * loop variable is the loop variable definition. The multiplicity upper
	 * bound for the variable is 1. The multiplicity lower bound is 1 if the
	 * loop variable definition is the first in a for statement and 0 otherwise.
	 * If collection conversion is not required, then the variable has the
	 * inferred or declared type from the definition. If collection conversion
	 * is required, then the variable has the argument type of the collection
	 * class.
	 **/
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
	    LoopVariableDefinition self = this.getSelf();
	    String variable = self.getVariable();
	    Expression expression1 = self.getExpression1();
	    Expression expression2 = self.getExpression2();
	    Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
	    Map<String, AssignedSource> assignmentsAfter = assignmentsBefore;
	    if (expression1 != null) {
	        expression1.getImpl().setAssignmentBefore(assignmentsBefore);
	        assignmentsAfter = expression1.getImpl().getAssignmentAfterMap();
	        if (expression2 != null) {
	            expression2.getImpl().setAssignmentBefore(assignmentsBefore);
	            assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsAfter);
	            assignmentsAfter.putAll(expression2.getImpl().getAssignmentAfterMap());
	        }
	    }
	    if (variable != null) {
	        ElementReference type = this.getType();
	        if (self.getIsCollectionConversion()) {
	            type = type.getImpl().getCollectionArgument();
	        }
	        int lower = self.getIsFirst()? 1: 0;
            assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsAfter);
	        assignmentsAfter.put(variable, AssignedSourceImpl.makeAssignment(
	                variable, self, type, lower, 1));
	    }
	    return assignmentsAfter;
	}
	
	/*
	 * Derivations
	 */

	public boolean loopVariableDefinitionAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}
	
    public boolean loopVariableDefinitionTypeDerivation() {
        this.getSelf().getType();
        return true;
    }

    public boolean loopVariableDefinitionIsCollectionConversionDerivation() {
        this.getSelf().getIsCollectionConversion();
        return true;
    }

	/*
	 * Constraints
	 */

	/**
	 * The assignments before the expressions of a loop variable definition are
	 * the assignments before the loop variable definition.
	 **/
	public boolean loopVariableDefinitionAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * If a loop variable definition has two expressions, then both expressions
	 * must have type Integer and a multiplicity upper bound of 1, and no name
	 * may be newly assigned or reassigned in more than one of the expressions.
	 **/
	public boolean loopVariableDefinitionRangeExpressions() {
	    LoopVariableDefinition self = this.getSelf();
	    Expression expression1 = self.getExpression1();
	    Expression expression2 = self.getExpression2();
	    if (expression1 == null || expression2 == null) {
	        return true;
	    } else {
	        ElementReference type1 = expression1.getType();
	        ElementReference type2 = expression2.getType();
	        if (type1 == null || !type1.getImpl().isInteger() || 
	                expression1.getUpper() != 1 ||
	                type2 == null || !type2.getImpl().isInteger() ||
	                expression2.getUpper() != 1) {
	            return false;
	        } else {
	            this.getAssignmentBeforeMap(); // Force computation of assignments
	            Collection<AssignedSource> newAssignments = 
	                new ArrayList<AssignedSource>(expression1.getImpl().getNewAssignments());
	            newAssignments.retainAll(expression2.getImpl().getNewAssignments());
	            return newAssignments.isEmpty();
	        }
        }
	}

	/**
	 * If a loop variable definition has a type name, then this name must
	 * resolve to a non-template classifier.
	 **/
	public boolean loopVariableDefinitionTypeName() {
	    LoopVariableDefinition self = this.getSelf();
		return self.getTypeName() == null || self.getType() != null;
	}

	/**
	 * If the type of a loop variable definition is not inferred, then the first
	 * expression of the definition must have a type that conforms to the
	 * declared type.
	 **/
	public boolean loopVariableDefinitionDeclaredType() {
	    LoopVariableDefinition self = this.getSelf();
	    Expression expression = self.getExpression1();
		return self.getTypeIsInferred() || expression == null || 
		            expression.getType().getImpl().conformsTo(self.getType());
	}

	/**
	 * The variable name given in a loop variable definition must be unassigned
	 * after the expression or expressions in the definition.
	 **/
	public boolean loopVariableDefinitionVariable() {
	    LoopVariableDefinition self = this.getSelf();
	    String variable = self.getVariable();
	    Expression expression1 = self.getExpression1();
	    Expression expression2 = self.getExpression2();
	    if (variable == null || expression1 == null) {
	        return true;
	    } else {
	        this.getAssignmentAfterMap(); // Force computation of assignments.
	        return !(expression2 == null? expression1: expression2).getImpl().
	                    getAssignmentAfterMap().containsKey(variable);
	    }
	}
	
	/*
	 * Helper Methods
	 */

    public void setCurrentScope(NamespaceDefinition currentScope) {
        LoopVariableDefinition self = this.getSelf();
        Expression expression1 = self.getExpression1();
        Expression expression2 = self.getExpression2();
        if (expression1 != null) {
            expression1.getImpl().setCurrentScope(currentScope);
        }
        if (expression2 != null) {
            expression2.getImpl().setCurrentScope(currentScope);
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof LoopVariableDefinition) {
            LoopVariableDefinition self = this.getSelf();
            LoopVariableDefinition baseStatement = (LoopVariableDefinition)base;
            Expression expression1 = baseStatement.getExpression1();;
            Expression expression2 = baseStatement.getExpression2();;
            QualifiedName typeName = baseStatement.getTypeName();
            boolean typeIsInferred = baseStatement.getTypeIsInferred();
            
            self.setVariable(baseStatement.getVariable());
            if (expression1 != null) {
                self.setExpression1((Expression)expression1.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (expression2 != null) {
                self.setExpression2((Expression)expression2.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (typeName != null) {
                self.setTypeName(typeName.getImpl().
                        updateForBinding(templateParameters, templateArguments));
            }
            self.setTypeIsInferred(typeIsInferred);
        }
    }
    
} // LoopVariableDefinitionImpl

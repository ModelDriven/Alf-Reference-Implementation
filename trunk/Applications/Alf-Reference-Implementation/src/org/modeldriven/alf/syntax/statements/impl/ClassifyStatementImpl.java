
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A statement that changes the classification of an object.
 **/

public class ClassifyStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.StatementImpl {

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

    /**
     * The from classes of a classify statement are the class referents of the
     * qualified names in the from list for the statement.
     **/
	protected Collection<ElementReference> deriveFromClass() {
        QualifiedNameList fromList = this.getSelf().getFromList();
        return fromList == null? new ArrayList<ElementReference>():
            fromList.getImpl().getNonTemplateClassifierReferents();
	}
	
    /**
     * The to classes of a classify statement are the class referents of the
     * qualified names in the to list for the statement.
     **/
	protected Collection<ElementReference> deriveToClass() {
        QualifiedNameList toList = this.getSelf().getToList();
        return toList == null? new ArrayList<ElementReference>():
            toList.getImpl().getNonTemplateClassifierReferents();
	}
	
    /**
     * The assignments before the expression of a classify statement are the
     * same as the assignments before the statement.
     *
     * The assignments after a classify statement are the same as the
     * assignments after its expression.
     **/
    @Override
    public Map<String, AssignedSource> deriveAssignmentAfter() {
        Expression expression = this.getSelf().getExpression();
        if (expression == null) {
            return new HashMap<String, AssignedSource>();
        } else {
            expression.getImpl().setAssignmentBefore(this.getAssignmentBeforeMap());
            return expression.getImpl().getAssignmentAfterMap();
        }
    }

	/*
	 * Derivations
	 */

    public boolean classifyStatementFromClassDerivation() {
        this.getSelf().getFromClass();
        return true;
    }

    public boolean classifyStatementToClassDerivation() {
        this.getSelf().getToClass();
        return true;
    }
    
	/**
	 * The expression in a classify statement must have a class as its type and
	 * multiplicity upper bound of 1.
	 **/
	public boolean classifyStatementExpression() {
	    Expression expression = this.getSelf().getExpression();
	    ElementReference type = expression == null? null: expression.getType();
		return type != null && type.getImpl().isClass() &&
		            expression.getUpper() == 1;
	}

	/**
	 * All qualified names listed in the from or to lists of a classify
	 * statement must resolve to classes.
	 **/
	public boolean classifyStatementClassNames() {
	    ClassifyStatement self = this.getSelf();
	    QualifiedNameList fromList = self.getFromList();
	    QualifiedNameList toList = self.getToList();
	    Collection<QualifiedName> qualifiedNames = new ArrayList<QualifiedName>();
	    if (fromList != null) {
	        qualifiedNames.addAll(fromList.getName());
	    }
	    if (toList != null) {
	        qualifiedNames.addAll(toList.getName());
	    }
	    for (QualifiedName qualifiedName: qualifiedNames) {
	        ElementReference referent = qualifiedName.getImpl().getNonTemplateClassifierReferent();
	        if (referent == null || !referent.getImpl().isClass()) {
	            return false;
	        }
	    }
		return true;
	}

	/**
	 * All the from and to classes of a classify statement must be subclasses of
	 * the type of the target expression and none of them may have a common
	 * superclass that is a subclass of the type of the target expression (that
	 * is, they must be disjoint subclasses).
	 **/
	public boolean classifyStatementClasses() {
	    ClassifyStatement self = this.getSelf();
	    ElementReference targetType = self.getExpression().getType();
	    if (targetType != null ) {
	        Collection<ElementReference> fromClasses = self.getFromClass();
	        Collection<ElementReference> toClasses = self.getToClass();
	        Collection<ElementReference> classes = new ArrayList<ElementReference>();
	        if (fromClasses != null) {
	            classes.addAll(fromClasses);
	        }
	        if (toClasses != null) {
	            classes.addAll(toClasses);
	        }

	        // Check that all classes are subclasses of the target type.
	        Set<ElementReference> commonAncestors = new HashSet<ElementReference>();
	        boolean first = true;
	        for (ElementReference referent: classes) {
	            Collection<ElementReference> ancestors = referent.getImpl().allParents();
	            if (!targetType.getImpl().isContainedIn(ancestors)) {
	                return false;
	            }
	            if (first) {
	                commonAncestors.addAll(ancestors);
	            } else {
	                commonAncestors.retainAll(ancestors);
	            }
	        }

	        //Check that no common ancestors are subclasses of the target type.
	        for (ElementReference referent: commonAncestors) {
	            if (targetType.getImpl().isContainedIn(referent.getImpl().allParents())) {
	                return false;
	            }
	        }
	    }
		return true;
	}

	/**
	 * The assignments before the expression of a classify statement are the
	 * same as the assignments before the statement.
	 **/
	public boolean classifyStatementAssignmentsBefore() {
	    // Note: This is handled by setAssignmentAfter.
		return true;
	}

	/**
	 * The assignments after a classify statement are the same as the
	 * assignments after its expression.
	 **/
	public boolean classifyStatementAssignmentsAfter() {
        // Note: This is handled by setAssignmentAfter.
		return true;
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    ClassifyStatement self = this.getSelf();
	    Expression expression = self.getExpression();
	    QualifiedNameList fromList = self.getFromList();
	    QualifiedNameList toList = self.getToList();
	    if (expression != null) {
	        expression.getImpl().setCurrentScope(currentScope);
	    }
	    if (fromList != null) {
	        fromList.getImpl().setCurrentScope(currentScope);
	    }
        if (toList != null) {
            toList.getImpl().setCurrentScope(currentScope);
        }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ClassifyStatement) {
            ClassifyStatement self = this.getSelf();
            ClassifyStatement baseStatement = (ClassifyStatement)base;
            Expression expression = baseStatement.getExpression();
            QualifiedNameList fromList = baseStatement.getFromList();
            QualifiedNameList toList = baseStatement.getToList();
            if (expression != null) {
                self.setExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (fromList != null) {
                self.setFromList((QualifiedNameList)fromList.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (toList != null) {
                self.setToList((QualifiedNameList)toList.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // ClassifyStatementImpl

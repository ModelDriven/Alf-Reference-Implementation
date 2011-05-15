
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An expression that comprises a name reference.
 **/

public class NameExpressionImpl extends ExpressionImpl {

	private ElementReference enumerationLiteral = null; // DERIVED
	private AssignedSource assignment = null; // DERIVED
	private PropertyAccessExpression propertyAccess = null; // DERIVED
	private QualifiedName name = null;
	
	private NamespaceDefinition currentScope = null;

	public NameExpressionImpl(NameExpression self) {
		super(self);
	}

	public NameExpression getSelf() {
		return (NameExpression) this.self;
	}

    @Override
    public String toString() {
        NameExpression self = this.getSelf();
        return super.toString() + " name:" + self.getName().getImpl().getPathName();
    }
    
	public ElementReference getEnumerationLiteral() {
		if (this.enumerationLiteral == null) {
			this.setEnumerationLiteral(this.deriveEnumerationLiteral());
		}
		return this.enumerationLiteral;
	}

	public void setEnumerationLiteral(ElementReference enumerationLiteral) {
		this.enumerationLiteral = enumerationLiteral;
	}

	public AssignedSource getAssignment() {
		if (this.assignment == null) {
			this.setAssignment(this.deriveAssignment());
		}
		return this.assignment;
	}

	public void setAssignment(AssignedSource assignment) {
		this.assignment = assignment;
	}

	public PropertyAccessExpression getPropertyAccess() {
		if (this.propertyAccess == null) {
			this.setPropertyAccess(this.derivePropertyAccess());
		}
		return this.propertyAccess;
	}

	public void setPropertyAccess(PropertyAccessExpression propertyAccess) {
		this.propertyAccess = propertyAccess;
	}

	public QualifiedName getName() {
		return this.name;
	}

	public void setName(QualifiedName name) {
		this.name = name;
		if (this.name != null) {
		    this.name.getImpl().setContainingExpression(this.getSelf());
		}
	}

	/**
	 * If the name in a name expression resolves to an enumeration literal name,
	 * then that is the enumeration literal for the expression.
	 **/
	protected ElementReference deriveEnumerationLiteral() {
        QualifiedName name = this.getSelf().getName();
        return name == null || name.getIsFeatureReference()? null: 
                    name.getImpl().getEnumerationLiteralReferent();
	}

	/**
	 * If the name in a name expression is a local or parameter name, then its
	 * assignment is its assigned source before the expression.
	 **/
	protected AssignedSource deriveAssignment() {
        QualifiedName name = this.getSelf().getName();
        AssignedSource assignment = null;
        if (name != null && !name.getIsFeatureReference()) {
            ElementReference parameterReference = this.getParameter();
            NameBinding unqualifiedName = name.getUnqualifiedName();
            if (parameterReference != null && 
                    parameterReference.getImpl().isInNamespace(this.currentScope) || 
                    name.getQualification() == null && unqualifiedName != null) {
                String localName = unqualifiedName.getName();
                assignment = this.getAssignmentBefore(localName);
                if (assignment == null && parameterReference != null) {
                    FormalParameter parameter = parameterReference.getImpl().asParameter();
                    if (!"out".equals(parameter.getDirection())) {
                        assignment = AssignedSourceImpl.makeAssignment
                                        (localName, parameter, 
                                                parameter.getType(), 
                                                parameter.getLower(), 
                                                parameter.getUpper());
                    }
                }
            }
        }
        return assignment;
	}

	/**
	 * If the name in a name expression disambiguates to a feature reference,
	 * then the equivalent property access expression has the disambiguation of
	 * the name as its feature. The assignments before the property access
	 * expression are the same as those before the name expression.
	 **/
	protected PropertyAccessExpression derivePropertyAccess() {
        QualifiedName name = this.getSelf().getName();
        if (!name.getIsFeatureReference()) {
            return null;
        } else {
            PropertyAccessExpression propertyAccess = new PropertyAccessExpression();
            propertyAccess.setFeatureReference(name.getDisambiguation());
            // Note: Setting the assignments before is handled in updateAssignments.
            return propertyAccess;
        }
	}
	
	/**
	 * The type of a name expression is determined by its name. If the name is a
	 * local name, then the type of the name expression is the type of that 
	 * assignment. If the name is a parameter, then the type of the name
	 * expression is the type of that parameter. If the name is an enumeration
	 * literal, then the type of the name expression is the corresponding
	 * enumeration. If the name disambiguates to a feature reference, then the
	 * type of the name expression is the type of the equivalent property access
	 * expression.
	 **/
	@Override
	protected ElementReference deriveType() {
	    NameExpression self = this.getSelf();
	    AssignedSource assignment = self.getAssignment();
	    ElementReference parameter = this.getParameter();
	    ElementReference enumerationLiteral = self.getEnumerationLiteral();
	    PropertyAccessExpression propertyAccess = self.getPropertyAccess();
	    if (assignment != null) {
	        return assignment.getType();
	    } else if (parameter != null) {
	        return parameter.getImpl().getType();
	    } else if (enumerationLiteral != null) {
	        return enumerationLiteral.getImpl().getType();
	    } else if (propertyAccess != null) {
	        return propertyAccess.getType();
	    } else {
	        return null;
	    }
	}
	
    /**
	 * The multiplicity upper bound of a name expression is determined by its
	 * name.
	 **/
	@Override
	protected Integer deriveUpper() {
        NameExpression self = this.getSelf();
        AssignedSource assignment = self.getAssignment();
        ElementReference parameter = this.getParameter();
        ElementReference enumerationLiteral = self.getEnumerationLiteral();
        PropertyAccessExpression propertyAccess = self.getPropertyAccess();
        if (assignment != null) {
            return assignment.getUpper();
        } else if (parameter != null) {
            return parameter.getImpl().getUpper();
        } else if (enumerationLiteral != null) {
            return 1;
        } else if (propertyAccess != null) {
            return propertyAccess.getUpper();
        } else {
            return 0;
        }
	}
	
	/**
	 * The multiplicity lower bound of a name expression is determined by its
	 * name.
	 **/
    @Override
    protected Integer deriveLower() {
        NameExpression self = this.getSelf();
        AssignedSource assignment = self.getAssignment();
        ElementReference parameter = this.getParameter();
        ElementReference enumerationLiteral = self.getEnumerationLiteral();
        PropertyAccessExpression propertyAccess = self.getPropertyAccess();
        if (assignment != null) {
            return assignment.getLower();
        } else if (parameter != null) {
            return parameter.getImpl().getLower();
        } else if (enumerationLiteral != null) {
            return 1;
        } else if (propertyAccess != null) {
            return propertyAccess.getLower();
        } else {
            return 0;
        }
    }
    
	/*
	 * Derivations
	 */

	public boolean nameExpressionAssignmentDerivation() {
		this.getSelf().getAssignment();
		return true;
	}

	public boolean nameExpressionEnumerationLiteralDerivation() {
		this.getSelf().getEnumerationLiteral();
		return true;
	}

	public boolean nameExpressionPropertyAccessDerivation() {
		this.getSelf().getPropertyAccess();
		return true;
	}

	public boolean nameExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean nameExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean nameExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * If the name referenced by this expression is not a disambiguated feature
	 * reference or a local or parameter name, then it must resolve to exactly
	 * one enumeration literal.
	 **/
	public boolean nameExpressionResolution() {
	    NameExpression self = this.getSelf();
		return self.getPropertyAccess() != null || 
		       self.getAssignment() != null || 
		       self.getEnumerationLiteral() != null;
	}
	
	/*
	 * Helper Methods
	 */
	
    public ElementReference getParameter() {
        QualifiedName name = this.getSelf().getName();
        return name == null? null: name.getImpl().getParameterReferent();
    }

	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    this.currentScope = currentScope;
	    QualifiedName name = this.getSelf().getName();
	    if (name != null) {
	        name.getImpl().setCurrentScope(currentScope);
	    }
	}

} // NameExpressionImpl

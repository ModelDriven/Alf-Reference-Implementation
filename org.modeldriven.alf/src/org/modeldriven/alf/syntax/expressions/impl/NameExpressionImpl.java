/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	private boolean isAddTarget = false;

	public NameExpressionImpl(NameExpression self) {
		super(self);
	}

	public NameExpression getSelf() {
		return (NameExpression) this.self;
	}

    @Override
    public String toString(boolean includeDerived) {
        NameExpression self = this.getSelf();
        return super.toString(includeDerived) + " name:" + self.getName().getImpl().getPathName();
    }
    
    @Override
    public void addExternalReferences(Collection<ExternalElementReference> references) {
        super.addExternalReferences(references);
        NameExpression self = this.getSelf();
        SyntaxElement.addExternalReference(references, self.getEnumerationLiteral());
        SyntaxElement.addExternalReference(references, this.getParameter());
        SyntaxElement.addExternalReferencesFor(references, self.getPropertyAccess());
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
	
	// Force recomputation of assignment if assignments before change.
	@Override
    public void afterSetAssignmentBefore() {
        super.afterSetAssignmentBefore();
        this.getSelf().setAssignment(null);
    }

	/**
	 * If the source of a local name is a For Statement, it must be a @parallel
	 * local name and it is only legal if this name expression is the target of
	 * a CollectionFunctions::add invocation.
	 */
	@Override
	public AssignedSource getAssignmentBefore(String name) {
	    AssignedSource assignment = super.getAssignmentBefore(name);
	    if (assignment != null && 
	            assignment.getImpl().getIsParallelLocalName() && 
	            !this.isAddTarget) {
	        assignment = null;
	    }
	    return assignment;
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
                    if (!"out".equals(parameterReference.getImpl().getDirection())) {
                        assignment = AssignedSourceImpl.makeAssignment
                                        (localName, parameterReference, 
                                                parameterReference.getImpl().getType(), 
                                                parameterReference.getImpl().getLower(), 
                                                parameterReference.getImpl().getUpper());
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
	    NameExpression self = this.getSelf();
        QualifiedName name = self.getName();
        if (!name.getIsFeatureReference()) {
            return null;
        } else {
            PropertyAccessExpression propertyAccess = new PropertyAccessExpression(self);
            propertyAccess.setFeatureReference(name.getDisambiguation());
            // Note: Setting the assignments before is handled in updateAssignments.
            return propertyAccess;
        }
	}
	
	/**
     * The type of a name expression is determined by its name. If the name is a
     * local name or parameter with an assignment, then the type of the name
     * expression is the best known type of that assignment. If the name is an
     * enumeration literal, then the type of the name expression is the
     * corresponding enumeration. If the name disambiguates to a feature
     * reference, then the type of the name expression is the type of the
     * equivalent property access expression.
	 **/
	@Override
	protected ElementReference deriveType() {
	    NameExpression self = this.getSelf();
	    AssignedSource assignment = self.getAssignment();
	    ElementReference parameter = this.getParameter();
	    ElementReference enumerationLiteral = self.getEnumerationLiteral();
	    PropertyAccessExpression propertyAccess = self.getPropertyAccess();
	    if (assignment != null) {
	        return assignment.getKnownType();
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
    
    /**
     * If a name expression has a derived assignment, then its declared type is
     * the type of that assignment. Otherwise it is the same as the type of the
     * expression.
     */
    @Override
    public ElementReference declaredType() {
        NameExpression self = this.getSelf();
        AssignedSource assignment = self.getAssignment();
        return assignment != null? assignment.getType(): self.getType();
    }
    
    /**
     * The assignments after a name expression are the assignments after its
     * property access expression, if it has one, and otherwise are the same as
     * the assignments before the name expression.
     */
    @Override
    public Map<String, AssignedSource> updateAssignmentMap() {
        Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
        PropertyAccessExpression propertyAccess = this.getSelf().getPropertyAccess();
        if (propertyAccess != null) {
            propertyAccess.getImpl().setAssignmentBefore(assignments);
            assignments = propertyAccess.getImpl().getAssignmentAfterMap();
        }
        return assignments;
    }
    
    /**
     * If the name does not disambiguate to a feature reference, then it is
     * considered known null if the condition is true and known non-null if
     * the condition is false.
     */
    @Override
    public Map<String, AssignedSource> adjustMultiplicity(
            Map<String, AssignedSource> assignmentMap, boolean condition) {
        QualifiedName name = this.getSelf().getName();
        if (name != null && !name.getIsFeatureReference()) {
            AssignedSource assignment = 
                    assignmentMap.get(name.getUnqualifiedName().getName());
            if (assignment != null) {
                assignment = AssignedSourceImpl.makeAssignment(assignment);
                if (condition) {
                    assignment.setLower(0);
                } else if (assignment.getLower() == 0) {
                    assignment.setLower(1);
                }
                assignmentMap.put(assignment.getName(), assignment);
            }
        }
        return assignmentMap;
    }

    /**
     * If the name does not disambiguate to a feature reference, then it is
     * considered to have the given subtype.
     */
    @Override
    public Map<String, AssignedSource> adjustType(
            Map<String, AssignedSource> assignmentMap, ElementReference subtype) {
        QualifiedName name = this.getSelf().getName();
        if (name != null && !name.getIsFeatureReference()) {
            AssignedSource assignment = 
                    assignmentMap.get(name.getUnqualifiedName().getName());
            if (assignment != null) {
                assignment = AssignedSourceImpl.makeAssignment(assignment);
                assignment.getImpl().setProperSubtype(subtype);
                assignmentMap.put(assignment.getName(), assignment);
            }
        }
        return assignmentMap;
    }

    @Override
    public void setIsAddTarget() {
        this.isAddTarget = true;
    }

	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    this.currentScope = currentScope;
	    QualifiedName name = this.getSelf().getName();
	    if (name != null) {
	        name.getImpl().setCurrentScope(currentScope);
	    }
	}
	
	public NamespaceDefinition getCurrentScope() {
	    return this.currentScope;
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof NameExpression) {
            QualifiedName name = ((NameExpression)base).getName();
            if (name != null) {
                this.getSelf().setName(name.getImpl().
                        updateBindings(templateParameters, templateArguments));
            }
        }
    }
    
} // NameExpressionImpl

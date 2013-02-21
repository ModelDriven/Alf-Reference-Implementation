
/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.NameBinding;
import org.modeldriven.alf.syntax.expressions.PositionalTemplateBinding;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.ParameterableElement;
import org.modeldriven.alf.uml.TemplateParameter;
import org.modeldriven.alf.uml.TemplateSignature;

/**
 * A reference to a model element, either directly or via its Alf abstract
 * syntax representation. (NOTE: The definitions of all the helper operations of
 * ElementReference are specific to its subclasses.)
 **/

public abstract class ElementReferenceImpl {
    
    // Used as a non-null value to represent the "any" type.
    private static final ElementReference any = new InternalElementReference();
    
    private static Map<ParameterableElement, ElementReference> templateBindings = 
            new HashMap<ParameterableElement, ElementReference>();

	protected ElementReference self;

	public ElementReferenceImpl(ElementReference self) {
		this.self = self;
	}

	public ElementReference getSelf() {
		return (ElementReference) this.self;
	}
	
	@Override
	public String toString() {
	    return this.toString(false);
	}
	
    public void deriveAll() {
        this.getSelf()._deriveAll();
    }

	public abstract String toString(boolean includeDerived);
	
	/*
	 * Helper Methods
	 */
	
	public abstract SyntaxElement getAlf();
	public abstract Element getUml();
	
	public abstract boolean isNamespace();
	public abstract boolean isPackage();
    public abstract boolean isProfile();

	public abstract boolean isClassifier();
    public abstract boolean isAbstractClassifier();
    public abstract boolean isAssociation();
	public abstract boolean isClass();  
	public abstract boolean isClassOnly(); // But not any subtype of Class
    public abstract boolean isActiveClass();
	public abstract boolean isDataType();
	public abstract boolean isBehavior();
	public abstract boolean isActivity();
    public abstract boolean isEnumeration();
    public abstract boolean isPrimitive();
    public abstract boolean isSignal();
    public abstract boolean isStereotype();
    public abstract boolean isEnumerationLiteral();
    
    public abstract boolean isTemplate();
    public abstract boolean isClassifierTemplateParameter();
    public abstract boolean isCompletelyBound();

    public abstract boolean isPackageableElement();
    public abstract boolean isFeature();
    public abstract boolean isOrdered();
    public abstract boolean isUnique();
    public abstract boolean isOperation();
    public abstract boolean isConstructor();
    public abstract boolean isDestructor();
    public abstract boolean isReception();
    public abstract boolean isProperty();
    public abstract boolean isAssociationEnd();
    public abstract boolean isParameter();

    public abstract FormalParameter asParameter();
    public abstract NamespaceDefinition asNamespace();
    public abstract boolean isInNamespace(NamespaceDefinition namespace);

    public abstract boolean hasReceptionFor(ElementReference signal);
    
    public abstract Collection<ElementReference> parents();
    public abstract Collection<ElementReference> allParents();

    public abstract String getName();
    public abstract String getVisibility();
    public abstract List<ElementReference> getOwnedMembers();
    public abstract List<ElementReference> getMembers();
    public abstract List<Member> getPublicMembers(Collection<ElementReference> excluded);
    public abstract List<ElementReference> getAttributes();
    public abstract List<ElementReference> getAssociationEnds();
    public abstract List<Member> getInheritableMembers();
    public abstract List<FormalParameter> getParameters();
    public abstract FormalParameter getReturnParameter();
    public abstract List<ElementReference> getTemplateParameters();
    public abstract List<ElementReference> getTemplateActuals();
    public abstract ElementReference getParameteredElement();
    public abstract ElementReference getTemplate();
    public abstract Collection<ElementReference> getConstrainingClassifiers();
    public abstract ElementReference getType();
    public abstract ElementReference getAssociation();
    public abstract Integer getLower();
    public abstract Integer getUpper();
    public abstract ElementReference getClassifierBehavior();
    public abstract ElementReference getNamespace();
    public abstract Collection<ElementReference> getRedefinedElements();
    public abstract ElementReference getSignal();

    
    public QualifiedName getQualifiedName() {
        QualifiedName qualifiedName;
        if (this.getAlf() instanceof ModelNamespace) {
            qualifiedName = new QualifiedName();
        } else {
            ElementReference namespace = this.getNamespace();
            String name = this.getName();
            qualifiedName = 
                namespace != null? namespace.getImpl().getQualifiedName():
                name != null? new QualifiedName(): null;
            if (qualifiedName != null) {
                ElementReference template = this.getTemplate();
                if (template != null) {
                    name = template.getImpl().getName();
                }
                if (name == null) {
                    name = "";
                }
                NameBinding nameBinding = new NameBinding();
                nameBinding.setName(name);
                if (template != null) {
                    PositionalTemplateBinding templateBinding = 
                        new PositionalTemplateBinding();
                    for (ElementReference templateActual: this.getTemplateActuals()) {
                        templateBinding.addArgumentName(
                                templateActual == null? new QualifiedName():
                                templateActual.getImpl().getQualifiedName());
                        nameBinding.setBinding(templateBinding);
                    }                
                }
                qualifiedName.addNameBinding(nameBinding);
            }
        }
        return qualifiedName;
    }

    /**
     * Return the active class corresponding to an activity, if any.
     * This is either the activity itself, if it is active, or the class that
     * has the activity as a classifier behavior.
     */
    public abstract ElementReference getActiveClass();
    
    public boolean isActiveBehavior() {
        return this.getActiveClass() != null;
    }

    public boolean isCollectionClass() {
        ElementReference collectionClasses = 
            RootNamespace.getCollectionClassesPackage();
        if (collectionClasses == null) {
            return false;
        } else {
            ElementReference template = this.getTemplate();
            return template != null && 
                collectionClasses.getImpl().equals(template.getImpl().getNamespace());
        }
    }

    public boolean isIntegerCollection() {
        ElementReference collectionArgument = this.getCollectionArgument();
        return collectionArgument != null && 
                    collectionArgument.getImpl().isInteger();
    }
    
    public ElementReference getCollectionArgument() {
        ElementReference toSequenceOperation = this.getToSequenceOperation();
        return toSequenceOperation == null? null: 
            toSequenceOperation.getImpl().getType();
    }
    
    public ElementReference getToSequenceOperation() {
        NamespaceDefinition namespace = this.asNamespace();
        ElementReference toSequenceOperation = null;
        if (namespace != null) {
            for (Member member: namespace.getImpl().resolve("toSequence")) {
                ElementReference referent = member.getImpl().getReferent();
                if (referent.getImpl().isOperation() && 
                        referent.getImpl().getParameters().size() == 1 &&
                        referent.getImpl().getReturnParameter() != null) {
                    if (toSequenceOperation != null) {
                        return null;
                    }
                    toSequenceOperation = referent;
                }
            }
        }
        return toSequenceOperation;
    }

    public boolean isInteger() {
        return this.conformsTo(RootNamespace.getIntegerType());
    }

    public boolean isBoolean() {
        return this.conformsTo(RootNamespace.getBooleanType());
    }

    public boolean isString() {
        return this.conformsTo(RootNamespace.getStringType());
    }

    public boolean isUnlimitedNatural() {
        return this.conformsTo(RootNamespace.getUnlimitedNaturalType());
    }

    public boolean isBitString() {
        return this.conformsTo(RootNamespace.getBitStringType());
    }

    public boolean isNatural() {
        return this.conformsTo(RootNamespace.getNaturalType());
    }

    public boolean isNumeric() {
        return this.isInteger() || 
               this.isUnlimitedNatural() || 
               this.isNatural();
    }

    public abstract boolean conformsTo(ElementReference type);
    
    public boolean isContainedIn(Collection<ElementReference> references) {
        for (ElementReference reference: references) {
            if (this.equals(reference)) {
                return true;
            }
        }
        return false;
    }
    
    public static void addTemplateBinding(
            TemplateParameter templateParameter, 
            ElementReference templateArgument) {
        templateBindings.put(
                templateParameter.getParameteredElement(), 
                templateArgument == null? any: templateArgument);
    }
    
    public static void replaceTemplateBindingsIn(Element context) {
        Set<TemplateSignature> templateSignatures = 
                new HashSet<TemplateSignature>();
        for (ParameterableElement element: templateBindings.keySet()) {
            ElementReference reference = templateBindings.get(element);
            
            TemplateParameter templateParameter = 
                    element.getTemplateParameter();
            templateSignatures.add(
                    templateParameter.getSignature());
            templateParameter.setParameteredElement(null);
            templateParameter.setOwnedParameteredElement(null);
            
            if (reference == any) {
                context.replace(element, null);
            } else {
                FumlMapping mapping = ((ElementReferenceMapping)
                        FumlMapping.getMapping(reference)).getMapping();
                context.replace(element, mapping == null? null:
                    mapping.getElement());
            }
        }
        
        for (TemplateSignature signature: templateSignatures) {
            signature.getTemplate().setOwnedTemplateSignature(null);
        }
     }
    
    public static boolean isBound(TemplateParameter templateParameter) {
        return templateBindings.containsKey(
                templateParameter.getParameteredElement());
    }
    
    public static ElementReference makeElementReference(Element element) {
        return makeElementReference(element, null);
    }
    
    public static ElementReference makeElementReference(
            Element element, NamespaceDefinition namespace) {
        ElementReference reference = null;
        
        if (element != null) {
            reference = templateBindings.get(element);
            
            if (reference == null) {
                ExternalElementReference externalReference = 
                        new ExternalElementReference();
                externalReference.setElement(element);
                externalReference.getImpl().setNamespace(namespace);
                reference = externalReference;
            } else if (reference == any) {
                reference = null;
            }
        }
        
        return reference;
    }

} // ElementReferenceImpl

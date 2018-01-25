/*******************************************************************************
 * Copyright 2011-2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.AssignableElement;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.NameBinding;
import org.modeldriven.alf.syntax.expressions.PositionalTemplateBinding;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.expressions.impl.AssignableElementImpl;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.impl.BoundClassifierImpl;
import org.modeldriven.alf.syntax.units.impl.FormalParameterImpl;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.ParameterableElement;
import org.modeldriven.alf.uml.TemplateParameter;
import org.modeldriven.alf.uml.TemplateSignature;
import org.modeldriven.alf.uml.TemplateableElement;

/**
 * A reference to a model element, either directly or via its Alf abstract
 * syntax representation. (NOTE: The definitions of all the helper operations of
 * ElementReference are specific to its subclasses.)
 **/

public abstract class ElementReferenceImpl implements AssignableElement {
    
    // Used as a non-null value to represent the "any" type.
    public static final ElementReference any = new InternalElementReference();

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
	
	public ElementReference getReferent() {
	    return this.getSelf();
	}
	
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
    public abstract boolean isParameteredElement();
    public abstract boolean isCompletelyBound();
    public abstract boolean isTemplateBinding();

    public abstract boolean isNamedElement();
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
    public abstract boolean isImported();

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
    public abstract List<ElementReference> getParameters();
    public abstract ElementReference getReturnParameter();
    public abstract List<ElementReference> getMethods();
    public abstract ElementReference getSpecification(); // Note: An Operation is considered its own specification.
    
    public abstract List<ElementReference> getTemplateParameters();
    public abstract List<ElementReference> getTemplateActuals();
    public abstract List<ElementReference> getParameteredElements();
    public abstract ElementReference getParameteredElement();
    public abstract ElementReference getTemplate();
    public abstract Collection<ElementReference> getConstrainingClassifiers();
    public abstract ElementReference getTemplateBinding();
    
    public abstract ElementReference getType();
    public abstract ElementReference getAssociation();
    public abstract Integer getLower();
    public abstract Integer getUpper();
    public abstract String getDirection();
    public abstract ElementReference getClassifierBehavior();
    public abstract ElementReference getNamespace();
    public abstract Collection<ElementReference> getRedefinedElements();
    public abstract ElementReference getSignal();
    public abstract ElementReference getContext();
    
    public abstract Collection<Class<?>> getStereotypeMetaclasses();
    public abstract Class<?> getUMLMetaclass();
    
    public abstract List<ElementReference> resolveInScope(String name, boolean classifierOnly);
    
    public List<ElementReference> getEffectiveParameters() {
        if (this.isBehavior() || this.isOperation()) {
            return this.getParameters();
        } else {
            List<ElementReference> parameters = new ArrayList<ElementReference>();
            if (this.isReception()){
                ElementReference signal = this.getSignal();
                if (signal != null) {
                    for (ElementReference property: signal.getImpl().getAttributes()) {
                        parameters.add(parameterFromProperty(property).getImpl().getReferent());
                    }
                }
            } else if (this.isDataType() || this.isSignal()){
                for (ElementReference property: this.getAttributes()) {
                    parameters.add(parameterFromProperty(property).getImpl().getReferent());
                }
            } else if (this.isAssociation()) {
                for (ElementReference property: this.getAssociationEnds()) {
                    FormalParameter parameter = parameterFromProperty(property);
                    parameter.setLower(1);
                    parameter.setUpper(1);
                    parameters.add(parameter.getImpl().getReferent());
                }
            } else if (this.isAssociationEnd()) {
                ElementReference association = this.getAssociation();
                String referentName = this.getName();
                for (ElementReference property: association.getImpl().getAssociationEnds()) {
                    if (!property.getImpl().getName().equals(referentName)) {
                        FormalParameter parameter = parameterFromProperty(property);
                        parameter.setLower(1);
                        parameter.setUpper(1);
                        parameters.add(parameter.getImpl().getReferent());
                    }
                }
            }
            return parameters;
        }
    }

    public static FormalParameter parameterFromProperty(ElementReference property) {
        ElementReferenceImpl propertyImpl = property.getImpl();
        FormalParameter parameter = new FormalParameter();
        parameter.setName(propertyImpl.getName());
        parameter.setType(propertyImpl.getType());
        parameter.setLower(propertyImpl.getLower());
        parameter.setUpper(propertyImpl.getUpper());
        parameter.setDirection("in");
        return parameter;
    }
    
    public QualifiedName getQualifiedName() {
        QualifiedName qualifiedName;
        if (this.getAlf() instanceof ModelNamespace) {
            qualifiedName = new QualifiedName();
        } else {
            ElementReference template = this.getTemplate();
            if (template == null) {
                ElementReference namespace = this.getNamespace();
                qualifiedName = 
                    namespace == null? new QualifiedName(): 
                        namespace.getImpl().getQualifiedName();                    
                String name = this.getName();
                qualifiedName.getImpl().addName(name == null? "": name);
            } else {
                qualifiedName = template.getImpl().getQualifiedName();
                List<NameBinding> nameBindings = qualifiedName.getNameBinding();
                int n = nameBindings.size();
                if (n > 0) {
                    NameBinding nameBinding = nameBindings.get(n-1);
                    PositionalTemplateBinding templateBinding = 
                            new PositionalTemplateBinding();
                    for (ElementReference templateActual: this.getTemplateActuals()) {
                        templateBinding.addArgumentName(
                                templateActual == null? new QualifiedName():
                                    templateActual.getImpl().getQualifiedName());
                    }                
                    nameBinding.setBinding(templateBinding);
                }
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
    
    public boolean isOwnedBehavior() {
        return this.getContext() != null;
    }

    public boolean isCollectionFunction() {
        ElementReference collectionFunctions = 
            RootNamespace.getRootScope().getCollectionFunctionsPackage();
        return collectionFunctions != null && collectionFunctions.getImpl().equals(this.getNamespace());
    }

    public boolean isCollectionClass() {
        if (!this.isClass()) {
            return false;
        } else {
            ElementReference constructorType = this.getCollectionConstructorType();
            ElementReference sequenceType = this.getCollectionSequenceType();
            return constructorType != null && constructorType.getImpl().equals(sequenceType);
        }
    }

    public boolean isNaturalCollection() {
        ElementReference collectionArgument = this.getCollectionSequenceType();
        return collectionArgument != null && 
                    collectionArgument.getImpl().isNatural();
    }
    
    public boolean isIntegerCollection() {
        ElementReference collectionArgument = this.getCollectionSequenceType();
        return collectionArgument != null && 
                    collectionArgument.getImpl().isInteger();
    }
    
    public boolean isRealCollection() {
        ElementReference collectionArgument = this.getCollectionSequenceType();
        return collectionArgument != null && 
                    collectionArgument.getImpl().isReal();
    }
    
    public ElementReference getCollectionSequenceType() {
        ElementReference toSequenceOperation = this.getToSequenceOperation();
        return toSequenceOperation == null? null: 
            toSequenceOperation.getImpl().getType();
    }
    
    public ElementReference getCollectionConstructorType() {
        String name = this.getName();
        for (ElementReference ownedMember: this.getOwnedMembers()) {
            String memberName = ownedMember.getImpl().getName();
            if (memberName != null && memberName.equals(name) && 
                    ownedMember.getImpl().isConstructor()) {
                ElementReference inputParameter = null; 
                for (ElementReference parameter: ownedMember.getImpl().getParameters()) {
                    String direction = parameter.getImpl().getDirection();
                    if ("in".equals(direction) && inputParameter == null && 
                            parameter.getImpl().getLower() == 0 && 
                            parameter.getImpl().getUpper() == -1) {
                        inputParameter = parameter;
                    } else if (!"return".equals(direction)) {
                        inputParameter = null;
                        break;
                    }
                }
                if (inputParameter != null) {
                    return inputParameter.getImpl().getType();
                }
            }
        }
        return null;
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
        return this.conformsTo(RootNamespace.getRootScope().getIntegerType());
    }

    public boolean isBoolean() {
        return this.conformsTo(RootNamespace.getRootScope().getBooleanType());
    }

    public boolean isString() {
        return this.conformsTo(RootNamespace.getRootScope().getStringType());
    }

    public boolean isUnlimitedNatural() {
        return this.conformsTo(RootNamespace.getRootScope().getUnlimitedNaturalType());
    }

    public boolean isReal() {
        return this.conformsTo(RootNamespace.getRootScope().getRealType());
    }

    public boolean isBitString() {
        return this.conformsTo(RootNamespace.getRootScope().getBitStringType());
    }

    public boolean isNatural() {
        return this.conformsTo(RootNamespace.getRootScope().getNaturalType());
    }

    public boolean isIntegerOrReal() {
        return this.isInteger() || 
               this.isReal();
    }
    
    public boolean isAny() {
        return this.getSelf() == any;
    }

    public boolean conformsTo(ElementReference type) {
        if (!this.isClassifier() || type == null) {
            return false;
        } else if (type.getImpl().isAny()) {
            return true;
        } else if (!type.getImpl().isClassifier()) {
            return false;
        } else {
            ElementReference thisType = effectiveElementFor(this.getSelf());
            ElementReference otherType = effectiveElementFor(type);
            if (thisType.getImpl().equals(otherType)) {
                return true;
            } else {
                for (ElementReference parent: thisType.getImpl().parents()) {
                    if (parent.getImpl().conformsTo(otherType)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }
    
    public boolean isContainedIn(Collection<? extends ElementReference> references) {
        for (ElementReference reference: references) {
            if (this.equals(reference)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isAssignableFrom(ElementReference source) {
        return this.isAssignableFrom(source.getImpl());
    }
    
    public boolean isAssignableFrom(Expression source) {
        return this.isAssignableFrom(source.getImpl());
    }
    
    public boolean isAssignableFrom(AssignableElement source) {
        return AssignableElementImpl.isAssignable(this, source);
    }
    
    public boolean isSameKindAs(ElementReference other) {
        Class<?> metaclass = this.getUMLMetaclass();
        Class<?> otherMetaclass = other.getImpl().getUMLMetaclass();
        return (metaclass.isAssignableFrom(otherMetaclass) ||
                otherMetaclass.isAssignableFrom(metaclass)) &&
               (!this.isOperation() || FormalParameterImpl.match(
                       this.getParameters(), other.getImpl().getParameters()));
    }
    
    public ElementReference bind(List<ElementReference> actuals) {
        return BoundClassifierImpl.makeBoundClassifier(
                this.getSelf(), actuals).getImpl().getReferent();
    }
    
    public static void clearTemplateBindings() {        
        templateBindings.clear();
    }
    
    public static void addTemplateBinding(
            TemplateParameter templateParameter, 
            ElementReference templateArgument) {
        templateBindings.put(
                templateParameter.getParameteredElement(), 
                templateArgument);
    }
    
    public static Set<ParameterableElement> getTemplateBindingParameters() {
        return templateBindings.keySet();
    }
    
    public static void replaceTemplateBindingsIn(Element context) {
        Set<TemplateSignature> templateSignatures = 
                new HashSet<TemplateSignature>();
        List<ParameterableElement> elements = 
                new ArrayList<ParameterableElement>(templateBindings.keySet());
        List<Element> newElements = new ArrayList<Element>();
        for (ParameterableElement element: elements) {
            ElementReference reference = templateBindings.get(element);
            
            TemplateParameter templateParameter = 
                    element.getTemplateParameter();
            templateSignatures.add(
                    templateParameter.getSignature());
            templateParameter.setParameteredElement(null);
            templateParameter.setOwnedParameteredElement(null);
            
            Element newElement = null;
            if (reference != null && !reference.getImpl().isAny()) {
                FumlMapping mapping = ((ElementReferenceMapping)
                        FumlMapping.getMapping(reference)).getMapping();
                if (mapping != null) {
                    newElement = mapping.getElement();
                }
                if (newElement instanceof TemplateParameter) {
                    newElement = ((TemplateParameter)newElement).getParameteredElement();
                }
            }
            newElements.add(newElement);
        }

        context.replaceAll(elements, newElements);

        for (TemplateSignature signature: templateSignatures) {
            signature.getTemplate().setOwnedTemplateSignature(null);
        }
     }
    
    public static boolean isBound(TemplateParameter templateParameter) {
        return templateBindings.containsKey(
                templateParameter.getParameteredElement());
    }
    
    public static ElementReference makeElementReference(Element element) {
        ElementReference reference = null;
        
        if (element != null) {
            reference = templateBindings.get(element);
            
            if (reference == null) {
                ExternalElementReference externalReference = 
                        new ExternalElementReference();
                externalReference.setElement(element);
                reference = externalReference;
            }
        }
        
        return reference;
    }
    
    /**
     * For an element with template bindings, return a bound element reference.
     */
    public static ElementReference makeBoundReference(Element element) {
        ElementReference reference = makeElementReference(element);
        if (element instanceof TemplateableElement && 
                !((TemplateableElement)element).getTemplateBinding().isEmpty()) {
            ElementReference templateReferent = reference.getImpl().getTemplate();
            reference = templateReferent.getImpl().bind(reference.getImpl().getTemplateActuals());
        }
        return reference;
    }
    
    /**
     * For a reference to a type, return the "any" reference for the "null" type (i.e, "untyped").
     */
    public static ElementReference makeTypeReference(Element element) {
        return element == null? any: makeBoundReference(element);
    }
    
    /**
     * Get the expanded effective bound element corresponding to this element, if it
     * is a bound element.
     */
    public abstract ElementReference getEffectiveBoundElement();
    
    /**
     * Get the owned member of an effectively bound namespace that corresponds to the
     * given bound element reference.
     */
    public abstract ElementReference getEffectiveBoundElement(BoundElementReference Element);
    
    public static ElementReference effectiveElementFor(ElementReference reference) {
        if (reference == null) {
            return null;
        } else {
            ElementReference boundElement = reference.getImpl().getEffectiveBoundElement();
            return boundElement == null? reference: boundElement;
        }
    }

}

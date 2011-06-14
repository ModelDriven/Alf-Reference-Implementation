
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.ExternalParameter;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.ImportedMember;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;
import org.omg.uml.Activity;
import org.omg.uml.Association;
import org.omg.uml.Behavior;
import org.omg.uml.BehavioredClassifier;
import org.omg.uml.Class;
import org.omg.uml.Classifier;
import org.omg.uml.ClassifierTemplateParameter;
import org.omg.uml.DataType;
import org.omg.uml.Element;
import org.omg.uml.Enumeration;
import org.omg.uml.EnumerationLiteral;
import org.omg.uml.Feature;
import org.omg.uml.NamedElement;
import org.omg.uml.Namespace;
import org.omg.uml.Operation;
import org.omg.uml.Package;
import org.omg.uml.Parameter;
import org.omg.uml.ParameterableElement;
import org.omg.uml.Primitive;
import org.omg.uml.Profile;
import org.omg.uml.Property;
import org.omg.uml.Reception;
import org.omg.uml.Signal;
import org.omg.uml.Stereotype;
import org.omg.uml.TemplateBinding;
import org.omg.uml.TemplateParameter;
import org.omg.uml.TemplateParameterSubstitution;
import org.omg.uml.TemplateSignature;
import org.omg.uml.TemplateableElement;

/**
 * A direct reference to a UML model element.
 **/

public class ExternalElementReferenceImpl extends ElementReferenceImpl {

    private Element element = null;

	public ExternalElementReferenceImpl(ExternalElementReference self) {
		super(self);
	}

	@Override
	public ExternalElementReference getSelf() {
		return (ExternalElementReference) this.self;
	}
	
	@Override
	public String toString() {
	    return "ExternalElementReference " + 
	                this.getElement().getClass().getSimpleName() + 
	                " name:" + this.getName();
	}

    public Element getElement() {
        return this.element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public SyntaxElement getAlf() {
        return null;
    }

    @Override
    public Element getUml() {
        return this.getSelf().getElement();
    }

    @Override
    public boolean isNamespace() {
        return this.getSelf().getElement() instanceof Namespace;
    }

    @Override
    public boolean isPackage() {
        return this.getSelf().getElement() instanceof Package;
    }

    @Override
    public boolean isProfile() {
        return this.getSelf().getElement() instanceof Profile;
    }

    @Override
    public boolean isClassifier() {
        return this.getSelf().getElement() instanceof Classifier;
    }

    @Override
    public boolean isAbstractClassifier() {
        return this.isClassifier() &&
                ((Classifier)this.getSelf().getElement()).getIsAbstract();
    }

    @Override
    public boolean isAssociation() {
        return this.getSelf().getElement() instanceof Association;
    }

    @Override
    public boolean isClass() {
        return this.getSelf().getElement() instanceof Class;
    }

    @Override
    public boolean isClassOnly() {
        return this.getSelf().getElement().getClass() == Class.class;
    }

    @Override
    public boolean isActiveClass() {
        return this.isClass() && ((Class)this.getSelf().getElement()).getIsActive();
    }

    @Override
    public boolean isDataType() {
        return this.getSelf().getElement() instanceof DataType;
    }

    @Override
    public boolean isBehavior() {
        return this.getSelf().getElement() instanceof Behavior;
    }

    @Override
    public boolean isActivity() {
        return this.getSelf().getElement() instanceof Activity;
    }

    @Override
    public boolean isEnumeration() {
        return this.getSelf().getElement() instanceof Enumeration;
    }

    @Override
    public boolean isPrimitive() {
        return this.getSelf().getElement() instanceof Primitive;
    }

    @Override
    public boolean isSignal() {
        return this.getSelf().getElement() instanceof Signal;
    }

    @Override
    public boolean isStereotype() {
        return this.getSelf().getElement() instanceof Stereotype;
    }

    @Override
    public boolean isFeature() {
        return this.getSelf().getElement() instanceof Feature;
    }

    @Override
    public boolean isOperation() {
        return this.getSelf().getElement() instanceof Operation;
    }

    @Override
    public boolean isConstructor() {
        // TODO: Check for external constructors.
        return false;
    }
    
    @Override
    public boolean isDestructor() {
        // TODO: Check for external destructors.
        return false;
    }
    
    @Override
    public boolean isReception() {
        return this.getSelf().getElement() instanceof Reception;
    }

    @Override
    public boolean isEnumerationLiteral() {
        return this.getSelf().getElement() instanceof EnumerationLiteral;        
    }

    @Override
    public boolean isTemplate() {
        Element element = this.getSelf().getElement();
        return element instanceof TemplateableElement && 
                ((TemplateableElement)element).isTemplate();
    }

    @Override
    public boolean isClassifierTemplateParameter() {
        return this.getSelf().getElement() instanceof ClassifierTemplateParameter;
    }

    @Override
    public boolean isProperty() {
        return this.getSelf().getElement() instanceof Property;
    }
    
    @Override
    public boolean isAssociationEnd() {       
        return this.isProperty() && 
                ((Property)this.getSelf().getElement()).getAssociation() != null;
    }

    @Override
    public boolean isParameter() {
        return this.getSelf().getElement() instanceof Parameter;
    }

    @Override
    public FormalParameter asParameter() {
        if (this.isParameter()) {
            return new ExternalParameter((Parameter)this.getSelf().getElement());
        } else {
            return null;
        }
    }

    @Override
    public NamespaceDefinition asNamespace() {
        if (this.isNamespace()) {
            return new ExternalNamespace((Namespace)this.getSelf().getElement());
        } else {
            return null;
        }
    }
    
    @Override
    public boolean isInNamespace(NamespaceDefinition namespace) {
        Element element = this.getSelf().getElement();
        return namespace instanceof ExternalNamespace &&
                    element instanceof NamedElement &&
                    ((NamedElement)element).getNamespace() == 
                        ((ExternalNamespace)namespace).getUmlNamespace();
    }

    @Override
    public boolean hasReceptionFor(ElementReference signal) {
        Element umlSignal = signal.getImpl().getUml();
        if (umlSignal == null || !(umlSignal instanceof Signal) 
                || !this.isClass()) {
            return false;
        } else {
            Class class_ = (Class)this.getUml();
            for (Feature feature: class_.getFeature()) {
                if (feature instanceof Reception &&
                        ((Reception)feature).getSignal() == umlSignal) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Collection<ElementReference> parents() {
        if (this.isClassifier()) {
            return setOf(((Classifier)this.getSelf().getElement()).parents());
        } else {
            return new HashSet<ElementReference>();
        }
    }

    @Override
    public Collection<ElementReference> allParents() {
        if (this.isClassifier()) {
            return setOf(((Classifier)this.getSelf().getElement()).allParents());
        } else {
            return new HashSet<ElementReference>();
        }
    }
    
    private static Set<ElementReference> setOf(Set<Classifier> classifiers) {
        Set<ElementReference> references = new HashSet<ElementReference>();
        for (Classifier classifier: classifiers) {
            ExternalElementReference reference = new ExternalElementReference();
            reference.setElement(classifier);
            references.add(reference);
        }
        return references;
    }
    
    @Override
    public String getName() {
        Element element = this.getSelf().getElement();
        if (element instanceof TemplateParameter) {
            element = (Element)((TemplateParameter)element).getParameteredElement();
        }
        return element instanceof NamedElement?
                    ((NamedElement)element).getName(): null;
    }

    @Override
    public String getVisibility() {
        Element element = this.getSelf().getElement();
        if (!(element instanceof NamedElement)) {
            return null;
        } else {
            String visibility = ((NamedElement)element).getVisibility().toString();
            return visibility.substring(0,visibility.length()-1);
        }
    }

    @Override
    public List<Member> getPublicMembers() {
        List<Member> members = new ArrayList<Member>();
        if (this.isPackage()) {
            Package element = (Package)this.getSelf().getElement();
            for (NamedElement member: element.visibleMembers()) {
                for (String name: element.getNamesOfMember(member)) {
                    members.add(ImportedMemberImpl.makeImportedMember(name, member));
                }
            }
        }
        return members;
        
    }

    @Override
    public List<ElementReference> getFeatures() {
        List<ElementReference> features = new ArrayList<ElementReference>();
        if (this.isClassifier()) {
            for (Feature feature: ((Classifier)this.getSelf().getElement()).getFeature()) {
                ExternalElementReference reference = new ExternalElementReference();
                reference.setElement(feature);
                features.add(reference);
            }
        }
        return features;
    }

    @Override
    public List<ElementReference> getAttributes() {
        List<ElementReference> attributes = new ArrayList<ElementReference>();
        if (this.isClassifier()) {
            for (Property attribute: ((Classifier)this.getSelf().getElement()).getAttribute()) {
                ExternalElementReference reference = new ExternalElementReference();
                reference.setElement(attribute);
                attributes.add(reference);
            }
        }
        return attributes;
    }

    @Override
    public List<ElementReference> getAssociationEnds() {
        List<ElementReference> attributes = new ArrayList<ElementReference>();
        if (this.isAssociation()) {
            for (Property attribute: ((Association)this.getSelf().getElement()).getMemberEnd()) {
                ExternalElementReference reference = new ExternalElementReference();
                reference.setElement(attribute);
                attributes.add(reference);
            }
        }
        return attributes;
    }

    @Override
    public List<Member> getInheritableMembers() {
        List<Member> inheritableMembers = new ArrayList<Member>();
        if (this.isClassifier()) {
            Classifier classifier = (Classifier)this.getSelf().getElement();
            for (NamedElement element: classifier.inheritableMembers()) {
                for (String name: classifier.getNamesOfMember(element)) {
                    ImportedMember member = 
                        ImportedMemberImpl.makeImportedMember(name, element);
                    member.setIsFeature(element instanceof Feature);
                    inheritableMembers.add(member);
                }
            }
        }
        return inheritableMembers;
    }

    @Override
    public List<FormalParameter> getParameters() {
        List<Parameter> ownedParameters = null;
        if (this.isBehavior()) {
            ownedParameters = ((Behavior)this.getSelf().getElement()).getOwnedParameter();
        } else if (this.isOperation()) {
            ownedParameters = ((Operation)this.getSelf().getElement()).getOwnedParameter();
        } else {
            ownedParameters = new ArrayList<Parameter>();
        }
        List<FormalParameter> parameters = new ArrayList<FormalParameter>();
        for (Parameter parameter: ownedParameters) {
            parameters.add(new ExternalParameter(parameter));
        }
        return parameters;
    }

    @Override
    public FormalParameter getReturnParameter() {
        for (FormalParameter parameter: this.getParameters()) {
            if (parameter.getDirection().equals("return")) {
                return parameter;
            }
        }
        return null;
    }

    @Override
    public List<ElementReference> getTemplateParameters() {
        List<ElementReference> templateParameters = new ArrayList<ElementReference>();
        Element element = this.getSelf().getElement();
        if (element instanceof TemplateableElement) {
            TemplateSignature signature = ((TemplateableElement)element).getTemplateSignature();
            if (signature != null) {
                for (TemplateParameter parameter: signature.getParameter()) {
                    ExternalElementReference reference = new ExternalElementReference();
                    reference.setElement(parameter);
                    templateParameters.add(reference);
                }
            }
        }
        return templateParameters;
    }
    
    @Override
    public ElementReference getParameteredElement() {
        Element element = this.getSelf().getElement();
        if (!(element instanceof TemplateParameter)) {
            return null;
        } else {
            ParameterableElement parameteredElement = 
                ((TemplateParameter)element).getParameteredElement();
            ExternalElementReference reference = new ExternalElementReference();
            reference.setElement((Element)parameteredElement);
            return reference;
        }
    }
    
    @Override
    public ElementReference getTemplate() {
        Element element = this.getSelf().getElement();
        if (!(element instanceof TemplateableElement)) {
            return null;
        } else {
            TemplateBinding templateBinding = ((TemplateableElement)element).getTemplateBinding();
            TemplateSignature signature = templateBinding == null? null: 
                templateBinding.getSignature();
            if (signature == null) {
                return null;
            } else {
                ExternalElementReference reference = new ExternalElementReference();
                reference.setElement((Element)signature.getTemplate());
                return reference;
            }
        }
    }
    
    @Override 
    public List<ElementReference> getTemplateArguments() {
        Element element = this.getSelf().getElement();
        List<ElementReference> references = new ArrayList<ElementReference>();
        if (element instanceof TemplateableElement) {
            TemplateBinding templateBinding = ((TemplateableElement)element).getTemplateBinding();
            TemplateSignature signature = templateBinding == null? null: 
                templateBinding.getSignature();
            if (signature != null) {
                Collection<TemplateParameterSubstitution> substitutions = 
                    templateBinding.getParameterSubstitution();
                for (TemplateParameter parameter: signature.getParameter()) {
                    for (TemplateParameterSubstitution substitution: substitutions) {
                        ExternalElementReference reference = null;
                        if (substitution.getFormal() == parameter) {
                            reference = new ExternalElementReference();
                            reference.setElement((Element)substitution.getActual());                           
                        }
                        references.add(reference);
                    }                    
                }
            }
        }
        return references;
    }

    @Override
    public Collection<ElementReference> getConstrainingClassifiers() {
        Collection<ElementReference> constrainingClassifiers = new ArrayList<ElementReference>();
        if (this.isClassifierTemplateParameter()) {
            for (Classifier classifier: 
                ((ClassifierTemplateParameter)this.getSelf().getElement()).
                    getConstrainingClassifier()) {
                ExternalElementReference reference = new ExternalElementReference();
                reference.setElement(classifier);
                constrainingClassifiers.add(reference);
            }
        }
        return constrainingClassifiers;
    }

    @Override
    public ElementReference getType() {
        ExternalElementReference reference = new ExternalElementReference();
        if (this.isProperty()) {
            reference.setElement(((Property)this.getSelf().getElement()).getType());
            return reference;
        } else if (this.isParameter()) {
            reference.setElement(((Parameter)this.getSelf().getElement()).getType());
            return reference;
        } else if (this.isOperation()) {
            reference.setElement(((Operation)this.getSelf().getElement()).getType());
            return reference;
        } else if (this.isBehavior()) {
            FormalParameter parameter = this.getReturnParameter();
            return parameter == null? null: parameter.getType();
        } else if (this.isEnumerationLiteral()) {
            reference.setElement(((EnumerationLiteral)this.getSelf().getElement()).getEnumeration());
            return reference;
        } else {
            return null;
        }
    }

    @Override
    public ElementReference getAssociation() {
        if (!this.isAssociationEnd()) {
            return null;
        } else {
            Association association = 
                ((Property)this.getSelf().getElement()).getAssociation();
            ExternalElementReference reference = new ExternalElementReference();
            reference.setElement(association);
            return reference;
        }
    }

    public Integer getLower() {
        Integer lower = null;
        if (this.isProperty()) {
            lower = ((Property)this.getSelf().getElement()).getLower();
        } else if (this.isParameter()) {
            lower = ((Parameter)this.getSelf().getElement()).getLower();
        } else if (this.isOperation()) {
            lower = ((Operation)this.getSelf().getElement()).getLower();
        } else if (this.isBehavior()) {
            FormalParameter parameter = this.getReturnParameter();
            if (parameter != null) {
                lower = parameter.getLower();
            }
        }
        // Note: This will return 0 for an operation with no return parameter.
        return lower == null? 0: lower;
    }

    public Integer getUpper() {
        Integer upper = null;
        if (this.isProperty()) {
            upper = ((Property)this.getSelf().getElement()).getUpper();
        } else if (this.isParameter()) {
            upper = ((Parameter)this.getSelf().getElement()).getUpper();
        } else if (this.isOperation()) {
            upper = ((Operation)this.getSelf().getElement()).getUpper();
        } else if (this.isBehavior()) {
            FormalParameter parameter = this.getReturnParameter();
            if (parameter != null) {
                upper = parameter.getUpper();
            }
        }
        // Note: This will return 0 for an operation with no return parameter.
        return upper == null? 0: upper;
    }
    
    @Override
    public ElementReference getClassifierBehavior() {
        Element element = this.getSelf().getElement();
        if (!(element instanceof BehavioredClassifier)) {
            return null;
        } else {
             Behavior behavior =((BehavioredClassifier)element).getClassifierBehavior();
             if (behavior == null) {
                 return null;
             } else {
                 ExternalElementReference reference = new ExternalElementReference();
                 reference.setElement(behavior);
                 return reference;
             }
        }
    }

    @Override
    public ElementReference getNamespace() {
        Element element = this.getSelf().getElement();
        if (!(element instanceof NamedElement)) {
            return null;
        } else {
            Namespace namespace = ((NamedElement)element).getNamespace();
            if (namespace == null) {
                return null;
            } else {
                ExternalElementReference reference = new ExternalElementReference();
                reference.setElement(namespace);
                return reference;
            }
        }
    }
    
    @Override
    public ElementReference getActiveClass() {
        ExternalElementReference self = this.getSelf();
        if (!this.isActivity()) {
            return null;
        } else {
            Activity element = (Activity)self.getElement();
            if (element.getIsActive()) {
                return self;
            } else {
                BehavioredClassifier context = element.getContext();
                if (context != null && 
                        context.getClassifierBehavior() == element) {
                    ExternalElementReference reference = new ExternalElementReference();
                    reference.setElement(context);
                    return reference;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        Element element = null;
        if (object == null) {
            return false;
        } else {
            if (object instanceof ElementReference) {
                element = ((ElementReference)object).getImpl().getUml();
            } else if (object instanceof ElementReferenceImpl) {
                element = ((ElementReferenceImpl)object).getUml();
            } else if (object instanceof Element) {
                element = (Element)object;
            }
            return element != null && this.getSelf().getElement() == element;
        }
    }

    @Override
    public boolean conformsTo(ElementReference type) {
        if (!this.isClassifier()) {
            return false;
        } else if (type == null) {
            return true;
        } else if (!type.getImpl().isClassifier() || !(type instanceof ExternalElementReference)) {
            return false;
        } else {
            return ((Classifier)this.getSelf().getElement()).
                conformsTo((Classifier)((ExternalElementReference)type).getElement());
        }
    }

} // ExternalElementReferenceImpl

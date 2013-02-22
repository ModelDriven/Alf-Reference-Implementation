
/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;
import org.modeldriven.alf.uml.Activity;
import org.modeldriven.alf.uml.Association;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.BehavioredClassifier;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.ClassifierTemplateParameter;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Dependency;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Enumeration;
import org.modeldriven.alf.uml.EnumerationLiteral;
import org.modeldriven.alf.uml.Feature;
import org.modeldriven.alf.uml.MultiplicityElement;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Namespace;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Package;
import org.modeldriven.alf.uml.PackageableElement;
import org.modeldriven.alf.uml.Parameter;
import org.modeldriven.alf.uml.ParameterableElement;
import org.modeldriven.alf.uml.Primitive;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Property;
import org.modeldriven.alf.uml.Realization;
import org.modeldriven.alf.uml.Reception;
import org.modeldriven.alf.uml.RedefinableElement;
import org.modeldriven.alf.uml.Signal;
import org.modeldriven.alf.uml.Stereotype;
import org.modeldriven.alf.uml.StereotypeApplication;
import org.modeldriven.alf.uml.TemplateBinding;
import org.modeldriven.alf.uml.TemplateParameter;
import org.modeldriven.alf.uml.TemplateParameterSubstitution;
import org.modeldriven.alf.uml.TemplateSignature;
import org.modeldriven.alf.uml.TemplateableElement;
import org.modeldriven.alf.uml.TypedElement;

/**
 * A direct reference to a UML model element.
 **/

public class ExternalElementReferenceImpl extends ElementReferenceImpl {

    private Element element = null;
    private NamespaceDefinition namespace = null;

	public ExternalElementReferenceImpl(ExternalElementReference self) {
		super(self);
	}

	@Override
	public ExternalElementReference getSelf() {
		return (ExternalElementReference) this.self;
	}
	
	@Override
	public String toString(boolean includeDerived) {
	    return "ExternalElementReference " + this.getElement();
	}

    public Element getElement() {
        return this.element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
    
    public void setNamespace(NamespaceDefinition namespace) {
        this.namespace = namespace;
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
        Element element = this.getSelf().getElement();
        return element instanceof Classifier || 
                
               // For Alf, a ClassifierTemplateParameter is also considered to
               // be a Classifier.
               element instanceof ClassifierTemplateParameter;
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
        return this.getSelf().getElement() instanceof Class_;
    }

    @Override
    public boolean isClassOnly() {
        return this.isClass() && !this.isActivity();
    }

    @Override
    public boolean isActiveClass() {
        return this.isClassOnly() && 
                ((Class_)this.getSelf().getElement()).getIsActive();
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
    public boolean isPackageableElement() {
        return this.getSelf().getElement() instanceof PackageableElement;
    }

    @Override
    public boolean isFeature() {
        return this.getSelf().getElement() instanceof Feature;
    }
    
    @Override
    public boolean isOrdered() {
        Element element = this.getSelf().getElement();
        return element instanceof MultiplicityElement &&
            ((MultiplicityElement)element).getIsOrdered();
    }

    @Override
    public boolean isUnique() {
        Element element = this.getSelf().getElement();
        return element instanceof MultiplicityElement &&
            ((MultiplicityElement)element).getIsUnique();
    }

    @Override
    public boolean isOperation() {
        return this.getSelf().getElement() instanceof Operation;
    }

    @Override
    public boolean isConstructor() {
        if (!this.isOperation()) {
            return false;
        } else {
            ElementReference stereotype = RootNamespace.getCreateStereotype();
            return stereotype != null && 
                    this.isStereotypeApplied((Stereotype)stereotype.getImpl().getUml());
        }
    }
    
    @Override
    public boolean isDestructor() {
        if (!this.isOperation()) {
            return false;
        } else {
            ElementReference stereotype = RootNamespace.getDestroyStereotype();
            return stereotype != null && 
                    this.isStereotypeApplied((Stereotype)stereotype.getImpl().getUml());
        }
    }
    
    public boolean isStereotypeApplied(Stereotype stereotype) {
        return StereotypeApplication.isStereotypeApplied(
                this.getElement(), stereotype);
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
        if (element instanceof TemplateableElement) {
            TemplateableElement templateableElement = (TemplateableElement)element;
            if (templateableElement.isTemplate()) {
                for (TemplateParameter templateParameter: 
                    templateableElement.getOwnedTemplateSignature().getParameter()) {
                    if (!isBound(templateParameter)) {
                        return true;
                    }
                }
             }
        }
        return false;
    }

    @Override
    public boolean isClassifierTemplateParameter() {
        return this.getSelf().getElement() instanceof ClassifierTemplateParameter;
    }
    
    @Override
    public boolean isCompletelyBound() {
        return true;
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
        Element element = this.getSelf().getElement();
        if (element instanceof TemplateParameter) {
            element = ((TemplateParameter)element).getParameteredElement();
        }
        return !(element instanceof Namespace)? null: 
            ExternalNamespace.makeExternalNamespace(
                    (Namespace)element, this.namespace);
    }
    
    @Override
    public boolean isInNamespace(NamespaceDefinition namespace) {
        Element element = this.getSelf().getElement();
        return namespace instanceof ExternalNamespace &&
                    element instanceof NamedElement &&
                    ((NamedElement)element).getNamespace().equals( 
                        ((ExternalNamespace)namespace).getUmlNamespace());
    }

    @Override
    public boolean hasReceptionFor(ElementReference signal) {
        Element umlSignal = signal.getImpl().getUml();
        if (umlSignal == null || !(umlSignal instanceof Signal) 
                || !this.isClass()) {
            return false;
        } else {
            Class_ class_ = (Class_)this.getUml();
            for (NamedElement member: class_.getMember()) {
                if (member instanceof Reception &&
                        ((Reception)member).getSignal().equals(umlSignal)) {
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
            references.add(ElementReferenceImpl.makeElementReference(classifier));
        }
        return references;
    }
    
    @Override
    public String getName() {
        Element element = this.getSelf().getElement();
        if (element instanceof TemplateParameter) {
            element = ((TemplateParameter)element).getParameteredElement();
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
             return ((NamedElement)element).getVisibility();
        }
    }

    @Override
    public List<ElementReference> getOwnedMembers() {
        List<ElementReference> features = new ArrayList<ElementReference>();
        if (this.isClassifier()) {
            for (NamedElement member: ((Classifier)this.getSelf().getElement()).getOwnedMember()) {
                 features.add(ElementReferenceImpl.makeElementReference(member, this.asNamespace()));
            }
        }
        return features;
    }

    @Override
    public List<ElementReference> getMembers() {
        List<ElementReference> features = new ArrayList<ElementReference>();
        if (this.isClassifier()) {
            for (NamedElement member: ((Classifier)this.getSelf().getElement()).getMember()) {
               features.add(ElementReferenceImpl.makeElementReference(member, this.asNamespace()));
            }
        }
        return features;
    }

    @Override
    public List<Member> getPublicMembers(Collection<ElementReference> excluded) {
        List<Member> members = new ArrayList<Member>();
        if (this.isPackage()) {
            Package element = (Package)this.getSelf().getElement();
            for (NamedElement member: element.visibleMembers()) {
                for (String name: element.getNamesOfMember(member)) {
                    members.add(ImportedMemberImpl.makeImportedMember(
                            name, member, this.asNamespace()));
                }
            }
        }
        return members;       
    }

    @Override
    public List<ElementReference> getAttributes() {
        List<ElementReference> attributes = new ArrayList<ElementReference>();
        if (this.isClassifier()) {
            for (Property attribute: ((Classifier)this.getSelf().getElement()).getAttribute()) {
                attributes.add(ElementReferenceImpl.makeElementReference(attribute));
            }
        }
        return attributes;
    }

    @Override
    public List<ElementReference> getAssociationEnds() {
        List<ElementReference> attributes = new ArrayList<ElementReference>();
        if (this.isAssociation()) {
            for (Property attribute: ((Association)this.getSelf().getElement()).getMemberEnd()) {
                attributes.add(ElementReferenceImpl.makeElementReference(attribute, this.asNamespace()));
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
                        ImportedMemberImpl.makeImportedMember(
                                name, element, this.asNamespace());
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
            TemplateSignature signature = ((TemplateableElement)element).getOwnedTemplateSignature();
            if (signature != null) {
                for (TemplateParameter parameter: signature.getParameter()) {
                    if (!isBound(parameter)) {
                        templateParameters.add(ElementReferenceImpl.makeElementReference(
                                parameter, this.asNamespace()));
                    }
                }
            }
        }
        return templateParameters;
    }
    
    @Override
    public List<ElementReference> getTemplateActuals() {
        ArrayList<ElementReference> templateActuals = 
            new ArrayList<ElementReference>();
        TemplateBinding templateBinding = this.getTemplateBinding();
        if (templateBinding != null) {
            Collection<TemplateParameterSubstitution> parameterSubstitutions =
                    templateBinding.getParameterSubstitution();
            for (TemplateParameter formal: 
                templateBinding.getSignature().getParameter()) {
                ElementReference templateActual = null;
                for (TemplateParameterSubstitution parameterSubstitution: 
                    parameterSubstitutions) {
                    if (parameterSubstitution.getFormal().equals(formal)) {
                        templateActual = ElementReferenceImpl.makeElementReference(
                                parameterSubstitution.getActual());
                        break;
                    }
                }
                templateActuals.add(templateActual);
            }
        }
        return templateActuals;
    }
    
    @Override
    public ElementReference getParameteredElement() {
        Element element = this.getSelf().getElement();
        if (!(element instanceof TemplateParameter)) {
            return null;
        } else {
            ParameterableElement parameteredElement = 
                ((TemplateParameter)element).getParameteredElement();
            return ElementReferenceImpl.makeElementReference(parameteredElement);
        }
    }
    
    @Override
    public ElementReference getTemplate() {
        TemplateBinding templateBinding = this.getTemplateBinding();
        TemplateSignature signature = templateBinding == null? null: 
            templateBinding.getSignature();
        return signature == null? null:
            ElementReferenceImpl.makeElementReference(signature.getTemplate());
    }
    
    public TemplateBinding getTemplateBinding() {
        return getTemplateBinding(this.getSelf().getElement());
    }
    
    public static TemplateBinding getTemplateBinding(Element element) {
        TemplateBinding templateBinding = null;
        if (element instanceof TemplateableElement) {
            List<TemplateBinding> templateBindings = 
                    ((TemplateableElement)element).getTemplateBinding();
            if (!templateBindings.isEmpty()) {
                templateBinding = templateBindings.get(0);
            } else if (element instanceof NamedElement) {
                for (Dependency dependency: 
                    ((NamedElement)element).getClientDependency()) {
                    if (dependency instanceof Realization) {
                        NamedElement supplier = dependency.getSupplier();
                        if (supplier instanceof TemplateableElement) {
                            templateBindings = ((TemplateableElement)supplier).
                                    getTemplateBinding();
                            if (!templateBindings.isEmpty()) {
                                templateBinding = templateBindings.get(0);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return templateBinding;       
    }
    
    @Override
    public Collection<ElementReference> getConstrainingClassifiers() {
        Collection<ElementReference> constrainingClassifiers = 
                new ArrayList<ElementReference>();
        if (this.isClassifierTemplateParameter()) {
            for (Classifier classifier: 
                ((ClassifierTemplateParameter)this.getSelf().getElement()).
                    getConstrainingClassifier()) {
                constrainingClassifiers.add(
                        ElementReferenceImpl.makeElementReference(classifier));
            }
        }
        return constrainingClassifiers;
    }

    @Override
    public ElementReference getType() {
        if (this.isProperty() || this.isParameter()) {
            return ElementReferenceImpl.makeElementReference(
                    ((TypedElement)this.getSelf().getElement()).getType());
        } else if (this.isOperation()) {
            return ElementReferenceImpl.makeElementReference(
                    ((Operation)this.getSelf().getElement()).getType());
        } else if (this.isBehavior()) {
            FormalParameter parameter = this.getReturnParameter();
            return parameter == null? null: parameter.getType();
        } else if (this.isEnumerationLiteral()) {
            return ElementReferenceImpl.makeElementReference(
                    ((EnumerationLiteral)this.getSelf().getElement()).getEnumeration());
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
            return ElementReferenceImpl.makeElementReference(association);
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
                 return ElementReferenceImpl.makeElementReference(behavior);
             }
        }
    }

    @Override
    public ElementReference getNamespace() {
        Element element = this.getSelf().getElement();
        if (!(element instanceof NamedElement)) {
            return null;
        } else if (this.namespace != null) {
            return this.namespace.getImpl().getReferent();            
        } else {
            return ElementReferenceImpl.makeElementReference(((NamedElement)element).getNamespace());
        }
    }
    
    @Override
    public Collection<ElementReference> getRedefinedElements() {
        Element element = this.getSelf().getElement();
        if (!(element instanceof RedefinableElement)) {
            return new ArrayList<ElementReference>();
        } else {
            Collection<ElementReference> redefinableElements = 
                new ArrayList<ElementReference>();
            for (RedefinableElement redefinableElement: 
                ((RedefinableElement)element).getRedefinedElement()) {
                redefinableElements.add(ElementReferenceImpl.makeElementReference(
                        redefinableElement));
            }
            return redefinableElements;
        }
    }
    
    @Override
    public ElementReference getSignal() {
        if (!this.isReception()) {
            return null;
        } else {
            return ElementReferenceImpl.makeElementReference(
                    ((Reception)this.getElement()).getSignal());
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
                        context.getClassifierBehavior().equals(element)) {
                    return ElementReferenceImpl.makeElementReference(context);
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
            return element != null && this.getSelf().getElement().equals(element);
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

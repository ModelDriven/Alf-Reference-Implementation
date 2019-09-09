/*******************************************************************************
 * Copyright 2011-2019 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.common.impl.ExternalElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.BehavioralFeature;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.InstanceSpecification;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Namespace;
import org.modeldriven.alf.uml.Package;
import org.modeldriven.alf.uml.PackageableElement;
import org.modeldriven.alf.uml.Parameter;
import org.modeldriven.alf.uml.ParameterableElement;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Signal;
import org.modeldriven.alf.uml.SignalEvent;
import org.modeldriven.alf.uml.Stereotype;
import org.modeldriven.alf.uml.TemplateParameter;
import org.modeldriven.alf.uml.TemplateableElement;

public class ExternalNamespaceImpl extends NamespaceDefinitionImpl {

    public ExternalNamespaceImpl(ExternalNamespace self) {
        super(self);
    }
    
    @Override
    public ExternalNamespace getSelf() {
        return (ExternalNamespace) this.self;
    }
    
    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }
    
    @Override
    public String getName() {
        return this.getSelf().getUmlNamespace().getName();
    }
    
    @Override
    public void setName(String name) {
        this.getSelf().setName(name);
    }
    
    @Override
    public void setExactName(String name) {
        this.getSelf().getUmlNamespace().setName(name);
    }
    
    @Override
    public List<Member> getOwnedMember() {
        ExternalNamespace self = this.getSelf();
        List<Member> ownedMembers = this.getTemplateParameterMembers();
        for (NamedElement element: self.getUmlNamespace().getOwnedMember()) {
            ownedMembers.add(ImportedMemberImpl.makeImportedMember(
                    element.getName(), element, self));
        }
        return ownedMembers;
    }
    
    @Override
    public void addOwnedMember(Member ownedMember) {
        NamedElement element = 
                (NamedElement)ownedMember.getImpl().getReferent().getImpl().getUml();
        if (element != null) {
            Namespace umlNamespace = this.getSelf().getUmlNamespace();
            if (umlNamespace instanceof Package) {
                if (element instanceof PackageableElement) {
                    ((Package)umlNamespace).addPackagedElement(
                            (PackageableElement)element);
                }
            } else if (umlNamespace instanceof Class_){
                if (element instanceof Classifier) {
                    ((Class_)umlNamespace).addNestedClassifier(
                            (Classifier)element);
                }
            }
        }
    }
    
    @Override
    public void addMember(Member member) {
        if (this.member != null) {
            super.addMember(member);
        }
    }
    
    @Override
    protected Collection<Member> deriveMember() {
        ExternalNamespace self = this.getSelf();
        List<Member> members = this.getTemplateParameterMembers();
        Namespace umlNamespace = this.getSelf().getUmlNamespace();
        for (NamedElement member: umlNamespace.getMember()) {
            Collection<String> names = umlNamespace.getNamesOfMember(member);
            if (names.isEmpty()) {
                members.add(ImportedMemberImpl.makeImportedMember(
                        member.getName(), member, self));
            } else {
                for (String name: names) {
                    members.add(ImportedMemberImpl.makeImportedMember(
                            name, member, self));
                }
            }
        }
        return members;
    }
    
    private List<Member> getTemplateParameterMembers() {
        ExternalNamespace self = this.getSelf();
        List<Member> members = new ArrayList<Member>();
        for (ElementReference templateParameter: 
            self.getImpl().getReferent().getImpl().getTemplateParameters()) {
            Member member = ImportedMemberImpl.makeImportedMember(templateParameter, false);
            member.setVisibility("public");
            member.setNamespace(self);
            members.add(member);
        }
        return members;
    }
    
    @Override
    public NamespaceDefinition getNamespace() {
        NamespaceDefinition namespaceDefinition = super.getNamespace();
        if (namespaceDefinition == null) {
            Namespace umlNamespace = this.getSelf().getUmlNamespace();
            Namespace namespace = umlNamespace.getNamespace();
            if (umlNamespace instanceof ParameterableElement) {
                TemplateParameter templateParameter = 
                        ((ParameterableElement)umlNamespace).getOwningTemplateParameter();
                if (templateParameter != null) {
                    namespace = (Namespace)
                            templateParameter.getSignature().getTemplate();
                }
            }
            namespaceDefinition = namespace == null? 
                RootNamespace.getRootScope():
                ExternalNamespace.makeExternalNamespace(namespace, null);
            this.setNamespace(namespaceDefinition);
        }
        return namespaceDefinition;
    }
    
    @Override
    public QualifiedName getNamespaceName() {
        return this.getNamespace().getImpl().getQualifiedName();
    }

    @Override
    public UnitDefinition getUnit() {
        UnitDefinition unit = super.getUnit();
        if (unit == null) {
            NamespaceDefinition self = this.getSelf();
            unit = new UnitDefinition();
            unit.setDefinition(self);
            unit.getImpl().setHasImplicitImports(true);
            
            NamespaceDefinition namespace = self.getNamespace();
            unit.setNamespace(namespace == null? null: 
                namespace.getImpl().getReferent());
            
            self.setUnit(unit);
        }
        return unit;
    }
    
    @Override
    public ElementReference getReferent() {
         return ElementReferenceImpl.makeElementReference(
                 this.getSelf().getUmlNamespace());
    }
    
    @Override
    protected boolean allowPackageOnly() {
        return !(this.getSelf().getUmlNamespace() instanceof Package);
    }
    
    @Override
    public List<Member> resolveInScope(String name, boolean classifierOnly) {
        ExternalNamespace self = this.getSelf();
        List<NamedElement> elements = 
                self.getUmlNamespace().resolveInScope(name, classifierOnly);
        
        // NOTE: Null, as opposed to an empty list, means external resolution
        // is not directly supported.
        if (elements == null) {
            return super.resolveInScope(name, classifierOnly);
        } else {
            List<Member> members = new ArrayList<Member>();
            for (NamedElement element: elements) {
                members.add(ImportedMemberImpl.makeImportedMember(
                        name, element, self));
            }
            return members;
        }
    }
    
    @Override
    public boolean isProfile() {
        return this.getSelf().getUmlNamespace() instanceof Profile;
    }
    
    @Override
    public boolean isStereotype() {
        return this.getSelf().getUmlNamespace() instanceof Stereotype;
    }
    
    @Override
    public boolean isCompletelyBound() {
        Namespace namespace = this.getSelf().getUmlNamespace();
        return !(namespace instanceof TemplateableElement && 
                ((TemplateableElement)namespace).isTemplate());
    }
    
    @Override
    public ElementReference getContext() {
        return this.getReferent().getImpl().getContext();
    }
    
    @Override
    public Member bind(String name,
            NamespaceDefinition namespace,
            boolean isOwnedMember,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {

       ExternalNamespace self = this.getSelf();
       Namespace umlNamespace = self.getUmlNamespace();
       Member boundMember = null;

       if (umlNamespace instanceof TemplateableElement) {
           Collection<NamedElement> additionalElements = 
                   new ArrayList<NamedElement>();
           Set<Element> externalReferences = new HashSet<Element>();
           TemplateableElement instantiation = instantiate(
                   (TemplateableElement)umlNamespace,
                   templateParameters, templateArguments,
                   additionalElements, externalReferences);
           
           boundMember = ExternalNamespace.makeExternalNamespace(
                   (Namespace)instantiation, namespace);
           boundMember.getImpl().setExactName(name);
           boundMember.getImpl().setBase(self);
           
           if (namespace != null) {
               namespace.addMember(boundMember);
               if (isOwnedMember) {
                   boundMember.setNamespace(namespace);
                   namespace.addOwnedMember(boundMember);
               }
           }
           
           // NOTE: This needs to take place after the bound member is added
           // to the namespace, to avoid recursive instantiation if the
           // bound member refers to itself.
           fixExternalReferences(
                   (TemplateableElement)umlNamespace,
                   instantiation, templateParameters, templateArguments, 
                   externalReferences, additionalElements);
           
           if (namespace != null && !additionalElements.isEmpty()) {
               for (NamedElement element: additionalElements) {
                   Member member = ImportedMemberImpl.makeImportedMember(
                           element.getName(), element, namespace);
                   namespace.addOwnedMember(member);
                   namespace.addMember(member);
               }
           }

       }

       return boundMember;
   }

   /**
    * Instantiate the given template, returning the new instantiation.
    * Add additional elements and external references to the corresponding
    * collections.
    */
   private static TemplateableElement instantiate(
           TemplateableElement template,
           List<ElementReference> boundParameters, 
           List<ElementReference> arguments,
           Collection<NamedElement> additionalElements,
           Set<Element> externalReferences) {
       
       // Create an instantiation of the external namespace as a template.
       TemplateableElement instantiation = 
               template.instantiate(externalReferences);
       additionalElements.addAll(instantiation.bindTo(template));
       
       // Treat all generalizations as external references, since any bound
       // elements must be expanded, even if they are nested in the 
       // instantiation.
       if (instantiation instanceof Classifier) {
           for (Classifier general: ((Classifier)instantiation).getGeneral()) {
               externalReferences.add(general);
           }
       }
       
       // Record the template parameter bindings, to be applied later.
       // (This allows for internal element references to be used as template
       // arguments before mapping.)
       List<TemplateParameter> templateParameters =
               template.getOwnedTemplateSignature().getParameter();
       List<TemplateParameter> instantiatedParameters = 
               instantiation.getOwnedTemplateSignature().getParameter();
       for (int i = 0; i < boundParameters.size() && i < arguments.size(); i++) {
           TemplateParameter boundParameter = 
                   (TemplateParameter)boundParameters.get(i).getImpl().getUml();
           int j = templateParameters.indexOf(boundParameter);
           if (j > -1) {
               ElementReferenceImpl.addTemplateBinding(
                       instantiatedParameters.get(j), arguments.get(i));
           }
       }
       
       return instantiation;
   }
   
   /**
    * Fix up any external references that may have been based on qualified
    * names that used template parameters.
    */
   private static void fixExternalReferences(
           TemplateableElement template,
           TemplateableElement instantiation, 
           List<ElementReference> templateParameters,
           List<ElementReference> templateArguments,
           Set<Element> externalReferences,
           Collection<NamedElement> additionalElements) {
       List<Element> references = new ArrayList<Element>();
       List<Element> newReferences = new ArrayList<Element>();
       for (Element reference: externalReferences) {
           Element newReference = null;
           
           if (reference instanceof InstanceSpecification) {
               try {
                   InstanceSpecification newInstanceSpecification = 
                           (InstanceSpecification)reference.getClass().newInstance();
                   for (Classifier classifier: 
                       ((InstanceSpecification)reference).getClassifier()) {
                       Classifier newClassifier = (Classifier)getNewElement(
                               instantiation, classifier, templateParameters, templateArguments);
                       if (newClassifier == null) {
                           newInstanceSpecification.addClassifier(classifier);
                       } else {
                           newInstanceSpecification.addClassifier(newClassifier);
                           newReference = newInstanceSpecification;
                       }
                   }
                   if (newReference != null) {
                       additionalElements.add(newInstanceSpecification);
                   }
               } catch (Exception e) {
               }
           } else if (reference instanceof SignalEvent) {
               Signal signal = (Signal)((SignalEvent) reference).getSignal();
               Signal newSignal = (Signal)getNewElement(
                       instantiation, signal, templateParameters, templateArguments);
               if (newSignal != null) {
                   try {
                       SignalEvent newEvent = 
                               (SignalEvent)reference.getClass().newInstance();
                       newEvent.setSignal(newSignal);
                       additionalElements.add(newEvent);
                       newReference = newEvent;
                   } catch (Exception e) {
                   }
               }
           } else if (reference instanceof NamedElement) {
               newReference = getNewElement(
                       instantiation,
                       (NamedElement)reference, 
                       templateParameters, templateArguments);
           }
           
           if (newReference != null) {
               references.add(reference);
               newReferences.add(newReference);
           }
       }
       
       instantiation.replaceAll(references, newReferences);
   }
   
   private static Element getNewElement(
           TemplateableElement instantiation,
           NamedElement reference,
           List<ElementReference> templateParameters,
           List<ElementReference> templateArguments) {
       ElementReference newReference = getNewReference(instantiation, reference, templateParameters, templateArguments);
       return newReference == null? null: newReference.getImpl().getUml();
   }
   
   private static ElementReference getNewReference(
           TemplateableElement instantiation,
           NamedElement reference,
           List<ElementReference> templateParameters,
           List<ElementReference> templateArguments) {
       ElementReference newReference = null;
       if (reference != null) {
           org.modeldriven.alf.uml.TemplateBinding templateBinding = 
                   ExternalElementReferenceImpl.getUMLTemplateBinding(reference);
           if (templateBinding == null) {
               Element owner = reference.getOwner();
               Namespace namespace = owner instanceof TemplateParameter?
                       (Namespace)((TemplateParameter)owner).getSignature().getTemplate():
                       reference.getNamespace();
               if (namespace != null) {
                   ElementReference namespaceReference = getNewReference(
                           instantiation, namespace, templateParameters, templateArguments);
                   if (namespaceReference != null) {
                       NamespaceDefinition namespaceDefinition = namespaceReference.getImpl().asNamespace();
                       if (namespaceDefinition != null) {
                           String name = reference.getName();
                           for (Member member: namespaceDefinition.getOwnedMember()) {
                               ElementReference referent = member.getImpl().getReferent();
                               if (name == null && referent.getImpl().getName() == null ||
                                       name != null && name.equals(referent.getImpl().getName())) {
                                   if (referent.getImpl().isClassifierTemplateParameter()) {
                                       referent = referent.getImpl().getParameteredElement();
                                   }
                                   Element element = referent.getImpl().getUml();
                                   if (element != null && !element.equals(reference) && 
                                           isSameKind(element, reference, instantiation, templateParameters, templateArguments)) {
                                       newReference = referent;
                                   }
                               }
                           }
                       }
                   }
               }
           } else {
               NamedElement template = 
                       (NamedElement)templateBinding.getSignature().getTemplate();
               if (template == instantiation) {
                   template = (NamedElement)instantiation.getTemplateBinding().get(0).getSignature().getTemplate();
               }
               List<ElementReference> formals = new ArrayList<ElementReference>();
               List<ElementReference> actuals = new ArrayList<ElementReference>();
               for (org.modeldriven.alf.uml.TemplateParameterSubstitution parameterSubstitution: 
                   templateBinding.getParameterSubstitution()) {
                   formals.add(
                           ElementReferenceImpl.makeElementReference(parameterSubstitution.getFormal()));
                   actuals.add(makeSubstitution(
                           ElementReferenceImpl.makeElementReference(parameterSubstitution.getActual()),
                           templateParameters, templateArguments));
               }
               ElementReference templateReference = 
                       getNewReference(instantiation, template, templateParameters, templateArguments);
               if (templateReference == null) {
                   newReference = RootNamespace.getRootScope().getEffectiveBoundElement(
                           ElementReferenceImpl.makeElementReference(template), formals, actuals);
               } else {
                   newReference = RootNamespace.getRootScope().getEffectiveBoundElement(
                           templateReference, formals, actuals);
               }
           }
       }
       return newReference;
   }
   
   private static boolean isSameKind(
           Element element1, Element element2,
           TemplateableElement instantiation,
           List<ElementReference> templateParameters,
           List<ElementReference> templateArguments) {
       if (element1.getClass() != element2.getClass()) {
           return false;
       } else if (!(element1 instanceof BehavioralFeature)){
           return true;
       } else {
           List<Parameter> parameters1 = ((BehavioralFeature)element1).getOwnedParameter();
           List<Parameter> parameters2 = ((BehavioralFeature)element2).getOwnedParameter();
           if (parameters1.size() != parameters2.size()) {
               return false;
           } else {
               for (int i = 0; i < parameters1.size(); i++) {
                   Parameter parameter1 = parameters1.get(i);
                   Parameter parameter2 = parameters2.get(i);
                   if (
                       parameter1.getName() == null && parameter2.getName() != null ||
                       parameter1.getName() != null && !parameter1.getName().equals(parameter2.getName()) ||
                       !parameter1.getDirection().equals(parameter2.getDirection()) ||
                       parameter1.getLower() != parameter2.getLower() ||
                       parameter1.getUpper() != parameter2.getUpper() ||
                       parameter1.getIsOrdered() != parameter2.getIsOrdered() ||
                       parameter1.getIsUnique() != parameter2.getIsUnique() ||
                       !matches(parameter1.getType(), parameter2.getType(), 
                               instantiation, templateParameters, templateArguments)
                   ) {
                       return false;
                   }
               }
               return true;
           }
       }
   }
   
   private static boolean matches(
           NamedElement element1, NamedElement element2,
           TemplateableElement instantiation,
           List<ElementReference> templateParameters,
           List<ElementReference> templateArguments) {
       if (element1 != null) {
           // NOTE: This accounts for possible deferred template bindings.
           ElementReference reference = ElementReferenceImpl.makeElementReference(element1);
           element1 = reference == null? null: (NamedElement)reference.getImpl().getUml();
       }
       if (element2 != null) {
           ElementReference reference = getNewReference(instantiation, element2, templateParameters, templateArguments);
           if (reference == null) {
               reference = ElementReferenceImpl.makeBoundReference(element2);
           }
           reference = makeSubstitution(reference, templateParameters, templateArguments);
           element2 = reference == null? null: (NamedElement)reference.getImpl().getUml();
       }
       return element1 == null && element2 == null ||
              element1 != null && element1.equals(element2);
   }
   
   private static ElementReference makeSubstitution(
           ElementReference reference,
           List<ElementReference> templateParameters,
           List<ElementReference> templateArguments) {
       for (int i = 0; i < templateParameters.size(); i++) {
           if (reference.getImpl().equals(
                   templateParameters.get(i).getImpl().getParameteredElement())) {
               reference = i >= templateArguments.size()? null: 
                       templateArguments.get(i);
               if (reference != null && reference.getImpl().isClassifierTemplateParameter()) {
                   reference = reference.getImpl().getParameteredElement();
               }
               break;
           }
       }
       return reference;
   }

}

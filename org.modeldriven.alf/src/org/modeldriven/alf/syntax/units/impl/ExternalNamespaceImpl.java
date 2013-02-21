/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.syntax.expressions.NameBinding;
import org.modeldriven.alf.syntax.expressions.NamedTemplateBinding;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.expressions.TemplateParameterSubstitution;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Namespace;
import org.modeldriven.alf.uml.Package;
import org.modeldriven.alf.uml.PackageableElement;
import org.modeldriven.alf.uml.StereotypeApplication;
import org.modeldriven.alf.uml.TemplateParameter;
import org.modeldriven.alf.uml.TemplateSignature;
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
    protected Collection<Member> deriveMember() {
        ExternalNamespace self = this.getSelf();
        List<Member> members = this.getTemplateParameterMembers();
        Namespace umlNamespace = this.getSelf().getUmlNamespace();
        for (NamedElement member: umlNamespace.getMember()) {
            List<String> names = umlNamespace.getNamesOfMember(member);
            if (names.isEmpty()) {
                members.add(ImportedMemberImpl.makeImportedMember(
                        member.getName(), member, self));
            } else {
                for (String name: umlNamespace.getNamesOfMember(member)) {
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
        Namespace umlNamespace = self.getUmlNamespace();
        if (umlNamespace instanceof TemplateableElement) {
            TemplateSignature signature = 
                    ((TemplateableElement)umlNamespace).getOwnedTemplateSignature();
            if (signature != null) {
                for (TemplateParameter templateParameter: signature.getParameter()) {
                    Element element = templateParameter.getParameteredElement();
                    String name = !(element instanceof NamedElement)? "":
                        ((NamedElement)element).getName();
                    Member member = ImportedMemberImpl.makeImportedMember(
                            name, templateParameter, self);
                    member.setVisibility("public");
                    members.add(member);
                }
            }
        }
        return members;
    }
    
    @Override
    public NamespaceDefinition getNamespace() {
        NamespaceDefinition namespace = super.getNamespace();
        return namespace != null? namespace:
            ExternalNamespace.makeExternalNamespace(
                this.getSelf().getUmlNamespace().getNamespace(), null);
    }

    /*
    @Override
    public ElementReference getNamespaceReference() {
        return ElementReferenceImpl.makeElementReference(
                this.getSelf().getUmlNamespace().getNamespace());
    }
    */
    
    @Override
    public ElementReference getReferent() {
         return ElementReferenceImpl.makeElementReference(
                 this.getSelf().getUmlNamespace(), super.getNamespace());
    }
    
    @Override
    protected boolean allowPackageOnly() {
        return !(this.getSelf().getUmlNamespace() instanceof Package);
    }
    
    @Override
    public boolean isCompletelyBound() {
        Namespace namespace = this.getSelf().getUmlNamespace();
        return !(namespace instanceof TemplateableElement && 
                ((TemplateableElement)namespace).isTemplate());
    }
    
   @Override
    public Member bind(String name,
            NamespaceDefinition namespace,
            boolean isOwnedMember,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
       // System.out.println("[bind] name=" + name);

       Namespace umlNamespace = this.getSelf().getUmlNamespace();
       Member boundMember = null;

       if (umlNamespace instanceof TemplateableElement) {
           Collection<NamedElement> additionalElements = 
                   new ArrayList<NamedElement>();
           Collection<StereotypeApplication> stereotypeApplications = 
                   new ArrayList<StereotypeApplication>();
           Set<Element> externalReferences = new HashSet<Element>();
           TemplateableElement instantiation = 
                   this.instantiate(
                           namespace, templateParameters, templateArguments,
                           additionalElements, stereotypeApplications, externalReferences);
           
           boundMember = ExternalNamespace.makeExternalNamespace(
                   (Namespace)instantiation, namespace);
           boundMember.getImpl().setExactName(name);
           
           if (namespace != null) {
               // Record any stereotype applications that need to be made within the
               // new instantiation.
               namespace.getImpl().getModelScope().addStereotypeApplications(
                       stereotypeApplications);
               
               namespace.addMember(boundMember);
               if (isOwnedMember) {
                   boundMember.setNamespace(namespace);
                   namespace.addOwnedMember(boundMember);
                   
                   if (additionalElements.isEmpty()) {
                       System.out.println("Cannot bind " + name);
                   } else {
                       for (NamedElement element: additionalElements) {
                           Member member = ImportedMemberImpl.makeImportedMember(
                                   element.getName(), element, namespace);
                           namespace.addOwnedMember(member);
                           namespace.addMember(member);
                       }
                   }
                   
                   if (namespace.getImpl().getModelScope() == 
                           RootNamespace.getRootScope()) {
                       RootNamespace.recordAdditionalElement(instantiation);
                       RootNamespace.recordAdditionalElements(additionalElements);
                   }
               }
                             
           }
           
           // NOTE: This needs to take place after the bound member is added
           // to the namespace, to avoid recursive instantiation if the
           // bound member refers to itself.
           fixExternalReferences(
                   instantiation, templateParameters, templateArguments, 
                   externalReferences);
           
       }

       return boundMember;
   }

   /**
    * Instantiate the external namespace as a template, returning the
    * new instantiation.
    */
   private TemplateableElement instantiate(
           NamespaceDefinition parent,
           List<ElementReference> boundParameters, 
           List<ElementReference> arguments,
           Collection<NamedElement> additionalElements,
           Collection<StereotypeApplication> stereotypeApplications,
           Set<Element> externalReferences) {
       
       // Create an instantiation of the external namespace as a template.
       TemplateableElement template = 
               (TemplateableElement)this.getSelf().getUmlNamespace();
       TemplateableElement instantiation = 
               template.instantiate(stereotypeApplications, externalReferences);
       additionalElements.addAll(instantiation.bindTo(template));
       
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
           TemplateableElement instantiation, 
           List<ElementReference> templateParameters,
           List<ElementReference> templateArguments,
           Set<Element> externalReferences) {
       // System.out.println("[fixExternalReferences] instantiation=" + instantiation);
       for (Element reference: externalReferences) {
           // System.out.println("[fixExternalReferences] reference=" + reference);
           if (reference instanceof NamedElement) {
               QualifiedName qualifiedName = 
                       makeQualifiedName((NamedElement)reference);
               // System.out.println("[fixExternalReferences] qualifiedName=" + qualifiedName.getImpl().getPathName());
               qualifiedName = qualifiedName.getImpl().updateBindings(
                       templateParameters, templateArguments);
               // System.out.println("[fixExternalReferences] updated qualifiedName=" + qualifiedName.getImpl().getPathName());
               for (ElementReference referent: qualifiedName.getReferent()) {
                   Element newReference = referent.getImpl().getUml();
                   if (newReference != null && !newReference.equals(reference) && 
                           isSameKind(newReference, reference)) {
                       // System.out.println("[fixExternalReferences] referent=" + referent);
                       instantiation.replace(reference, newReference);
                       break;
                   }
               }
           }
       }  
   }
   
   /**
    * Return a qualified name for the given element, including template
    * bindings. All templates involved must be named elements.
    */
   private static QualifiedName makeQualifiedName(
           NamedElement element) {
       QualifiedName qualifiedName;
       org.modeldriven.alf.uml.TemplateBinding templateBinding = 
               ExternalElementReferenceImpl.getTemplateBinding(element);
       
       if (templateBinding == null) {
           Element owner = element.getOwner();
           Namespace namespace = owner instanceof TemplateParameter?
                   (Namespace)((TemplateParameter)owner).getSignature().getTemplate():
                   element.getNamespace();
                   
           qualifiedName = namespace == null? 
                   new QualifiedName(): makeQualifiedName(namespace);
           NameBinding nameBinding = new NameBinding();
           nameBinding.getImpl().setExactName(element.getName());
           qualifiedName.addNameBinding(nameBinding);
           
       } else {
           qualifiedName = makeQualifiedName(
                   (NamedElement)templateBinding.getSignature().getTemplate());
           List<NameBinding> nameBindings = qualifiedName.getNameBinding();
           NameBinding nameBinding = nameBindings.get(nameBindings.size() - 1);
           NamedTemplateBinding binding = new NamedTemplateBinding();
           nameBinding.setBinding(binding);
           for (org.modeldriven.alf.uml.TemplateParameterSubstitution parameterSubstitution: 
               templateBinding.getParameterSubstitution()) {
               TemplateParameterSubstitution substitution = new 
                       TemplateParameterSubstitution();
               substitution.setParameterName(
                       ((NamedElement)parameterSubstitution.getFormal().
                               getParameteredElement()).getName());
               NamedElement actual = (NamedElement)parameterSubstitution.getActual();
               substitution.setArgumentName(actual == null? new QualifiedName():
                       makeQualifiedName(actual));
               binding.addSubstitution(substitution);
           }
       }
       
       qualifiedName.getImpl().setCurrentScope(RootNamespace.getRootScope());
       qualifiedName.getImpl().setIsVisibleOnly(false);
       return qualifiedName;
   }
   
   private static boolean isSameKind(Element element1, Element element2) {
       // TODO: Allow for overloading of operations.
       return element1.getClass() == element2.getClass();
   }

}

/*******************************************************************************
 * Copyright 2011-2019 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.QualifiedNameList;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The definition of a classifier.
 **/

public abstract class ClassifierDefinitionImpl extends NamespaceDefinitionImpl {

    private Boolean isAbstract = false;
    private QualifiedNameList specialization = null;
    private Collection<ElementReference> specializationReferent = null; // DERIVED
    
	public ClassifierDefinitionImpl(ClassifierDefinition self) {
		super(self);
	}

	@Override
	public ClassifierDefinition getSelf() {
		return (ClassifierDefinition) this.self;
	}

    @Override
    public void addExternalReferences(Collection<ExternalElementReference> references) {
        super.addExternalReferences(references);
        SyntaxElement.addExternalReferences(references, this.getSelf().getSpecializationReferent());
    }
    
    public Boolean getIsAbstract() {
        return this.isAbstract;
    }

    public void setIsAbstract(Boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public QualifiedNameList getSpecialization() {
        return this.specialization;
    }

    public void setSpecialization(QualifiedNameList specialization) {
        this.specialization = specialization;
        specialization.getImpl().setCurrentScope(this.getSelf());
    }

    public Collection<ElementReference> getSpecializationReferent() {
        if (this.specializationReferent == null) {
            this.setSpecializationReferent(this.deriveSpecializationReferent());
        }
        return this.specializationReferent;
    }

    public void setSpecializationReferent(
            Collection<ElementReference> specializationReferent) {
        this.specializationReferent = specializationReferent;
    }

    public void addSpecializationReferent(
            ElementReference specializationReferent) {
        this.specializationReferent.add(specializationReferent);
    }
    
    /**
     * The specialization referents of a classifier definition are the
     * classifiers denoted by the names in the specialization list for the
     * classifier definition.
     **/
	protected Collection<ElementReference> deriveSpecializationReferent() {
	    QualifiedNameList specialization = this.getSelf().getSpecialization();
	    if (specialization == null) {
	        return new ArrayList<ElementReference>();
	    } else {
    	    return specialization.getImpl().getNonTemplateClassifierReferents();
	    }
	}
	
    /**
     * The members of a classifier definition include non-private members
     * inherited from the classifiers it specializes. The visibility of
     * inherited members is as specified in the UML Superstructure, Subclause
     * 7.3.8. Elements inherited from external classifiers are treated as
     * imported members.
     **/
	@Override
	protected Collection<Member> deriveMember(Collection<ElementReference> excluded) {
	    Collection<Member> members = super.deriveMember(excluded);
	    
	    if (!this.getSelf().getIsStub()) {
	        
            // Note: The members field is set here in order to avoid the possibility
            // of an infinite loop in name resolution of names in the specialization
    	    // clause.
    	    this.setMember(members);
    	    
            Collection<ElementReference> specializationReferents = 
                    this.getSelf().getSpecializationReferent();
            if (!specializationReferents.isEmpty()) {
                List<Member> inheritedMembers = new ArrayList<Member>();
        	    for (ElementReference parent: specializationReferents) {
        	        inheritedMembers.addAll(parent.getImpl().
        	                getInheritableMembers());
        	    }
        	    MemberImpl.removeDuplicates(inheritedMembers);
        	    
        	    // Eliminate duplicates with imported members
                for (Member inheritedMember: inheritedMembers) {
                    ElementReferenceImpl referent = 
                        inheritedMember.getImpl().getReferent().getImpl();
                    for (Object otherMember: members.toArray()) {
                        if (referent.equals
                                (((Member)otherMember).getImpl().getReferent())) {
                            this.removeMember((Member)otherMember);
                        }
                    }
                }
                
                // Note: Inherited members are added here so inherited type
                // names may be used in the resolution of parameter types for 
                // the distinguishibility test used in the inherit method for 
                // class definitions.
                members = new ArrayList<Member>(this.getMember());
        	    this.addAllMembers(inheritedMembers);
        	    
                members.addAll(this.inherit(inheritedMembers));
                
            }
            
            // Note: If there were excluded elements, the set of members may
            // not have been completely determined. Clear it so that it can
            // be recomputed later.
            if (excluded != null && excluded.size() > 0) {
                this.setMember(null);
            }
	    }
	    
	    return members;
	}
	
     /*
	 * Derivations
	 */

	public boolean classifierDefinitionSpecializationReferentDerivation() {
		this.getSelf().getSpecializationReferent();
		return true;
	}
	
	/*
	 * Constraints
	 */

    /**
     * Each name listed in the specialization list for a classifier definition
     * must have a single classifier referent. None of these referents may be
     * templates.
     **/
    public boolean classifierDefinitionSpecialization() {
        ClassifierDefinition self = this.getSelf();
        QualifiedNameList specialization = self.getSpecialization();
        if (specialization != null) {
            for (QualifiedName qualifiedName: specialization.getName()) {
                if (qualifiedName.getImpl().getNonTemplateClassifierReferent() == null) {
                    return false;
                }
            }
        }
        return true;
    }

	/**
	 * The members of a classifier definition include non-private members
	 * inherited from the classifiers it specializes. The visibility of
	 * inherited members is as specified in the UML Superstructure, Subclause
	 * 7.3.8.
	 **/
	public boolean classifierDefinitionInheritedMembers() {
	    this.getSelf().getMember();
		return true;
	}

    /*
     * Helper Methods
     */
	
	/**
	 * The namespace definition associated with the given unit definition must
	 * be a classifier definition. The subunit classifier definition may be
	 * abstract if and only if the subunit classifier definition is abstract.
	 * The subunit classifier definition must have the same specialization
	 * referents as the stub classifier definition. (Note that it is the
	 * referents that must match, not the exact names or the ordering of those
	 * names in the specialization list.) The subunit classifier definition must
	 * also have a matching classifier template parameter for each classifier
	 * template parameter of the stub classifier definition. Two template
	 * parameters match if they have same names and the same specialization
	 * referents.
	 **/
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
	    NamespaceDefinition namespace = unit.getDefinition();
	    if (!(namespace instanceof ClassifierDefinition)) {
	        return false;
	    } else {
	        ClassifierDefinition other = (ClassifierDefinition)namespace;
	        Collection<ElementReference> otherSpecializations = other.getSpecializationReferent();
	        List<ClassifierTemplateParameter> otherParameters = other.getImpl().getClassifierTemplateParameters();
	        
	        ClassifierDefinition self = this.getSelf();
            Collection<ElementReference> mySpecializations = self.getSpecializationReferent();
            List<ClassifierTemplateParameter> myParameters = self.getImpl().getClassifierTemplateParameters();
            
            for (ElementReference specialization: mySpecializations) {
                if (!specialization.getImpl().isContainedIn(otherSpecializations)) {
                    return false;
                }
            }
            
            for (ClassifierTemplateParameter myParameter: myParameters) {
                boolean found = false;
                for (ClassifierTemplateParameter otherParameter: otherParameters) {
                    if (myParameter.getImpl().equals(otherParameter)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            
            return  other.getIsAbstract() == self.getIsAbstract() &&
                    otherSpecializations.size() == mySpecializations.size() &&
                    otherParameters.size() == myParameters.size();
 	    }
	} // matchForStub

    /**
     * This operation is provided so that, if getOwnedMember is overridden, this
     * operation can still get the original owned members synthesized from
     * parsing.
     */
    public List<Member> getOnlyOwnedMembers() {
        return this.getOwnedMember();
    }
    
	@Override
    public boolean isTemplate() {
        for (Member member: this.getOnlyOwnedMembers()) {
            if (member instanceof ClassifierTemplateParameter &&
                    !((ClassifierTemplateParameter)member).getImpl().isBound()) {
                return true;
            }
        }
        return false;
    }
	
	public boolean isBound() {
	    for (Member member: this.getOnlyOwnedMembers()) {
	        if (member instanceof ClassifierTemplateParameter &&
	                ((ClassifierTemplateParameter)member).getImpl().isBound()) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public List<ClassifierTemplateParameter> getClassifierTemplateParameters() {
	    List<ClassifierTemplateParameter> templateParameters = 
	        new ArrayList<ClassifierTemplateParameter>();
        for (Member member: this.getOnlyOwnedMembers()) {
            if (member instanceof ClassifierTemplateParameter &&
                    !((ClassifierTemplateParameter)member).getImpl().isBound()) {
                templateParameters.add((ClassifierTemplateParameter)member);
            }
        }
        return templateParameters;
	}
	
    public List<ClassifierTemplateParameter> getBoundTemplateParameters() {
        List<ClassifierTemplateParameter> templateParameters = 
            new ArrayList<ClassifierTemplateParameter>();
        for (Member member: this.getOnlyOwnedMembers()) {
            if (member instanceof ClassifierTemplateParameter &&
                    ((ClassifierTemplateParameter)member).getImpl().isBound()) {
                templateParameters.add((ClassifierTemplateParameter)member);
            }
        }
        return templateParameters;
    }
    
    public List<ElementReference> getTemplateParameters() {
        List<ElementReference> templateParameters = new ArrayList<ElementReference>();
        for (ClassifierTemplateParameter templateParameter: this.getClassifierTemplateParameters()) {
            templateParameters.add(templateParameter.getImpl().getReferent());
        }
        return templateParameters;
    }
    
	public List<ElementReference> getTemplateActuals() {
	    List<ElementReference> templateActuals = new ArrayList<ElementReference>();
        for (Member member: this.getOnlyOwnedMembers()) {
            if (member instanceof ClassifierTemplateParameter) {
                ClassifierTemplateParameterImpl parameter = 
                    ((ClassifierTemplateParameter)member).getImpl();
                if (parameter.isBound()) {
                    ElementReference boundArgument = parameter.getBoundArgument();
                    if (boundArgument != null) {
                        templateActuals.add(boundArgument);
                    }
                }
            }
        }
        return templateActuals;
	}
	
	@Override
	public boolean isCompletelyBound() {
	    return !this.isTemplate();
//	    ClassifierDefinition self = this.getSelf();
//	    NamespaceDefinition namespace = self.getNamespace();
//	    if (namespace != null && !namespace.getImpl().isCompletelyBound()){
//	        return false;
//	    } else {
//            for (Member member: this.getOnlyOwnedMembers()) {
//                if (member instanceof ClassifierTemplateParameter) {
//                    ClassifierTemplateParameterImpl parameter = 
//                        ((ClassifierTemplateParameter)member).getImpl();
//                    if (!parameter.isBound()) {
//                        return false;
//                    } else {
//                        ElementReference boundArgument = 
//                            parameter.getBoundArgument();
//                        if (boundArgument != null && 
//                                !boundArgument.getImpl().isCompletelyBound()) {
//                            return false;
//                        }
//                    }
//                }
//            }
//            return true;
//	    }
	}

	public List<Member> getInheritableMembers() {
	    ArrayList<Member> inheritableMembers = new ArrayList<Member>();
	    for (Member member: this.getSelf().getMember()) {
	        if (!member.getImpl().isPrivate()) {
	            inheritableMembers.add(member);
	        }
	    }
	    return inheritableMembers;
	}

	// Note: Overrides of this operation may modify the contents of inheritableMembers.
	protected List<Member> inherit(List<Member> inheritableMembers) {
	    return inheritableMembers;
	}

    private static ElementReference commonAncestorImpl(Collection<ElementReferenceImpl> classifiers) {
        while (classifiers.size() > 1) {
            // Construct the set of all common ancestors of the given classifiers.
            boolean isFirst = true;
            Set<ElementReferenceImpl> commonAncestors = new HashSet<ElementReferenceImpl>();
            for (ElementReferenceImpl classifier: classifiers) {
                if (classifier.isAny()) {
                    return classifier.getSelf();
                }
                // Note: allParents may be cached, so it should not be mutated.
                Collection<ElementReference> ancestors = classifier.allParents();
                if (isFirst) {
                    commonAncestors.add(classifier);
                    for (ElementReference ancestor: ancestors) {
                        commonAncestors.add(ancestor.getImpl());
                    }
                    isFirst = false;
                } else {
                    for (Object object: commonAncestors.toArray()) {
                        ElementReferenceImpl commonAncestor = (ElementReferenceImpl)object;
                        if (!commonAncestor.equals(classifier) && 
                                !commonAncestor.isContainedIn(ancestors)) {
                            commonAncestors.remove(commonAncestor);
                        }
                    }
                }
                if (commonAncestors.isEmpty()) {
                    return any;
                }
            }
            
            // Remove any common ancestors that are parents of other common
            // ancestors.
            for (Object ancestor: commonAncestors.toArray()) {
                Collection<ElementReference> parents = 
                        ((ElementReferenceImpl)ancestor).parents();
                for (ElementReference parent: parents) {
                    commonAncestors.remove(parent.getImpl());
                }

            }
            
            classifiers = commonAncestors;
        }
        for (ElementReferenceImpl classifier: classifiers) {
            return classifier.getSelf();
        }
        return any;
    }
    
    public static ElementReference commonAncestor(Collection<ElementReference> classifiers) {
        HashSet<ElementReferenceImpl> classifierSet = new HashSet<ElementReferenceImpl>();
        for (ElementReference classifier: classifiers) {
            if (classifier == null) {
                return null;
            } else {
                classifierSet.add(implForClassifier(classifier));
            }
        }
        return commonAncestorImpl(classifierSet);
    }
    
    public static ElementReference commonAncestor(ElementReference... classifiers) {
        HashSet<ElementReferenceImpl> classifierSet = new HashSet<ElementReferenceImpl>();
        for (ElementReference classifier: classifiers) {
            if (classifier == null) {
                return null;
            } else {
                classifierSet.add(implForClassifier(classifier));
            }
        }
        return commonAncestorImpl(classifierSet);
    }
    
    private static ElementReferenceImpl implForClassifier(ElementReference classifier) {
        ElementReferenceImpl impl = classifier.getImpl();
        ElementReference parameteredElement = impl.getParameteredElement();
        if (parameteredElement != null) {
            impl = parameteredElement.getImpl();
        }
        return impl;
    }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ClassifierDefinition) {
            ClassifierDefinition self = this.getSelf();
            ClassifierDefinition baseClassifier = (ClassifierDefinition)base;
            self.setIsAbstract(baseClassifier.getIsAbstract());
            QualifiedNameList baseSpecialization = baseClassifier.getSpecialization();
            if (baseSpecialization != null) {
                QualifiedNameList specialization = new QualifiedNameList();
                for (QualifiedName baseName: baseSpecialization.getName()) {
                    QualifiedName qualifiedName = baseName.getImpl().
                        updateBindings(templateParameters, templateArguments);
                    qualifiedName.getImpl().setCurrentScope(self);
                    specialization.addName(qualifiedName);
                }
                self.setSpecialization(specialization);
            }            
        }
    }
    
} // ClassifierDefinitionImpl


/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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

	public org.modeldriven.alf.syntax.units.ClassifierDefinition getSelf() {
		return (ClassifierDefinition) this.self;
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
     * 7.3.8.
     **/
	@Override
	protected Collection<Member> deriveMember() {
	    Collection<Member> members = super.deriveMember();
	    
        // Note: The members field is set here in order to avoid the possibility
        // of an infinite loop in name resolution of names in the specialization
	    // clause.
	    this.setMember(members);
	    
	    List<Member> inheritedMembers = new ArrayList<Member>();
	    for (ElementReference parent: this.getSelf().getSpecializationReferent()) {
	        inheritedMembers.addAll(parent.getImpl().getInheritableMembers());
	    }
	    MemberImpl.removeDuplicates(inheritedMembers);
	    
	    // Eliminate duplicates with imported members
        for (int i = 0; i < inheritedMembers.size(); i++) {
            ElementReferenceImpl inheritedMember = 
                inheritedMembers.get(i).getImpl().getReferent().getImpl();
            for (Object otherMember: members.toArray()) {
                if (inheritedMember.equals
                        (((Member)otherMember).getImpl().getReferent())) {
                    this.removeMember((Member)otherMember);
                }
            }
        }
        
	    // Note: Inherited members are added here so inherited type names may be
	    // used in the resolution of parameter types for the distinguishibility
	    // test used in the inherit method for class definitions.
        members = new ArrayList<Member>(this.getMember());
	    this.addAllMembers(inheritedMembers);
	           
        members.addAll(this.inherit(inheritedMembers));
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
	        List<ClassifierTemplateParameter> otherParameters = other.getImpl().getTemplateParameters();
	        
	        ClassifierDefinition self = this.getSelf();
            Collection<ElementReference> mySpecializations = self.getSpecializationReferent();
            List<ClassifierTemplateParameter> myParameters = self.getImpl().getTemplateParameters();
	        
            for (ElementReference specialization: mySpecializations) {
                if (!specialization.getImpl().isContainedIn(otherSpecializations)) {
                    return false;
                }
            }
            
            for (ClassifierTemplateParameter myParameter: myParameters) {
                for (ClassifierTemplateParameter otherParameter: otherParameters) {
                    if (!myParameter.getImpl().equals(otherParameter)) {
                        return false;
                    }
                }
            }
            
            return  other.getIsAbstract() == self.getIsAbstract() &&
                    otherSpecializations.size() == mySpecializations.size() &&
                    otherParameters.size() == myParameters.size();
 	    }
	} // matchForStub

	/*
	 * Helper Methods
	 */
	
	@Override
    public boolean isTemplate() {
        for (Member member: this.getSelf().getOwnedMember()) {
            if (member instanceof ClassifierTemplateParameter) {
                return true;
            }
        }
        return false;
    }
	
	public List<ClassifierTemplateParameter> getTemplateParameters() {
	    List<ClassifierTemplateParameter> templateParameters = new ArrayList<ClassifierTemplateParameter>();
        for (Member member: this.getSelf().getOwnedMember()) {
            if (member instanceof ClassifierTemplateParameter) {
                templateParameters.add((ClassifierTemplateParameter)member);
            }
        }
        return templateParameters;
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

    public static ElementReference commonAncestor(Collection<ElementReference> classifiers) {
        while (classifiers.size() > 1) {
            // Construct the set of all common ancestors of the given classifiers.
            boolean isFirst = true;
            Set<ElementReference> commonAncestors = new HashSet<ElementReference>();
            for (ElementReference classifier: classifiers) {
                if (classifier == null) {
                    return null;
                }
                Collection<ElementReference> ancestors = classifier.getImpl().allParents();
                ancestors.add(classifier);
                if (isFirst) {
                    commonAncestors.addAll(ancestors);
                    isFirst = false;
                } else {
                    for (Object commonAncestor: commonAncestors.toArray()) {
                        if (!(((ElementReference)commonAncestor).getImpl().
                                isContainedIn(ancestors))) {
                            commonAncestors.remove(commonAncestor);
                        }
                    }
                }
                if (commonAncestors.isEmpty()) {
                    return null;
                }
            }
            
            // Remove any common ancestors that are parents of other common
            // ancestors.
            for (Object ancestor: commonAncestors.toArray()) {
                Collection<ElementReference> parents = ((ElementReference)ancestor).getImpl().parents();
                for (ElementReference parent: parents) {
                    commonAncestors.remove(parent.getImpl());
                }

            }
             
            classifiers = commonAncestors;
        }
        if (classifiers.size() == 0) {
            return null;
        } else {
            return (ElementReference)classifiers.toArray()[0];
        }
    }
    
    public static ElementReference commonAncestor(ElementReference... classifiers) {
        HashSet<ElementReference> classifierSet = new HashSet<ElementReference>();
        for (ElementReference classifier: classifiers) {
            classifierSet.add(classifier);
        }
        return commonAncestor(classifierSet);
    }
    
    @Override
    protected void bindTo(Member base,
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

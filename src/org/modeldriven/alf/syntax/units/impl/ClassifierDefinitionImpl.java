
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.QualifiedNameList;
import org.modeldriven.alf.syntax.units.*;
import org.omg.uml.Classifier;
import org.omg.uml.Element;
import org.omg.uml.NamedElement;

import java.util.ArrayList;
import java.util.List;

/**
 * The definition of a classifier.
 **/

public abstract class ClassifierDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.NamespaceDefinitionImpl {

	public ClassifierDefinitionImpl(ClassifierDefinition self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.ClassifierDefinition getSelf() {
		return (ClassifierDefinition) this.self;
	}

    /**
     * The specialization referents of a classifier definition are the
     * classifiers denoted by the names in the specialization list for the
     * classifier definition.
     **/
	public ArrayList<ElementReference> deriveSpecializationReferent() {
	    ClassifierDefinition self = this.getSelf();
	    ArrayList<ElementReference> specializationReferents = new ArrayList<ElementReference>();
	    QualifiedNameList specialization = self.getSpecialization();
	    if (specialization != null) {
    	    for (QualifiedName qualifiedName: specialization.getName()) {
    	        qualifiedName.getImpl().setCurrentScope(this.getOuterScope());
    	        ArrayList<ElementReference> referents = qualifiedName.getReferent();
    	        if (referents.size() > 0) {
    	            specializationReferents.add(referents.get(0));
    	        }
    	    }
	    }
		return specializationReferents;
	}
	
    /**
     * The members of a classifier definition include non-private members
     * inherited from the classifiers it specializes. The visibility of
     * inherited members is as specified in the UML Superstructure, Subclause
     * 7.3.8.
     **/
	@Override
	public ArrayList<Member> deriveMember() {
	    ArrayList<Member> inheritedMembers = new ArrayList<Member>();
	    for (ElementReference parent: this.getSelf().getSpecializationReferent()) {
	        inheritedMembers.addAll(this.getInheritableMembersOf(parent));
	    }
	    ArrayList<Member> members = super.deriveMember();
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
        for (QualifiedName qualifiedName: self.getSpecialization().getName()) {
            qualifiedName.getImpl().setCurrentScope(this.getOuterScope());
            if (qualifiedName.getImpl().getNonTemplateClassifierReferent() == null) {
                return false;
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
	        List<ElementReference> otherSpecializations = other.getSpecializationReferent();
	        List<ClassifierTemplateParameter> otherParameters = other.getImpl().getTemplateParameters();
	        
	        ClassifierDefinition self = this.getSelf();
            List<ElementReference> mySpecializations = self.getSpecializationReferent();
            List<ClassifierTemplateParameter> myParameters = self.getImpl().getTemplateParameters();
	        
            return  other.getIsAbstract() == self.getIsAbstract() &&
                    otherSpecializations.size() == mySpecializations.size() &&
                    otherSpecializations.containsAll(mySpecializations) &&
                    otherParameters.size() == myParameters.size() &&
                    otherParameters.containsAll(myParameters);
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

	private ArrayList<Member> getInheritableMembersOf(ElementReference parent) {
	    SyntaxElement alfParent = parent.getImpl().getAlf();
	    Element umlParent = parent.getImpl().getUml();
	    ArrayList<Member> inheritableMembers = null;
	    if (alfParent != null && alfParent instanceof ClassifierDefinition) {
	        inheritableMembers = ((ClassifierDefinition)alfParent).getImpl().getInheritableMembers();
	    } else if (umlParent != null && umlParent instanceof Classifier) {
	        inheritableMembers = new ArrayList<Member>();
	        for (NamedElement element: ((Classifier)umlParent).inheritableMembers()) {
	            inheritableMembers.add(ImportedMemberImpl.makeImportedMember(element));
	        }
	    } else {
	        inheritableMembers = new ArrayList<Member>();
	    }
	    return this.inherit(inheritableMembers);
	}

	public ArrayList<Member> getInheritableMembers() {
	    ArrayList<Member> inheritableMembers = new ArrayList<Member>();
	    for (Member member: this.getSelf().getMember()) {
	        if (!member.getImpl().isPrivate()) {
	            inheritableMembers.add(member);
	        }
	    }
	    return inheritableMembers;
	}

	// Note: Overrides of this operation may modify the contents of inheritableMembers.
	protected ArrayList<Member> inherit(ArrayList<Member> inheritableMembers) {
	    return inheritableMembers;
	}

} // ClassifierDefinitionImpl

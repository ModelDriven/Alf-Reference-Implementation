/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.statements.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;

/**
 * The definition of a classifier.
 **/

public abstract class ClassifierDefinition extends NamespaceDefinition {

	@Override
    public ClassifierDefinitionImpl getImpl() {
		return (ClassifierDefinitionImpl) this.impl;
	}

	public Boolean getIsAbstract() {
		return this.getImpl().getIsAbstract();
	}

	public void setIsAbstract(Boolean isAbstract) {
		this.getImpl().setIsAbstract(isAbstract);
	}

	public QualifiedNameList getSpecialization() {
		return this.getImpl().getSpecialization();
	}

	public void setSpecialization(QualifiedNameList specialization) {
		this.getImpl().setSpecialization(specialization);
	}

	public Collection<ElementReference> getSpecializationReferent() {
		return this.getImpl().getSpecializationReferent();
	}

	public void setSpecializationReferent(
			Collection<ElementReference> specializationReferent) {
		this.getImpl().setSpecializationReferent(specializationReferent);
	}

	public void addSpecializationReferent(
			ElementReference specializationReferent) {
		this.getImpl().addSpecializationReferent(specializationReferent);
	}

	/**
	 * Each name listed in the specialization list for a classifier definition
	 * must have a single classifier referent. None of these referents may be
	 * templates.
	 **/
	public boolean classifierDefinitionSpecialization() {
		return this.getImpl().classifierDefinitionSpecialization();
	}

	/**
	 * The specialization referents of a classifier definition are the
	 * classifiers denoted by the names in the specialization list for the
	 * classifier definition.
	 **/
	public boolean classifierDefinitionSpecializationReferentDerivation() {
		return this.getImpl()
				.classifierDefinitionSpecializationReferentDerivation();
	}

	/**
	 * The members of a classifier definition include non-private members
	 * inherited from the classifiers it specializes. The visibility of
	 * inherited members is as specified in the UML Superstructure, Subclause
	 * 7.3.8. Elements inherited from external classifiers are treated as
	 * imported members.
	 **/
	public boolean classifierDefinitionInheritedMembers() {
		return this.getImpl().classifierDefinitionInheritedMembers();
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
		return this.getImpl().matchForStub(unit);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getSpecialization());
    }

	@Override
    public void _deriveAll() {
		this.getSpecializationReferent();
		super._deriveAll();
		QualifiedNameList specialization = this.getSpecialization();
		if (specialization != null) {
			specialization.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.classifierDefinitionSpecialization()) {
			violations.add(new ConstraintViolation(
					"classifierDefinitionSpecialization", this));
		}
		if (!this.classifierDefinitionSpecializationReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"classifierDefinitionSpecializationReferentDerivation",
					this));
		}
		if (!this.classifierDefinitionInheritedMembers()) {
			violations.add(new ConstraintViolation(
					"classifierDefinitionInheritedMembers", this));
		}
		QualifiedNameList specialization = this.getSpecialization();
		if (specialization != null) {
			specialization.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" isAbstract:");
		s.append(this.getIsAbstract());
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		QualifiedNameList specialization = this.getSpecialization();
		if (specialization != null) {
			System.out.println(prefix + " specialization:");
			specialization.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			Collection<ElementReference> specializationReferent = this
					.getSpecializationReferent();
			if (specializationReferent != null
					&& specializationReferent.size() > 0) {
				System.out.println(prefix + " /specializationReferent:");
				for (Object _object : specializationReferent.toArray()) {
					ElementReference _specializationReferent = (ElementReference) _object;
					System.out.println(prefix + "  "
							+ _specializationReferent.toString(includeDerived));
				}
			}
		}
	}
} // ClassifierDefinition

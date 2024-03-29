/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class Property extends StructuralFeature implements
		org.modeldriven.alf.uml.Property {
	public Property() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createProperty());
	}

	public Property(org.eclipse.uml2.uml.Property base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Property getBase() {
		return (org.eclipse.uml2.uml.Property) this.base;
	}

	public boolean getIsDerived() {
		return this.getBase().isDerived();
	}

	public void setIsDerived(boolean isDerived) {
		this.getBase().setIsDerived(isDerived);
	}

	public boolean getIsReadOnly() {
		return this.getBase().isReadOnly();
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
	}

	public boolean getIsDerivedUnion() {
		return this.getBase().isDerivedUnion();
	}

	public void setIsDerivedUnion(boolean isDerivedUnion) {
		this.getBase().setIsDerivedUnion(isDerivedUnion);
	}

	public String getAggregation() {
		return this.getBase().getAggregation().toString();
	}

	public void setAggregation(String aggregation) {
		this.getBase().setAggregation(
				org.eclipse.uml2.uml.AggregationKind.get(aggregation));
	}

	public boolean getIsComposite() {
		return this.getBase().isComposite();
	}

	public org.modeldriven.alf.uml.Association getOwningAssociation() {
		return (org.modeldriven.alf.uml.Association) wrap(this.getBase()
				.getOwningAssociation());
	}

	public org.modeldriven.alf.uml.DataType getDatatype() {
		return (org.modeldriven.alf.uml.DataType) wrap(this.getBase()
				.getDatatype());
	}

	public org.modeldriven.alf.uml.Association getAssociation() {
		return (org.modeldriven.alf.uml.Association) wrap(this.getBase()
				.getAssociation());
	}

	public void setAssociation(org.modeldriven.alf.uml.Association association) {
		this.getBase().setAssociation(
				association == null ? null : ((Association) association)
						.getBase());
	}

	public org.modeldriven.alf.uml.Class_ getClass_() {
		return (org.modeldriven.alf.uml.Class_) wrap(this.getBase().getClass_());
	}

	public org.modeldriven.alf.uml.Property getOpposite() {
		return (org.modeldriven.alf.uml.Property) wrap(this.getBase()
				.getOpposite());
	}

	public boolean getIsID() {
		return this.getBase().isID();
	}

	public void setIsID(boolean isID) {
		this.getBase().setIsID(isID);
	}

	@Override
	public org.modeldriven.alf.uml.ValueSpecification getDefaultValue() {
		return (org.modeldriven.alf.uml.ValueSpecification) wrap(this.getBase().getDefaultValue());
	}

	@Override
	public void setDefaultValue(org.modeldriven.alf.uml.ValueSpecification defaultValue) {
		this.getBase().setDefaultValue(defaultValue == null? null: ((ValueSpecification)defaultValue).getBase());
	}

	@Override
	public boolean isStereotypeBaseProperty() {
		return this.getBase().getAssociation() instanceof org.eclipse.uml2.uml.Extension;
	}

}

/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

public class Property extends StructuralFeature implements
		org.modeldriven.alf.uml.Property {
	public Property() {
		this(new fUML.Syntax.Classes.Kernel.Property());
	}

	public Property(fUML.Syntax.Classes.Kernel.Property base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Property getBase() {
		return (fUML.Syntax.Classes.Kernel.Property) this.base;
	}

	public boolean getIsDerived() {
		return this.getBase().isDerived;
	}

	public void setIsDerived(boolean isDerived) {
		this.getBase().isDerived = isDerived;
	}

	public boolean getIsReadOnly() {
		return this.getBase().isReadOnly;
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
	}

	public boolean getIsDerivedUnion() {
		return this.getBase().isDerivedUnion;
	}

	public void setIsDerivedUnion(boolean isDerivedUnion) {
		this.getBase().isDerivedUnion = isDerivedUnion;
	}

	public String getAggregation() {
		return this.getBase().aggregation.toString();
	}

	public void setAggregation(String aggregation) {
		this.getBase()
				.setAggregation(
						fUML.Syntax.Classes.Kernel.AggregationKind
								.valueOf(aggregation));
	}

	public boolean getIsComposite() {
		return this.getBase().isComposite;
	}

	public org.modeldriven.alf.uml.Association getOwningAssociation() {
		return (Association)wrap(this.getBase().owningAssociation);
	}

	public org.modeldriven.alf.uml.DataType getDatatype() {
		return (DataType)wrap(this.getBase().datatype);
	}

	public org.modeldriven.alf.uml.Association getAssociation() {
		return (Association)wrap(this.getBase().association);
	}

	public org.modeldriven.alf.uml.Class_ getClass_() {
		return (Class_)wrap(this.getBase().class_);
	}

	public org.modeldriven.alf.uml.Property getOpposite() {
		return (Property)wrap(this.getBase().opposite);
	}

	@Override
	public org.modeldriven.alf.uml.ValueSpecification getDefaultValue() {
		return null;
	}

	@Override
	public void setDefaultValue(org.modeldriven.alf.uml.ValueSpecification defaultValue) {
	}

	@Override
	public boolean isStereotypeBaseProperty() {
		return false;
	}

}

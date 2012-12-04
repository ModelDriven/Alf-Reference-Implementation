package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Property extends StructuralFeature implements
		org.modeldriven.alf.uml.Property {
	public Property() {
		this(UMLFactory.eINSTANCE.createProperty());
	}

	public Property(fUML.Syntax.Classes.Kernel.Property base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Property getBase() {
		return (org.eclipse.uml2.uml.Property) this.base;
	}

	public boolean getIsDerived() {
		return this.getBase().getIsDerived();
	}

	public void setIsDerived(boolean isDerived) {
		this.getBase().setIsDerived(isDerived);
	}

	public boolean getIsReadOnly() {
		return this.getBase().getIsReadOnly();
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
	}

	public boolean getIsDerivedUnion() {
		return this.getBase().getIsDerivedUnion();
	}

	public void setIsDerivedUnion(boolean isDerivedUnion) {
		this.getBase().setIsDerivedUnion(isDerivedUnion);
	}

	public String getAggregation() {
		return this.getBase().getAggregation().toString();
	}

	public void setAggregation(String aggregation) {
		this.getBase()
				.setAggregation(
						fUML.Syntax.Classes.Kernel.AggregationKind
								.valueOf(aggregation));
	}

	public boolean getIsComposite() {
		return this.getBase().getIsComposite();
	}

	public org.modeldriven.alf.uml.Association getOwningAssociation() {
		return new Association(this.getBase().getOwningAssociation());
	}

	public org.modeldriven.alf.uml.DataType getDatatype() {
		return new DataType(this.getBase().getDatatype());
	}

	public org.modeldriven.alf.uml.Association getAssociation() {
		return new Association(this.getBase().getAssociation());
	}

	public void setAssociation(org.modeldriven.alf.uml.Association association) {
		this.getBase().setAssociation(
				association == null ? null : ((Association) association)
						.getBase());
	}

	public org.modeldriven.alf.uml.Class_ getClass() {
		return new Class_(this.getBase().getClass());
	}

	public org.modeldriven.alf.uml.Property getOpposite() {
		return new Property(this.getBase().getOpposite());
	}

	public boolean getIsID() {
		return this.getBase().getIsID();
	}

	public void setIsID(boolean isID) {
		this.getBase().setIsID(isID);
	}

}

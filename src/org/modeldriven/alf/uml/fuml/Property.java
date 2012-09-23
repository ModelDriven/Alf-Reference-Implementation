package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Association;
import org.modeldriven.uml.fuml.DataType;
import org.modeldriven.uml.fuml.Property;
import org.modeldriven.uml.fuml.StructuralFeature;

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
		return new Association(this.getBase().owningAssociation);
	}

	public org.modeldriven.alf.uml.DataType getDatatype() {
		return new DataType(this.getBase().datatype);
	}

	public org.modeldriven.alf.uml.Association getAssociation() {
		return new Association(this.getBase().association);
	}

	public org.modeldriven.alf.uml.Class getClass_() {
		return new Class(this.getBase().class_);
	}

	public org.modeldriven.alf.uml.Property getOpposite() {
		return new Property(this.getBase().opposite);
	}

}

package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Association;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Property;
import org.modeldriven.alf.uml.StructuralFeature;

public interface Property extends StructuralFeature {
	public boolean getIsDerived();

	public void setIsDerived(boolean isDerived);

	public boolean getIsReadOnly();

	public void setIsReadOnly(boolean isReadOnly);

	public boolean getIsDerivedUnion();

	public void setIsDerivedUnion(boolean isDerivedUnion);

	public String getAggregation();

	public void setAggregation(String aggregation);

	public boolean getIsComposite();

	public Association getOwningAssociation();

	public DataType getDatatype();

	public Association getAssociation();

	public Class getClass_();

	public Property getOpposite();
}

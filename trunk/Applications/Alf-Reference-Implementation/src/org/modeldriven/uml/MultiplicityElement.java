package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.alf.uml.ValueSpecification;
import org.modeldriven.uml.Element;

public interface MultiplicityElement extends Element {
	public boolean getIsOrdered();

	public void setIsOrdered(boolean isOrdered);

	public boolean getIsUnique();

	public void setIsUnique(boolean isUnique);

	public int getUpper();

	public int getLower();

	public ValueSpecification getUpperValue();

	public void setUpperValue(ValueSpecification upperValue);

	public ValueSpecification getLowerValue();

	public void setLowerValue(ValueSpecification lowerValue);
}

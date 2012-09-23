package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.InstanceSpecification;
import org.modeldriven.alf.uml.ValueSpecification;

public interface InstanceValue extends ValueSpecification {
	public InstanceSpecification getInstance();

	public void setInstance(InstanceSpecification instance);
}

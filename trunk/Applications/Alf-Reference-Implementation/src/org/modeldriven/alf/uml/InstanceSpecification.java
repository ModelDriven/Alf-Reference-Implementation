package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Slot;
import org.modeldriven.uml.Classifier;
import org.modeldriven.uml.NamedElement;

public interface InstanceSpecification extends NamedElement {
	public List<Classifier> getClassifier();

	public void addClassifier(Classifier classifier);

	public List<Slot> getSlot();

	public void addSlot(Slot slot);
}

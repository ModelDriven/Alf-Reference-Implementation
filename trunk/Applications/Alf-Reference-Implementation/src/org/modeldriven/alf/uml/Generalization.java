package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.Classifier;
import org.modeldriven.uml.Element;

public interface Generalization extends Element {
	public boolean getIsSubstitutable();

	public void setIsSubstitutable(boolean isSubstitutable);

	public Classifier getSpecific();

	public Classifier getGeneral();

	public void setGeneral(Classifier general);
}

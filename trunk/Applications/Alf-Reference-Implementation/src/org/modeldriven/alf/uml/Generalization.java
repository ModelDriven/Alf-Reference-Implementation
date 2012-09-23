package org.modeldriven.alf.uml;


public interface Generalization extends Element {
	public boolean getIsSubstitutable();

	public void setIsSubstitutable(boolean isSubstitutable);

	public Classifier getSpecific();

	public Classifier getGeneral();

	public void setGeneral(Classifier general);
}

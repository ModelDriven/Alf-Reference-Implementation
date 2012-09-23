package org.modeldriven.alf.uml;


public interface AddStructuralFeatureValueAction extends
		WriteStructuralFeatureAction {
	public boolean getIsReplaceAll();

	public void setIsReplaceAll(boolean isReplaceAll);

	public InputPin getInsertAt();

	public void setInsertAt(InputPin insertAt);
}

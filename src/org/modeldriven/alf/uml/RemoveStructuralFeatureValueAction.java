package org.modeldriven.alf.uml;


public interface RemoveStructuralFeatureValueAction extends
		WriteStructuralFeatureAction {
	public boolean getIsRemoveDuplicates();

	public void setIsRemoveDuplicates(boolean isRemoveDuplicates);

	public InputPin getRemoveAt();

	public void setRemoveAt(InputPin removeAt);
}

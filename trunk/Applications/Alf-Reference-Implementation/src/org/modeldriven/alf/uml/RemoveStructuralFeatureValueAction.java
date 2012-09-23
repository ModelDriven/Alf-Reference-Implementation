package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.WriteStructuralFeatureAction;

public interface RemoveStructuralFeatureValueAction extends
		WriteStructuralFeatureAction {
	public boolean getIsRemoveDuplicates();

	public void setIsRemoveDuplicates(boolean isRemoveDuplicates);

	public InputPin getRemoveAt();

	public void setRemoveAt(InputPin removeAt);
}

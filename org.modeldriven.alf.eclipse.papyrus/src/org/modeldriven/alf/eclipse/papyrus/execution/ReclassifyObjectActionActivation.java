package org.modeldriven.alf.eclipse.papyrus.execution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IFeatureValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IReference;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.BasicActions.ActionActivation;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Object_;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ReclassifyObjectAction;

public class ReclassifyObjectActionActivation extends ActionActivation {

	@Override
	public void doAction() {
		// Get the value of the object input pin. If it is not a reference, then
		// do nothing. Otherwise, do the following.
		// Remove all types from the referent object that are in the set of old
		// classifiers but not the set of new classifiers (or just all types
		// that are not new classifiers, if isReplaceAll is true).
		// Remove the feature values from the referent object for all
		// classifiers that are removed.
		// Add all new classifiers as types of the referent object that are not
		// already types.
		// Add (empty) feature values to the referent object for the structural
		// features of all added classifiers.
		ReclassifyObjectAction action = (ReclassifyObjectAction) (this.node);
		List<Classifier> newClassifiers = action.getNewClassifiers();
		List<Classifier> oldClassifiers = action.getOldClassifiers();
		IValue input = this.takeTokens(action.getObject()).get(0);
		if (input instanceof IReference) {
			Object_ object = (Object_)((IReference) input).getReferent();
			int i = 1;
			while (i <= object.types.size()) {
				Class type = (Class) object.types.get(i - 1);
				boolean toBeRemoved = true;
				int j = 1;
				while (toBeRemoved & j <= newClassifiers.size()) {
					toBeRemoved = (type != newClassifiers.get(j - 1));
					j = j + 1;
				}
				if (toBeRemoved & !action.isReplaceAll()) {
					boolean notInOld = true;
					int k = 1;
					while (notInOld & k <= oldClassifiers.size()) {
						notInOld = (type != oldClassifiers.get(k - 1));
						k = k + 1;
					}
					toBeRemoved = !notInOld;
				}
				if (toBeRemoved) {
					object.types.remove(i - 1); // Apply fix for FUML12-21 ReclassifyObjectAction handles removal of structural features incorrect
				} else {
					i = i + 1;
				}
			}
			for (int n = 0; n < newClassifiers.size(); n++) {
				Classifier classifier = newClassifiers.get(n);
				boolean toBeAdded = true;
				int j = 1;
				while (toBeAdded & j <= object.types.size()) {
					toBeAdded = (classifier != object.types.get(j - 1));
					j = j + 1;
				}
				if (toBeAdded) {
					object.types.add((Class)classifier); // Apply fix for FUML12-21 ReclassifyObjectAction handles removal of structural features incorrect
				}
			}
			// Apply fix for FUML12-21 ReclassifyObjectAction handles removal of structural features incorrect
			List<IFeatureValue> oldFeatureValues = object.getFeatureValues();
			object.setFeatureValues(new ArrayList<IFeatureValue>());
			object.addFeatureValues(oldFeatureValues);
		}
	}
}

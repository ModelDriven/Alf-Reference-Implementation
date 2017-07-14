package org.modeldriven.alf.eclipse.papyrus.execution;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Activities.ExtraStructuredActivities.IExpansionActivationGroup;
import org.eclipse.papyrus.moka.fuml.Semantics.Activities.IntermediateActivities.IToken;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.ExtraStructuredActivities.ExpansionActivationGroup;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ActivityNodeActivationGroup;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Activities.IntermediateActivities.ControlNodeActivation;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class ActivityFinalNodeActivation extends ControlNodeActivation {

	@Override
	public void fire(List<IToken> incomingTokens) {
		// Terminate the activity execution or structured node activation
		// containing this activation.
		Debug.println("[fire] Activity final node " + this.node.getName() + "...");
		if (incomingTokens.size() > 0 | this.incomingEdges.size() == 0) {
			if (((ActivityNodeActivationGroup)this.getGroup()).activityExecution != null) {
				((ActivityNodeActivationGroup)this.getGroup()).activityExecution.terminate();
			} else if (this.getGroup().getContainingActivation() != null) {
				this.getGroup().getContainingActivation().terminateAll();
			} else if (this.getGroup() instanceof ExpansionActivationGroup) {
				((IExpansionActivationGroup) this.getGroup()).getRegionActivation().terminate();
			}
		}
	}
}

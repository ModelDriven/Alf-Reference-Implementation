/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.moka.execution;

import java.util.Collections;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;

public class RootExecution extends org.eclipse.papyrus.moka.fuml.control.execution.RootExecution {
	
	Operation constructor = null;
	
	public RootExecution(Class classifier, ILocus locus, IObject_ context){
		super(classifier, Collections.emptyList(), locus);
		this.setContext(context);
	}
	
	@Override
	public void execute() {
		if(this.classifier instanceof Behavior){
			List<IParameterValue> outputParameterValues = 
					this.locus.getExecutor().execute((Behavior)this.classifier, this.getContext(), this.parameterValues);
			for(IParameterValue parameterValue : outputParameterValues){
				this.setParameterValue(parameterValue);
			}
		} else if (this.classifier instanceof Class){
			Class class_ = (Class)this.classifier;			
			if (class_.isActive() && class_.getClassifierBehavior() !=null ) {
				// Execute the classifier behavior.
				this.getContext().startBehavior(class_, Collections.emptyList());
			}
		}
	}

}

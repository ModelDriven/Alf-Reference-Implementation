/*******************************************************************************
 * Copyright 2013, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms,
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.moka.execution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class UnimplementedBehaviorExecution extends OpaqueBehaviorExecution {

    public UnimplementedBehaviorExecution() {
        super(null);
        this.base = new BaseUnimplementedBehaviorExecution();
    }

    private class BaseUnimplementedBehaviorExecution 
    	extends org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {
    
    	@Override
    	public void doBody(List<IParameterValue> inputParameters,
    			List<IParameterValue> outputParameters) {
    		Debug.println("[error] Primitive behavior" + 
    				(this.types.size() == 0? "": " " + this.types.get(0).getName()) + 
    				" not implemented.");
    		outputParameters.get(0).setValues(new ArrayList<IValue>());
    	}

    	@Override
    	public IValue new_() {
    		return new BaseUnimplementedBehaviorExecution();
    	}
    }

}

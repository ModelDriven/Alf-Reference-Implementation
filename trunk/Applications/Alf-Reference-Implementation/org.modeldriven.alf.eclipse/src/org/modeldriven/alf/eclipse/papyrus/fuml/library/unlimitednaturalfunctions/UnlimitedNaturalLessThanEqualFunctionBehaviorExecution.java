/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.library.unlimitednaturalfunctions;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class UnlimitedNaturalLessThanEqualFunctionBehaviorExecution extends
    UnlimitedNaturalRelationalFunctionBehaviorExecution {

	@Override
    public boolean doUnlimitedNaturalFunction(List<Integer> arguments) {
		
		int i1 = arguments.get(0);
		int i2 = arguments.get(1);
		
    	// This function returns true if i1 <= i2, where a value of -1 means 
		// "unbounded", which is the highest possible value.
    	
		boolean result = i1 == i2 || i2 < 0 || i1 >= 0 && i1 < i2;
    	
    	Debug.println("[doBody] Unlimited Natural Less Than or Equal result = " + result);
    	return result;
    }

	@Override
    public Value new_() {
        return new UnlimitedNaturalLessThanEqualFunctionBehaviorExecution();
    }

} // UnlimitedNaturalLessThanEqualFunctionBehaviorExecution

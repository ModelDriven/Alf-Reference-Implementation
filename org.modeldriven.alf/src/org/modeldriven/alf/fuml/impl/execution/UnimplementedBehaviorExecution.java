/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms,
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.execution;

import org.modeldriven.fuml.library.LibraryFunctions;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class UnimplementedBehaviorExecution extends OpaqueBehaviorExecution {

    public UnimplementedBehaviorExecution() {
        super(null);
        this.base = new BaseUnimplementedBehaviorExecution();
    }

    private class BaseUnimplementedBehaviorExecution extends 
        fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {
        
        @Override
        public void doBody(ParameterValueList inputParameters,
                ParameterValueList outputParameters) {
            /*
            throw new Error("Primitive behavior" + 
                    (this.types.size() == 0? "": " " + this.types.get(0).name) + 
                    " not implemented.") ;
            */
            Debug.println("[error] Primitive behavior" + 
                    (this.types.size() == 0? "": " " + this.types.get(0).name) + 
                    " not implemented.");
            LibraryFunctions.addEmptyValueListToOutputList(outputParameters);
        }

        @Override
        public Value new_() {
            return new BaseUnimplementedBehaviorExecution();
        }
        
    }
}

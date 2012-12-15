/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.execution;

import org.modeldriven.alf.uml.Class_;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class Object_ implements org.modeldriven.alf.fuml.execution.Object_ {
    
    private fUML.Semantics.Classes.Kernel.Object_ base = null;
    
    public Object_(fUML.Semantics.Classes.Kernel.Object_ base) {
        this.base = base;
    }
    
    public fUML.Semantics.Classes.Kernel.Object_ getBase() {
        return this.base;
    }

    @Override
    public void startBehavior(Class_ classifier) {
        this.base.startBehavior(
                ((org.modeldriven.alf.fuml.impl.uml.Class_)classifier).getBase(), 
                new ParameterValueList());
    }
    
    public String toString() {
        return this.getBase().toString();
    }

}

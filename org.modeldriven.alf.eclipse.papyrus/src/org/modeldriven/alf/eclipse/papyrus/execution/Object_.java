/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.execution;

import java.util.ArrayList;

import org.modeldriven.alf.uml.Class_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;

public class Object_ implements org.modeldriven.alf.fuml.execution.Object_ {
    
    private IObject_ base = null;
    
    public Object_(IObject_ base) {
        this.base = base;
    }
    
    public IObject_ getBase() {
        return this.base;
    }

    @Override
    public void startBehavior(Class_ classifier) {
        this.base.startBehavior(
                ((org.modeldriven.alf.eclipse.uml.Class_)classifier).getBase(), 
                new ArrayList<IParameterValue>());
    }
    
    public String toString() {
        return this.getBase().toString();
    }

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.execution;

import org.modeldriven.alf.execution.fuml.Object_;
import org.modeldriven.alf.uml.Behavior;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class Executor implements org.modeldriven.alf.execution.fuml.Executor {
    
    private fUML.Semantics.Loci.LociL1.Executor base = null;
    
    public Executor() {
        this(new fUML.Semantics.Loci.LociL1.Executor());
    }
    
    public Executor(fUML.Semantics.Loci.LociL1.Executor base) {
        this.base = base;
    }
    
    public fUML.Semantics.Loci.LociL1.Executor getBase() {
        return this.base;
    }

    @Override
    public void execute(Behavior behavior, Object_ context) {
        this.base.execute(
                ((org.modeldriven.alf.fuml.impl.uml.Behavior)behavior).getBase(), 
                context == null? null:
                    ((org.modeldriven.alf.fuml.impl.execution.Object_)context).getBase(),
                new ParameterValueList());
    }

}

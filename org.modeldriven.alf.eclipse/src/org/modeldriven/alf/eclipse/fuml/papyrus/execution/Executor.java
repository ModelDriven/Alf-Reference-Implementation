/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.fuml.papyrus.execution;

import java.util.ArrayList;

import org.modeldriven.alf.fuml.execution.Object_;
import org.modeldriven.alf.uml.Behavior;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;

public class Executor implements org.modeldriven.alf.fuml.execution.Executor {
    
    private org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Executor base = null;
    
    public Executor() {
        this(new org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Executor());
    }
    
    public Executor(org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Executor base) {
        this.base = base;
    }
    
    public org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Executor getBase() {
        return this.base;
    }

    @Override
    public void execute(Behavior behavior, Object_ context) {
        this.base.execute(
                ((org.modeldriven.alf.eclipse.uml.Behavior)behavior).getBase(), 
                context == null? null:
                    ((org.modeldriven.alf.eclipse.fuml.papyrus.execution.Object_)context).getBase(),
                new ArrayList<ParameterValue>());
    }

}

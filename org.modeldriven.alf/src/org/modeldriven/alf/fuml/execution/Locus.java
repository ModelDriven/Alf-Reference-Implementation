/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.execution;

import java.util.List;

import org.modeldriven.alf.uml.Class_;

public interface Locus {
    public ExecutionFactory getFactory();

    public Executor getExecutor();

    public Object_ instantiate(Class_ type);
    
    public List<? extends Object_> getExtent(Class_ class_);
}

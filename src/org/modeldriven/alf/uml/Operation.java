/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

public interface Operation extends BehavioralFeature {

    public Type getType();
    public Integer getLower();
    public Integer getUpper();
    
    // NOTE: These operations are necessary because there is no standard way
    // in the UML abstract syntax to access stereotype applications.
    public boolean isConstructor(); // i.e, has <<Create>> stereotype applied
    public boolean isDestructor(); // i.e., has <<Destroy>> stereotype applied

}

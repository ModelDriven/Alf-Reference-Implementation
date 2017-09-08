/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.library;

import java.util.List;

public interface ParameterValue {
    public List<? extends Value> getValues();
    public void addValue(Value value);
    
    public List<Object> getObjects();    
    public void addBooleanValue(boolean value);
    public void addIntegerValue(int value);
    public void addRealValue(double value);
    public void addStringValue(String value);
    public void addUnlimitedNaturalValue(int value);
    public void addBitStringValue(int value);
    public void addNaturalValue(int value);
    
    public default void addValues(List<? extends Value> list) {
        for (Value value: list) {
            this.addValue(value);
        }
    }
}

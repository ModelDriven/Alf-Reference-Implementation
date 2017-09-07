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
    public List<Object> getValues();    
    public void addValue(Object value);
    
    public void addBooleanValue(boolean value);
    public void addIntegerValue(int value);
    public void addRealValue(double value);
    public void addStringValue(String value);
    public void addUnlimitedNaturalValue(int value);
    public void addBitStringValue(int value);
    public void addNaturalValue(int value);
    
    public default void addValues(List<Object> values) {
        for (Object value: values) {
            this.addValue(value);
        }
    }
}

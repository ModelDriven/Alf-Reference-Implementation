
/*******************************************************************************
 * Copyright 2011-2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.TaggedValue;

/**
 * An assignment of a value to an attribute of an applied stereotype.
 **/

public class TaggedValueImpl extends SyntaxElementImpl {

	private String name = "";
	private String value = "";
	private String operator = "";

	public TaggedValueImpl(TaggedValue self) {
		super(self);
	}

	@Override
	public TaggedValue getSelf() {
		return (TaggedValue) this.self;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

    /*
     * Helper Methods
     */

	public ElementReference getType() {
	    RootNamespace rootScope = RootNamespace.getRootScope();
	    return this.isBooleanValue()? rootScope.getBooleanType():
               this.isStringValue()? rootScope.getStringType():
               this.isRealValue()? rootScope.getRealType():
	           this.isUnboundedValue()? rootScope.getUnlimitedNaturalType():
	               rootScope.getNaturalType();
	}
	public boolean isBooleanValue() {
	    String value = this.getSelf().getValue();
	    return "true".equals(value) || "false".equals(value);
	}
	
    public boolean isNaturalValue() {
        String value = this.getSelf().getValue();
        return value != null && value.length() > 1 && 
                value.charAt(0) >= '0' && value.charAt(0) <= '9' &&
                !value.contains(".") && !value.contains("e") && !value.contains("E");       
    }
    
    public boolean isRealValue() {
        String value = this.getSelf().getValue();
        return value != null && value.length() > 1 && 
                (value.charAt(0) == '.' || 
                 value.charAt(0) >= '0' && value.charAt(0) <= '9' &&
                 (value.contains(".") || value.contains("e")  || value.contains("E")));       
    }
    
    public boolean isUnboundedValue() {
        String value = this.getSelf().getValue();
        return "*".equals(value);
    }
    
    public boolean isStringValue() {
        String value = this.getSelf().getValue();
        return value != null && value.length() > 1 && value.charAt(0) == '"';       
    }
    
}

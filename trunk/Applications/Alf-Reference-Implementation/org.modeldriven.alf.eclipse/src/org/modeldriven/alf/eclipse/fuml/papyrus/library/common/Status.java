/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.fuml.papyrus.library.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.DataValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.PrimitiveValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

public class Status {
	
	private PrimitiveType stringType = null;
	private PrimitiveType integerType = null;

	private String context;
	private int code;
	private String description;
	
	static private DataType statusType = null;
	
	static public DataType getStatusType() {
	    /*
		if (statusType == null) {
			statusType = (DataType)Library.getInstance().lookup("Common-Status");
		}
		*/
		return statusType;
	}
	
	static public void setStatusType(DataType type) {
		statusType = type;
	}
	
	public Status(Locus locus, String context, int code, String description) {
		this.setPrimitiveTypes(locus);
		this.setStatus(context, code, description);
	}
	
	public Status(Locus locus, String context) {
		this.setPrimitiveTypes(locus);
		this.setStatus(context, 0, "Normal");
	}
	
	private void setPrimitiveTypes(Locus locus) {
		this.stringType = locus.factory.getBuiltInType("String");
		this.integerType = locus.factory.getBuiltInType("Integer");
	}
	
	public boolean isNormal() {
		return this.code == 0;
	}
	
	public void setStatus(String context, int code, String description) {
		this.context = context;
		this.code = code;
		this.description = description;
	}
	
	public Value getValue() {
		DataValue value = new DataValue();
		DataType statusType = getStatusType();
		value.type = statusType;
		for (Property attribute: statusType.getOwnedAttributes()) {
			PrimitiveValue attributeValue = null;
			if (attribute.getName().equals("context")) {
				attributeValue = new StringValue();
				attributeValue.type = this.stringType;
				((StringValue)attributeValue).value = this.context;
			} else if (attribute.getName().equals("code")) {
				attributeValue = new IntegerValue();
				attributeValue.type = this.integerType;
				((IntegerValue)attributeValue).value = this.code;
			} else if (attribute.getName().equals("description")) {
				attributeValue = new StringValue();
				attributeValue.type = this.stringType;
				((StringValue)attributeValue).value = this.description;
			}
			List<Value> values = new ArrayList<Value>();
			values.add(attributeValue);
			value.setFeatureValue(attribute, values, 0);
		}
		return value;
	}
	
}

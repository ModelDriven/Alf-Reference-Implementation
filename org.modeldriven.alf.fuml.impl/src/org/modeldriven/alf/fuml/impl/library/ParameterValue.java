/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.impl.library;

import java.util.ArrayList;
import java.util.List;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.RealValue;
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;

public class ParameterValue implements org.modeldriven.alf.fuml.library.ParameterValue {
	
	private final fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue base;
	private final fUML.Semantics.Loci.LociL1.Locus locus;
	
	public ParameterValue(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue base,
			fUML.Semantics.Loci.LociL1.Locus locus) {
		this.base = base;
		this.locus = locus;
	}

	@Override
	public List<? extends org.modeldriven.alf.fuml.library.Value> getValues() {
		List<org.modeldriven.alf.fuml.library.Value> values = 
				new ArrayList<org.modeldriven.alf.fuml.library.Value>();
		for (fUML.Semantics.Classes.Kernel.Value value: this.base.values) {
			values.add(new Value(value));
		}
		return values;
	}

	@Override
	public List<Object> getObjects() {
		List<Object> values = new ArrayList<Object>();
		for (fUML.Semantics.Classes.Kernel.Value value: this.base.values) {
			values.add(objectFor(value));
		}
		return values;
	}

	private static Object objectFor(fUML.Semantics.Classes.Kernel.Value value) {
		return value instanceof IntegerValue? ((IntegerValue)value).value:
			   value instanceof RealValue? ((RealValue)value).value:
			   value instanceof BooleanValue? ((BooleanValue)value).value:
			   value instanceof StringValue? ((StringValue)value).value:
			   value instanceof UnlimitedNaturalValue? ((UnlimitedNaturalValue)value).value:
			   value;
	}

	@Override
	public void addValue(org.modeldriven.alf.fuml.library.Value value) {
		this.addBaseValue(((Value)value).getBase());
	}
	
	private void addBaseValue(fUML.Semantics.Classes.Kernel.Value value) {
		this.base.values.add(value);
	}
	
	@Override
	public void addBooleanValue(boolean value) {
		BooleanValue booleanValue = new BooleanValue();
		booleanValue.value = value;
		booleanValue.type = this.locus.factory.getBuiltInType("Boolean");
		this.addBaseValue(booleanValue);
	}

	@Override
	public void addIntegerValue(int value) {
		IntegerValue integerValue = new IntegerValue();
		integerValue.value = value;
		integerValue.type = this.locus.factory.getBuiltInType("Integer");
		this.addBaseValue(integerValue);
	}

	@Override
	public void addRealValue(double value) {
		RealValue realValue = new RealValue();
		realValue.value = (float)value;
		realValue.type = this.locus.factory.getBuiltInType("Real");
		this.addBaseValue(realValue);
	}

	@Override
	public void addStringValue(String value) {
		StringValue stringValue = new StringValue();
		stringValue.value = value;
		stringValue.type = this.locus.factory.getBuiltInType("String");
		this.addBaseValue(stringValue);
	}

	@Override
	public void addUnlimitedNaturalValue(int value) {
		UnlimitedNaturalValue integerValue = new UnlimitedNaturalValue();
		integerValue.value = new UnlimitedNatural();
		integerValue.value.naturalValue = value;
		integerValue.type = this.locus.factory.getBuiltInType("UnlimitedNatural");
		this.addBaseValue(integerValue);
	}

	@Override
	public void addBitStringValue(int value) {
		IntegerValue integerValue = new IntegerValue();
		integerValue.value = value;
		integerValue.type = this.locus.factory.getBuiltInType("BitString");
		this.addBaseValue(integerValue);
	}

	@Override
	public void addNaturalValue(int value) {
		IntegerValue integerValue = new IntegerValue();
		integerValue.value = value;
		integerValue.type = this.locus.factory.getBuiltInType("Natural");
		this.addBaseValue(integerValue);
	}
	
}

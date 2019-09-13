/*******************************************************************************
 * Copyright 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.moka.library;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IBooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IIntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IRealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IStringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IUnlimitedNaturalValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IParameterValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.BooleanValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.IntegerValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.RealValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.UnlimitedNaturalValue;
import org.eclipse.uml2.uml.PrimitiveType;

public class ParameterValue implements org.modeldriven.alf.fuml.library.ParameterValue {
	
	private final IParameterValue base;
	private final ILocus locus;
	
	public ParameterValue(IParameterValue base, ILocus locus) {
		this.base = base;
		this.locus = locus;
	}
	
	@Override
	public List<? extends org.modeldriven.alf.fuml.library.Value> getValues() {
		List<org.modeldriven.alf.fuml.library.Value> values = 
				new ArrayList<org.modeldriven.alf.fuml.library.Value>();
		for (IValue value: this.base.getValues()) {
			values.add(new Value(value));
		}
		return values;
	}

	@Override
	public List<Object> getObjects() {
		List<Object> values = new ArrayList<Object>();
		for (IValue value: this.base.getValues()) {
			values.add(valueOf(value));
		}
		return values;
	}

	private static Object valueOf(IValue value) {
		return value instanceof IIntegerValue? ((IIntegerValue)value).getValue():
			   value instanceof IRealValue? ((IRealValue)value).getValue():
			   value instanceof IBooleanValue? ((IBooleanValue)value).getValue():
			   value instanceof IStringValue? ((IStringValue)value).getValue():
			   value instanceof IUnlimitedNaturalValue? ((IUnlimitedNaturalValue)value).getValue():
			   value;
	}

	@Override
	public void addValue(org.modeldriven.alf.fuml.library.Value value) {
		this.addBaseValue(((Value)value).getBase());
	}
	
	private void addBaseValue(IValue value) {
		this.base.getValues().add(value);
	}
		
	@Override
	public void addBooleanValue(boolean value) {
		IBooleanValue booleanValue = new BooleanValue();
		booleanValue.setValue(value);
		booleanValue.setType((PrimitiveType)this.locus.getFactory().getBuiltInType("Boolean"));
		this.addBaseValue(booleanValue);
	}

	@Override
	public void addIntegerValue(int value) {
		IIntegerValue integerValue = new IntegerValue();
		integerValue.setValue(value);
		integerValue.setType((PrimitiveType)this.locus.getFactory().getBuiltInType("Integer"));
		this.addBaseValue(integerValue);
	}

	@Override
	public void addRealValue(double value) {
		IRealValue realValue = new RealValue();
		realValue.setValue(value);
		realValue.setType((PrimitiveType)this.locus.getFactory().getBuiltInType("Real"));
		this.addBaseValue(realValue);
	}

	@Override
	public void addStringValue(String value) {
		IStringValue stringValue = new StringValue();
		stringValue.setValue(value);
		stringValue.setType((PrimitiveType)this.locus.getFactory().getBuiltInType("String"));
		this.addBaseValue(stringValue);
	}

	@Override
	public void addUnlimitedNaturalValue(int value) {
		IUnlimitedNaturalValue integerValue = new UnlimitedNaturalValue();
		integerValue.setValue(value);
		integerValue.setType((PrimitiveType)this.locus.getFactory().getBuiltInType("UnlimitedNatural"));
		this.addBaseValue(integerValue);
	}

	@Override
	public void addBitStringValue(int value) {
		IIntegerValue integerValue = new IntegerValue();
		integerValue.setValue(value);
		integerValue.setType((PrimitiveType)this.locus.getFactory().getBuiltInType("BitString"));
		this.addBaseValue(integerValue);
	}

	@Override
	public void addNaturalValue(int value) {
		IIntegerValue integerValue = new IntegerValue();
		integerValue.setValue(value);
		integerValue.setType((PrimitiveType)this.locus.getFactory().getBuiltInType("Natural"));
		this.addBaseValue(integerValue);
	}
	
}

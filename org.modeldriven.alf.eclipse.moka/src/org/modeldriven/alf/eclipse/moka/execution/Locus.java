/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.moka.execution;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.uml.Class_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IObject_;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutionFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutor;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ILocus;

public class Locus implements org.modeldriven.alf.fuml.execution.Locus {

	private ILocus base = null;

	public Locus() {
		this.base = new org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.Locus();
		this.base.setExecutor(new org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.Executor());
		this.base.setFactory(new org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL3.ExecutionFactoryL3());
	}

	public Locus(ILocus base) {
		this.base = base;
	}

	public ILocus getBase() {
		return this.base;
	}

	@Override
	public List<Object_> getExtent(Class_ class_) {
		List<Object_> list = new ArrayList<Object_>();
		for (org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IExtensionalValue object: 
			this.getBase().getExtent(((org.modeldriven.alf.eclipse.uml.Class_)class_).getBase())) {
			list.add(new Object_((org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.Object_)object));
		}
		return list;
	}

	@Override
	public ExecutionFactory getFactory() {
		return this.base.getFactory() == null? null: new ExecutionFactory(
				(org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.ExecutionFactory)this.base.getFactory());
	}

	@Override
	public Executor getExecutor() {
		return this.base.getExecutor() == null? null: new Executor(this.base.getExecutor());
	}

	@Override
	public Object_ instantiate(Class_ type) {
		IObject_ object_ = type == null? null: 
			this.base.instantiate(((org.modeldriven.alf.eclipse.uml.Class_)type).getBase());
		return object_ == null? null: new Object_(object_);
	}

	public void setExecutor(IExecutor executor) {
		this.base.setExecutor(executor);
	}

	public void setFactory(IExecutionFactory factory) {
		this.base.setFactory(factory);
	}

	public void add(IObject_ object) {
		if (object != null) {
			this.base.add(object);
		}
	}

}

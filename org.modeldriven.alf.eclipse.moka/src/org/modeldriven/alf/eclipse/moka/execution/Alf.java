/*******************************************************************************
 * Copyright 2013-2017 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.eclipse.moka.execution;

import org.apache.log4j.Logger;
import org.modeldriven.alf.eclipse.moka.library.channel.StandardInputChannelObject;
import org.modeldriven.alf.eclipse.moka.library.channel.StandardOutputChannelObject;
import org.modeldriven.alf.eclipse.moka.library.common.Status;
import org.modeldriven.alf.eclipse.moka.library.libraryclass.ImplementationObject;
import org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.ElementFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.IOpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.IntermediateActions.DefaultCreateObjectActionStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.IntermediateActions.DefaultGetAssociationStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.FirstChoiceStrategy;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class Alf extends org.modeldriven.alf.fuml.execution.Alf {

	@Override
	public void setDebugLevel(String level) {
		super.setDebugLevel(level);
		Logger logger = Logger.getLogger(Debug.class);
		logger.setLevel(this.debugLevel);
	}

	@Override
	protected Locus createLocus() {
		Locus locus = new Locus();
		org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.IExecutionFactory factory = locus.getFactory().getBase(); 
		factory.setStrategy(new RedefinitionBasedDispatchStrategy());
		factory.setStrategy(new FIFOGetNextEventStrategy());
		factory.setStrategy(new FirstChoiceStrategy());       
		factory.setStrategy(new DefaultCreateObjectActionStrategy());
		factory.setStrategy(new DefaultGetAssociationStrategy());

		return locus;
	}

	@Override
	protected ElementFactory createElementFactory() {
		return org.modeldriven.alf.eclipse.uml.Element.FACTORY;
	}

	@Override
	protected OpaqueBehaviorExecution getUnimplementedBehaviorExecution() {
		return new UnimplementedBehaviorExecution();
	}

	@Override
	protected OpaqueBehaviorExecution getOpaqueBehaviorExecution(Object object) {
		IOpaqueBehaviorExecution execution =
				object instanceof org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution?
						new org.modeldriven.alf.eclipse.moka.library.OpaqueBehaviorExecution(
								(org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution)object):
						(IOpaqueBehaviorExecution)object;
		return new org.modeldriven.alf.eclipse.moka.execution.OpaqueBehaviorExecution(execution);
	}

	@Override 
	protected String getPrototypeClassName(Member definition, String prototypeName) {
		return prototypeName.contains(".")? prototypeName:
			"org.modeldriven.alf.fuml.library." + 
				definition.getNamespace().getName().toLowerCase() + "." + prototypeName;
	}

	@Override
	protected void createSystemServices() {
		QualifiedName standardOutputChannel = 
				RootNamespace.getRootScope().getBasicInputOutput().getImpl().copy().
				addName("StandardOutputChannel");
		this.createSystemService
		(standardOutputChannel, new StandardOutputChannelObject());

		QualifiedName standardInputChannel = 
				RootNamespace.getRootScope().getBasicInputOutput().getImpl().copy().
				addName("StandardInputChannel");
		this.createSystemService
		(standardInputChannel, new StandardInputChannelObject());

		QualifiedName status = 
				RootNamespace.getRootScope().getBasicInputOutput().getImpl().copy().
				addName("Status");
		Classifier statusType = getClassifier(status);
		if (statusType instanceof DataType) {
			Status.setStatusType(((org.modeldriven.alf.eclipse.uml.DataType)statusType).getBase());
		} else {
			System.out.println("Cannot find Status datatype.");
		}
	}

	private void createSystemService (
			QualifiedName name,
			ImplementationObject object) {
		Classifier type = getClassifier(name);
		if (type instanceof Class_) {
			org.eclipse.uml2.uml.Class class_ = 
					((org.modeldriven.alf.eclipse.uml.Class_)type).getBase();
			object.types.add(class_);
			((Locus)this.getLocus()).add(object);
			printVerbose("Instantiated " + name.getPathName() + 
					" as " + object.getClass().getName());
		}
	}

	public static void main(String[] args) {
		new Alf().run(args);
	}
}

/*******************************************************************************
 * Copyright 2013-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.execution;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.IntermediateActions.DefaultCreateObjectActionStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Actions.IntermediateActions.DefaultGetAssociationStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.FirstChoiceStrategy;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.modeldriven.alf.eclipse.papyrus.library.channel.StandardInputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.channel.StandardOutputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.library.libraryclass.ImplementationObject;
import org.modeldriven.alf.eclipse.units.RootNamespaceImpl;
import org.modeldriven.alf.fuml.execution.Locus;
import org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution;
import org.modeldriven.alf.fuml.execution.Object_;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.OpaqueBehavior;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Package;
import org.modeldriven.alf.uml.Parameter;
import org.modeldriven.alf.uml.PrimitiveType;

public class Fuml {

	private String umlDirectory = "UML";
	private boolean isVerbose = false;

	private final RootNamespaceImpl rootScopeImpl = new RootNamespaceImpl();

	private org.modeldriven.alf.eclipse.papyrus.execution.Locus locus;

	public org.modeldriven.alf.eclipse.papyrus.execution.Locus getLocus() {
		return this.locus;
	}

	public void setUmlLibraryDirectory(String umlLibraryDirectory) {
		this.rootScopeImpl.setLibraryDirectory(umlLibraryDirectory);
	}

	public void setUmlDirectory(String umlDirectory) {
		this.umlDirectory = umlDirectory;
	}

	public void setIsVerbose(boolean isVerbose) {
		this.isVerbose = isVerbose;
		this.rootScopeImpl.setIsVerbose(this.isVerbose);
	}

	private void createLocus() {
		this.locus = new org.modeldriven.alf.eclipse.papyrus.execution.Locus();
		org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL1.ExecutionFactory factory = locus.getFactory().getBase(); 
		factory.setStrategy(new RedefinitionBasedDispatchStrategy());
		factory.setStrategy(new FIFOGetNextEventStrategy());
		factory.setStrategy(new FirstChoiceStrategy());       
		factory.setStrategy(new DefaultCreateObjectActionStrategy());
		factory.setStrategy(new DefaultGetAssociationStrategy());
	}

	private void addPrimitiveTypes() {
		try {
			Package primitiveTypes = this.getPackage(
					"Alf::Library::PrimitiveTypes");
			for (NamedElement element: primitiveTypes.getMember()) {
				if (element instanceof PrimitiveType) {
					this.locus.getFactory().addBuiltInType((PrimitiveType)element);
					this.printVerbose("Added " + element.getQualifiedName());
				}
			}
		} catch (ElementResolutionError e) {
			this.println(e.getMessage());
		}
	}

	private void addPrimitiveBehaviorPrototypes() {
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::Or", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.Or.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::Xor", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.Xor.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::And", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.And.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::Not", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.Not.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::Implies", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.Implies.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::ToString", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.ToString.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::ToBoolean", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.ToBoolean.class);

		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Neg", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Neg.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Abs", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Abs.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::+", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Add.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::-", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Minus.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::*", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Times.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::/", 
				org.modeldriven.alf.eclipse.papyrus.library.integerfunctions.Divide.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Div", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Div.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Mod", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Mod.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Max", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Max.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Min", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Min.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::<", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Lower.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::>", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Greater.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::<=", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.LowerOrEqual.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::>=", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.GreaterOrEqual.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::ToString", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.ToString.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::ToUnlimitedNatural", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.ToUnlimitedNatural.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::ToInteger", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.ToInteger.class);

		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::Neg", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Neg.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::Abs", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Abs.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::Inv", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Abs.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::+", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Add.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::-", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Minus.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::*", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Times.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::/", 
				org.modeldriven.alf.eclipse.papyrus.library.realfunctions.Divide.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::Floor", 
				org.modeldriven.alf.eclipse.papyrus.library.realfunctions.Floor.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::Round", 
				org.modeldriven.alf.eclipse.papyrus.library.realfunctions.Round.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::Max", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Max.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::Min", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Min.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::<", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Lower.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::>", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.Greater.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::<=", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.LowerOrEqual.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::>=", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.GreaterOrEqual.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::ToString", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.ToString.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::ToReal", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.real.ToReal.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::RealFunctions::ToInteger", 
				org.modeldriven.alf.eclipse.papyrus.library.realfunctions.ToInteger.class);

		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::ListFunctions::ListSize", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.list.ListSize.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::ListFunctions::ListGet", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.list.ListGet.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::ListFunctions::ListConcat", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.list.ListConcat.class);

		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::StringFunctions::Concat", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.string.Concat.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::StringFunctions::Size", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.string.Size.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::StringFunctions::Substring", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.string.Substring.class);

		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::Max", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.Max.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::Min", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.Min.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::<", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.Lower.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::>", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.Greater.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::<=", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.LowerOrEqual.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::>=", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.GreaterOrEqual.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::ToString", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.ToString.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::ToInteger", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.ToInteger.class);
		this.createPrimitiveBehaviorPrototype(
				"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::ToUnlimitedNatural", 
				org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.ToUnlimitedNatural.class);

		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::IsSet", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringIsSetFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::BitLength", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringBitLengthFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::ToBitString", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringToBitStringFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::ToInteger", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringToIntegerFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::ToHexString", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringToHexStringFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::ToOctalString", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringToOctalStringFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::~", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringComplementFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::|", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringOrFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::^", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringXorFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::&", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringAndFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::<<", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringShiftLeftFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::>>", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringShiftRightFunctionBehaviorExecution.class);
		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::BitStringFunctions::>>>", 
				org.modeldriven.alf.fuml.library.bitstringfunctions.BitStringUnsignedShiftRightFunctionBehaviorExecution.class);

		this.createAlfLibraryBehaviorPrototype(
				"Alf::Library::PrimitiveBehaviors::IntegerFunctions::ToNatural", 
				org.modeldriven.alf.fuml.library.integerfunctions.IntegerToNaturalFunctionBehaviorExecution.class);
	}

	private void createAlfLibraryBehaviorPrototype(
			String behaviorName, 
			Class<? extends org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution> libraryClass) {
		org.modeldriven.alf.eclipse.papyrus.library.OpaqueBehaviorExecution prototype = null;
		try {
			prototype = new org.modeldriven.alf.eclipse.papyrus.library.OpaqueBehaviorExecution(libraryClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
		}
		this.addPrimitiveBehaviorPrototype(behaviorName, prototype);
	}

	private void createPrimitiveBehaviorPrototype(
			String behaviorName, 
			Class<? extends org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution> prototypeClass) {
		org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution prototype = null;
		try {
			prototype = prototypeClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
		}
		this.addPrimitiveBehaviorPrototype(behaviorName, prototype);
	}

	private void addPrimitiveBehaviorPrototype(
			String behaviorName, 
			org.eclipse.papyrus.moka.fuml.Semantics.impl.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution prototype) {
		try {
			Classifier behavior = this.getClassifier(behaviorName);
			if (behavior instanceof OpaqueBehavior) {
				OpaqueBehaviorExecution execution = null;		        
				execution = prototype == null? new UnimplementedBehaviorExecution():
					new org.modeldriven.alf.eclipse.papyrus.execution.OpaqueBehaviorExecution(prototype);
				execution.addType((OpaqueBehavior)behavior);

				this.locus.getFactory().addPrimitiveBehaviorPrototype(execution);
				this.printVerbose("Added " + behavior.getQualifiedName());
			}
		} catch (ElementResolutionError e) {
			this.println(e.getMessage());
		}
	}

	protected void createSystemServices() {
		String basicInputOutput = "FoundationalModelLibrary::BasicInputOutput";

		try {
			Classifier standardOutputChannel = this.getClassifier(
					basicInputOutput + "::StandardOutputChannel");
			this.createSystemService
			(standardOutputChannel, new StandardOutputChannelObject());
		} catch (ElementResolutionError e) {
			this.println(e.getMessage());
		}

		try {
			Classifier standardInputChannel = this.getClassifier(
					basicInputOutput + "::StandardInputChannel");
			this.createSystemService
			(standardInputChannel, new StandardInputChannelObject());
		} catch (ElementResolutionError e) {
			this.println(e.getMessage());
		}

		try {
			Classifier statusType = this.getClassifier(
					"FoundationalModelLibrary::Common::Status");
			if (statusType instanceof DataType) {
				Status.setStatusType(((org.modeldriven.alf.eclipse.uml.DataType)statusType).getBase());
			} else {
				this.println("Cannot find Status datatype.");
			}
		} catch (ElementResolutionError e) {
			this.println(e.getMessage());
		}
	}

	private void createSystemService (
			Classifier type,
			ImplementationObject object) {
		if (type instanceof Class_) {
			org.eclipse.uml2.uml.Class class_ = 
					((org.modeldriven.alf.eclipse.uml.Class_)type).getBase();
			object.types.add(class_);
			this.locus.add(object);
			this.printVerbose("Instantiated " + type.getQualifiedName() + 
					" as " + object.getClass().getName());
		}
	}

	public class ElementResolutionError extends Exception {

		private static final long serialVersionUID = 1L;

		public ElementResolutionError(String message) {
			super(message);
		}

	}

	public Package getPackage(String qualifiedName) throws ElementResolutionError {
		Element element = this.getElement(qualifiedName);
		if (!(element instanceof Package)) {
			throw new ElementResolutionError(qualifiedName + " is not a Package.");
		} else {
			return (Package)element;
		}
	}

	public Classifier getClassifier(String qualifiedName) throws ElementResolutionError {
		Element element = this.getElement(qualifiedName);
		if (!(element instanceof Classifier)) {
			throw new ElementResolutionError(qualifiedName + " is not a Classifier.");
		} else {
			return (Classifier)element;
		}
	}

	public Element getElement(String qualifiedName) throws ElementResolutionError {
		Element element = null;
		Collection<org.eclipse.uml2.uml.NamedElement> elements = 
				UMLUtil.findNamedElements(
						this.rootScopeImpl.getResourceSet(), qualifiedName);
		if (elements.size() == 0) {
			throw new ElementResolutionError("Cannot find " + qualifiedName);
		} else if (elements.size() > 1) {
			throw new ElementResolutionError("More than one " + qualifiedName);
		} else {
			element = org.modeldriven.alf.eclipse.uml.Element.
					wrap(((org.eclipse.uml2.uml.Element)elements.toArray()[0]));
		}
		return element;    	
	}

	public String parseArgs(String[] args) {
		int i = 0;
		while (i < args.length) {
			String arg = args[i];
			if (arg.charAt(0) != '-') {
				break;
			}
			String option = arg.substring(1);
			i++;
			if (i < args.length) {
				if (option.equals("v")) {
					this.setIsVerbose(true);
				} else if (option.matches("[dlu]")) {
					arg = args[i];
					if (arg.length() > 0 && arg.charAt(0) == '-') {
						return null;
					}
					i++;
					if (option.equals("l")) {
						this.setUmlLibraryDirectory(arg);
					} else if (option.equals("u")) {
						this.setUmlDirectory(arg);
					}
				} else {
					return null;
				}
			}
		}

		return i == args.length - 1? args[i]: null;
	}

	public static Operation getInitializationOperation(Class_ class_) {
		Operation operation = null;
		String initializerName = class_.getName() + "$initialization$";
		int n = initializerName.length();

		for (Operation ownedOperation: class_.getOwnedOperation()) {
			String operationName = ownedOperation.getName();
			if (operationName != null && 
					operationName.length() > n &&
					operationName.substring(0, n).equals(initializerName) &&
					operationName.substring(n).matches("[0-9]+")) {
				operation = ownedOperation;
			}
		}

		return operation;
	}

	public void initializeEnvironment() {
		this.rootScopeImpl.initialize();	

		this.createLocus();
		this.addPrimitiveTypes();
		this.addPrimitiveBehaviorPrototypes();
		this.createSystemServices();
	}

	public Resource getResource(String name) {
		Resource resource = 
				this.rootScopeImpl.getResource(this.umlDirectory, name);
		if (resource != null) {
			Map<URI, URI> map = resource.getResourceSet().getURIConverter().getURIMap();
			map.put(URI.createURI(""), resource.getURI());
		}
		return resource;	
	}

	public static boolean isConstructor(Operation operation) {
		return ((org.modeldriven.alf.eclipse.uml.Operation)operation).getBase().
				getAppliedStereotype("StandardProfile::Create") != null;
	}

	public static Operation getDefaultConstructor(Class_ class_) {
		for (Operation operation: class_.getOwnedOperation()) {
			if (isConstructor(operation) &&
					operation.getName().equals(class_.getName())) {
				List<Parameter> parameters = operation.getOwnedParameter();
				if (parameters.size() == 1 && 
						parameters.get(0).getDirection().equals("return")) {
					return operation;
				}
			}
		}
		return null;
	}

	public void execute(Classifier element) {
		if (element instanceof Behavior) {
			Behavior behavior = (Behavior)element;
			if (!behavior.getOwnedParameter().isEmpty()) {
				this.println("Cannot execute a behavior with parameters.");                        
			} else {
				this.printVerbose("Executing...");
				this.getLocus().getExecutor().execute(behavior, null);
			}
		} else if (element instanceof Class_) { 
			Class_ class_ = (Class_)element;
			if (class_.getIsAbstract()) {
				this.println("Cannot instantiate an abstract class.");
			} else {
				Operation constructor = getDefaultConstructor(class_);
				if (constructor == null) {
					this.println("Class does not have a default constructor.");
				} else {
					Locus locus = this.getLocus();

					// Instantiate the class.
					this.printVerbose("Instantiating...");
					Object_ object = locus.instantiate(class_);

					// Execute the default constructor.
					locus.getExecutor().execute(
							((Behavior)constructor.getMethod().get(0)), 
							object);

					if (class_.getIsActive() && class_.getClassifierBehavior() !=null ) {
						// Execute the classifier behavior.
						this.printVerbose("Executing...");
						object.startBehavior(class_);
					}
				}
			}
		} else {
			this.println("Unit is not executable.");
		}
	}

	public void execute(String name) {
		Resource resource = null;
		try {
			resource = this.getResource(name);
		} catch (Exception e) {
			this.println(e.getMessage());
		}
		if (resource != null) {
			try {
				this.initializeEnvironment();
				Classifier element = this.getClassifier("Model::" + name);
				this.execute(element);
			} catch (ElementResolutionError e) {
				this.println(e.getMessage());
			}
		}
	}

	protected void printVerbose(String message) {
		if (this.isVerbose) {
			this.println(message);
		}
	}

	protected void println(String message) {
		System.out.println(message);
	}

	public Fuml() {
		this.setUmlLibraryDirectory("Libraries");
	}

	public Fuml(String[] args) {
		this();

		String name = this.parseArgs(args);

		if (name != null) {

			int l1 = name.length();
			int l2 = UMLResource.FILE_EXTENSION.length() + 1;
			if (l1 > l2 && name.substring(l1 - l2).
					equals("." + UMLResource.FILE_EXTENSION)) {
				name = name.substring(0, l1 - l2);
			}

			this.execute(name);

		} else {
			this.println("Usage is");
			this.println("  fuml [options] file");
			this.println("where file is the name of an executable unit and");
			this.println("allowable options are:");
			this.println("  -l path   Set library directory path (default is \"Libraries\")");
			this.println("  -u path   Set UML directory path (default is \"UML\")");
			this.println("  -v        Set verbose mode");
		}         
	}

	public static void main(String[] args) {
		new Fuml(args);
	}

}

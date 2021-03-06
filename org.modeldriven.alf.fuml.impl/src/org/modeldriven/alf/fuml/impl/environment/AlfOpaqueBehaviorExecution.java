/*******************************************************************************
 * Copyright 2016-2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.environment;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.fuml.impl.units.RootNamespaceImpl;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.FumlMappingFactory;
import org.modeldriven.alf.fuml.mapping.units.MemberMapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.ParserFactory;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.StereotypeApplication;
import org.modeldriven.fuml.FumlException;

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.Classes.Kernel.ParameterList;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

public class AlfOpaqueBehaviorExecution extends 
	fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution {
	
	private static RootNamespaceImpl rootScopeImpl = null;
	
	private static RootNamespaceImpl getRootScopeImpl() {
		if (rootScopeImpl == null) {
			rootScopeImpl = new RootNamespaceImpl();
			FumlMapping.setFumlFactory(new FumlMappingFactory());
			FumlMapping.setElementFactory(org.modeldriven.alf.fuml.impl.uml.Element.FACTORY);
		}
		return rootScopeImpl;
	}

	@Override
	public void doBody(ParameterValueList inputParameters,
			ParameterValueList outputParameters) {
		OpaqueBehavior behavior = (OpaqueBehavior)this.getBehavior();
		List<String> bodies = behavior.body;
		List<String> languages = behavior.language;
		for (int i = 0; i < languages.size(); i++) {
			if (languages.get(i).equals("Alf")) {
				Element element = compile(behavior, bodies.get(i));
				if (element instanceof Activity) {
					this.execute((Activity)element, inputParameters, outputParameters);
				}
			}
		}
	}

	@Override
	public Value new_() {
		return new AlfOpaqueBehaviorExecution();
	}
	
	private void execute(
			Activity activity, 
			ParameterValueList inputParameters, 
			ParameterValueList outputParameters) {
		ParameterValueList inputs = new ParameterValueList();
		ParameterList parameters = activity.ownedParameter;
		int i= 0;
		for (Parameter parameter: parameters) {
			if (i >= inputParameters.size()) {
				break;
			} else if (parameter.direction == ParameterDirectionKind.in || 
					parameter.direction == ParameterDirectionKind.inout) {
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.parameter = parameter;
				parameterValue.values = inputParameters.get(i).values;
				inputs.add(parameterValue);
				i++;
			}
		}
		
		ParameterValueList outputs = 
				this.locus.executor.execute(activity, this.context, inputs);
		for (int j = 0; j < outputParameters.size() && j < outputs.size(); j++) {
			outputParameters.get(j).values = outputs.get(j).values;
		}
	}

	public static UnitDefinition parse(
			Behavior behavior, String textualRepresentation) {
		getRootScopeImpl().setContext(behavior);
		
		ElementReferenceImpl.clearTemplateBindings();
		StereotypeApplication.clearStereotypeApplications();

		Parser parser = ParserFactory.defaultImplementation().createParser(
		        behavior.name, new StringReader(textualRepresentation));

        Block body = parser.parseStatementSequence(true);

        Collection<SourceProblem> parsingProblems = parser.getProblems();
        checkForProblems(parsingProblems);

        UnitDefinition unit = makeUnitForBehavior(behavior, body);

        NamespaceDefinition modelScope = RootNamespace.getModelScope(unit);
        modelScope.deriveAll();

        Collection<ConstraintViolation> violations = modelScope.checkConstraints();
        checkForProblems(violations);
        return unit;
	}

	protected static UnitDefinition makeUnitForBehavior(Behavior behavior,
			Block body) {
		ActivityDefinition activityDefinition = new ActivityDefinition();
		activityDefinition.getImpl().setExactName(behavior.name);
		
		for (Parameter parameter: behavior.ownedParameter) {
			FormalParameter formalParameter = new FormalParameter();
			formalParameter.getImpl().setExactName(parameter.name);
			formalParameter.setDirection(
					parameter.direction == ParameterDirectionKind.return_? "return":
						parameter.direction.toString());
			formalParameter.setType(ElementReferenceImpl.makeElementReference(
					org.modeldriven.alf.fuml.impl.uml.Element.wrap(parameter.type)));
			formalParameter.setNamespace(activityDefinition);
			activityDefinition.addOwnedMember(formalParameter);
		}
		
		activityDefinition.setBody(body);
		
		UnitDefinition unit =  new UnitDefinition();
		unit.setDefinition(activityDefinition);
		unit.setIsModelLibrary(true);
		activityDefinition.setUnit(unit);
		
		return unit;
	}
	
    public static FumlMapping map(
    		NamespaceDefinition definition, 
    		Collection<org.modeldriven.alf.uml.Element> otherElements) {
        try {
        	// NOTE: deriveAll is necessary here to ensure computation of
        	// derived attributes of library units that may have been
        	// imported during parsing of the model unit.
			RootNamespace.getRootScope().deriveAll();
			
            FumlMapping mapping = FumlMapping.getMapping(definition);
        	mapping.getModelElements();
        	otherElements.addAll(((MemberMapping)mapping).mapBody());
        	return mapping;
        } catch (MappingError e) {
        	throw new FumlException(e);
        }
    }
    
	public static Element compile(
			Behavior behavior, String textualRepresentation) {
		UnitDefinition unit = parse(behavior, textualRepresentation);
		Collection<org.modeldriven.alf.uml.Element> otherElements =
				new ArrayList<org.modeldriven.alf.uml.Element>();
		FumlMapping mapping = map(unit.getDefinition(), otherElements);
		
		// NOTE: The fUML Reference Implementation does not require that 
		// the otherElements actually be owned within the in-memory model
		// representation.
		
		return (Element)((org.modeldriven.alf.fuml.impl.uml.Element)mapping.
				getElement()).getBase();
	}

    private static void checkForProblems(Collection<? extends SourceProblem> problems) {
        if (!problems.isEmpty()) {
            StringBuffer msg = new StringBuffer("Constraint violations:\n");
            for (SourceProblem problem: problems) {
                msg.append(problem + "\n");
            }
            throw new FumlException(msg.toString());
        }
    }	
}

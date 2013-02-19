
/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.NamespaceDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.units.ClassifierDefinition;
import org.modeldriven.alf.syntax.units.ClassifierTemplateParameter;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Generalization;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.ParameterableElement;
import org.modeldriven.alf.uml.RedefinableTemplateSignature;
import org.modeldriven.alf.uml.TemplateBinding;
import org.modeldriven.alf.uml.TemplateParameterSubstitution;

public abstract class ClassifierDefinitionMapping extends
		NamespaceDefinitionMapping {
    
    private Classifier classifier = null;
    private boolean notFullyMapped = true;
    protected Collection<Element> otherElements = new ArrayList<Element>();
    
    /**
     * 1. A classifier definition (other than a classifier template parameter)
     * maps to a classifier and its features, as specified for each kind of
     * classifier in the appropriate subsequent subclause. If the classifier
     * definition is a stub, then it is mapped according to its associated
     * subunit definition.
     * 
     * Specialization
     * 
     * 2. If the classifier definition has specialization referents, then the
     * classifier being defined has generalization relationships with each of
     * the referents. If the classifier definition is abstract, then the
     * classifier has isAbstract=true. Otherwise isAbstract=false.
     */
    
    public void mapTo(Classifier classifier) throws MappingError {
        ClassifierDefinition definition = this.getClassifierDefinition();

        // NOTE: Mapping of template parameters and binding is handled before
        // mapping the memebrs of the classifier, in case a template refers
        // to an instantiation of itself.
        if (this.supportsTemplates()) {
            if (definition.getImpl().isTemplate()) {
                RedefinableTemplateSignature signature = 
                        this.create(RedefinableTemplateSignature.class);
                for (ClassifierTemplateParameter templateParameter: 
                    definition.getImpl().getTemplateParameters()) {
                    FumlMapping mapping = this.fumlMap(templateParameter);
                    if (!(mapping instanceof ClassifierTemplateParameterMapping)) {
                        this.throwError("Error mapping template parameter " + 
                                definition.getName() + ": " + 
                                mapping.getErrorMessage());
                    } else {
                        org.modeldriven.alf.uml.ClassifierTemplateParameter parameter = 
                                ((ClassifierTemplateParameterMapping)mapping).
                                    getTemplateParameter();
                        if (parameter != null) {
                            signature.addOwnedParameter(parameter);
                        }
                    }
                }
                if (signature.getOwnedParameter().size() > 0) {
                    classifier.setOwnedTemplateSignature(signature);
                }
            }
            if (definition.getImpl().isBound()) {
                SyntaxElement base = definition.getImpl().getBase();
                if (base != null) {
                    FumlMapping mapping = this.fumlMap(base);
                    if (!(mapping instanceof ClassifierDefinitionMapping)) {
                        this.throwError("Error mapping base template: " + 
                                mapping.getErrorMessage());
                    } else {
                        Classifier template = ((ClassifierDefinitionMapping)mapping).
                                getClassifier();

                        List<NamedElement> elements = classifier.bindTo(template);
                        if (elements.isEmpty()) {
                            this.throwError("Cannot bind to " + template);
                        } else {
                            Classifier boundClassifier = 
                                    (Classifier)(elements.get(0));
                            this.otherElements.addAll(elements);

                            TemplateBinding binding = 
                                    boundClassifier.getTemplateBinding().get(0);
                            binding.setSignature(
                                    template.getOwnedTemplateSignature());

                            for (ClassifierTemplateParameter templateParameter: 
                                definition.getImpl().getBoundTemplateParameters()) {
                                TemplateParameterSubstitution substitution = 
                                        this.create(
                                                TemplateParameterSubstitution.class);
                                binding.addParameterSubstitution(substitution);
                                mapping = this.fumlMap(
                                        templateParameter.getImpl().getBase());
                                if (!(mapping instanceof ClassifierTemplateParameterMapping)) {
                                    this.throwError("Error mapping template parameter " + 
                                            templateParameter.getName() + ": " + 
                                            mapping.getErrorMessage());
                                } else {
                                    substitution.setFormal( 
                                            ((ClassifierTemplateParameterMapping)mapping).
                                            getTemplateParameter());
                                    ElementReference boundArgument = 
                                            templateParameter.getImpl().getBoundArgument();
                                    Element actual = null;
                                    if (boundArgument != null) {
                                        actual = boundArgument.getImpl().getUml();
                                        if (actual == null) {
                                            mapping = this.fumlMap(boundArgument);
                                            if (mapping instanceof ElementReferenceMapping) {
                                                mapping = ((ElementReferenceMapping)mapping).getMapping();
                                            }
                                            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                                                this.throwError("Error mapping template actual for " + 
                                                        definition.getName() + "::" + 
                                                        templateParameter.getName() + ": " + 
                                                        mapping.getErrorMessage());
                                            } else {
                                                actual = ((ClassifierDefinitionMapping)mapping).
                                                        getClassifierOnly();
                                            }
                                        }
                                        if (!(actual instanceof ParameterableElement)) {
                                            this.throwError("Template actual for " + 
                                                    definition.getName() + "::" + 
                                                    templateParameter.getName() + 
                                                    " is not a ParameterableElement: " + 
                                                    actual);
                                        }
                                    }
                                    substitution.setActual((ParameterableElement)actual);
                                }
                            }
                        }
                    }
                }
            }
        }

        super.mapTo(classifier);

        classifier.setIsAbstract(definition.getIsAbstract());
        
        Collection<ElementReference> referents = 
            definition.getSpecializationReferent();

        for (ElementReference referent: referents) {
            Classifier general = (Classifier)referent.getImpl().getUml();
            if (general == null) {
                FumlMapping mapping = this.fumlMap(referent);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (!(mapping instanceof ClassifierDefinitionMapping)) {
                    this.throwError("Error mapping generalization: " + referent);
                } else {
                    general = 
                            ((ClassifierDefinitionMapping)mapping).getClassifier();        
                }
            }
            Generalization generalization = this.create(Generalization.class);
            generalization.setGeneral(general);
            classifier.addGeneralization(generalization);                   
        }

    }
    
    public abstract Classifier mapClassifier();
    
    /**
     * This operation creates a new classifier, but does not fully map it.
     * This allows, e.g., a parameter of an operation of a class to have the
     * class as its type without causing the entire class to be mapped before
     * the mapping of the operation is finished. 
     * @return
     */
    public Classifier getClassifierOnly() {
        if (this.classifier == null) {
            this.classifier = this.mapClassifier();
        }
        return this.classifier;
    }
    
    public Classifier getClassifier() throws MappingError {
        if (this.notFullyMapped) {
            this.notFullyMapped = false;
            this.mapTo(this.getClassifierOnly());
        }
        return this.classifier;
    }
    
    @Override
    public Element getElement() {
        return this.classifier;
    }

    @Override
    public List<Element> getModelElements() throws MappingError {
        List<Element> elements = super.getModelElements();
        elements.addAll(this.otherElements);
        return elements;
    }

	public ClassifierDefinition getClassifierDefinition() {
		return (ClassifierDefinition) this.getSource();
	}
	
	@Override
	public NamedElement getNamedElement() throws MappingError {
	    return this.getClassifier();
	}
	
	@Override
	public String toString() {
	    return super.toString() + (this.classifier == null? "":
	        " isAbstract:" + this.classifier.getIsAbstract());
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    System.out.println(prefix + " classifier: " + this.classifier);
	    if (this.classifier != null && !this.classifier.getGeneral().isEmpty()) {
	        System.out.println(prefix + " general:");
    	    for (Classifier general: this.classifier.getGeneral()) {
    	        System.out.println(prefix + "  " + general);
    	    }
	    }
	}

} // ClassifierDefinitionMapping

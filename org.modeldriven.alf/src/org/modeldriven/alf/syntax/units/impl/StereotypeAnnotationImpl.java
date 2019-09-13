
/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.uml.ElementFactory;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An annotation of a member definition indicating the application of a
 * stereotype (or one of a small number of special-case annotations).
 **/

public class StereotypeAnnotationImpl extends SyntaxElementImpl {

	private TaggedValueList taggedValues = null;
	private QualifiedNameList names = null;
	private QualifiedName stereotypeName = null;
	
	// NOTE: An ElementReference is used here to allow for the
	// (non-standard) possibility of a stereotype defined using
	// Alf.
	private ElementReference stereotype = null; // DERIVED
	
	private Member owningMember = null;
	private NamespaceDefinition currentScope = null;
	private boolean unscoped = true;
	
	public StereotypeAnnotationImpl(StereotypeAnnotation self) {
		super(self);
	}

	@Override
	public StereotypeAnnotation getSelf() {
		return (StereotypeAnnotation) this.self;
	}
	
	@Override
	public String toString(boolean includeDerived) {
	    return super.toString(includeDerived) + " stereotypeName:" + stereotypeName.getPathName();
	}

    @Override
    public void addExternalReferences(Collection<ExternalElementReference> references) {
        super.addExternalReferences(references);
        SyntaxElement.addExternalReference(references, this.getStereotypeReference());
    }
    
	public TaggedValueList getTaggedValues() {
		return this.taggedValues;
	}

	public void setTaggedValues(TaggedValueList taggedValues) {
		this.taggedValues = taggedValues;
	}

	public QualifiedNameList getNames() {
		return this.names;
	}

	public void setNames(QualifiedNameList names) {
		this.names = names;
	}

	public QualifiedName getStereotypeName() {
		return this.stereotypeName;
	}

	public void setStereotypeName(QualifiedName stereotypeName) {
		this.stereotypeName = stereotypeName;
	}

	public Stereotype getStereotype() {
	    ElementReference reference = this.getStereotypeReference();
		return reference == null? null: (Stereotype)reference.getImpl().getUml();
	}

	public void setStereotype(Stereotype stereotype) {
		this.setStereotypeReference(ElementReferenceImpl.makeElementReference(stereotype));
	}
	
	public ElementReference getStereotypeReference() {
        if (this.stereotype == null) {
            this.setStereotypeReference(this.deriveStereotype());
        }
        return this.stereotype;
	}
	
	// The element reference must be a reference to a stereotype.
	public void setStereotypeReference(ElementReference stereotype) {
	    this.stereotype = stereotype;
	}

    /**
     * Unless the stereotype name is "apply", "primitive" or "external" then the
     * stereotype for a stereotype annotation is the stereotype denoted by the
     * stereotype name.
     **/
	protected ElementReference deriveStereotype() {
	    QualifiedName stereotypeName = this.getSelf().getStereotypeName();
	    NamespaceDefinition currentScope = this.getCurrentScope();
	    return stereotypeName == null || this.isNonstereotypeAnnotation() || currentScope == null? null:
	        currentScope.getImpl().resolveStereotype(stereotypeName);
	}
	
	/*
	 * Derivations
	 */

	public boolean stereotypeAnnotationStereotypeDerivation() {
		this.getSelf().getStereotype();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The stereotype name of a stereotype annotation must either be one of
	 * "apply", "primitive" or "external", or it must denote a single stereotype
	 * from a profile applied to an enclosing package. The stereotype name does
	 * not need to be qualified if there is only one applied profile with a
	 * stereotype of that name or if the there is a standard UML profile with
	 * the name.
	 **/
	public boolean stereotypeAnnotationStereotypeName() {
		return this.isNonstereotypeAnnotation() ||
		            this.getStereotypeReference() != null;
	}

	/**
	 * If the stereotype name of a stereotype annotation is "apply", then it
	 * must have a name list and all of the names in the list must resolve to
	 * profiles.
	 **/
	public boolean stereotypeAnnotationApply() {
	    StereotypeAnnotation self = this.getSelf();
	    if (self.getStereotypeName().getImpl().equals("apply")) {
	        for (QualifiedName name: this.getNamesWithScope()) {
	            if (!name.getImpl().isProfileReferent()) {
	                return false;
	            }
	        }
	    }
		return true;
	}

	/**
	 * If the stereotype name of a stereotype annotation is "primitive", then it
	 * may not have tagged values or names.
	 **/
	public boolean stereotypeAnnotationPrimitive() {
        StereotypeAnnotation self = this.getSelf();
        QualifiedNameList nameList = self.getNames();
        TaggedValueList taggedValueList = self.getTaggedValues();
		if (!self.getStereotypeName().getImpl().equals("primitive")) {
		    return true;
		    
	    // Allow the @primitive annotation to have an implementation name
	    // as a single (non-standard) argument.
		} else if (nameList != null){
		    return nameList.getName().size() == 1;
		} else if (taggedValueList != null) {
            Collection<TaggedValue> taggedValues = taggedValueList.getTaggedValue();
		    if (taggedValues.size() > 1) {
		        return false;
		    } else {
		        TaggedValue taggedValue = (TaggedValue)taggedValues.toArray()[0];
		        return taggedValue.getName().equals("implementation") &&
		               taggedValue.getImpl().isStringValue();
		    }
		} else {
		    return true;
		}
	}

	/**
	 * If the stereotype name of a stereotype annotation is "external", then it
	 * may optionally have a single tagged value with the name "file" and no
	 * operator.
	 **/
	// And tagged value must be a string.
	public boolean stereotypeAnnotationExternal() {
        StereotypeAnnotation self = this.getSelf();
        TaggedValueList taggedValueList = self.getTaggedValues();
        if (!self.getStereotypeName().getImpl().equals("external")) {
            return true;
        } else {
            if (self.getNames() != null) {
                return false;
            } else if (taggedValueList != null) {
                Collection<TaggedValue> taggedValues = taggedValueList.getTaggedValue();
                if (taggedValues == null || taggedValues.size() > 1) {
                    return false;
                } else {
                    TaggedValue taggedValue = (TaggedValue)taggedValues.toArray()[0];
                    return  taggedValue.getName().equals("file") &&
                            taggedValue.getImpl().isStringValue();
                }
            } else {
                return true;
            }
        }
	}

	/**
	 * If a stereotype annotation has a stereotype and tagged values, then
	 * each tagged value must have the name of an attribute of the stereotype
	 * and a value that is legally interpretable for the type of that attribute.
	 **/
	// Also checks stereotype attribute multiplicity constraints.
	public boolean stereotypeAnnotationTaggedValues() {
        StereotypeAnnotation self = this.getSelf();
        ElementReference stereotype = this.getStereotypeReference();
        if (stereotype == null || self.getNames() != null) {
            return true;
        } else {
            Collection<ElementReference> attributes = stereotype.getImpl().getAttributes();
            TaggedValueList taggedValues = self.getTaggedValues();
            if (taggedValues != null) {
                for (TaggedValue taggedValue: taggedValues.getTaggedValue()) {
                    ElementReference namedAttribute = null;
                    for (ElementReference attribute: attributes) {
                        if (taggedValue.getName().equals(attribute.getImpl().getName())) {
                            namedAttribute = attribute;
                            break;
                        }
                    }
                    if (namedAttribute == null ||
                        namedAttribute.getImpl().getLower() > 1 ||
                        namedAttribute.getImpl().getUpper() == 0 ||
                        !taggedValue.getImpl().getType().getImpl().conformsTo(
                                namedAttribute.getImpl().getType())) {
                        return false;
                    }
                    
                    // NOTE: Removing the attribute ensures that there can be at most 
                    // one tagged value for it.
                    attributes.remove(namedAttribute);
                }
            }
            
            // Check that all required attributes have been given values.
            for (ElementReference attribute: attributes) {
                if (attribute.getImpl().getLower() > 0) {
                    return false;
                }
            }
            
            return true;
        }
	}

	/**
	 * If a stereotype annotation has a stereotype and a list of names, then all
	 * the names in the list must resolve to visible model elements and the
	 * stereotype must have a single attribute with a (metaclass) type and
	 * multiplicity that are consistent with the types and number of the
	 * elements denoted by the given names.
	 **/
	public boolean stereotypeAnnotationNames() {
	    StereotypeAnnotation self = this.getSelf();
        ElementReference stereotype = this.getStereotypeReference();
	    if (stereotype == null || self.getNames() == null) {
	        return true;
	    } else {
	        List<ElementReference> attributes = stereotype.getImpl().getAttributes();
	        if (attributes.size() != 1) {
	            return false;
	        } else {
	            ElementReference attribute = attributes.get(0);
	            ElementReference type = attribute.getImpl().getType();
	            int lower = attribute.getImpl().getLower();
	            int upper = attribute.getImpl().getUpper();
	            int size = self.getNames().getName().size();
                Class<?> metaclass = type == null? null:
                    ElementFactory.interfaceForName(type.getImpl().getName());
	            if (metaclass == null || size < lower || upper != -1 && size > upper) {
	                return false;
	            }
	            for (QualifiedName name: this.getNamesWithScope()) {
	                Collection<ElementReference> referents = name.getReferent();
	                if (referents.size() != 1) {
	                    return false;
	                }
	                ElementReference referent = (ElementReference)referents.toArray()[0];
	                Class<?> referentMetaclass = referent.getImpl().getUMLMetaclass();
	                if (referentMetaclass == null || !metaclass.isAssignableFrom(referentMetaclass)) {
	                    return false;
	                }
	            }
	            return true;
	        }
	    }
	}
	
	/*
	 * Helper Methods
	 */
	
	private boolean isNonstereotypeAnnotation() {
	    return stereotypeName.getImpl().equals("apply") ||
        	   stereotypeName.getImpl().equals("primitive") ||
        	   stereotypeName.getImpl().equals("external") ||
        	   // The following are non-standard, to allow for the standard
        	   // profile to be specified in Alf.
        	   stereotypeName.getImpl().equals("profile") ||
        	   stereotypeName.getImpl().equals("stereotype");
	}
	
	public boolean isStereotype(ElementReference stereotype) {
	    ElementReference reference = this.getStereotypeReference();
	    return reference != null && reference.getImpl().equals(stereotype);
	}
	
	public Collection<QualifiedName> getNamesWithScope() {
	    QualifiedNameList nameList = this.getSelf().getNames();
	    if (nameList == null) {
	        return new ArrayList<QualifiedName>();
	    } else {
	        Collection<QualifiedName> names = nameList.getName();
	        if (this.unscoped) {
    	        NamespaceDefinition scope = this.getCurrentScope();
    	        for (QualifiedName name: names) {
    	            name.getImpl().setCurrentScope(scope);
    	        }
    	        this.unscoped = false;
	        }
	        return names;
	    }
	}
	
	public NamespaceDefinition getCurrentScope() {
	    if (this.currentScope == null && this.owningMember != null) {
	        if (this.owningMember.getNamespace() == null) {
    	        this.currentScope = this.owningMember.getImpl().getModelScope();
    	    } else {
    	        this.currentScope = this.owningMember.getNamespace();
    	    }
	    }
	    return this.currentScope;
	}
	
	public void setOwningMember(Member owningMember) {
	    this.owningMember = owningMember;
	    // NOTE: Defer computation of current scope until parsing is complete.
	    this.currentScope = null;
	    this.unscoped = true;
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof StereotypeAnnotation) {
            StereotypeAnnotation self = this.getSelf();
            StereotypeAnnotation annotation = (StereotypeAnnotation)base;
            self.setStereotypeName(annotation.getStereotypeName());
            self.setNames(annotation.getNames());
            self.setTaggedValues(self.getTaggedValues());
        }
    }
    
} // StereotypeAnnotationImpl

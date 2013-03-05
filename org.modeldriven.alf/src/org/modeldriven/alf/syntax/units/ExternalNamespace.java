/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units;

import java.util.Collection;

import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.units.impl.ExternalNamespaceImpl;
import org.modeldriven.alf.uml.Namespace;

public class ExternalNamespace extends NamespaceDefinition {
    
    private Namespace umlNamespace = null;
    
    public ExternalNamespace(Namespace namespace, NamespaceDefinition parent) {
        this.impl = new ExternalNamespaceImpl(this);
        this.umlNamespace = namespace;
        if (parent != null) {
            this.setNamespace(parent);
        }
    }
    
    @Override
    public ExternalNamespaceImpl getImpl() {
        return (ExternalNamespaceImpl)this.impl;
    }
    
    public Namespace getUmlNamespace() {
        return this.umlNamespace;
    }
    
    @Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
        
    }
    
    public static NamespaceDefinition makeExternalNamespace(
            Namespace namespace,
            NamespaceDefinition parent) {
        NamespaceDefinition externalNamespace = null;
        if (namespace != null) {
            externalNamespace = new ExternalNamespace(namespace, parent);
        }
        return externalNamespace;
    }
    
 }

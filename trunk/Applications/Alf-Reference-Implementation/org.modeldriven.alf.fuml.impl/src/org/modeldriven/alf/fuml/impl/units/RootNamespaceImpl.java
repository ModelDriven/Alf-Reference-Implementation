/*******************************************************************************
 * Copyright 2013 Ivar Jacobson International SA
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.units;

import org.modeldriven.alf.fuml.impl.units.ModelNamespaceImpl;
import org.modeldriven.alf.fuml.impl.uml.Element;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Namespace;

import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.repository.RepositorylException;

public class RootNamespaceImpl extends org.modeldriven.alf.fuml.units.RootNamespaceImpl {

	private fUML.Syntax.Classes.Kernel.Element contextElement;
    
	public RootNamespaceImpl() {
		super(RootNamespace.getRootScope());
		super.setLibraryDirectory("Libraries");
	}

    public void setContext(fUML.Syntax.Classes.Kernel.Element contextElement) {
    	this.contextElement = contextElement;
    	
        ModelNamespace modelNamespace = new ModelNamespace();
        ModelNamespaceImpl modelNamespaceImpl = 
        		new ModelNamespaceImpl(modelNamespace);
        modelNamespaceImpl.setContext(this.contextElement);
        modelNamespace.setImpl(modelNamespaceImpl);
        this.setModelNamespace(modelNamespace);
    }
    
    @Override
    public UnitDefinition resolveModelUnit(QualifiedName qualifiedName) {
    	fUML.Syntax.Classes.Kernel.Element element = 
    			findElementInRepository(qualifiedName);
    	if (!(element instanceof fUML.Syntax.Classes.Kernel.Namespace)) {
    		return super.resolveModelUnit(qualifiedName);
    	} else {
			NamespaceDefinition namespace = 
					ExternalNamespace.makeExternalNamespace(
							(Namespace)Element.wrap(
									(fUML.Syntax.Classes.Kernel.Namespace)element),
									this.getSelf());
			UnitDefinition unit = new UnitDefinition();
			unit.setIsModelLibrary(true);
			unit.setDefinition(namespace);
			namespace.setUnit(unit);
			return unit;
    	}
    }

	protected fUML.Syntax.Classes.Kernel.Element findElementInRepository(
			QualifiedName qualifiedName) {
		String pathName = qualifiedName.getPathName().replace("::", ".");
    	org.modeldriven.fuml.repository.Element repositoryElement = 
    			Repository.INSTANCE.findElementByQualifiedName(pathName);
    	if (repositoryElement == null) {
    		try {
	    		repositoryElement = 
	    				Repository.INSTANCE.getPackageByQualifiedName(pathName);
    		} catch (RepositorylException e) {
    			return null;
    		}
    	}
    	return repositoryElement.getDelegate();

	}
    
    @Override
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
    	return this.resolveModelUnit(qualifiedName);
    }

}

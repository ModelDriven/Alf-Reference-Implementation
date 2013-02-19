/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

import java.util.ArrayList;
import java.util.List;

public abstract class NamedElement extends Element implements
		org.modeldriven.alf.uml.NamedElement {

	public NamedElement(fUML.Syntax.Classes.Kernel.NamedElement base) {
		super(base);
	}

    @Override
	public fUML.Syntax.Classes.Kernel.NamedElement getBase() {
		return (fUML.Syntax.Classes.Kernel.NamedElement) this.base;
	}

    @Override
	public String getName() {
		return this.getBase().name;
	}

    @Override
	public void setName(String name) {
		this.getBase().setName(name);
	}

    @Override
	public String getVisibility() {
		String visibility = this.getBase().visibility.toString();
		return visibility == null? null: visibility.substring(0, visibility.length() - 1);
	}

    @Override
	public void setVisibility(String visibility) {
		this.getBase().setVisibility(visibility == null? null:
				fUML.Syntax.Classes.Kernel.VisibilityKind.valueOf(visibility + "_"));
	}

    @Override
	public String getQualifiedName() {
		return this.getBase().qualifiedName;
	}

    @Override
	public org.modeldriven.alf.uml.Namespace getNamespace() {
		return (Namespace)this.wrap(this.getBase().namespace);
	}
    
    @Override
    public List<org.modeldriven.alf.uml.Dependency> getClientDependency() {
        return new ArrayList<org.modeldriven.alf.uml.Dependency>();
    }

    @Override
    public boolean isDistinguishableFrom(org.modeldriven.alf.uml.NamedElement otherElement,
            org.modeldriven.alf.uml.Namespace namespace) {
        if (this.getClass().isAssignableFrom(otherElement.getClass()) || 
                otherElement.getClass().isAssignableFrom(this.getClass())) {
            List<String> names = namespace.getNamesOfMember(this);
            names.removeAll(namespace.getNamesOfMember(otherElement));
            return names.isEmpty();
        } else {
            return true;
        }
    }

}

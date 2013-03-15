/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms,
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;

import org.eclipse.uml2.uml.UMLFactory;

public class Dependency extends PackageableElement implements org.modeldriven.alf.uml.Dependency {
	
	public Dependency() {
		this(UMLFactory.eINSTANCE.createDependency());
	}

	public Dependency(org.eclipse.uml2.uml.Dependency base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.Dependency getBase() {
		return (org.eclipse.uml2.uml.Dependency)this.base;
	}

	@Override
	public org.modeldriven.alf.uml.NamedElement getClient() {
		List<org.eclipse.uml2.uml.NamedElement> clients = this.getBase().getClients();
		return (org.modeldriven.alf.uml.NamedElement)wrap(
				clients.size() == 0? null: clients.get(0));
	}

	@Override
	public void setClient(org.modeldriven.alf.uml.NamedElement client) {
		List<org.eclipse.uml2.uml.NamedElement> clients = this.getBase().getClients();
		clients.clear();
		if (client != null) {
			clients.add(((NamedElement)client).getBase());
		}
	}

	@Override
	public org.modeldriven.alf.uml.NamedElement getSupplier() {
		List<org.eclipse.uml2.uml.NamedElement> suppliers = this.getBase().getSuppliers();
		return (org.modeldriven.alf.uml.NamedElement)wrap(
				suppliers.size() == 0? null: suppliers.get(0));
	}

	@Override
	public void setSupplier(org.modeldriven.alf.uml.NamedElement supplier) {
		List<org.eclipse.uml2.uml.NamedElement> suppliers = this.getBase().getSuppliers();
		suppliers.clear();
		if (supplier != null) {
			suppliers.add(((NamedElement)supplier).getBase());
		}
	}

}

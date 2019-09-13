/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.Collection;
import java.util.List;

public interface Namespace extends NamedElement {
	public List<NamedElement> getMember();

	public List<NamedElement> getOwnedMember();

	public List<ElementImport> getElementImport();

	public void addElementImport(ElementImport elementImport);

	public List<PackageImport> getPackageImport();

	public void addPackageImport(PackageImport packageImport);

	public List<PackageableElement> getImportedMember();
	
    public Collection<String> getNamesOfMember(NamedElement member);
    
    public List<NamedElement> resolveInScope(String name, boolean classifierOnly);
}

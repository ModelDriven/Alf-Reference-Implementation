/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.List;
import java.util.Set;

public interface Classifier extends Type, Namespace, TemplateableElement, ParameterableElement {
	public boolean getIsAbstract();

	public void setIsAbstract(boolean isAbstract);

	public List<Generalization> getGeneralization();

	public void addGeneralization(Generalization generalization);

	public List<Feature> getFeature();

	public List<NamedElement> getInheritedMember();

	public List<Property> getAttribute();

	public List<Classifier> getGeneral();

	public boolean getIsFinalSpecialization();

	public void setIsFinalSpecialization(boolean isFinalSpecialization);

    public Set<Classifier> parents();

    public Set<Classifier> allParents();

    public List<NamedElement> inheritableMembers();

    public boolean conformsTo(Classifier classifier);
}

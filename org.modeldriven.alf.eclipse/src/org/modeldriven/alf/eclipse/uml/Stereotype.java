/*******************************************************************************
 * Copyright 2013-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.uml2.uml.UMLFactory;

public class Stereotype extends Class_ implements org.modeldriven.alf.uml.Stereotype {

	public Stereotype() {
		this(UMLFactory.eINSTANCE.createStereotype());
	}

	public Stereotype(org.eclipse.uml2.uml.Stereotype base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.Stereotype getBase() {
		return (org.eclipse.uml2.uml.Stereotype)this.base;
	}

	@Override
	public Collection<Class<?>> getExtendedMetaclass() {
		Collection<Class<?>> metaclasses = new ArrayList<Class<?>>();
		for (org.eclipse.uml2.uml.Property attribute: this.getBase().getAllAttributes()) {
			org.eclipse.uml2.uml.Association association = attribute.getAssociation();
			if (association instanceof org.eclipse.uml2.uml.Extension) {
				org.eclipse.uml2.uml.Type type = attribute.getType();
				if (type != null) {
					Class<?> metaclass = ElementFactory.interfaceForName(type.getName());
					if (metaclass != null) {
						metaclasses.add(metaclass);
					}
				}
			}
		}
		return metaclasses;
	}

}

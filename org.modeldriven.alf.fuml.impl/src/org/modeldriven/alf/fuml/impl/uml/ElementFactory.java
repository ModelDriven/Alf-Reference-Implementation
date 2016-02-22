/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

public class ElementFactory extends org.modeldriven.alf.uml.ElementFactory {

	public ElementFactory() {
		super(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior.class, 
			fUML.Syntax.Classes.Kernel.Class_.class, 
			fUML.Syntax.Classes.Kernel.Classifier.class, 
			fUML.Syntax.Classes.Kernel.NamedElement.class);
	}
	
}

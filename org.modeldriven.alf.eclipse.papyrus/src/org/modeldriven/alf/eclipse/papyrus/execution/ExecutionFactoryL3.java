/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.execution;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ISemanticVisitor;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ReclassifyObjectAction;

public class ExecutionFactoryL3 extends org.eclipse.papyrus.moka.fuml.Semantics.impl.Loci.LociL3.ExecutionFactoryL3 {

	@Override
	public ISemanticVisitor instantiateVisitor(Element element) {
		// This is a fix for Moka v2.0.0 bugs.
		if (element instanceof ActivityFinalNode) {
			return new ActivityFinalNodeActivation();
		} else if (element instanceof ReclassifyObjectAction) {
			return new ReclassifyObjectActionActivation();
		} else {
			return super.instantiateVisitor(element);
		}
	}
	
}

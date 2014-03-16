/*******************************************************************************
 * Copyright 2014 Ivar Jacobson International SA
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.execution.test;

import org.modeldriven.alf.eclipse.papyrus.execution.Alf;

public class RunTests extends org.modeldriven.alf.fuml.execution.test.RunTests {
		
	public static void main(String[] args) {
		runTests(new Alf());
	}

}

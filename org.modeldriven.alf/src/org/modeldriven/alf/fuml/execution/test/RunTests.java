/*******************************************************************************
 * Copyright 2015 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution.test;

import org.modeldriven.alf.fuml.execution.Alf;

public abstract class RunTests {
    
    private static final String TEST_DIR = "../org.modeldriven.alf/tests-x";
    
    protected static void runTests(Alf alf) {
        TestRunnerGenerator.generate(TEST_DIR);
        alf.run(new String[]{"-v", "-m", TEST_DIR, "_RunTests"});
    }
}
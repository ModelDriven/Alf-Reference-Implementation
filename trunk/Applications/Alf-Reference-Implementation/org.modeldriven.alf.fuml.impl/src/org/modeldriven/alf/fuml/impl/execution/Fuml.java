/*******************************************************************************
 * Copyright 2013 Ivar Jacobson International SA
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/

package org.modeldriven.alf.fuml.impl.execution;

import java.io.File;

import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.library.Library;
import org.modeldriven.alf.fuml.impl.environment.ExecutionFactory;

public class Fuml extends org.modeldriven.fuml.Fuml {

	public Fuml(File file) {
		super(file);
	}

    public Fuml(File file, String target) {
        super(file, target);
    }
    
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }
        
        Environment.getInstance(new ExecutionFactory());
        Library.getInstance();

        File modelFile = new File(args[0]);
        if (args.length == 1) {
        	new Fuml(modelFile);
        } 
        else {
            Fuml fuml = new Fuml(modelFile, args[1]);
            for (int i = 2; i < args.length; i++)
            	fuml.execute(modelFile.getName(), args[i]);
        }
    }

}

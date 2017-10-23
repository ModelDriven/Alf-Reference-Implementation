/*******************************************************************************
 *  Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

public class Model extends Package implements org.modeldriven.alf.uml.Model {
    // NOTE: The fUML subset does not include Model, so a regular Package is
    // used as the base for the Model interface.
    
    public Model() {
        this(new fUML.Syntax.Classes.Kernel.Package());
    }

    public Model(fUML.Syntax.Classes.Kernel.Package base) {
        super(base);
    }


}

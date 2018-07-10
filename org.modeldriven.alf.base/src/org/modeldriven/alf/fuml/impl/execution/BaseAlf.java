/*******************************************************************************
 * Copyright 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.execution;

import java.io.FileNotFoundException;

import org.modeldriven.alf.base.parser.BaseParserImpl;
import org.modeldriven.alf.fuml.units.ModelNamespaceImpl;
import org.modeldriven.alf.fuml.units.RootNamespaceImpl;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.units.ModelNamespace;

public class BaseAlf extends org.modeldriven.alf.fuml.impl.execution.Alf {
	
	@Override
	protected RootNamespaceImpl createRootScopeImpl() {
		return new RootNamespaceImpl() {
			@Override
			protected ModelNamespaceImpl createModelNamespaceImpl(ModelNamespace modelNamespace) {
				return new ModelNamespaceImpl(modelNamespace) {
					@Override
					protected Parser createParser(String path) throws FileNotFoundException {
						return new BaseParserImpl(path);
					}
				};
			}
		};
	}

    public static void main(String[] args) {
        new BaseAlf().run(args);
    }
}

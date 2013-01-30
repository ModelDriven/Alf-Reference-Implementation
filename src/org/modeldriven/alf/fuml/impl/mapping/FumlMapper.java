/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.mapping;

import org.modeldriven.alf.fuml.impl.execution.ExecutionFactory;
import org.modeldriven.alf.fuml.impl.uml.ElementFactory;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.SyntaxElement;

public class FumlMapper {
    
    public static void main(String[] args) {
        String fileName = null;
        
        if (args.length > 0) {
            fileName = args[0];
        }
        
        FumlMapping.setFumlFactory(new FumlMappingFactory());
        FumlMapping.setElementFactory(new ElementFactory());
        FumlMapping.setExecutionFactory(new ExecutionFactory());
        FumlMapping mapping = null;
        try {
            mapping = FumlMapping.parseAndMap(fileName);

            if (mapping == null) {
                System.out.println("Mapping failed.");
            } else {
                mapping.getModelElements();
                System.out.println("Mapped successfully.");
                mapping.print();
            }
        } catch (MappingError e) {
            System.out.println("Mapping failed.");
            Mapping errorMapping = e.getMapping();
            System.out.println(errorMapping);
            System.out.println(" error: " + e.getMessage());
            Object source = errorMapping.getSource();
            if (source != null) {
                System.out.println(" source: " + source);
                if (source instanceof SyntaxElement) {
                    SyntaxElement element = (SyntaxElement)source;
                    System.out.println(" file: " + element.getFileName() + 
                            " at line " + element.getLine() + 
                            " column " + element.getColumn());
                }
            }
            if (mapping != null) {
                mapping.print();
            }
        } catch (Exception e) {
            System.out.println("Mapping failed.");
            e.printStackTrace();
        }
    }
    
}

/*******************************************************************************
 *  Copyright 2019-2020 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.modeldriven.alf.uml.ElementFactory;

import org.apache.commons.lang3.tuple.Pair;

public class ElementFactoryTest {
    @Test
    void simple() {

        List<Pair<String, String>> classNames = Arrays.asList(
            Pair.<String, String>of("Package", "Package"), 
            Pair.<String, String>of("Class", "Class_"),
            Pair.<String, String>of("Classifier", "Classifier"),
            Pair.<String, String>of("Activity", "Activity")
        );
        classNames.stream().forEach(expectation -> {
            Class<?> javaInterface = ElementFactory.interfaceForName(expectation.getLeft());
            assertNotNull(javaInterface);
            assertEquals(expectation.getRight(), javaInterface.getSimpleName());
            assertTrue(javaInterface.isInterface()); 
        });

        assertNull(ElementFactory.interfaceForName("Foo"));
    }
}
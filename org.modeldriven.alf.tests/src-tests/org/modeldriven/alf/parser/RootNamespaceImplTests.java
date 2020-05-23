/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modeldriven.alf.fuml.units.ModelNamespaceImpl;
import org.modeldriven.alf.fuml.units.RootNamespaceImpl;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class RootNamespaceImplTests {
    
    private File baseDir;
    
    @BeforeEach
    public void createBaseDir() throws IOException {
        baseDir = Files.createTempDirectory(null).toFile(); 
    }
    
    @Test
    public void resolveModelFile() throws Exception {
        ModelNamespaceImpl rootNamespace = new RootNamespaceImpl();
        rootNamespace.setModelDirectory(Helper.getSampleLocationPath().toString());
        UnitDefinition resolved = rootNamespace.resolveModelFile(Helper.getSampleLocationPath().resolve("Example0a.alf").toString());
        assertNotNull(resolved);
        assertFalse(resolved instanceof MissingUnit);
    }
    
    @Test
    public void resolveModelFile_fileNotFound() throws Exception {
        ModelNamespaceImpl rootNamespace = new RootNamespaceImpl();
        rootNamespace.setModelDirectory(Helper.getSampleLocationPath().toString());
        try {
            rootNamespace.resolveModelFile(Helper.getSampleLocationPath().resolve("NonExisting.alf").toString());
            fail("Should have failed");
        } catch (FileNotFoundException e) {
            // success
        }
    }
    
    @Test
    public void resolveModelFile_parsingError() throws Exception {
        ModelNamespaceImpl rootNamespace = new RootNamespaceImpl();
        File badFile = new File(baseDir, "foo.alf");
        Files.write(badFile.toPath(), Collections.singletonList("namespace bar"));
        rootNamespace.setModelDirectory(baseDir.getAbsolutePath());
        UnitDefinition resolved = rootNamespace.resolveModelFile(badFile.getAbsolutePath());
        assertNull(resolved);
    }
    
}

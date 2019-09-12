package org.modeldriven.alf.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.modeldriven.alf.fuml.units.ModelNamespaceImpl;
import org.modeldriven.alf.fuml.units.RootNamespaceImpl;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class RootNamespaceImplTests {
    
    @Rule
    public TemporaryFolder privateDir = new TemporaryFolder(); 
    
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
        File badFile = privateDir.newFile("foo.alf");
        Files.write(badFile.toPath(), Collections.singletonList("namespace bar"));
        rootNamespace.setModelDirectory(privateDir.getRoot().getAbsolutePath());
        UnitDefinition resolved = rootNamespace.resolveModelFile(badFile.getAbsolutePath());
        assertNull(resolved);
    }
    
}

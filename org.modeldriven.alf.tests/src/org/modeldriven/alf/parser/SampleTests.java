/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.modeldriven.alf.parser.Helper.ensureNoProblems;
import static org.modeldriven.alf.parser.Helper.getSampleLocationPath;
import static org.modeldriven.alf.parser.Helper.safeRun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.modeldriven.alf.syntax.units.UnitDefinition;

/**
 * A suite that runs the Alf parser against every Alf source file in some location.
 * 
 * By default, uses {@value Helper#DEFAULT_SAMPLE_LOCATION} as sample location, 
 * but that value can overridden via the alf.samples.dir property.
 */
public class SampleTests {
    @TestFactory
    public Stream<DynamicTest> sampleFiles() throws IOException, URISyntaxException {
        Stream<URI> sampleFilePaths = getSampleFiles();
        if (sampleFilePaths == null) {
            return Stream.of(error(() -> "No sample files found"));
        }
        Stream<DynamicTest> tests = sampleFilePaths.map(this::createTest);
        return tests;
    }

    private Stream<URI> getSampleFiles() throws IOException, URISyntaxException {
        return getSampleFilesFromFileSystem();
    }

    private Stream<URI> getSampleFilesFromFileSystem() throws IOException {
        return Files.list(getSampleLocationPath()).map(it -> it.toUri());
    }

    // TODO-RC consider use this approach for obtaining sample files from the classpath for better build portability
    @SuppressWarnings("unused")
	private Stream<URI> getSampleFilesFromClasspath(String path) throws IOException, URISyntaxException {
        List<String> paths = new ArrayList<>();
        InputStream entries = getClass().getResourceAsStream(path);
        if (entries == null) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entries))) {
            String line;
            while ((line = reader.readLine()) != null) {
                paths.add(line);
            }
        }
        return paths.stream().map(it -> getClass().getResource(path + it)).map(it -> safeRun(() -> it.toURI()));
    }
    
    private DynamicTest error(Supplier<String> supplier) {
        return DynamicTest.dynamicTest("Samples not found", () -> fail(supplier));
    }

    private DynamicTest createTest(URI sampleFile) {
        return DynamicTest.dynamicTest(Paths.get(sampleFile.getPath()).getFileName().toString(), () -> runTest(sampleFile.toURL()));
    }

    private void runTest(URL sampleFile) throws Throwable {
        try (InputStreamReader contents = new InputStreamReader(sampleFile.openStream())) {
            Parser parser = ParserFactory.defaultImplementation().createParser(contents);
            UnitDefinition parsed = parser.parseUnitDefinition(true);
            ensureNoProblems(parser.getProblems());
            assertNotNull(parsed);
//            ensureNoProblems(parsed.checkConstraints());
        }
    }
}

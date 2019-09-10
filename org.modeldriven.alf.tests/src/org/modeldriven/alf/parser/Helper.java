package org.modeldriven.alf.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.PositionalTuple;
import org.modeldriven.alf.syntax.units.UnitDefinition;

@SuppressWarnings("unchecked")
public class Helper {
    
    public static final String DEFAULT_SAMPLE_LOCATION = "../org.modeldriven.alf/tests/";
    static final String SAMPLE_LOCATION = System.getProperty("alf.samples.dir", DEFAULT_SAMPLE_LOCATION);

    public static <T> List<T> getArguments(BehaviorInvocationExpression behaviorInvocation, int expectedCount,
            Function<Expression, T> mapper) {
        return assertAndMap(expectedCount, ((PositionalTuple) behaviorInvocation.getTuple()).getExpression(), mapper);
    }

    public static <T extends S, S> List<T> cast(Collection<S> input) {
        return map(input, (S s) -> (T) s);
    }

    public static <T extends S, S> List<T> cast(Collection<S> input, Class<T> clazz) {
        return map(input, (S s) -> (T) s);
    }

    public static <T extends Q, S, R extends S, Q> List<T> map(Collection<S> input, Function<R, Q> mapper) {
        return input.stream().map(s -> (T) mapper.apply((R) s)).collect(Collectors.toList());
    }

    public static <T extends Q, S, R extends S, Q> T single(Collection<S> input, Function<R, Q> mapper) {
        List<T> mapped = assertAndMap(1, input, mapper);
        return mapped.get(0);
    }

    public static <T extends S, S> T single(Collection<S> input) {
        return getAt(1, input, 0);
    }

    public static <T extends S, S> T search(Collection<S> input, Predicate<S> filter) {
        assertTrue(!input.isEmpty());
        return (T) input.stream().filter(filter).findFirst().orElseThrow(() -> new AssertionError("None matched"));
    }

    public static <T extends S, S> List<T> filter(Collection<S> input, Predicate<S> filter) {
        return input.stream().filter(filter).map(it -> (T) it).collect(Collectors.toList());
    }

    public static <T extends S, S> List<T> filter(Class<T> clazz, Collection<S> input) {
        return input.stream().filter(it -> clazz.isInstance(it)).map(it -> (T) it).collect(Collectors.toList());
    }

    public static <T extends S, S> T first(int expectedSize, Collection<S> input) {
        return getAt(expectedSize, input, 0);
    }

    public static <T extends S, S> T getAt(int expectedSize, Collection<S> input, int index) {
        List<T> mapped = assertAndMap(expectedSize, input);
        return mapped.get(index);
    }

    public static <Q, T extends Q, R extends S, S> T safeGet(S input, Function<R, Q> mapper) {
        Q result = mapper.apply((R) input);
        assertNotNull(result);
        return (T) result;
    }

    public static <T extends S, S> T require(S input) {
        assertNotNull(input);
        return (T) input;
    }

    public static <T extends S, S> T assertIsA(S input, Class<T> type) {
        assertNotNull(input);
        assertTrue(input.getClass().getSimpleName(), type.isInstance(input));
        return (T) input;
    }

    public static <T extends Q, S, R extends S, Q> List<T> assertAndMap(int expected, Collection<S> input,
            Function<R, Q> mapper) {
        assertEquals(expected, input.size());
        return map(input, mapper);
    }

    public static <T extends S, S> List<T> requireAtLeast(int minimumExpected, Collection<S> input) {
        final int actual = input.size();
        assertTrue(input.size() + " < " + minimumExpected + " - " + input.toString(), minimumExpected <=  actual);
        return cast(input);
    }
    
    public static <T extends S, S> List<T> require(int expected, Collection<S> input) {
        assertEquals(expected, input.size());
        return cast(input);
    }

    public static <T extends S, S> void requireEmpty(Collection<S> input, Function<T, String> toStringMapper) {
        String message = input.stream().map(it -> toStringMapper.apply((T) it)).collect(Collectors.joining(", "));
        assertTrue(message, input.isEmpty());
    }

    public static <T extends S, S> List<T> assertAndMap(int expected, Collection<S> input) {
        assertTrue(expected <= input.size());
        return map(input, (S s) -> (T) s);
    }

    public static Parser newParser(String input) {
        return ParserFactory.defaultImplementation().createParser(new StringReader(input));
    }

    public static void checkConstraints(SyntaxElement syntaxElement) {
        requireEmpty(syntaxElement.checkConstraints(), it -> it.getProblemKey());
    }
    
    interface SafeRunnable<T> {
        T run() throws Exception; 
    }
    
    public static <T> T safeRun(SafeRunnable<T> runnable) {
        try {
            return runnable.run();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getSampleLocationPath() {
        return Paths.get(SAMPLE_LOCATION).toAbsolutePath();
    }
    
    public static <SP extends SourceProblem> void ensureNoProblems(Collection<SP> problems) {
        assertProblems(problems.isEmpty(), problems);
    }
    
    public static <SP extends SourceProblem> void assertProblems(boolean condition, Collection<SP> problems) {
        Assertions.assertTrue(condition, () -> generateProblemString(problems));
    }
    
    public static String generateProblemString(Collection<? extends SourceProblem> problems) {
        return problems.stream().map(it -> it.toString()).collect(Collectors.joining(", "));
    }

    public static UnitDefinition parse(Parser parser, boolean eof) {
        UnitDefinition parsedUnit = parser.parseUnitDefinition(eof);
        checkProblemMessages(parser.getProblems());
        return parsedUnit;
    }

    public static void checkProblemMessages(Collection<SourceProblem> problems) {
        problems.forEach(Helper::checkProblemMessage);
    }
    
    public static void checkProblemMessage(SourceProblem problem) {
        assertTrue(problem.getErrorMessage(), problem.getErrorMessage().startsWith("[" + problem.getBeginLine() + ":" + problem.getBeginColumn()));
        assertFalse(problem.getProblemKey(), problem.getProblemKey().startsWith("["));
    }
    
}

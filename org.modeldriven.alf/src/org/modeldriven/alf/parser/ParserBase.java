package org.modeldriven.alf.parser;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.UnitDefinition;

/**
 * A base implementation for Alf parsers.
 * 
 * Implementation note: this class a 100% handwritten Java class meant to contain 
 * most of the custom Java code that is required by the Alf RI parser,
 * thus avoiding the idiosyncrasies of embedding Java code in a JavaCC grammar.  
 */
public abstract class ParserBase implements Parser {
    
    /**
     * A protocol for JavaCC-generated parsing operations.
     */
    private static interface ParseOperation<T> {
        T parse() throws ParseException;
    }
    
    private class ParserTokenSequence implements TokenSequence {

        @Override
        public int skip() {
            return getNextToken().kind;
        }

        @Override
        public int peek(int index) {
            return getToken(index).kind;
        }
        
    }
    
    private TokenSequence tokenSource = new ParserTokenSequence();

    protected String fileName = "";
    
    private List<SourceProblem> collectedProblems = new ArrayList<>();

    protected abstract SimpleCharStream getCharStream();
    
    protected abstract Token getCurrentToken();

    protected abstract Token getToken(int index);
    
    protected abstract Token getNextToken();
    
    public Collection<SourceProblem> getProblems() {
        return new ArrayList<>(this.collectedProblems);
    }
    
    protected void collectParsingError(ParseException e) {
        collectParsingError(e.getMessage(), new UnexpectedElement(this));
    }
    
    protected void collectParsingError(String message, ParsedElement element) {
        ParsingProblem problem = new ParsingProblem(message, element);
        collectProblem(problem);
    }

    protected void collectLexicalError(TokenMgrError e) {
        collectLexicalError(e.getMessage(), e.getLine(), e.getColumn());
    }
    
    protected void collectLexicalError(String message, int lineNumber, int columnNumber) {
        LexicalProblem problem = new LexicalProblem(message, new UnexpectedElement(fileName, lineNumber, columnNumber));
        collectProblem(problem);
    }

    private void collectProblem(SourceProblem problem) {
        if (!collectedProblems.isEmpty()) {
            SourceProblem previousProblem = collectedProblems.get(collectedProblems.size()-1);
            boolean sameAsPrevious = previousProblem.compareTo(problem) == 0;
            if (sameAsPrevious) {
                // ignore - parser may report the same problem twice due to error recovery
                return;
            }
        }
        collectedProblems.add(problem);
    }
    
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setTabSize(int tabSize) {
        this.getCharStream().setTabSize(tabSize);
    }

    public int getTabSize() {
        return this.getCharStream().getTabSize();
    }

    public int getLine() {
        return this.getCurrentToken().beginLine;
    }

    public int getColumn() {
        return this.getCurrentToken().beginColumn;
    }
    
    public void provideInfo(ParsedElement element, boolean fromNextToken) {
        Token token = this.getToken(0);
        if (fromNextToken && token.next != null) {
            token = token.next;
        }
        provideInfo(element, token);
    }
    
    public void provideInfo(ParsedElement element, Token sourceElement) {
        element.setParserInfo(this.fileName, sourceElement.beginLine, sourceElement.beginColumn, sourceElement.endLine,
                sourceElement.endColumn);
    }

    protected void provideBegin(ParsedElement element, Token token) {
        element.setBegin(token.beginLine, token.beginColumn);
    }

    protected void provideEnd(ParsedElement element) {
        Token token = this.getToken(0);
        element.setEnd(token.endLine, token.endColumn);
    }

    protected ParseException generateParseException(Token token, String message) {
        return new ParseException(token, message);
    }
    
    @Override
    public QualifiedName parseTypeName() {
        return performParseOperation(this::TypeName, false);
    }
    
    @Override
    public Expression parseExpression(boolean eof) {
        Expression parsed = performParseOperation(this::Expression, eof);
        return parsed;
    }
    
    @Override
    public Block parseStatementSequence(boolean eof) {
        Block parsed = performParseOperation(this::StatementSequence, eof);
        return parsed;
    }
    
    @Override
    public UnitDefinition parseUnitDefinition(boolean eof) {
        UnitDefinition parsed = performParseOperation(this::UnitDefinition, eof);
        return parsed;
    }
    
    /**
     * Performs a parsing operation collecting any potentially generated exceptions.
     *  
     * @param toRun
     * @param eofExpected whether the parsed element should be the last one
     * @return a partially parsed element, never null (bugs aside)
     */
    private <T> T performParseOperation(ParseOperation<T> toRun, boolean eofExpected) {
        collectedProblems.clear();
        T parsed = null;
        try {
            parsed = toRun.parse();
        } catch (TokenMgrError e) {
            collectLexicalError(e);
            return parsed;
        } catch (ParseException e) {
            // a case we can't recover from
            // (a bug or a missing feature)
            collectParsingError(e);
            return null;
        }
        if (eofExpected && collectedProblems.isEmpty()) {
            parseEOF();
        }
        return parsed;

    }
    
    protected abstract void parseEOF();

    protected abstract QualifiedName TypeName() throws ParseException;
    
    protected abstract Block StatementSequence() throws ParseException;
    
    protected abstract Block StatementSequenceEOF() throws ParseException;
    
    protected abstract Expression Expression() throws ParseException;
    
    protected abstract Expression ExpressionEOF() throws ParseException;
    
    protected abstract UnitDefinition UnitDefinition() throws ParseException;
    
    protected abstract UnitDefinition UnitDefinitionEOF() throws ParseException;

    protected boolean skipToOrPast(BitSet skipTo, BitSet skipPast) {
        return tokenSource.skipToOrPast(skipTo, skipPast);
    }
    
}


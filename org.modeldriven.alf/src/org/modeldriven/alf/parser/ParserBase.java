package org.modeldriven.alf.parser;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.expressions.Expression;
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

    protected String fileName = "";
    
    private Collection<SourceProblem> collectedProblems = new ArrayList<>();

    protected abstract SimpleCharStream getCharStream();
    
    protected abstract Token getCurrentToken();

    protected abstract Token getToken(int index);
    
    protected abstract Token getNextToken();
    
    public Collection<SourceProblem> getProblems() {
        return new ArrayList<>(this.collectedProblems);
    }
    
    protected void collectParsingError(ParseException e) {
        collectedProblems.add(new ParsingProblem(e.getMessage(), new UnexpectedElement(this)));
    }
    
    protected void collectParsingError(TokenMgrError e) {
        collectedProblems.add(new LexicalProblem(e.getMessage(), new UnexpectedElement(fileName, e.getLine(), e.getColumn())));
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
    
    protected Token skipToNextToken(int kind) {
        Token t;
        do {
          t = getNextToken();
        } while (t.kind != kind && kind != -1);
        return t;
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

    private String formatErrorMessage(String message) {
        return SourceProblem.formatErrorMessage(this.getLine(), this.getColumn(), message);
    }

    protected ParseException generateParseException(Token token, String message) {
        return new ParseException(token, formatErrorMessage(message));
    }
    
    @Override
    public Expression parseExpression(boolean eof) {
        return performParseOperation(eof ? this::ExpressionEOF : this::Expression);
    }
    
    @Override
    public Block parseStatementSequence(boolean eof) {
        return performParseOperation(eof ? this::StatementSequenceEOF : this::StatementSequence);
    }
    
    @Override
    public UnitDefinition parseUnitDefinition(boolean eof) {
        return performParseOperation(eof ? this::UnitDefinitionEOF : this::UnitDefinition);
    }
    
    /**
     * Performs a parsing operation collecting any potentially generated exceptions.
     *  
     * @param toRun
     * @return 
     */
    private <T> T performParseOperation(ParseOperation<T> toRun) {
        collectedProblems.clear();
        try {
            return toRun.parse();
        } catch (TokenMgrError e) {
            collectParsingError(e);
            return null;
        } catch (ParseException e) {
            // we will already have collected any exception
            return null;
        }
    }
    
    protected abstract Block StatementSequence() throws ParseException;
    
    protected abstract Block StatementSequenceEOF() throws ParseException;
    
    protected abstract Expression Expression() throws ParseException;
    
    protected abstract Expression ExpressionEOF() throws ParseException;
    
    protected abstract UnitDefinition UnitDefinition() throws ParseException;
    
    protected abstract UnitDefinition UnitDefinitionEOF() throws ParseException;
}


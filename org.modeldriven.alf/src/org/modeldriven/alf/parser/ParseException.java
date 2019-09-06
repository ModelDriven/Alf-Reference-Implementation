/*******************************************************************************
 * Copyright (c) 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
/* Generated By:JavaCC: Do not edit this line. ParseException.java Version 6.0 */
/* JavaCCOptions:KEEP_LINE_COL=null */
package org.modeldriven.alf.parser;

import java.util.Arrays;
import java.util.function.Supplier;

import org.modeldriven.alf.syntax.common.SourceProblem;

/**
 * This exception is thrown when parse errors are encountered.
 * You can explicitly create objects of this exception type by
 * calling the method generateParseException in the generated
 * parser.
 *
 * You can modify this class to customize your error reporting
 * mechanisms so long as you retain the public fields.
 */
@SuppressWarnings("all")
class ParseException extends Exception {

  /**
   * The version identifier for this Serializable class.
   * Increment only if the <i>serialized</i> form of the
   * class changes.
   */
  private static final long serialVersionUID = 1L;

  /**
   * This constructor is used by the method "generateParseException"
   * in the generated parser.  Calling this constructor generates
   * a new object of this type with the fields "currentToken",
   * "expectedTokenSequences", and "tokenImage" set.
   */
  public ParseException(Token currentTokenVal,
                        int[][] expectedTokenSequencesVal,
                        String[] tokenImageVal
                       )
  {
    super(initialise(currentTokenVal, expectedTokenSequencesVal, tokenImageVal));
    this.setCurrentToken(currentTokenVal);
    expectedTokenSequences = expectedTokenSequencesVal;
    tokenImage = tokenImageVal;
  }
  
  private void setCurrentToken(Token currentTokenVal) {
      if (currentTokenVal.next != null) {
          currentTokenVal = currentTokenVal.next;
      }
      beginLine = currentTokenVal.beginLine;
      beginColumn = currentTokenVal.beginColumn;
      endLine = currentTokenVal.endLine;
      endColumn = currentTokenVal.endColumn;
  }

  public ParseException() {
    super();
  }
  
  /** Constructor with message. */
  public ParseException(Token currentToken, String message) {
    super(message);
    this.setCurrentToken(currentToken);
  }


  /**
   * Each entry in this array is an array of integers.  Each array
   * of integers represents a sequence of tokens (by their ordinal
   * values) that is expected at this point of the parse.
   */
  public int[][] expectedTokenSequences;
  
  public String[] expectedTokenImages;

  /**
   * This is a reference to the "tokenImage" array of the generated
   * parser within which the parse error occurred.  This array is
   * defined in the generated ...Constants interface.
   */
  public String[] tokenImage;
  
  protected int beginLine = 0;
  protected int beginColumn = 0;
  protected int endLine = 0;
  protected int endColumn = 0;
  
  public boolean isIncomplete() {
      return expectedTokenSequences != null; 
  }
  
  public int[] getExpectedTokens() {
      return Arrays.stream(expectedTokenSequences).mapToInt(sequence -> sequence[0]).toArray();
  }
  
  public String[] getExpectedTokenImages() {
      return Arrays.stream(expectedTokenSequences).map(sequence -> tokenImage[sequence[0]]).toArray(sz -> new String[sz]);
  }
  
  public int getBeginLine() {
      return this.beginLine;
  }

  public int getBeginColumn() {
      return this.beginColumn;
  }

  public int getEndLine() {
      return this.endLine;
  }

  public int getEndColumn() {
      return this.endColumn;
  }

  private static String initialise(Token currentToken,
                           int[][] expectedTokenSequences,
                           String[] tokenImage) {
    String eol = System.getProperty("line.separator", "\n");
    int maxSize = 0;
    for (int i = 0; i < expectedTokenSequences.length; i++) {
      if (maxSize < expectedTokenSequences[i].length) {
        maxSize = expectedTokenSequences[i].length;
      }
    }
    Token tok = currentToken.next;
    String retval = "Encountered \"";
    for (int i = 0; i < maxSize; i++) {
      if (i != 0) retval += " ";
      if (tok.kind == 0) {
        retval += tokenImage[0];
        break;
      }
      retval += " " + tokenImage[tok.kind];
      retval += " \"";
      retval += add_escapes(tok.image);
      retval += " \"";
      tok = tok.next;
    }
    retval += "\".";
    return retval;
  }

  /**
   * The end of line string for this machine.
   */
  protected String eol = System.getProperty("line.separator", "\n");

  /**
   * Used to convert raw characters to their escaped version
   * when these raw version cannot be used as part of an ASCII
   * string literal.
   */
  static protected String add_escapes(String str) {
      StringBuffer retval = new StringBuffer();
      char ch;
      for (int i = 0; i < str.length(); i++) {
        switch (str.charAt(i))
        {
           case 0 :
              continue;
           case '\b':
              retval.append("\\b");
              continue;
           case '\t':
              retval.append("\\t");
              continue;
           case '\n':
              retval.append("\\n");
              continue;
           case '\f':
              retval.append("\\f");
              continue;
           case '\r':
              retval.append("\\r");
              continue;
           case '\"':
              retval.append("\\\"");
              continue;
           case '\'':
              retval.append("\\\'");
              continue;
           case '\\':
              retval.append("\\\\");
              continue;
           default:
              if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
                 String s = "0000" + Integer.toString(ch, 16);
                 retval.append("\\u" + s.substring(s.length() - 4, s.length()));
              } else {
                 retval.append(ch);
              }
              continue;
        }
      }
      return retval.toString();
   }

}
/* JavaCC - OriginalChecksum=8dc207f12a5ba6f400268022dda9be93 (do not edit this line) */

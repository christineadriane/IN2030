package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspStmt extends AspSyntax{

    AspStmt(int n){
        super(n);
    }

    static AspStmt parse(Scanner s){
        enterParser("statement");

        AspStmt as = null;

        switch (s.curToken().kind){
            case forToken:
            case ifToken:
            case whileToken:
            case defToken:
                as = AspCompoundStmt.parse(s); break;
            case nameToken:
            case integerToken:
            case floatToken:
            case stringToken:
            case passToken:
            case returnToken:
                as = AspSmallStmtList.parse(s); break;
            default:
                //as = AspSmallStmtList.parse(s); //break;
                parserError("Expected an expression stmt, but found a " + s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("statement");
        return as;
    }
}

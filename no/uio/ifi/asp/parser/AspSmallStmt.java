package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


abstract class AspSmallStmt extends AspSyntax {

	AspSmallStmt(int n) {
		super(n);
	}

	static AspSmallStmt parse(Scanner s) {
		enterParser("small stmt");

		AspSmallStmt ass = null;

        switch (s.curToken().kind){
            case nameToken:
			case integerToken:
            case floatToken:
            case stringToken:
                if(s.anyEqualToken()){
                    ass = AspAssignment.parse(s); break;
                }
                else{
                    ass = AspExprStmt.parse(s); break;
                }
            case passToken:
                ass = AspPassStmt.parse(s); break;
            case returnToken:
                ass = AspReturnStmt.parse(s); break;
            default:
				//return null;
                parserError("Expected an expression small stmt, but found a " + s.curToken().kind + "!", s.curLineNum());
        }

		leaveParser("small stmt");
		return ass;
	}
}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSmallStmtList extends AspStmt{
    ArrayList<AspSmallStmt> smallStmt = new ArrayList<>();

    AspSmallStmtList(int n){
        super(n);
    }

    public static AspSmallStmtList parse(Scanner s){
    enterParser("small stmt list");
    AspSmallStmtList assl = new AspSmallStmtList(s.curLineNum());

	while(s.curToken().kind != newLineToken){
		assl.smallStmt.add(AspSmallStmt.parse(s));

		if(s.curToken().kind == semicolonToken){
			skip(s, TokenKind.semicolonToken);
		}
	}

    skip(s, TokenKind.newLineToken);

    leaveParser("small stmt list");
    return assl;
    }


    @Override
    public void prettyPrint() {
        int nPrinted = 0;
        for(AspSmallStmt ass : smallStmt){
            if(nPrinted > 0){
                prettyWrite(";");
            }
            ass.prettyPrint();
            nPrinted++;
        }
        prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = smallStmt.get(0).eval(curScope);
        for (int i = 1; i < smallStmt.size(); i++) {
            v = smallStmt.get(i).eval(curScope);
        }
        return v;
    }
}

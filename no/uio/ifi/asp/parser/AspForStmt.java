package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspForStmt extends AspCompoundStmt{
    AspName an;
    AspExpr ae;
    AspSuite as;

    AspForStmt(int n){
        super(n);
    }

    static AspForStmt parse(Scanner s){
        enterParser("for statement");
        AspForStmt afs = new AspForStmt(s.curLineNum());

        skip(s, forToken);
        afs.an = AspName.parse(s);
        skip(s, inToken);
        afs.ae = AspExpr.parse(s);
        skip(s, colonToken);
        afs.as = AspSuite.parse(s);

        leaveParser("for statement");
        return afs;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("for");
        an.prettyPrint();
        prettyWrite("in");
        ae.prettyPrint();
        prettyWrite(":");
        as.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }

}

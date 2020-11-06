package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspReturnStmt extends AspSmallStmt{
    AspExpr ae;

    AspReturnStmt(int n){
        super(n);
    }

    static AspReturnStmt parse(Scanner s){
        enterParser("return statement");

        AspReturnStmt ars = new AspReturnStmt(s.curLineNum());

        skip(s, returnToken);
        ars.ae = AspExpr.parse(s);

        leaveParser("return statement");
        return ars;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("return");
        ae.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }

}

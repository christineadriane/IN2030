package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspExprStmt extends AspSmallStmt{
    AspExpr ae;

    AspExprStmt(int n){
        super(n);
    }

    static AspExprStmt parse(Scanner s){
        enterParser("expression statement");

        AspExprStmt aes = new AspExprStmt(s.curLineNum());
        aes.ae = AspExpr.parse(s);

        leaveParser("expression statement");
        return aes;
    }

    @Override
    public void prettyPrint(){
        ae.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }
}

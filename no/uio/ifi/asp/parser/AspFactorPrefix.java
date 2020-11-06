package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorPrefix extends AspSyntax{
    String factor;

    AspFactorPrefix(int n){
        super(n);
    }

    static AspFactorPrefix parse(Scanner s){
        enterParser("factor prefix");

        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());

        if(s.curToken().kind == plusToken){
            afp.factor = "+";
            skip(s, plusToken);
        }
        else if(s.curToken().kind == minusToken){
            afp.factor = "-";
            skip(s, minusToken);
        }

        leaveParser("factor prefix");
        return afp;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(factor);
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }
}

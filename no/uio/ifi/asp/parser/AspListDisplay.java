package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspListDisplay extends AspAtom{
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspListDisplay(int n){
        super(n);
    }

    static AspListDisplay parse(Scanner s){
        enterParser("list display");

        AspListDisplay als = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);

        if(s.curToken().kind != rightBracketToken){
            while(true){
                als.expr.add(AspExpr.parse(s));
                if(s.curToken().kind != commaToken){
                    break;
                }
                skip(s, commaToken);
            }
        }

        skip(s, rightBracketToken);

        leaveParser("list display");
        return als;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");
        if(expr.size() > 0){
            int nPrinted = 0;
            for(AspExpr ae : expr){
                if(nPrinted > 0){
                    prettyWrite(",");
                }
                ae.prettyPrint();
                ++nPrinted;
            }
        }
        prettyWrite("]");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }

}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspDictDisplay extends AspAtom{
    ArrayList<AspStringLiteral> strLit = new ArrayList<>();
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspDictDisplay(int n){
        super(n);
    }

    static AspDictDisplay parse(Scanner s){
        enterParser("dict display");

        AspDictDisplay add = new AspDictDisplay(s.curLineNum());
        skip(s, leftBraceToken);


        if(s.curToken().kind != rightBraceToken){
            while(true){
                add.strLit.add(AspStringLiteral.parse(s));
                skip(s, colonToken);
                add.expr.add(AspExpr.parse(s));
                if(s.curToken().kind != commaToken){
                    break;
                }
                skip(s, commaToken);
            }
        }

        skip(s, rightBraceToken);

        leaveParser("dict display");
        return add;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("{");

        if(strLit.size()>0){
            for(int i = 0; i < strLit.size(); i++){
                strLit.get(i).prettyPrint();
                prettyWrite(" : ");
                expr.get(i).prettyPrint();
            }
        }
        prettyWrite("}");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }
}

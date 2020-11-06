package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTermOpr extends AspSyntax{
    String opr;
    TokenKind kind;

    AspTermOpr(int n){
        super(n);
    }

    static AspTermOpr parse(Scanner s){
        enterParser("term opr");

        AspTermOpr ato = new AspTermOpr(s.curLineNum());

        if(s.curToken().kind == plusToken){
            ato.opr = "+";
            ato.kind = plusToken;
            skip(s, plusToken);
        }
        else if(s.curToken().kind == minusToken){
            ato.opr = "-";
            ato.kind = minusToken;
            skip(s, minusToken);
        }

        leaveParser("term opr");
        return ato;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(opr);
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }

}

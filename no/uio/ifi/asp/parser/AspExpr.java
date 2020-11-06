package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andTest = new ArrayList<>();

    AspExpr(int n){
        super(n);
    }

    public static AspExpr parse(Scanner s){
        enterParser("expr");

        AspExpr ae = new AspExpr(s.curLineNum());

        while(true){
            ae.andTest.add(AspAndTest.parse(s));
            if(s.curToken().kind != orToken){
                break;
            }
            skip(s, orToken);
        }

        leaveParser("expr");
        return ae;
    }


    @Override
    public void prettyPrint() {
        int nPrinted = 0;

        for (AspAndTest aat : andTest){
            if (nPrinted > 0){
                prettyWrite(" or ");
            }
            aat.prettyPrint();
            nPrinted++;
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = andTest.get(0).eval(curScope);
        for (int i = 1; i < andTest.size(); i++) {
            if (!v.getBoolValue("or operand", this))
            return v;
            v = andTest.get(i).eval(curScope);
        }
        return v;
    }
}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspComparison extends AspSyntax{
    ArrayList<AspCompOpr> compOpr = new ArrayList<>();
    ArrayList<AspTerm> term = new ArrayList<>();

    AspComparison(int n){
        super(n);
    }

    static AspComparison parse(Scanner s){
        enterParser("comparison");

        AspComparison ac = new AspComparison(s.curLineNum());

        while(true){
            ac.term.add(AspTerm.parse(s));
            if(s.isCompOpr()){
                ac.compOpr.add(AspCompOpr.parse(s));
            }
            else{
                break;
            }
        }
        leaveParser("comparison");
        return ac;
    }

    @Override
    public void prettyPrint() {

        int nPrinted = 0;

        for (AspTerm at : term){
            int i = 0;
            if (nPrinted > 0){
                compOpr.get(i).prettyPrint();
                i++;
            }
            at.prettyPrint();
            nPrinted++;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = term.get(0).eval(curScope);
        for(int i = 1; i < term.size(); i++){
            TokenKind k = compOpr.get(i-1).kind;
            switch(k){
            case lessToken:
                v = v.evalLess(compOpr.get(i).eval(curScope), this); break;
            case greaterToken:
                v = v.evalGreater(compOpr.get(i).eval(curScope), this); break;
            case doubleEqualToken:
                v = v.evalEqual(compOpr.get(i).eval(curScope), this); break;
            case greaterEqualToken:
                v = v.evalGreaterEqual(compOpr.get(i).eval(curScope), this); break;
            case lessEqualToken:
                v = v.evalLessEqual(compOpr.get(i).eval(curScope), this); break;
            case notEqualToken:
                v = v.evalNotEqual(compOpr.get(i).eval(curScope), this); break;
            default:
                Main.panic("Illegal term operator: " + k + "!");
            }
        }
        return v;
    }
}

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactor extends AspSyntax{
    ArrayList<AspFactorPrefix> factorPrefix = new ArrayList<>();
    ArrayList<AspPrimary> primary = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOpr = new ArrayList<>();

    AspFactor(int n){
        super(n);
    }

    static AspFactor parse (Scanner s){
        enterParser("factor");

        AspFactor af = new AspFactor(s.curLineNum());

        while(true){
            if(s.isFactorPrefix()){
                af.factorPrefix.add(AspFactorPrefix.parse(s));
            }
            else{
                af.factorPrefix.add(null);
            }

            af.primary.add(AspPrimary.parse(s));

            if(!s.isFactorOpr()){
                break;
            }
            af.factorOpr.add(AspFactorOpr.parse(s));
        }

        leaveParser("factor");
        return af;
    }

    // ikke helt riktig, maa ordne if-setningene.
    @Override
    public void prettyPrint(){
        for(int i = 0; i < primary.size(); i++){
            if(factorPrefix.get(i) != null){
                factorPrefix.get(i).prettyPrint();
            }
            primary.get(i).prettyPrint();

            if(i < factorOpr.size()){
                factorOpr.get(i).prettyPrint();
            }
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }

}

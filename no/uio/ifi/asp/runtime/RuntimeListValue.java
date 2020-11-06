package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    ArrayList<RuntimeValue> listValue = new ArrayList<>();

    public RuntimeListValue(ArrayList<RuntimeValue> lv) {
	    listValue = lv;
    }

    @Override
    protected String typeName() {
	    return "list";
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	    return "list";
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeListValue(multiply(listValue, v.getIntValue("* operand", where)));
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    public ArrayList<RuntimeValue> multiply(ArrayList<RuntimeValue> v, long l){
        ArrayList<RuntimeValue> newList = new ArrayList<>();
        for(int i = 0; i < l; i++){
            newList.addAll(v);
        }
        return newList;
    }
}

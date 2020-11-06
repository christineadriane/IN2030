package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String stringValue;

    public RuntimeStringValue(String s) {
	    stringValue = s;
    }

    @Override
    protected String typeName() {
	    return "string";
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	    return "string";
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeStringValue(stringValue + v.getStringValue("+ operand", where));
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeStringValue(multiply(stringValue, v.getIntValue("* operand", where)));
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    // hjelpemetode for aa multiplisere streng
    public String multiply(String s, long l){
        String newStr = "";
        for(int i = 0; i < l; i++){
            newStr += s;
        }
        return newStr;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue == v.getStringValue("== operand", where));
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue != v.getStringValue("!= operand", where));
        }
        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    // https://www.w3schools.com/java/ref_string_compareto.asp
    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(
            stringValue.compareTo(v.getStringValue("< operand", where)) < 0);
        }
        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(
            stringValue.compareTo(v.getStringValue("<= operand", where)) <= 0);
        }
        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(
            stringValue.compareTo(v.getStringValue("> operand", where)) > 0);
        }
        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(
            stringValue.compareTo(v.getStringValue(">= operand", where)) >= 0);
        }
        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }
}

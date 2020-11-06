package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double f) {
	    floatValue = f;
    }

    @Override
    protected String typeName() {
	    return "float";
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	    return "float";
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where){
        return new RuntimeFloatValue(floatValue);
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where){
        return new RuntimeFloatValue((-1 * floatValue));
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(floatValue + v.getFloatValue("+ operand", where));
        }
        else if (v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(floatValue + v.getIntValue("+ operand", where));
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(floatValue - v.getFloatValue("- operand", where));
        }
        else if (v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(floatValue - v.getIntValue("- operand", where));
        }
        runtimeError("Type error for -.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(floatValue * v.getIntValue("* operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(floatValue * v.getFloatValue("* operand", where));
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(floatValue / v.getIntValue("/ operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operand", where));
        }
        runtimeError("Type error for /.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeFloatValue(Math.floor(floatValue / v.getIntValue("// operand", where)));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(Math.floor(floatValue / v.getFloatValue("// operand", where)));
        }
        runtimeError("Type error for //.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            double l = v.getFloatValue("% operand", where);
            return new RuntimeFloatValue((floatValue - l) * Math.floor(floatValue / l));
        }
        else if (v instanceof RuntimeFloatValue){
            double l = v.getFloatValue("% operand", where);
            return new RuntimeFloatValue((floatValue - l) * Math.floor(floatValue / l));
        }
        runtimeError("Type error for %.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(floatValue == v.getIntValue("== operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(floatValue == v.getFloatValue("== operand", where));
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(floatValue != v.getIntValue("!= operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(floatValue != v.getFloatValue("!= operand", where));
        }
        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(floatValue < v.getIntValue("< operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(floatValue < v.getFloatValue("< operand", where));
        }
        runtimeError("Type error for <.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(floatValue <= v.getIntValue("<= operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(floatValue <= v.getFloatValue("<= operand", where));
        }
        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(floatValue > v.getIntValue("> operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(floatValue > v.getFloatValue("> operand", where));
        }
        runtimeError("Type error for >.", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
        if(v instanceof RuntimeIntValue){
            return new RuntimeBoolValue(floatValue >= v.getIntValue(">= operand", where));
        }
        else if (v instanceof RuntimeFloatValue){
            return new RuntimeBoolValue(floatValue >= v.getFloatValue(">= operand", where));
        }
        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler
    }
}

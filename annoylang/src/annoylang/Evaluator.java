package annoylang;

import java.util.List;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import static annoylang.AST.*;
import static annoylang.Value.*;
import annoylang.Env.*;

public class Evaluator implements AST.Visitor<Value> {

    // Perform a type check. Check to see if the value is one
    // of the expected types. Returns the value on success and an
    // error value on failure
    private Value checkType(Value value, Class... types) {
        for (Class type : types) {
            if (type.isInstance(value)) {
                return value;
            }
        }
        return new Value.Error("type error: expected " + types[0] + ", got " + value.getClass().getSimpleName());
    }

    @Override
    public Value visit(AST.Program p, Env env) {
        Value result = null;
        for (AST.Exp exp : p.exps()) {
            result = exp.accept(this, env);
        }
        return result;
    }

    @Override
    public Value visit(AST.IdExp e, Env env) {
        return env.lookup(e.name());
    }

    @Override
    public Value visit(AST.NumExp e, Env env) {
        return new NumValue(e.v());
    }

    @Override
    public Value visit(AST.StringLiteralExp e, Env env) {
        return new StringValue(e.string());
    }

    @Override
    public Value visit(AST.NullExp e, Env env) {
        return new NullValue();
    }

    @Override
    public Value visit(AST.FunctionDefExp e, Env env) {
        Value v = new FunValue(env, e.params(), e.body());
        env.set(e.id().name(), v);
        return v;
    }

    @Override
    public Value visit(AST.ValueDefExp e, Env env) {
        Value v = e.body().accept(this, env);
        env.set(e.id().name(), v);
        return v;
    }

    @Override
    public Value visit(AST.CallExp e, Env env) {
        FunValue function = (FunValue) env.lookup(e.id().name());

		// Call-by-value semantics
		List<Value> actuals = new ArrayList<Value>();
		for(Exp exp : e.args()) 
			actuals.add((Value)exp.accept(this, env));

		List<String> formals = function.formals();
 		if (formals.size()!=actuals.size())
			return new Value.Error("Argument mismatch in call.");

		Env fun_env = new RefEnv(function.env());
		for (int index = 0; index < formals.size(); index++)
			fun_env.set(formals.get(index), actuals.get(index));

        return function.body().accept(this, fun_env);
    }

    @Override
    public Value visit(AST.AddExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() + right.v());
    }

    @Override
    public Value visit(AST.SubExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() - right.v());
    }

    @Override
    public Value visit(AST.MulExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() * right.v());
    }

    @Override
    public Value visit(AST.DivExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() / right.v());
    }

    @Override
    public Value visit(AST.PowExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(Math.pow(left.v(), right.v()));
    }

    @Override
    public Value visit(AST.EqExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() == right.v());
    }

    @Override
    public Value visit(AST.NeqExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() != right.v());
    }

    @Override
    public Value visit(AST.LtExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() < right.v());
    }

    @Override
    public Value visit(AST.LteExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() <= right.v());
    }

    @Override
    public Value visit(AST.GtExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() > right.v());
    }

    @Override
    public Value visit(AST.GteExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v() >= right.v());
    }

    @Override
    public Value visit(AST.AndExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v()!=0 && right.v()!=0);
    }

    @Override
    public Value visit(AST.OrExp e, Env env) {
        NumValue left = (NumValue) e.left().accept(this, env);
        NumValue right =(NumValue)  e.right().accept(this, env);
        return new NumValue(left.v()!=0 || right.v()!=0);
    }

    @Override
    public Value visit(AST.NotExp e, Env env) {
        NumValue val = (NumValue) e.exp().accept(this, env);
        return new NumValue(val.v() == 0);
    }

    @Override
    public Value visit(AST.WhileExp e, Env env) {
        Value condValue = e.cond().accept(this, env);
        condValue = checkType(condValue, NumValue.class);
        double n = ((NumValue) condValue).v();
        Value result = new NullValue();

        while (n != 0) {
            result = e.body().accept(this, env);
        }
        return result;
    }

    @Override
    public Value visit(AST.IfExp e, Env env) {
        Value condValue = e.cond().accept(this, env);
        condValue = checkType(condValue, NumValue.class);
        double n = ((NumValue) condValue).v();

        if (n != 0) {
            return e.then().accept(this, env);
        } else {
            return e.els().accept(this, env);
        }
    }

    @Override
    public Value visit(AST.RandExp e, Env env) {
        return new NumValue(Math.random());
    }

    @Override
    public Value visit(AST.PrintExp e, Env env) {
        Value exp = e.exp().accept(this, env);
        System.out.println(exp);
        return exp;
    }

    @Override
    public Value visit(AST.CatExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new StringValue(left.toString() + right.toString());
    }

    @Override
    public Value visit(AST.SlengthExp e, Env env) {
        Value exp = e.exp().accept(this, env);
        return new NumValue(exp.toString().length());
    }

    @Override
    public Value visit(AST.SmidExp e, Env env) {
        String s = e.exp().accept(this, env).toString();
        int start = (int) ((NumValue)e.start().accept(this, env)).v();
        int end = (int) ((NumValue)e.end().accept(this, env)).v();
        return new StringValue(s.substring(start, end));
    }

    @Override
    public Value visit(AST.StringExp e, Env env) {
        return e.exp().accept(this, env);
    }
}

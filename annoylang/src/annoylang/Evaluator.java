package annoylang;

import java.util.List;

public class Evaluator implements AST.Visitor<Value> {

    // Perform a type check. Check to see if the value is one
    // of the expected types. Returns the value on success and an
    // error value on failure
    private Value checkType(Value value, Class... types) {
        for (Value.Type type : types) {
            if (type.isInstance(value)) {
                return value;
            }
        }
        return new Value.Error("type error: expected " + types[0] + ", got " + value.type());
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
        Value v = Value.FunctionValue(env, e.formals(), e.body());
        env.set(e.name(), v);
        return v;
    }

    @Override
    public Value visit(AST.CallExp e, Env env) {
        FunctionValue function = (FunctionValue) env.lookup(e.id().name());
        List<Value> args = e.args().stream()
                .map(arg -> arg.accept(this, env))
                .collect(Collectors.toList());
        return function.apply(args);
    }

    @Override
    public Value visit(AST.AddExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new NumValue(left.asNum() + right.asNum());
    }

    @Override
    public Value visit(AST.SubExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new NumValue(left.asNum() - right.asNum());
    }

    @Override
    public Value visit(AST.MulExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new NumValue(left.asNum() * right.asNum());
    }

    @Override
    public Value visit(AST.DivExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new NumValue(left.asNum() / right.asNum());
    }

    @Override
    public Value visit(AST.PowExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new NumValue(Math.pow(left.asNum(), right.asNum()));
    }

    @Override
    public Value visit(AST.EqExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new BoolValue(left.equals(right));
    }

    @Override
    public Value visit(AST.NeqExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new BoolValue(!left.equals(right));
    }

    @Override
    public Value visit(AST.LtExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new BoolValue(left.asNum() < right.asNum());
    }

    @Override
    public Value visit(AST.LteExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new BoolValue(left.asNum() <= right.asNum());
    }

    @Override
    public Value visit(AST.GtExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new BoolValue(left.asNum() > right.asNum());
    }

    @Override
    public Value visit(AST.GteExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new BoolValue(left.asNum() >= right.asNum());
    }

    @Override
    public Value visit(AST.AndExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new BoolValue(left.asBool() && right.asBool());
    }

    @Override
    public Value visit(AST.OrExp e, Env env) {
        Value left = e.left().accept(this, env);
        Value right = e.right().accept(this, env);
        return new BoolValue(left.asBool() || right.asBool());
    }

    @Override
    public Value visit(AST.NotExp e, Env env) {
        Value exp = e.exp().accept(this, env);
        return new BoolValue(!exp.asBool());
    }

    @Override
    public Value visit(AST.WhileExp e, Env env) {
        Value condValue = e.cond().accept(this, env);
        condValue = typeCheck(condValue, Value.NumValue.class);
        double n = ((Value.NumValue) condValue).v();

        while (n != 0) {
            result = e.body().accept(this, env);
        }
        return result;
    }

    @Override
    public Value visit(AST.IfExp e, Env env) {
        Value condValue = e.cond().accept(this, env);
        condValue = typeCheck(condValue, Value.NumValue.class);
        double n = ((Value.NumValue) condValue).v();

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
        return new StringValue(left.asString() + right.asString());
    }

    @Override
    public Value visit(AST.SlengthExp e, Env env) {
        Value exp = e.exp().accept(this, env);
        return new NumValue(exp.asString().length());
    }

    @Override
    public Value visit(AST.SmidExp e, Env env) {
        Value exp = e.exp().accept(this, env);
        Value start = e.start().accept(this, env);
        Value end = e.end().accept(this, env);
        return new StringValue(exp.asString().substring(start.asNum().intValue(), end.asNum().intValue()));
    }

    @Override
    public Value visit(AST.StringExp e, Env env) {
        return e.exp().accept(this, env);
    }
}
package annoylang;
import java.util.List;
import annoylang.AST.Exp;

public interface Value {
    public String toString();

    static class FunValue implements Value {
        private Env _env;
        private List<String> _formals;
        private AST.Exp _body;

        public FunValue(Env env, List<String> formals, AST.Exp body) {
            _env = env;
            _formals = formals;
            _body = body;
        }

        public Env env() {
            return _env;
        }

        public List<String> formals() {
            return _formals;
        }

        public AST.Exp body() {
            return _body;
        }

        public String toString() {
            return "<function>";
        }
    }

    public static class NumValue implements Value {
        private double _val;

        public NumValue(double v) {
            _val = v;
        }

        public NumValue(boolean v) {
            if(v)
                _val = 1;
            else
                _val = 0;
        }

        public double v() {
            return _val;
        }

        public String toString() {
            String tmp = "" + _val;
            return tmp;
        }
    }

    public static class StringValue implements Value{
        private String _val;

        public StringValue(String v) {
            _val = v;
        }

        public String v() {
            return _val;
        }

        public String toString() {
            return _val;
        }
    }

    public static class NullValue implements Value {
        public String toString() {
            return "<null>";
        }
    }

    public static class Error implements Value {
        private String _msg;

        public Error(String msg) {
            _msg = msg;
        }

        public String msg() {
            return _msg;
        }

        public String toString() {
            return _msg;
        }
    }
}

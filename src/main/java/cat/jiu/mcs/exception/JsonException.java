package cat.jiu.mcs.exception;

public class JsonException extends RuntimeException{
	private static final long serialVersionUID = -6508867025396442598L;
	public JsonException() {super();}
    public JsonException(String s) { super(s); }
    public JsonException(String s, Throwable t) { super(s, t); }
    public JsonException(Throwable t) { super(t); }
}

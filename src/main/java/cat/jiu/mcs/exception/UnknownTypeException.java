package cat.jiu.mcs.exception;

public class UnknownTypeException extends RuntimeException{
	private static final long serialVersionUID = 2908141147333638581L;
	public UnknownTypeException() {super();}
    public UnknownTypeException(String s) { super(s); }
    public UnknownTypeException(String s, Throwable t) { super(s, t); }
    public UnknownTypeException(Throwable t) { super(t); }
}

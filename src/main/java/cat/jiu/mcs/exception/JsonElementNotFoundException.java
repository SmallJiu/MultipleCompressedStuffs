package cat.jiu.mcs.exception;

public class JsonElementNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -3564622984425050048L;
	public JsonElementNotFoundException() {super();}
    public JsonElementNotFoundException(String s) { super(s); }
    public JsonElementNotFoundException(String s, Throwable t) { super(s, t); }
    public JsonElementNotFoundException(Throwable t) { super(t); }
}

package cat.jiu.mcs.exception;

public class NonItemException extends RuntimeException{
	private static final long serialVersionUID = 4199155302180844610L;
	public NonItemException() {super();}
    public NonItemException(String s) { super(s); }
    public NonItemException(String s, Throwable t) { super(s, t); }
    public NonItemException(Throwable t) { super(t); }
}

package cat.jiu.mcs.exception;

public class ItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4199155302180844610L;

	public ItemNotFoundException() {
		super();
	}

	public ItemNotFoundException(String s) {
		super(s);
	}

	public ItemNotFoundException(String s, Throwable t) {
		super(s, t);
	}

	public ItemNotFoundException(Throwable t) {
		super(t);
	}
}

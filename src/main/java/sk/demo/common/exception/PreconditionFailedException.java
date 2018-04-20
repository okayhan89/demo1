package sk.demo.common.exception;

public class PreconditionFailedException extends RuntimeException{

	/**
	 * 사용자 정의 예외
	 */
	private static final long serialVersionUID = 4374948368762865202L;
	
	public PreconditionFailedException() {
        super();
    }
	
    public PreconditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PreconditionFailedException(String message) {
        super(message);
    }
    
    public PreconditionFailedException(Throwable cause) {
        super(cause);
    }
    
}

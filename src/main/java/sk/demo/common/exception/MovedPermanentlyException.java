package sk.demo.common.exception;

public class MovedPermanentlyException extends RuntimeException{

	/**
	 * 사용자 정의 예외
	 */
	private static final long serialVersionUID = 4374948368762865202L;
	
	public MovedPermanentlyException() {
        super();
    }
	
    public MovedPermanentlyException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MovedPermanentlyException(String message) {
        super(message);
    }
    
    public MovedPermanentlyException(Throwable cause) {
        super(cause);
    }
    
}

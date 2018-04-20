package sk.demo.common.exception;

public class CustomException2 extends RuntimeException{

	/**
	 * 사용자 정의 예외
	 */
	private static final long serialVersionUID = 4374948368762865202L;
	
	public CustomException2() {
        super();
    }
	
    public CustomException2(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CustomException2(String message) {
        super(message);
    }
    
    public CustomException2(Throwable cause) {
        super(cause);
    }
    
}

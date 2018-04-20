package sk.demo.common.exception;

public class CustomException1 extends RuntimeException{

	/**
	 * 사용자 정의 예외
	 */
	private static final long serialVersionUID = 4374948368762865202L;
	
	public CustomException1() {
        super();
    }
	
    public CustomException1(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CustomException1(String message) {
        super(message);
    }
    
    public CustomException1(Throwable cause) {
        super(cause);
    }
    
}

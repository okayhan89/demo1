package sk.demo.storeLogic.redis.repository.exception;


public class RedisException extends Exception {
	//========================================================
	//< private variables
	//========================================================
	private ERedisErrorType eErrorType;
	private String errorMessage;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Basic constructor
	 */
	public RedisException() {
		super();
	}
	
	/**
	 * Constructor
	 */
	public RedisException(Exception e, ERedisErrorType eErrorType) {
		super(e);
		this.setErrorMessage(e.getMessage());
		this.seteErrorType(eErrorType);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//< getter and setter

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ERedisErrorType geteErrorType() {
		return eErrorType;
	}

	public void seteErrorType(ERedisErrorType eErrorType) {
		this.eErrorType = eErrorType;
	}
}
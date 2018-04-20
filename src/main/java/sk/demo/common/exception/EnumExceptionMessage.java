package sk.demo.common.exception;

public enum EnumExceptionMessage {

	SUCCESS(200,"0000","요청 성공"),
	BAD_REQUEST(400, "1001", "잘못된 요청 입니다."), 
	NOT_FOUNT(404, "1002", "요청한 서비스를 찾을수 없습니다."), 
	CUST_ERROR1(400, "1003", "사용자 지정 에러입니다."), 
	CUST_ERROR2(400, "1004", "사용자 지정 에러입니다."), 
	UNAUTHORIZED_ERROR(401, "1005", "사용자 인증에 실패하였습니다."), 
	INTER_SERVER_ERROR(500, "2001", "서버에서 처리중 오류가 발생했습니다.");

	private final int status;

	private final String code;

	private final String reasonPhrase;

	public int getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	EnumExceptionMessage(int status, String code, String reasonPhrase) {
		this.status = status;
		this.code = code;
		this.reasonPhrase = reasonPhrase;
	}

	public static EnumExceptionMessage valueOf(int statusCode) {
		for (EnumExceptionMessage status : values()) {
			if (status.status == statusCode) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}

	public static String codeOf(int statusCode) {
		for (EnumExceptionMessage status : values()) {
			if (status.status == statusCode) {
				return status.code;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}

	public static String reasonPhraseOf(int statusCode) {
		for (EnumExceptionMessage status : values()) {
			if (status.status == statusCode) {
				return status.getReasonPhrase();
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}
}

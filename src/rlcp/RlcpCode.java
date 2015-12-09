package rlcp;

import java.io.Serializable;

/**
 * Enumeration representing return codes of RLCP-server.
 */
public enum RlcpCode implements Serializable {
    
    /**
     * "200", "Request is successfully processed and response contains result"
     */
    Success("200", "Request is successfully processed and response contains result"),
    /**
     * "300", "Requested resource is temporary moved"
     */
    Moved("300", "Requested resource is temporary moved"),
    /**
     * "400","Incorrect Request"
     */
    IncorrectRequest("400","Incorrect Request"),
    /**
     * "401", "Incorrect reference sets"
     */
    IncorrectReference("401", "Incorrect reference sets"),
    /**
     * "402", "Authentication failed"
     */
    AuthFailed("402", "Authentication failed"),
    /**
     * "403", "Unsupported method"
     */
    UnsupportedMethod("403", "Unsupported method"),
    /**
     * "404", "Absence of necessary header field"
     */
    MissingHeader("404", "Absence of necessary header field"),
    /**
     * "405", "Absence of necessary header value"
     */
    MissongHeaderValue("405", "Absence of necessary header value"),
    /**
     * "500", "Resource is temporary unavailable"
     */
    Unavailable("500", "Resource is temporary unavailable"),
    /**
     * "501", "Method is not supported by this server"
     */
    NotImplementedMethod("501", "Method is not supported by this server");
    
    
    
    private String code;
    private String message;

    private RlcpCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Returns numeric code as String.
     * @return numeric code as String
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns symbolic message, describig code type.
     * @return symbolic message, describig code type
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Returns RlcpCode instance with specified code or null if such instance is not found
     * @param code code
     * @return RlcpCode instance with specified code or null if such instance is not found
     */
    public static RlcpCode getByCode(String code){
        for (RlcpCode rlcpCode : values()) {
            if(rlcpCode.getCode().equals(code)){
                return rlcpCode;
            }
        }
        return null;
    }
}

package rlcp;

import java.io.Serializable;
import rlcp.method.RlcpMethod;
import rlcp.util.Util;

/**
 * Interface fo RLCP response entity.Contains RlcpResponseHeader,
 * RlcpResponseBody implemenattion and getters for them. Also provides method to
 * get instance of RlcpMethod implementation of this response.
 * @author Eugene Efimchick
 */
public abstract class RlcpResponse implements Serializable {
    
    /**
     * Returns response header.
     *
     * @return response header
     */
    public abstract RlcpResponseHeader getHeader();
     /**
     * Returns response body.
     *
     * @return response body
     */
    public abstract RlcpResponseBody getBody();
    /**
     * Returns RlcpMethod implementation of this response. Should NEVER return null.
     *
     * @return RlcpMethod implementation of this response. Should NEVER return null
     */
    public abstract RlcpMethod getMethod();
    
     /**
     * Returns String representation that are to receive from RLCP-server. Also this
     * String should be parsed by RlcpParser implementations.
     *
     * @return String representation of RlcpResponse.
     */
    @Override
    public String toString(){
        if (getHeader().isSuccessful()) {
            return new StringBuilder()
                    .append(getHeader().toString())
                    .append(Util.nativeLineSeparator)
                    .append(getBody().toString())
                    .append(Util.nativeLineSeparator)
                    .toString();
        } else {
            return getHeader().toString();
        }
    }
}

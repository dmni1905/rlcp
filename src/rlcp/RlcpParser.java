package rlcp;

import rlcp.exception.BadRlcpBodyException;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.BadRlcpResponseException;

/**
 * Interface for parser of raw representation of RLCP requests, responses and
 * their bodies, specified by RlcpRequest and RlcpResponse interfaces
 * implementations.
 *
 * @author Eugene Efimchick
 */
public interface RlcpParser<Request extends RlcpRequest, Response extends RlcpResponse> {

    /**
     * Returns RlcpRequest implementation instance for specified method parsed
     * from param.
     *
     * @param rlcpRequestString raw RlcpRequest representation
     * @return RlcpRequest implementation instance for specified method parsed
     * from param String
     * @throws BadRlcpRequestException
     */
    Request parseRequest(String rlcpRequestString) throws BadRlcpRequestException;

    /**
     * Returns RlcpResponse implementation instance for specified method parsed
     * from param.
     *
     * @param rlcpResponseString raw RlcpResponse representation
     * @return RlcpResponse implementation instance for specified method parsed
     * from param String
     * @throws BadRlcpResponseException
     */
    Response parseResponse(String rlcpResponseString) throws BadRlcpResponseException;

    /**
     * Returns RlcpRequestBody implementation instance for specified method
     * parsed from param.
     *
     * @param rlcpBodyString raw RlcpRequestBody representation
     * @return RlcpRequestBody implementation instance for specified method
     * parsed from param
     * @throws BadRlcpBodyException
     */
    RlcpRequestBody parseRequestBody(String rlcpBodyString) throws BadRlcpBodyException;

    /**
     * Returns RlcpResponseBody implementation instance for specified method
     * parsed from param.
     *
     * @param rlcpBodyString raw RlcpResponseBody representation
     * @return RlcpResponseBody implementation instance for specified method
     * parsed from param
     * @throws BadRlcpBodyException
     */
    RlcpResponseBody parseResponseBody(String rlcpBodyString) throws BadRlcpBodyException;
}

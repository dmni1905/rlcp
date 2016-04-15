package rlcp.server;

import rlcp.RlcpRequest;
import rlcp.RlcpResponse;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.UnsupportedRlcpMethodException;
import rlcp.method.*;
import rlcp.server.config.Config;
import rlcp.server.flow.*;
import rlcp.server.processor.factory.ProcessorFactoryContainer;

/**
 * Enumeration of RLCP-server methods. It is used to execute requests.
 *
 * @author Eugene Efimchick
 */
public enum ServerMethod {

    /**
     * RLCP-server implementation for Check method.
     */
    CHECK(Check.getInstance()) {
        @Override
        public RlcpRequestFlow getFlow() {
            return new RlcpCheckFlow();
        }
    },
    /**
     * RLCP-server implementation for Calculate method.
     */
    CALCULATE(Calculate.getInstance()) {
        @Override
        public RlcpRequestFlow getFlow() {
            return new RlcpCalculateFlow();
        }
    },
    /**
     * RLCP-server implementation for Generate method.
     */
    ECHO(Echo.getInstance()) {
        @Override
        public RlcpRequestFlow getFlow() {
            return new RlcpEchoFlow();
        }
    },

    GENERATE(Generate.getInstance()) {
        @Override
        public RlcpRequestFlow getFlow() {
            return new RlcpGenerateFlow();
        }
    },
    /**
     * RLCP-server implementation for invalid method. Used for exceptions and
     * unrecognized requests.
     */
    INVALID(null) {
        @Override
        public RlcpRequestFlow getFlow() {
            throw new UnsupportedRlcpMethodException("INVALID.getFlow will never be supported");
        }
    };
    private RlcpMethod method;

    private ServerMethod(RlcpMethod method) {
        this.method = method;
    }

    /**
     * Processes rlcpRequest using logicContainer. As usual, checks request and
     * ServerMethod compatibility and uses special Controllers for further
     * processing.
     *
     * @param rlcpRequest               rlcpRequest.
     * @param processorFactoryContainer logiccontainer.
     * @return rlcpResponse instance
     * @throws Exception
     */
    public RlcpResponse execute(RlcpRequest rlcpRequest, ProcessorFactoryContainer processorFactoryContainer, Config config) throws Exception {
        checkRequestMethodIsSameAsExpected(this, rlcpRequest);
        RlcpResponse rlcpResponse = getFlow().processRequest(rlcpRequest, processorFactoryContainer, config);
        return rlcpResponse;
    }

    /**
     * Return flow of requests
     *
     * @return flow of requests
     */
    public abstract RlcpRequestFlow getFlow();

    /**
     * Returns ServerMethod instance with specified name (ignoring case) or {@code ServerMethod.INVALID}
     * if any error occured or method with such name was not found.
     *
     * @param name ServerMethod name
     * @return ServerMethod instance with specified name (ignoring case) or {@code ServerMethod.INVALID}
     * if any error occured or method with such name was not found
     */
    public static ServerMethod lookForMethod(String name) {
        try {
            return ServerMethod.valueOf(name.toUpperCase().trim());
        } catch (NullPointerException exc) {
            return ServerMethod.INVALID;
        } catch (IllegalArgumentException exc) {
            return ServerMethod.INVALID;
        }
    }

    private static void checkRequestMethodIsSameAsExpected(ServerMethod method, RlcpRequest rlcpRequest) throws IllegalArgumentException {
        if (!rlcpRequest.getMethod().getName().equalsIgnoreCase(method.name())) {
            throw new IllegalArgumentException("Wrong Type of request in execution. Expected " + method.name() + ", but was " + rlcpRequest.getMethod().getName());
        }
    }
}

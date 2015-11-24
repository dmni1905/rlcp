package rlcp.server;

import rlcp.RlcpRequest;
import rlcp.RlcpResponse;
import rlcp.exception.BadRlcpRequestException;
import rlcp.exception.UnsupportedRlcpMethodException;
import rlcp.method.Calculate;
import rlcp.method.Check;
import rlcp.method.Generate;
import rlcp.method.RlcpMethod;
import rlcp.server.processor.factory.ProcessorFactoryContainer;
import rlcp.server.flow.RlcpCalculateFlow;
import rlcp.server.flow.RlcpCheckFlow;
import rlcp.server.flow.RlcpGenerateFlow;
import rlcp.server.flow.RlcpRequestFlow;

/**
 * Enumeration of RLCP-server methods. It is used to execute requests.
 */
public enum ServerMethod {

    /**
     * RLCP-server implementation for Check method.
     */
    CHECK(Check.getInstance()) {

        @Override
        public RlcpRequestFlow getProcessor() {
            return new RlcpCheckFlow();
        }
    },
    /**
     * RLCP-server implementation for Calculate method.
     */
    CALCULATE(Calculate.getInstance()) {

        @Override
        public RlcpRequestFlow getProcessor() {
            return new RlcpCalculateFlow();
        }
    },
    /**
     * RLCP-server implementation for Generate method.
     */
    GENERATE(Generate.getInstance()) {

        @Override
        public RlcpRequestFlow getProcessor() {
            return new RlcpGenerateFlow();
        }
    },
    /**
     * RLCP-server implementation for invalid method. Used for exceptions and
     * unrecognized requests.
     */
    INVALID(null) {

        @Override
        public RlcpRequest parseRequest(String rlcpRequestSource) throws BadRlcpRequestException {
            throw new UnsupportedRlcpMethodException("INVALID.parseRequest will never be supported");
        }

        @Override
        public RlcpRequestFlow getProcessor() {
            throw new UnsupportedRlcpMethodException("INVALID.getProcessor will never be supported");
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
     * @param rlcpRequest rlcpRequest.
     * @param processorFactoryContainer logiccontainer.
     * @return rlcpResponse instance
     * @throws Exception
     */
    public RlcpResponse execute(RlcpRequest rlcpRequest, ProcessorFactoryContainer processorFactoryContainer) throws Exception {
        checkRequestMethodIsSameAsExpected(this, rlcpRequest);
        RlcpResponse rlcpResponse = getProcessor().processRequestWithLogic(rlcpRequest, processorFactoryContainer);
        return rlcpResponse;
    }

    public abstract RlcpRequestFlow getProcessor();

    /**
     * Parses rlcpRequest instance from its String representation using {@code flow.Parser}
     * implementations.
     *
     * @param rlcpRequestSource RlcpRequest String representation
     * @return parsed RlcpResponse instance
     * @throws BadRlcpRequestException
     */
    public RlcpRequest parseRequest(String rlcpRequestSource) throws BadRlcpRequestException {
        return method.getParser().parseRequest(rlcpRequestSource);
    }

    /**
     * Returns ServerMethod instance with specified name (ignoring case) or {@code ServerMethod.INVALID}
     * if any error occured or method with such name was not found.
     *
     * @param name ServerMathod name
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

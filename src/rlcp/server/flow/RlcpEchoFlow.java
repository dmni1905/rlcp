package rlcp.server.flow;

import rlcp.RlcpRequestBody;
import rlcp.RlcpResponseBody;
import rlcp.echo.RlcpEchoResponseBody;
import rlcp.server.processor.factory.ProcessorFactoryContainer;

public class RlcpEchoFlow extends RlcpRequestFlow {
    @Override
    public RlcpResponseBody processBody(ProcessorFactoryContainer processorFactoryContainer, RlcpRequestBody body) {
        return new RlcpEchoResponseBody();
    }
}

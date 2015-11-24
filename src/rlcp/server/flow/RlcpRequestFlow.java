package rlcp.server.flow;

import rlcp.RlcpRequest;
import rlcp.RlcpRequestBody;
import rlcp.RlcpResponse;
import rlcp.RlcpResponseBody;
import rlcp.server.processor.factory.ProcessorFactoryContainer;

/**
 *
 * @author efimchick
 */
public abstract class RlcpRequestFlow {

    /**
     * Processes RLCP request using specified RequestProcessLogic from LogicContainer.
     * 
     * @param rlcpRequest Rlcp Request
     * @param processorFactoryContainer container of RequestProcessLogic instances
     * @return Rlcp Response
     */
    public RlcpResponse processRequestWithLogic(RlcpRequest rlcpRequest, ProcessorFactoryContainer processorFactoryContainer) {
        RlcpRequestBody requestBody = rlcpRequest.getBody();
        RlcpResponseBody responseBody = processBody(processorFactoryContainer, requestBody);
        return responseBody.getMethod().buildResponse(responseBody);
    }
//
//    /**
//     * Returns necessary RequestProcessLogic instance from LogicContainer
//     * @param logicContainer container of RequestProcessLogic instances
//     * @return necessary RequestProcessLogic instance from LogicContainer
//     */
//    public abstract RequestProcessAlgorithm getAlgorithmFactory(LogicContainer logicContainer);
    
    /**
     * Processes RlcpRequestBody with specified RequestProcessLogic. Returns body of RlcpResponse.
     * @param processorFactoryContainer
     * @param body Rlcp request body instance
     * @return body of RlcpResponse
     */
    public abstract RlcpResponseBody processBody(ProcessorFactoryContainer processorFactoryContainer, RlcpRequestBody body);
    
}

package rlcp.server.flow;

import rlcp.RlcpRequestBody;
import rlcp.generate.GeneratingResult;
import rlcp.generate.RlcpGenerateRequestBody;
import rlcp.generate.RlcpGenerateResponseBody;
import rlcp.server.logger.Logger;
import rlcp.server.processor.GenerateProcessor;
import rlcp.server.processor.factory.ProcessorFactoryContainer;

/**
 * Class for processing RLCP Generate method requests.
 */
public class RlcpGenerateFlow extends RlcpRequestFlow {

    /**
     * The container need to put an instance {@code GenerateProcessor}.
     * As a result, request body for generating will be processed and will be returned response body generating.
     *
     * @param container flow modules container
     * @param body      Rlcp request body instance
     * @return body of RLCP response for Calculate method
     * @see GenerateProcessor
     */
    @Override
    public RlcpGenerateResponseBody processBody(ProcessorFactoryContainer container, RlcpRequestBody body) {
        GenerateProcessor generateProcessor = container.getProcessor(GenerateProcessor.class);
        if (generateProcessor == null) {
            String msg = "No GenerateProcessor found";
            Logger.log(msg);
            return new RlcpGenerateResponseBody(new GeneratingResult(msg, msg, msg));
        }

        RlcpGenerateRequestBody requestBody = (RlcpGenerateRequestBody) body;
        GeneratingResult result = generateProcessor.generate(requestBody.getCondition());
        RlcpGenerateResponseBody responseBody = new RlcpGenerateResponseBody(result);
        return responseBody;
    }


}

package rlcp.server.flow;

import rlcp.RlcpRequestBody;
import rlcp.RlcpResponseBody;
import rlcp.calculate.CalculatingResult;
import rlcp.calculate.RlcpCalculateRequestBody;
import rlcp.calculate.RlcpCalculateResponseBody;
import rlcp.server.logger.Logger;
import rlcp.server.processor.CalculateProcessor;
import rlcp.server.processor.factory.ProcessorFactoryContainer;

/**
 * Class for processing RLCP Calculate method requests.
 *
 * @author Eugene Efimchick
 */
public class RlcpCalculateFlow extends RlcpRequestFlow {

    @Override
    public RlcpResponseBody processBody(ProcessorFactoryContainer processorFactoryContainer, RlcpRequestBody body) {
        RlcpCalculateRequestBody calculateRequestBody = (RlcpCalculateRequestBody) body;

        CalculateProcessor calculateProcessor = processorFactoryContainer.getProcessor(CalculateProcessor.class);
        if (calculateProcessor == null) {
            String msg = "No CalculateProcessor found";
            Logger.log(msg);
            return new RlcpCalculateResponseBody(new CalculatingResult(msg, msg));
        }
        CalculatingResult calculatingResult = calculateProcessor.calculate(
                calculateRequestBody.getCondition(),
                calculateRequestBody.getInstructions(),
                calculateRequestBody.getPreGenerated());
        RlcpCalculateResponseBody responseBody = new RlcpCalculateResponseBody(calculatingResult);
        return responseBody;
    }

}

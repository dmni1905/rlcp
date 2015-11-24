package rlcp.check;

import java.util.Collections;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import rlcp.RlcpResponseBody;
import rlcp.method.Check;
import rlcp.method.RlcpMethod;
import rlcp.util.Constants;

import static rlcp.util.Constants.*;

/**
 * RlcpResponseBody implementation for Check method
 * @author Eugene Efimchick
 */
public class RlcpCheckResponseBody extends RlcpResponseBody {

    private List<CheckingResult> results;

    /**
     * Simple constructor.
     * @param checkResults list of checking results 
     */
    public RlcpCheckResponseBody(List<CheckingResult> checkResults) {
        this.results = Collections.unmodifiableList(checkResults);
    }

    /**
     * Returns list of checking results. Warning: it is unmodifiable.
     * @return list of checking results
     */
    public List<CheckingResult> getResults() {
        return results;
    }

    /**
     * Returns checking result with specified id.
     * @param id result identifier
     * @return checking result with specified id
     */
    public CheckingResult getResultById(int id) {
        CheckingResult result = null;
        for (CheckingResult checkingResult : results) {
            if (checkingResult.getId() == id) {
                result = checkingResult;
            }
        }
        return result;
    }

    @Override
    public Document getDocument() {
        Document document = DocumentHelper.createDocument();
        Element responseElement = document.addElement(RESPONSE);
        for (CheckingResult checkingResult : results) {
            Element checkingResultElement = responseElement.addElement(CHECKING_RESULT);
            checkingResultElement.addAttribute(ID, Integer.toString(checkingResult.getId()));
            checkingResultElement.addAttribute(TIME, Long.toString(checkingResult.getTime()));
            checkingResultElement.addAttribute(RESULT, checkingResult.getResult());
            checkingResultElement.addComment(checkingResult.getOutput());
        }
        return document;
    }

    @Override
    public RlcpMethod getMethod() {
        return Check.getInstance();
    }
    
    
}

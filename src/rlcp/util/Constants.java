package rlcp.util;

/**
 * Class for xml mapping and xPath expressions constants.
 *
 * @author Eugene Efimchick
 */
public class Constants {

    public static final String REQUEST = "Request";
    public static final String CONDITIONS = "Conditions";
    public static final String CONDITION_FOR_CALCULATING = "ConditionForCalculating";
    public static final String CONDITION_FOR_CHECKING = "ConditionForChecking";
    public static final String CONDITION_FOR_GENERATING = "ConditionForGenerating";
    public static final String ID = "id";
    public static final String TIME = "Time";
    public static final String INPUT = "Input";
    public static final String OUTPUT = "Output";
    public static final String INSTRUCTIONS = "Instructions";
    public static final String PRE_GENERATED = "PreGenerated";
    public static final String PRE_GENERATED_TEXT = "Text";
    public static final String PRE_GENERATED_CODE = "Code";
    public static final String PRE_GENERATED_INSTRUCTIONS = "Instructions";
    public static final String RESPONSE = "Response";
    public static final String CALCULATING_RESULT = "CalculatingResult";
    public static final String CHECKING_RESULT = "CheckingResult";
    public static final String GENERATING_RESULT = "GeneratingResult";
    public static final String RESULT = "Result";
    public static final String xPath_selectCheckUnitsNodes = "//" + CONDITION_FOR_CHECKING;
    public static final String xPath_selectCheckUnit_id = "@" + ID;
    public static final String xPath_selectCheckUnit_timeLimit = "@" + TIME;
    public static final String xPath_selectCheckUnit_inputData = INPUT + "/comment()";
    public static final String xPath_selectCheckUnit_expectedOutputData = OUTPUT + "/comment()";
    public static final String xPath_selectPreGenerated = "//" + PRE_GENERATED;
    public static final String xPath_selectPreGeneratedInstructions = xPath_selectPreGenerated + "/" + PRE_GENERATED_INSTRUCTIONS + "/comment()";
    public static final String xPath_selectPreGeneratedText = xPath_selectPreGenerated + "/" + PRE_GENERATED_TEXT + "/comment()";
    public static final String xPath_selectPreGeneratedCode = xPath_selectPreGenerated + "/" + PRE_GENERATED_CODE + "/comment()";
    public static final String xPath_selectInstructions = REQUEST + "/" + INSTRUCTIONS + "/comment()";
    public static final String xPath_selectConditionForGenerating = "//" + CONDITIONS + "/" + CONDITION_FOR_GENERATING + "/" + INPUT + "/comment()";
    public static final String xPath_selectConditionForCalculating = "//" + CONDITIONS + "/" + CONDITION_FOR_CALCULATING + "/" + INPUT + "/comment()";


    public static final long rlcpDefaultCheckUnitTimeLimitInSec = 10;
    public static final long rlcpDefaultRequestFlowTimeLimitInSec = 20;
}

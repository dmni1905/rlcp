package rlcp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Special header fields container, where all unnecessary feilds stored.
 *
 * @author Eugene Efimchick
 */
public class RlcpHeaderFieldsContainer implements Serializable {
    private Map<String, String> headerFields = new HashMap<String, String>();
    
    /**
     * Sets header field with specified name and value. Replaces old value of fiels with such name if any.
     * @param name header fiels name
     * @param value header fiels value
     */
    public void setHeaderField(String name, String value){
        headerFields.put(name, value);
    }
    
    /**
     * Returns value of header field with specified name or {@code null} if there is no fiels with such name. 
     * @param name header fiels name
     * @return value of header field with specified name or {@code null} if there is no fiels with such name.
     */
    public String getHeaderFieldByName(String name){
        return headerFields.get(name);
    }
    
    /**
     * Retrieves Set of header fields names.
     * @return Set of header fields names.
     */
    public Set<String> getHeaderFieldNames(){
        return headerFields.keySet();
    }
    
}

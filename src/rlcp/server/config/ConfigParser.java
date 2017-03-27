package rlcp.server.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import rlcp.server.logger.Logger;
import rlcp.util.Constants;

import static rlcp.util.Constants.rlcpDefaultCheckUnitTimeLimitInSec;
import static rlcp.util.Constants.rlcpDefaultRequestFlowTimeLimitInSec;

/**
 * Configuration parser class.
 */
public class ConfigParser {

    private static Config defaultConfig;

    static {
        int defaultPort = 3000;
        User defaultUser = new User("user", "user");
        List<User> defaultUserContainer = new ArrayList<User>();
        defaultUserContainer.add(defaultUser);
        defaultConfig = new Config(defaultPort, defaultUserContainer, rlcpDefaultRequestFlowTimeLimitInSec, rlcpDefaultCheckUnitTimeLimitInSec);
    }

    private ConfigParser() {
    }//to prevent instantiation

    /**
     * Returns configuration from file found by path parameter. Returns default
     * config if failed.
     *
     * @param configPath configuration file path
     * @return configuration from file found by path parameter or default config
     * if failed.
     * @see rlcp.server.config.ConfigParser#getDefault()
     */
    public static Config readConfigFromFileOrGetDefaults(String configPath) {
        try {
            return readConfigFromFile(configPath);
        } catch (Exception exc) {
            Logger.log("Config was not written. Default config is loaded.\r\n" + exc.getMessage());
            return getDefault();
        }
    }

    /**
     * Returns configuration from file found by path parameter.
     *
     * @param configPath configuration file path
     * @return configuration from file found by path parameter
     * @throws FileNotFoundException configuration file was not found
     * @throws DocumentException configuration file has wrong format
     */
    public static Config readConfigFromFile(String configPath) throws FileNotFoundException, DocumentException {
        Document configXml = readXmlFromFile(configPath);
        Config config = parseConfigFromXML(configXml);
        return config;
    }

    private static Document readXmlFromFile(String path) throws DocumentException, FileNotFoundException {
        SAXReader saxReader = new SAXReader();
        return saxReader.read(new FileInputStream(path));
    }

    private static Config parseConfigFromXML(Document configXml) {
        int port = parsePortFromXML(configXml);
        List<User> users = parseUsersFromXML(configXml);
        long requestFlowTimeLimit = parseRequestFlowTimeLimit(configXml);
        long checkUnitTimeLimit = parseCheckUnitTimeLimit(configXml);

        return new Config(port, users, requestFlowTimeLimit, checkUnitTimeLimit);
    }

    private static int parsePortFromXML(Document xmlDoc) {
        return Integer.parseInt(xmlDoc.selectSingleNode("//Port").selectSingleNode("./@value").getText());
    }

    private static long parseRequestFlowTimeLimit(Document xmlDoc) {
        try{
            return Long.parseLong(xmlDoc.selectSingleNode("//RequestFlowTimeLimit").selectSingleNode("./@value").getText());
        } catch (Exception e){
            return rlcpDefaultRequestFlowTimeLimitInSec;
        }
    }

    private static long parseCheckUnitTimeLimit(Document xmlDoc) {
        try {
            return Long.parseLong(xmlDoc.selectSingleNode("//CheckUnitTimeLimit").selectSingleNode("./@value").getText());
        } catch (Exception e){
            return rlcpDefaultCheckUnitTimeLimitInSec;
        }

    }

    private static List<User> parseUsersFromXML(Document xmlDoc) {
        @SuppressWarnings("unchecked")
        List<Node> userNodes = xmlDoc.selectNodes("//UserInfo");
        List<User> users = parseUsersFromNodes(userNodes);
        return users;
    }

    private static List<User> parseUsersFromNodes(List<Node> userNodes) {
        List<User> users = new ArrayList<User>();
        for (Node userNode : userNodes) {
            User user = parseUserFromNode(userNode);
            users.add(user);
        }
        return users;
    }

    private static User parseUserFromNode(Node userNode) {
        String login = userNode.selectSingleNode("./@login").getText();
        String password = userNode.selectSingleNode("./@password").getText();
        return new User(login, password);
    }

    /**
     * Returns default config (default port: 300; default user's list: user with login-user, password-user)
     *
     * @return default config
     */
    public static Config getDefault() {
        return defaultConfig;
    }
}

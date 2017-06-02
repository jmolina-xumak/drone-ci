package com.xumak.util;

import layerx.Constants;
import layerx.api.ContentModel;
import org.apache.commons.lang3.StringUtils;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionWrapper;

import javax.jcr.RepositoryException;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION
 * -------------------------------------------------------------------------------------------------------
 * This Utils class contains generics methods that are used in XumaK.com project.
 *
 * -------------------------------------------------------------------------------------------------------
 * CHANGE HISTORY
 * -------------------------------------------------------------------------------------------------------
 * Version     |Date          |Developer               |Changes
 * 1.0         |05/18/2017    |Marco Cali              |InitialCreation
 * -------------------------------------------------------------------------------------------------------
 */

public final class Utils {

    /**
     * This constructor is used to avoid create an object from this class and to compliant with checkstyle.
     */
    private Utils() {
    }

    /**
     * This method is used to retrieve resource in the content model according to resourceKey parameter; the content
     * retrieved is returned as a map object.
     * @param contentModel    The object that contains the whole content in the content model.
     * @param resourceKey     The resource key that will retrieve in the content model.
     * @author mcali
     * @return the resource found as a map object.
     * @throws Exception if there is an issue to get the map object.
     */
    private static Map<String, Object> getResourceAsMap(final ContentModel contentModel, final String resourceKey)
            throws Exception {
        Map<String, Object> contentMap = null;
        if (null != contentModel && StringUtils.isNotBlank(resourceKey) && contentModel.has(resourceKey)) {
            final Object contentObject = contentModel.get(resourceKey);
            if (contentObject instanceof  Map) {
                contentMap = (Map<String, Object>) contentObject;
            } else {
                throw new Exception("The object that you trying to get is not a map");
            }
        }
        return contentMap;
    }

    /**
     * This method is used to retrieve content from the content model.
     * @param contentModel    The object that contains the whole content in the content model.
     * @author mcali
     * @return contentMap the resource found as a map object.
     * @throws Exception if there is an issue.
     */
    public static Map<String, Object> getContent(final ContentModel contentModel) throws Exception {
        return getResourceAsMap(contentModel, Constants.CONTENT);
    }

    /**
     * This method is used to retrieve config object from the content model.
     * @param contentModel    The object that contains the whole content in the content model.
     * @author mcali
     * @return configMap the resource found as a map object.
     * @throws Exception if there is an issue.
     */
    public static Map<String, Object> getConfig(final ContentModel contentModel) throws Exception {
        return getResourceAsMap(contentModel, Constants.CONFIG_PROPERTIES_KEY);
    }

    /**
     * This method is used to look for a key in the xk-config.json and return it as a list of strings.
     * @param configMap    The object that contains the whole configuration set in the xk-config.json file.
     * @param propArray    The resource key that will retrieve in the configMap and that resource key would be set
     *                     as a list of strings in the xk-config.json file.
     * @author mcali
     * @return a list of strings
     */
    public static List<String> getConfigPropertyAsList(final Map configMap, final String propArray) {
        List<String> listProperties = null;
        if (null != configMap && StringUtils.isNotBlank(propArray) && configMap.containsKey(propArray)) {
            final Object obj = configMap.get(propArray);
            if (obj instanceof List) {
                listProperties = (List<String>) configMap.get(propArray);
            }
        }
        return listProperties;
    }

    /**
     * This method is used to look for a key in the xk-config.json and return it as a simple string.
     * @param configMap       The object that contains the whole configuration set in the xk-config.json file.
     * @param propertyName    The resource key that will retrieve in the configMap.
     * @author mcali
     * @return a string object
     */
    public static String getConfigPropertyAsString(final Map configMap, final String propertyName) {
        String property = StringUtils.EMPTY;
        if (null != configMap && StringUtils.isNotBlank(propertyName) && configMap.containsKey(propertyName)) {
            property = configMap.get(propertyName).toString();
        }
        return property;
    }

    /**
     * This method is used to return the path of the resource using JCRNodeWrapper to get the path of the resource
     * according to the different workspace that Jahia has.
     * @param session             The object to get the node from its UUID.
     * @param resourceNodeUUID    The UUID of the resource.
     * @author mcali
     * @return string object.
     * @throws RepositoryException if there is an issue to get the node from its UUID.
     */
    public static String getResourceNodePath(final JCRSessionWrapper session, final String resourceNodeUUID)
            throws RepositoryException {
        String resourcePath = StringUtils.EMPTY;
        if (null != session && StringUtils.isNotBlank(resourceNodeUUID)) {
            final JCRNodeWrapper resourceNode = session.getNodeByIdentifier(resourceNodeUUID);
            if (null != resourceNode) {
                resourcePath = resourceNode.getUrl();
            } else {
                throw new RepositoryException("An error occurred to get the node in the repository");
            }
        }
        return resourcePath;
    }
}

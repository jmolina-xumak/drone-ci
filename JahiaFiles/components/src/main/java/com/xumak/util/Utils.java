package com.xumak.util;

import layerx.jahia.templating.TemplateContentModel;
import org.apache.commons.lang3.StringUtils;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

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
     */
    public static Map<String, Object> getResourceAsMap(
            final TemplateContentModel contentModel, final String resourceKey) {
        Map<String, Object> contentMap = null;
        if (null != contentModel && StringUtils.isNotBlank(resourceKey) && contentModel.has(resourceKey)) {
            final Object contentObject = contentModel.get(resourceKey);
            if (contentObject instanceof  Map) {
                contentMap = (Map<String, Object>) contentObject;
            }
        }
        return contentMap;
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
        try {
            if (null != configMap && StringUtils.isNotBlank(propArray) && configMap.containsKey(propArray)) {
                final Object obj = configMap.get(propArray);
                if (obj instanceof List) {
                    listProperties = (List<String>) configMap.get(propArray);
                }
            }
        } catch (ClassCastException e) {
            LOGGER.error("Error accessing the configuration object: " + e);
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
        String property = "";
        if (null != configMap && StringUtils.isNotBlank(propertyName) && configMap.containsKey(propertyName)) {
            property = configMap.get(propertyName).toString();
        }
        return property;
    }

    /**
     * This method is used to return the path of the resource using JCRNodeWrapper to get the path of the resource
     * according to the different workspace tha Jahia has.
     * @param session             The object to get the node from its UUID.
     * @param resourceNodeUUID    The UUID of the resource.
     * @author mcali
     * @return string object.
     */
    public static String getResourceNodePath(final JCRSessionWrapper session, final String resourceNodeUUID) {
        String resourcePath = StringUtils.EMPTY;
        try {
            if (null != session && StringUtils.isNotBlank(resourceNodeUUID)) {
                final JCRNodeWrapper resourceNode = session.getNodeByIdentifier(resourceNodeUUID);
                if (null != resourceNode) {
                    resourcePath = resourceNode.getUrl();
                }
            }
        } catch (RepositoryException repException) {
            LOGGER.error("An error occurred in the repository " + repException.toString());
        }
        return resourcePath;
    }
}

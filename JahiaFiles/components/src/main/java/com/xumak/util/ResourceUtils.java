package com.xumak.util;

import layerx.Constants;
import layerx.api.ContentModel;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;

/**
 * DESCRIPTION
 * -------------------------------------------------------------------------------------------------------
 * This ResourceUtils class contains generics methods that are used in XumaK.com project.
 *
 * -------------------------------------------------------------------------------------------------------
 * CHANGE HISTORY
 * -------------------------------------------------------------------------------------------------------
 * Version     |Date          |Developer               |Changes
 * 1.0         |06/02/2017    |Marco Cali              |InitialCreation
 * -------------------------------------------------------------------------------------------------------
 */

public final class ResourceUtils {

    /**
     * This constructor is used to avoid create an object from this class and to compliant with checkstyle.
     */
    private ResourceUtils() {
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
                throw new Exception("The object that you are trying to get is not a map");
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
    public static Map<String, Object> getContentResource(final ContentModel contentModel) throws Exception {
        return getResourceAsMap(contentModel, Constants.CONTENT);
    }

    /**
     * This method is used to retrieve config object from the content model.
     * @param contentModel    The object that contains the whole content in the content model.
     * @author mcali
     * @return configMap the resource found as a map object.
     * @throws Exception if there is an issue.
     */
    public static Map<String, Object> getConfigResource(final ContentModel contentModel) throws Exception {
        return getResourceAsMap(contentModel, Constants.CONFIG_PROPERTIES_KEY);
    }
}

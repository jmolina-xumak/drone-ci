package com.xumak.contextprocessors;

import com.xumak.util.Utils;
import com.google.common.collect.Sets;
import layerx.Constants;
import layerx.api.ExecutionContext;
import layerx.api.exceptions.ProcessException;
import layerx.core.contextprocessors.AbstractCheckComponentCategoryContextProcessor;
import layerx.jahia.templating.TemplateContentModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.render.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;

import static com.xumak.Constants.GET_PATH_FROM_RESOURCE;
import static com.xumak.Constants.PATH_FROM_RESOURCE_PROPERTIES_LIST;
import static layerx.jahia.Constants.JAHIA_RESOURCE;

/**
 * DESCRIPTION
 * ---------------------------------------------------------------------------------------------------------------
 * This Context Processor gets path of the resources configured in the component dialog.
 * is activated this process by placing "getPathFromResource" in the componentCategory and it is necessary
 * configure the list of properties name that you are going to use in the component to configure
 * images and pages only.
 * The xk-config.json looks like this:
 {
 "xk_componentCategory": [
 "getPathFromResource"
 ],
 "PathFromResourcePropertiesList": [
 "internalLink1",
 "internalLink2",
 "imagePath1",
 "imagePath2"
 ]
 }
 * you can configure the amount of properties according to your requirements.
 * ---------------------------------------------------------------------------------------------------------------
 * CHANGE HISTORY
 * ---------------------------------------------------------------------------------------------------------------
 * Version  | Date          | Developer         | Changes
 * 1.0      | 05/24/2017    | Marco Calí        | Initial Creation
 * ---------------------------------------------------------------------------------------------------------------
 */

@Component
@Service
public class GetPathFromResourcesContextProcessor extends
        AbstractCheckComponentCategoryContextProcessor<TemplateContentModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPathFromResourcesContextProcessor.class);

    @Override
    public Set<String> anyOf() {
        return Sets.newHashSet(GET_PATH_FROM_RESOURCE);
    }

    @Override
    public int priority() {
        return Constants.LOW_PRIORITY;
    }

    @Override
    public void process(
            final ExecutionContext executionContext, final TemplateContentModel contentModel) throws ProcessException {

        final Map<String, Object> contentMap = Utils.getResourceAsMap(contentModel, Constants.CONTENT);
        final Map<String, Object> configMap = Utils.getResourceAsMap(contentModel, Constants.CONFIG_PROPERTIES_KEY);
        final Resource resource = (Resource) executionContext.get(JAHIA_RESOURCE);
        if (null != resource && null != contentMap && null != configMap) {
            try {
                final JCRNodeWrapper componentNode = resource.getNode();
                final List<String> propertiesList =
                        Utils.getConfigPropertyAsList(configMap, PATH_FROM_RESOURCE_PROPERTIES_LIST);
                if (null != propertiesList) {
                    for (final String prop : propertiesList) {
                        if (null != componentNode && componentNode.hasProperty(prop)) {
                            //Validate type of property in the node.
                            if (componentNode.getProperty(prop).getType() == PropertyType.WEAKREFERENCE) {
                                final String resourceNodeUUID = componentNode.getProperty(prop).getValue().getString();
                                final String resourcePath =
                                        Utils.getResourceNodePath(componentNode.getSession(), resourceNodeUUID);
                                if (StringUtils.isNotBlank(resourcePath)) {
                                    contentMap.put(prop, resourcePath);
                                }
                            }
                        }
                    }
                }
            } catch (RepositoryException e) {
                LOGGER.error("GetPathFromResourcesContextProcessor --> An error occurred: ", e);
            }
        }
    }
}

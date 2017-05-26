package com.xumak.contextprocessors;

import com.xumak.utils.Utils;
import com.google.common.collect.Sets;
import layerx.api.ExecutionContext;
import layerx.api.exceptions.ProcessException;
import layerx.core.contextprocessors.AbstractCheckComponentCategoryContextProcessor;
import layerx.jahia.templating.TemplateContentModel;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.jahia.services.content.JCRNodeIteratorWrapper;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.render.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;

import static layerx.jahia.Constants.JAHIA_RESOURCE;
import layerx.Constants;
import static com.xumak.Constants.GET_ITEMS_PROPERTIES;
import static com.xumak.Constants.COMPONENT_CONTAINER_NAME;
import static com.xumak.Constants.NODE_ITEM_PROPERTIES;

/**
 * DESCRIPTION
 * ------------------------------------------------------------------------------------------------------------------
 * This Context Processor gets data from the children nodes of the current node.
 * It is used specifically in component containers and its items; the context processor
 * gets the whole data specified in the list in the xk-config.json and places it in the
 * scope "content" of the component container.
 * In the xk-config.json you have to have something like this structure:
 {
 "xk_componentCategory": [
 "getItemsProperties"
 ],
 "componentContainerName": "myElementsNode",
 "nodeItemProperties": [
 "title",
 "description",
 "articleDate"
 ]
 }
 The "componentContainerName" you specify the name which you use to include your item
 component in your component container.
 In myContainerScript.lx you can place this.
 {%includeList path="myElementsNode" nodeTypes="xk:carouselItem" %}

 and the "nodeItemProperties" is a list of properties tha you are going to specify as
 properties in your item component.

 * ------------------------------------------------------------------------------------------------------------------
 * CHANGE HISTORY
 * ------------------------------------------------------------------------------------------------------------------
 * Version     |Date         |Developer               |Changes
 * 1.0         |05/18/2017   |Marco Cali              |Initial Creation
 * ------------------------------------------------------------------------------------------------------------------
 */

@Component
@Service
public class ItemsContainerContextProcessor extends
        AbstractCheckComponentCategoryContextProcessor<TemplateContentModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsContainerContextProcessor.class);

    @Override
    public Set<String> anyOf() {
        return Sets.newHashSet(GET_ITEMS_PROPERTIES);
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
        if (null != resource) {
            try {
                //Getting xk configurations for the current component
                final String containerName = Utils.getConfigPropertyAsString(configMap, COMPONENT_CONTAINER_NAME);
                final List<String> nodeItemProperties = Utils.getConfigPropertyAsList(configMap, NODE_ITEM_PROPERTIES);

                final JCRNodeWrapper containerNode = resource.getNode();
                if (StringUtils.isNotBlank(containerName) && null != nodeItemProperties
                        && containerNode.hasNode(containerName)) {
                    final List<Map<String, Object>> listMaps = new ArrayList<>();

                    //Getting node according to specified in includeList tag specified by the developer.
                    final JCRNodeWrapper nodeElements = containerNode.getNode(containerName);

                    //Getting all nodes of the component item edited by the author in the component container.
                    final JCRNodeIteratorWrapper contentNodes = nodeElements.getNodes();
                    while (contentNodes.hasNext()) {
                        final JCRNodeWrapper contentItem = (JCRNodeWrapper) contentNodes.nextNode();
                        final Map<String, Object> elementMap = new HashMap<String, Object>();

                        //Getting all properties on each node specified as a list values in "nodeItemProperties"
                        for (final String prop : nodeItemProperties) {
                            if (contentItem.hasProperty(prop)) {

                                //Validate type of property in the node.
                                if (contentItem.getProperty(prop).getType() == PropertyType.WEAKREFERENCE) {
                                    final String resourceNodeUUID =
                                            contentItem.getProperty(prop).getValue().getString();
                                    final String resourcePath = Utils.getResourceNodePath(
                                            containerNode.getSession(), resourceNodeUUID);
                                    elementMap.put(prop, resourcePath);
                                } else {
                                    elementMap.put(prop, contentItem.getProperty(prop).getValue().getString());
                                }
                            }
                        }
                        listMaps.add(elementMap);
                    }
                    contentMap.put(NODE_ITEM_PROPERTIES, listMaps);
                }
            } catch (RepositoryException e) {
                LOGGER.error("Repository Exception found: ", e);
            }
        }
    }
}

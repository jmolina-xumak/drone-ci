package com.xumak.base.templatingsupport;

import com.xumak.base.configuration.MockLayerXConfiguration;
import com.xumak.base.configuration.MockLayerXConfigurationProvider;
import layerx.api.ExecutionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.render.Resource;
import org.mockito.Mockito;

import javax.jcr.Node;
import java.util.HashMap;
import java.util.Map;

import static layerx.Constants.TEMPLATE_CONTENT_MODEL_ATTR_NAME;
import static layerx.Constants.DESIGN_PROPERTIES_KEY;
import static layerx.Constants.GLOBAL_PROPERTIES_KEY;
import static layerx.Constants.RESOURCE_CONTENT_KEY;
import static layerx.Constants.CONFIG_PROPERTIES_KEY;
import static layerx.jahia.Constants.JAHIA_RESOURCE;
import static org.mockito.Mockito.when;

/**
 * BaseTest
 * Helps to configure all the objects needed as minimum in a test.
 * It includes:
 *      - Request
 *      - Response
 *      - Resource
 *      - Content Model
 *          * Config
 *          * Content
 *          * Design
 *          * Global
 *      - LayerX Configuration Provider
 *      - LayerX Configuration
 * -­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-‐
 * Change History
 * --------------------------------------------------------------------------------------
 * Version | Date       | Developer       | Changes
 * 1.0     | 17/02/2015 | Lesly Quiñonez  | Initial Creation
 * 1.0     | 11/04/216  | Isaías Sosa     | Added Node mock object
 * 1.1     | 10/05/5017 | Rainman Sián    | Adapted to LayerX + Jahia
 * --------------------------------------------------------------------------------------
 */

public class BaseTest {
    public MockTemplateContentModel contentModel;
    public String resourceType = JAHIA_RESOURCE;

    public ExecutionContext executionContext;
    public HttpServletRequest request;
    public HttpServletResponse response;
    public Resource resource;
    public Node node;
    public JCRNodeWrapper jcrNodeWrapper;

    public Map<String, Object> config;
    public Map<String, Object> content;
    public Map<String, Object> design;
    public Map<String, Object> global;
    public Map<String, Object> page;

    public void initializeConfiguration(final MockLayerXConfigurationProvider configurationProvider,
                                        final MockLayerXConfiguration configuration) throws Exception {
        // Init Objects
        initializeConfiguration();
        when(configurationProvider.getFor(JAHIA_RESOURCE)).thenReturn(configuration);
    }

    public void initializeConfiguration() {

        // Init Objects
        executionContext = Mockito.mock(ExecutionContext.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        resource = Mockito.mock(Resource.class);
        node = Mockito.mock(Node.class);
        jcrNodeWrapper = Mockito.mock(JCRNodeWrapper.class);

        contentModel = new MockTemplateContentModel(request, response);
        config = new HashMap<>();
        content = new HashMap<>();
        global = new HashMap<>();
        design = new HashMap<>();
        page = new HashMap<>();

        //resource
        when(executionContext.get(JAHIA_RESOURCE)).thenReturn(resource);
        when(resource.getNode()).thenReturn(jcrNodeWrapper);

        //contentModel
        when(request.getAttribute(TEMPLATE_CONTENT_MODEL_ATTR_NAME)).thenReturn(contentModel);

        //Setting up the content model
        contentModel.set(DESIGN_PROPERTIES_KEY, design);
        contentModel.set(GLOBAL_PROPERTIES_KEY, global);
        contentModel.set(RESOURCE_CONTENT_KEY, content);
        contentModel.set(CONFIG_PROPERTIES_KEY, config);
    }

    public void setResourceType(final String resourceType) {
        this.resourceType = resourceType;
    }
}

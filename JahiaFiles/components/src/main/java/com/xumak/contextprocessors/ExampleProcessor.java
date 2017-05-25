package com.xumak.contextprocessors;

import layerx.core.contextprocessors.AbstractCheckComponentCategoryContextProcessor;
import layerx.jahia.templating.TemplateContentModel;
import com.google.common.collect.Sets;
import layerx.api.ExecutionContext;
import layerx.api.exceptions.ProcessException;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import org.jahia.services.render.Resource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Set;
import com.xumak.Constants;

import static layerx.jahia.Constants.JAHIA_RESOURCE;
import static layerx.Constants.LOW_PRIORITY;
import static layerx.Constants.CONTENT;


/**
 * Basic example context processor definition.
 * Change History
 * --------------------------------------------------------------------------------------
 * Version | Date       | Developer       | Changes
 * 1.1     | 10/05/5017 | Rainman Sián    | Initial creation
 * --------------------------------------------------------------------------------------
 * */

@Component
@Service
public class ExampleProcessor extends AbstractCheckComponentCategoryContextProcessor<TemplateContentModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleProcessor.class);

    @Override
    public Set<String> anyOf() {
        return Sets.newHashSet(Constants.CATEGORY_NAME_EXAMPLE);
    }

    @Override
    public int priority() {
        return LOW_PRIORITY;
    }

    @Override
    public void process(final ExecutionContext executionContext, final TemplateContentModel contentModel)
            throws ProcessException {

        LOGGER.info("Example processor started  >>>>");

        final Resource resource = (Resource) executionContext.get(JAHIA_RESOURCE);
        if (null != resource) {
            final Object contentObject = contentModel.get(CONTENT);
            ((Map) contentObject).put(Constants.EXAMPLE_KEY, Constants.EXAMPLE_TEXT_VALUE);
        }
        LOGGER.info("Example processor finished >>>> \n");
    }
}

package com.xumak.contextprocessors;

import com.xumak.base.configuration.MockLayerXConfiguration;
import com.xumak.base.configuration.MockLayerXConfigurationProvider;
import com.xumak.base.templatingsupport.BaseTest;
import org.jahia.services.content.JCRNodeIteratorWrapper;
import org.jahia.services.content.JCRNodeWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.xumak.Constants.PATH_FROM_RESOURCE_PROPERTIES_LIST;
import static layerx.Constants.CONFIG_PROPERTIES_KEY;
import static layerx.Constants.DOT;
import static org.junit.Assert.*;

/**
 * DESCRIPTION
 * ------------------------------------------------------------------------------------------------------------------
 * Unit test to be sure to get not null list in content model.
 * ------------------------------------------------------------------------------------------------------------------
 * CHANGE HISTORY
 * ------------------------------------------------------------------------------------------------------------------
 * Version     |Date         |Developer               |Changes
 * 1.0         |05/24/2017   |Marco Cali              |Initial Creation
 * ------------------------------------------------------------------------------------------------------------------
 */
@RunWith(MockitoJUnitRunner.class)
public class GetPathFromResourcesContextProcessorTest {

    private JCRNodeWrapper jcrNodeWrapper;
    private JCRNodeIteratorWrapper jcrNodeIteratorWrapper;

    private static String IMAGE1 = "image";
    private static String IMAGE2 = "imageTwo";
    private static String PAGE1 = "pageOne";
    private static String PAGE2 = "pageTwo";

    @Mock
    public MockLayerXConfigurationProvider configurationProvider;

    @Mock
    public MockLayerXConfiguration configuration;

    @InjectMocks
    private ItemsContainerContextProcessor itemsContainerCP = new ItemsContainerContextProcessor();

    private BaseTest baseTest;

    @Before
    public void setUp() throws Exception {

        baseTest = new BaseTest();
        jcrNodeWrapper = Mockito.mock(JCRNodeWrapper.class);
        jcrNodeIteratorWrapper = Mockito.mock(JCRNodeIteratorWrapper.class);

        List<String> list = new ArrayList<String>();
        list.add(IMAGE1);
        list.add(IMAGE2);
        list.add(PAGE1);
        list.add(PAGE2);
        baseTest.initializeConfiguration(configurationProvider, configuration);
        baseTest.config.put(PATH_FROM_RESOURCE_PROPERTIES_LIST, list);
    }

    @Test
    public void testConfigNodePropertiesIsNotNull() throws Exception {
        itemsContainerCP.process(baseTest.executionContext, baseTest.contentModel);
        final Object nodeItemPropertiesObject = baseTest.contentModel.get(CONFIG_PROPERTIES_KEY + DOT + PATH_FROM_RESOURCE_PROPERTIES_LIST);
        assertNotNull(nodeItemPropertiesObject);
    }

}
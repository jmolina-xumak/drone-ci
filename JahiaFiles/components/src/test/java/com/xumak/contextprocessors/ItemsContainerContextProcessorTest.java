package com.xumak.contextprocessors;

import com.xumak.base.configuration.MockLayerXConfiguration;
import com.xumak.base.configuration.MockLayerXConfigurationProvider;
import com.xumak.base.templatingsupport.BaseTest;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRNodeIteratorWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xumak.Constants.COMPONENT_CONTAINER_NAME;
import static com.xumak.Constants.NODE_ITEM_PROPERTIES;
import static layerx.Constants.CONTENT;
import static layerx.Constants.CONFIG_PROPERTIES_KEY;
import static layerx.Constants.DOT;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * DESCRIPTION
 * ------------------------------------------------------------------------------------------------------------------
 * Unit test to be sure to get not null list in content model.
 * ------------------------------------------------------------------------------------------------------------------
 * CHANGE HISTORY
 * ------------------------------------------------------------------------------------------------------------------
 * Version     |Date         |Developer               |Changes
 * 1.0         |05/22/2017   |Marco Cali              |Initial Creation
 * ------------------------------------------------------------------------------------------------------------------
 */

@RunWith(MockitoJUnitRunner.class)
public class ItemsContainerContextProcessorTest {

    private JCRNodeWrapper jcrNodeWrapper;
    private JCRNodeIteratorWrapper jcrNodeIteratorWrapper;

    private static String COMPONENT_NAME = "componentName";
    private static String TITLE = "title";
    private static String DESCRIPTION = "description";
    private static String IMAGE = "image";

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
        list.add(TITLE);
        list.add(DESCRIPTION);
        list.add(IMAGE);
        baseTest.initializeConfiguration(configurationProvider, configuration);
        baseTest.config.put(NODE_ITEM_PROPERTIES, list);
        baseTest.config.put(COMPONENT_CONTAINER_NAME, COMPONENT_NAME);
        baseTest.jcrNodeWrapper.addNode(COMPONENT_NAME);
    }

    @Test
    public void testConfigNodePropertiesIsNotNull() throws Exception {
        itemsContainerCP.process(baseTest.executionContext, baseTest.contentModel);
        final Object nodeItemPropertiesObject = baseTest.contentModel.get(CONFIG_PROPERTIES_KEY + DOT + NODE_ITEM_PROPERTIES);
        assertNotNull(nodeItemPropertiesObject);
    }

    @Test
    public void testConfigComponentNameIsNotNull() throws Exception {
        itemsContainerCP.process(baseTest.executionContext, baseTest.contentModel);
        final Object componentName = baseTest.contentModel.get(CONFIG_PROPERTIES_KEY + DOT + COMPONENT_CONTAINER_NAME);
        assertNotNull(componentName);
    }

    @Test
    public void testListGeneratedIsNotNull() throws Exception{
        Answer<JCRNodeWrapper> answer = new Answer<JCRNodeWrapper>() {
            @Override
            public JCRNodeWrapper answer(InvocationOnMock invocationOnMock) throws Throwable {
                return jcrNodeWrapper;
            }
        };

        //adding node
        when(baseTest.jcrNodeWrapper.addNode(COMPONENT_NAME)).thenAnswer(answer);

        //getting node
        when(baseTest.jcrNodeWrapper.getNode(COMPONENT_NAME)).thenReturn(jcrNodeWrapper);

        boolean hasNode = true;
        when(baseTest.jcrNodeWrapper.hasNode(COMPONENT_NAME)).thenReturn(hasNode);
        when(jcrNodeWrapper.getNodes()).thenReturn(jcrNodeIteratorWrapper);
        itemsContainerCP.process(baseTest.executionContext, baseTest.contentModel);

        final List<Map<String, Object>> listMaps = (List<Map<String, Object>>) baseTest.contentModel.get(CONTENT + DOT + NODE_ITEM_PROPERTIES);
        assertNotNull(listMaps);
    }
}

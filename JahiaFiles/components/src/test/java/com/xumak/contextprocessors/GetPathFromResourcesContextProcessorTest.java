package com.xumak.contextprocessors;

import javax.jcr.PropertyType;
import com.xumak.base.configuration.MockLayerXConfiguration;
import com.xumak.base.configuration.MockLayerXConfigurationProvider;
import com.xumak.base.templatingsupport.BaseTest;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRPropertyWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.when;

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

    private JCRPropertyWrapper propertyWrapper;
    private static String IMAGE1 = "image";
    private static String IMAGE2 = "imageTwo";
    private static String PAGE1 = "pageOne";
    private static String PAGE2 = "pageTwo";
    private static String resourceAsUUID = "myResourceUUID";

    @Mock
    public MockLayerXConfigurationProvider configurationProvider;

    @Mock
    public MockLayerXConfiguration configuration;

    @InjectMocks
    private GetPathFromResourcesContextProcessor getPathFromResourcesCP = new GetPathFromResourcesContextProcessor();

    private BaseTest baseTest;

    @Before
    public void setUp() throws Exception {

        baseTest = new BaseTest();
        propertyWrapper = Mockito.mock(JCRPropertyWrapper.class);

        List<String> list = new ArrayList<String>();
        list.add(IMAGE1);
        list.add(IMAGE2);
        list.add(PAGE1);
        list.add(PAGE2);
        baseTest.initializeConfiguration(configurationProvider, configuration);
        baseTest.config.put(PATH_FROM_RESOURCE_PROPERTIES_LIST, list);
        baseTest.jcrNodeWrapper.setProperty(IMAGE1, resourceAsUUID);
    }

    @Test
    public void testNodePropertiesType() throws Exception {
        Answer<JCRPropertyWrapper> answer = new Answer<JCRPropertyWrapper>() {
            @Override
            public JCRPropertyWrapper answer(InvocationOnMock invocationOnMock) throws Throwable {
                return propertyWrapper;
            }
        };
        getPathFromResourcesCP.process(baseTest.executionContext, baseTest.contentModel);

        final List<String> nodeItemPropertiesObject = (List<String>) baseTest.contentModel.get(CONFIG_PROPERTIES_KEY
                + DOT + PATH_FROM_RESOURCE_PROPERTIES_LIST);
        assertNotNull(nodeItemPropertiesObject);

        boolean hasProperty = true;
        when(baseTest.jcrNodeWrapper.setProperty(IMAGE1, resourceAsUUID)).thenAnswer(answer);
        when(baseTest.jcrNodeWrapper.hasProperty(IMAGE1)).thenReturn(hasProperty);

    }

}
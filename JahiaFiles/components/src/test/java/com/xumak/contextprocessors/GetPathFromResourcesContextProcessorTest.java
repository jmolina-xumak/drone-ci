package com.xumak.contextprocessors;

import javax.jcr.PropertyType;
import com.xumak.base.configuration.MockLayerXConfiguration;
import com.xumak.base.configuration.MockLayerXConfigurationProvider;
import com.xumak.base.templatingsupport.BaseTest;
import org.jahia.services.content.JCRPropertyWrapper;
import org.jahia.services.content.JCRValueWrapper;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * DESCRIPTION
 * ------------------------------------------------------------------------------------------------------------------
 * Unit test to be sure the type of property will be a WEAKREFERENCE.
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
    private JCRValueWrapper jcrValueWrapper;
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
        jcrValueWrapper = Mockito.mock(JCRValueWrapper.class);

        List<String> list = new ArrayList<String>();
        list.add(IMAGE1);
        list.add(IMAGE2);
        list.add(PAGE1);
        list.add(PAGE2);
        baseTest.initializeConfiguration(configurationProvider, configuration);
        baseTest.config.put(PATH_FROM_RESOURCE_PROPERTIES_LIST, list);
        baseTest.jcrNodeWrapper.setProperty(IMAGE1, resourceAsUUID);

        when(baseTest.jcrNodeWrapper.hasProperty(IMAGE1)).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return Boolean.TRUE;
            }
        });
    }

    @Test
    public void testNodePropertiesTypeAsWeakReference() throws Exception {
        int type = 10;
        when(baseTest.jcrNodeWrapper.getProperty(IMAGE1)).thenReturn(propertyWrapper);
        when(propertyWrapper.getType()).thenReturn(type);
        when(propertyWrapper.getValue()).thenReturn(jcrValueWrapper);
        when(propertyWrapper.toString()).thenReturn(resourceAsUUID);
        getPathFromResourcesCP.process(baseTest.executionContext, baseTest.contentModel);

        final List<String> nodeItemPropertiesObject = (List<String>) baseTest.contentModel.get(CONFIG_PROPERTIES_KEY
                + DOT + PATH_FROM_RESOURCE_PROPERTIES_LIST);
        assertNotNull(nodeItemPropertiesObject);
        assertTrue(propertyWrapper.getType() == PropertyType.WEAKREFERENCE);
    }

}
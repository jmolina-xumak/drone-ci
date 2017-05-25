package com.xumak.contextprocessors;

import com.xumak.base.configuration.MockLayerXConfiguration;
import com.xumak.base.configuration.MockLayerXConfigurationProvider;
import com.xumak.base.templatingsupport.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static layerx.Constants.CONTENT;
import static layerx.Constants.DOT;
import static com.xumak.Constants.EXAMPLE_KEY;
import static com.xumak.Constants.EXAMPLE_TEXT_VALUE;

/**
 * ExampleProcessorTest
 * To do unit test for the ExampleProcessor.
 * -­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-‐
 * Change History
 * --------------------------------------------------------------------------------------
 * Version | Date       | Developer       | Changes
 * 1.1     | 10/05/5017 | Rainman Sián    | Initial creation
 * --------------------------------------------------------------------------------------
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleProcessorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleProcessorTest.class);

    private String contentExampleKey = CONTENT.concat(DOT).concat(EXAMPLE_KEY);

    @Mock
    public MockLayerXConfigurationProvider configurationProvider;

    @Mock
    public MockLayerXConfiguration configuration;

    @InjectMocks
    private ExampleProcessor exampleProcessor = new ExampleProcessor();

    private BaseTest baseTest;

    @Before
    public void setUp() throws Exception {
        baseTest = new BaseTest();
        baseTest.initializeConfiguration(configurationProvider, configuration);

        // config properties section
        /* section to add required configurations */
    }

    @Test
    public void testExampleTextIsNotNull() throws Exception {
        exampleProcessor.process(baseTest.executionContext, baseTest.contentModel);

        final Object exampleTextValue = baseTest.contentModel.get(contentExampleKey);
        assertNotNull(exampleTextValue);
    }

    @Test
    public void testExampleTextEquals() throws Exception{

        exampleProcessor.process(baseTest.executionContext, baseTest.contentModel);

        final String exampleTextValue = baseTest.contentModel.getAsString(contentExampleKey);

        LOGGER.info("exampleTextValue : " + exampleTextValue);

        assertEquals(EXAMPLE_TEXT_VALUE, exampleTextValue);
    }
}

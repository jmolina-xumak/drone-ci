package com.xumak.base.configuration;


import layerx.api.configuration.Configuration;
import layerx.api.configuration.ConfigurationProvider;

/**
 * MockLayerXConfigurationProvider
 * <description>
 * -­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-‐
 * Change History
 * --------------------------------------------------------------------------------------
 * Version | Date       | Developer       | Changes
 * 1.0     | 12/02/2015 | Lesly Quiñonez  | Initial Creation
 * 1.1     | 10/05/5017 | Rainman Sián    | Adapted to LayerX + Jahia
 * --------------------------------------------------------------------------------------
 */
public class MockLayerXConfigurationProvider implements ConfigurationProvider<String> {

    @Override
    public Configuration getFor(final String resourceType) throws Exception {
        return new MockLayerXConfiguration(resourceType);
    }

    @Override
    public boolean hasConfig(final String resourceType) throws Exception {
        return false; // TODO
    }

}

package com.xumak.base.configuration;

import layerx.api.configuration.Configuration;
import layerx.api.configuration.Mode;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * MockLayerXConfiguration
 * <description>
 * -­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-­‐-‐
 * Change History
 * --------------------------------------------------------------------------------------
 * Version | Date       | Developer       | Changes
 * 1.0     | 12/02/2015 | Lesly Quiñonez  | Initial Creation
 * 1.1     | 10/05/5017 | Rainman Sián    | Adapted to LayerX + Jahia
 * --------------------------------------------------------------------------------------
 */
public class MockLayerXConfiguration implements Configuration{

    private static final Mode DEFAULT_MODE = Mode.INHERIT;

    public MockLayerXConfiguration(final String resourceType) {
    }

    @Override
    public Mode defaultMode() {
        return DEFAULT_MODE;
    }

    @Override
    public Set<String> names(final Mode mode) throws Exception {
        return null;
    }

    @Override
    public Set<String> names() throws Exception {
        return null;
    }

    @Override
    public String asString(final String paramName, final Mode mode) throws Exception {
        return null;
    }

    @Override
    public Collection<String> asStrings(final String paramName, final Mode mode) throws Exception {
        return null;
    }

    @Override
    public String asString(final String paramName) throws Exception {
        return null;
    }

    @Override
    public Collection<String> asStrings(final String paramName) throws Exception {
        return null;
    }

    @Override
    public Number asNumber(final String paramName, final Mode mode) throws Exception {
        return null;
    }

    @Override
    public Collection<Number> asNumbers(final String paramName, final Mode mode) throws Exception {
        return null;
    }

    @Override
    public Number asNumber(final String paramName) throws Exception {
        return null;
    }

    @Override
    public Collection<Number> asNumbers(final String paramName) throws Exception {
        return null;
    }

    @Override
    public Date asDate(final String paramName, final Mode mode) throws Exception {
        return null;
    }

    @Override
    public Collection<Date> asDates(final String paramName, final Mode mode) throws Exception {
        return null;
    }

    @Override
    public Date asDate(final String paramName) throws Exception {
        return null;
    }

    @Override
    public Collection<Date> asDates(final String paramName) throws Exception {
        return null;
    }

    @Override
    public String toJSONString() throws Exception {
        return null;
    }

    @Override
    public String toJSONString(final JSONStyle style) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> toMap() throws Exception {
        return null; //TODO
    }

    @Override
    public JSONObject toJSONObject() throws Exception {
        return null; //TODO
    }

}

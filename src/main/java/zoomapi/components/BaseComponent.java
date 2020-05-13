package zoomapi.components;

import zoomapi.Util;

import java.util.HashMap;

public class BaseComponent extends Util.ApiClient {
    /**
     * Setup a new API Client
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config: The config details
     */
    public BaseComponent(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
    }
}

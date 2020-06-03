package zoomapi;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import okhttp3.*;
import zoomapi.handlers.CredentialHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Util {
    /** Simple wrapper for REST API requests */
    public static class ApiClient {
        private String baseUrl;
        private int timeout;
        public HashMap<String, String> config;

        /**
         * Setup a new API Client
         * @param baseUrl: The base URI to the API
         * @param timeout: The timeout to use for requests
         * @param config: The config details
         */
        public ApiClient(String baseUrl, int timeout, HashMap<String, String> config) {
            this.baseUrl = baseUrl;
            this.timeout = timeout;
            if (config != null) this.config = config;
            else this.config = new HashMap<>();
        }

        public void setBaseUrl(String baseUrl) {
            if (baseUrl != null && baseUrl.endsWith("/"))
                this.baseUrl = baseUrl.substring(0, baseUrl.length()-1);
            else this.baseUrl = baseUrl;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getTimeout() {
            return timeout;
        }

        /**
         * Get the URL for the given endpoint
         * @param endpoint: The endpoint
         * @return The full URL for the endpoint
         */
        public String urlFor(String endpoint) {
            if (!endpoint.startsWith("/")) endpoint = String.format("/%s", endpoint);
            if (endpoint.endsWith("/")) endpoint = endpoint.substring(0, endpoint.length()-1);
            return baseUrl + endpoint;
        }

        /**
         * Helper function for GET requests
         * @param endpoint: The endpoint
         * @return The :class:``requests.Response`` object for this request
         */
        public String getRequest(String endpoint) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url(urlFor(endpoint))
                    .addHeader("authorization", String.format("Bearer %s", config.get("token")))
                    .build();

            String string = null;
            try {
                Response response = client.newCall(request).execute();
                string = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return string;
        }

        public String postRequest(String endpoint, String content) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body;
            Request request;
            if (content == null) {
                request = new Request.Builder()
                        .url(urlFor(endpoint))
                        .post(null)
                        .addHeader("authorization", String.format("Bearer %s", config.get("token")))
                        .build();
            }
            else {
                body = RequestBody.create(mediaType, content);
                request = new Request.Builder()
                        .url(urlFor(endpoint))
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("authorization", String.format("Bearer %s", config.get("token")))
                        .build();
            }

            String string = null;
            try {
                Response response = client.newCall(request).execute();
                string = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return string;
        }

        public String patchRequest(String endpoint, String content) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, content);
            Request request = new Request.Builder()
                    .url(urlFor(endpoint))
                    .patch(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("authorization", String.format("Bearer %s", config.get("token")))
                    .build();

            String string = null;
            try {
                Response response = client.newCall(request).execute();
                string = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return string;
        }

        public String deleteRequest(String endpoint) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(urlFor(endpoint))
                    .delete(null)
                    .addHeader("authorization", String.format("Bearer %s", config.get("token")))
                    .build();

            String string = null;
            try {
                Response response = client.newCall(request).execute();
                string = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return string;
        }

        public String putRequest(String endpoint, String content) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, content);
            Request request = new Request.Builder()
                    .url(urlFor(endpoint))
                    .put(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("authorization", String.format("Bearer %s", config.get("token")))
                    .build();

            String string = null;
            try {
                Response response = client.newCall(request).execute();
                string = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return string;
        }
    }

    /**
     * A class containing a user email address
     */
    public static class Email {
        private String email;
        public Email(String email) {
            this.email = email;
        }
    }

    /**
     * Check whether the input is of a string type.
     * @param value The value to check whether it is a string
     * @return ``True`` if `value` is an instance of str, ``False`` if not
     */
    public boolean isStrType(Object value) {
        return value instanceof String;
    }

    /**
     * Require that the object have the given keys
     * @param d: The dict the check
     * @param keys: The keys to check `obj` for. This can either be a single
     *              string, or an iterable of strings
     * @param allowNull: Whether ``null`` values are allowed
     */
    public boolean requireKeys(HashMap<String, String> d, String[] keys, boolean allowNull) {
        for (String k : keys) {
            if (!d.containsKey(k)) try {
                throw new Exception(String.format("'%s' must be set", k));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!allowNull && d.get(k) == null) try {
                throw new Exception(String.format("'%s' cannot be null", k));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Convert date and datetime objects to a string
     * @param date: he :class:`datetime.date` or :class:`datetime.datetime` to
     *              convert to a string
     * @return The string representation of the date
     */
    public String dateToStr(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String generateJWT(String cid, String cSecret) {
        return null;
    }

    public static String getOauthToken(String cid, String cSecret, String port) {
        String accessToken = null;
        String authorizationUrl = "https://zoom.us/oauth/authorize";
        String tokenUrl = "https://zoom.us/oauth/token";

        CredentialHandler credentialHandler = new CredentialHandler();
        accessToken = credentialHandler.getOauthToken(cid);

        if (accessToken == null) {
            AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken
                    .authorizationHeaderAccessMethod(),
                    new NetHttpTransport(),
                    new JacksonFactory(),
                    new GenericUrl(tokenUrl),
                    new ClientParametersAuthentication(cid, cSecret),
                    cid,
                    authorizationUrl).setScopes(Arrays.asList("read")).build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(
                    "localhost").setPort(Integer.parseInt(port)).build();
            try {
                Credential credential = flow.loadCredential("user");
                if (credential == null) {
                    credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
                }
                accessToken = credential.getAccessToken();

                HashMap<String, String> credentialMap = new HashMap<>();
                credentialMap.put("clientId", cid);
                credentialMap.put("oauthToken", accessToken);
                credentialMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
                credentialHandler.insert(credentialMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return accessToken;
    }
}



package httpOperations;

import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler {

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Request request;
    private Response response;
    private String url;
    private static Logger logger = Logger.getLogger(HttpHandler.class);

    HttpUrl.Builder urlBuilder;

    public Response getRequest(String apiUrl, HashMap<String, String> queryParams) {
        urlBuilder = HttpUrl.parse(apiUrl).newBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        url = urlBuilder.build().toString();

        request = new Request.Builder().url(url).build();

        logger.info("Request URL is: " + request);
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}

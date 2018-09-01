package com.zaiym;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class HttpClientUtil {

    public static String upload(File file, String server, Map<String,String> params) throws IOException {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            entityBuilder.addBinaryBody("file", file);
            ContentType contentType = ContentType.APPLICATION_JSON;//ContentType.create("text/html", Consts.UTF_8);
            if (params != null && params.size() > 0) {
                for (Map.Entry<String,String> entry : params.entrySet()) {
                    entityBuilder.addTextBody(entry.getKey(), entry.getValue(), contentType);
                }
            }
            HttpPost post = new HttpPost(server);
            post.setEntity(entityBuilder.build());

            response = client.execute(post);
            if (response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity());
            }
            return "";
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
    }
}
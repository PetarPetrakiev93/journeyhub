package com.softuni.journeyhub.image.cloud;

import okhttp3.*;
import com.google.gson.Gson;
import com.softuni.journeyhub.image.models.Image;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class CloudImageExtractor {
    private static final String QUERY_PATH_SEPARATOR = "?";

    private static final String QUERY_PARAMETER_SEPARATOR = "&";

    private static final String FOLDER_ID_PARAMETER = "folderid=";

    private static final String FILE_ID_PARAMETER = "fileid=";

    private static final String AUTH_PARAMETER = "auth=";

    private static final String CODE_PARAMETER = "code=";

    private static final String LIST_FOLDER_URL
            = "https://api.pcloud.com/listfolder";

    private static final String LIST_FILE_URL
            = "https://api.pcloud.com/getfilepublink";

    private static final String DOWNLOAD_FILE_URL
            = "https://api.pcloud.com/getpublinkdownload";

    private final HttpRequestExecutor httpRequestExecutor;

    private final CloudAuthorizationService cloudAuthorizationService;

    public CloudImageExtractor(HttpRequestExecutor httpRequestExecutor, CloudAuthorizationService cloudAuthorizationService) {
        this.httpRequestExecutor = httpRequestExecutor;
        this.cloudAuthorizationService = cloudAuthorizationService;
    }

    public Image getImage(Response response) throws IOException {
        this.cloudAuthorizationService.login();
        Gson gson = new Gson();
        String accessToken = this.cloudAuthorizationService.getAccessToken();

        String fileResult = response.body().string();

        Map<String, Object> file = gson.fromJson(fileResult, Map.class);

        ArrayList<Map<String, String>> fileData = (ArrayList<Map<String, String>>) file.get("metadata");


            String fileId = fileData.get(0).get("id").toString().substring(1);

            String fileListJsonResult = this.httpRequestExecutor.executeGetRequest(
                    LIST_FILE_URL
                            + QUERY_PATH_SEPARATOR
                            + FILE_ID_PARAMETER
                            + fileId
                            + QUERY_PARAMETER_SEPARATOR
                            + AUTH_PARAMETER
                            + accessToken
            ).body()
                    .string();

            String fileCode = gson.fromJson(fileListJsonResult, Map.class).get("code").toString();

            String fileDownloadJsonResult = this.httpRequestExecutor.executeGetRequest(
                    DOWNLOAD_FILE_URL
                            + QUERY_PATH_SEPARATOR
                            + CODE_PARAMETER
                            + fileCode
            ).body()
                    .string();

            Map<String, Object> fileDownloadData = gson.fromJson(fileDownloadJsonResult, Map.class);

            String filePath = fileDownloadData.get("path").toString();
            String host = ((ArrayList<String>) fileDownloadData.get("hosts")).get(0);

            String fileName = fileData.get(0).get("name").toString();
            String fileUrl = "https://" + host + filePath;
            return new Image(fileName, fileUrl, fileId);
    }

    public Image updateImages(Image image) throws IOException {
        Gson gson = new Gson();
        String accessToken = this.cloudAuthorizationService.getAccessToken();

        String fileId = image.getCloudId();

        String fileListJsonResult = this.httpRequestExecutor.executeGetRequest(
                LIST_FILE_URL
                        + QUERY_PATH_SEPARATOR
                        + FILE_ID_PARAMETER
                        + fileId
                        + QUERY_PARAMETER_SEPARATOR
                        + AUTH_PARAMETER
                        + accessToken
        ).body()
                .string();

        String fileCode = gson.fromJson(fileListJsonResult, Map.class).get("code").toString();

        String fileDownloadJsonResult = this.httpRequestExecutor.executeGetRequest(
                DOWNLOAD_FILE_URL
                        + QUERY_PATH_SEPARATOR
                        + CODE_PARAMETER
                        + fileCode
        ).body()
                .string();

        Map<String, Object> fileDownloadData = gson.fromJson(fileDownloadJsonResult, Map.class);

        String filePath = fileDownloadData.get("path").toString();
        String host = ((ArrayList<String>) fileDownloadData.get("hosts")).get(0);
        String fileUrl = "https://" + host + filePath;
        image.setUrl(fileUrl);
        this.cloudAuthorizationService.login();
        return image;
    }
}

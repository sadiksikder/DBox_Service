package com.upwork;

import com.dropbox.core.*;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.*;
import java.net.URL;

public class DropboxClient {
    public static void main(String[] args) throws IOException, DbxException {

        // Get your app key and secret from the Dropbox developers website and put into 'app-info.json' file.
        // https://www.dropbox.com/developers/apps
        String appInfoFile = "app-info.json";

        // Dropbox config object
        DbxRequestConfig config = new DbxRequestConfig("RequestConfig/1.0");

        // Our Dropbox client
        DropboxClient dropboxClient = new DropboxClient();

        // Get ACCESS TOKEN after authorization through "auth" method
        String accessToken = dropboxClient.auth(config, appInfoFile);

        // Official Dropbox Client used for upload/download files
        DbxClientV2 client = new DbxClientV2(config, accessToken);

        // Shows the linked account display name and email
        System.out.println("\nLinked account:");
        System.out.println("- Name: " + client.users().getCurrentAccount().getName().getDisplayName());
        System.out.println("- Email: " + client.users().getCurrentAccount().getEmail());

        String filePath = "Full path of the file you want to upload";

        String pathOnDropbox = "Full path of the file on the Dropbox server";

        // Upload file
        dropboxClient.uploadFile(client, filePath);

        // Download file
        dropboxClient.downloadFile(client, pathOnDropbox);
    }

    public void downloadFile(DbxClientV2 client, String filePathOnDbxServer) throws IOException, DbxException {

        String home = System.getProperty("user.home");
        String[] splitPath = filePathOnDbxServer.split("/");
        String downloadPath = String.format("%s/Downloads/%s", home, splitPath[splitPath.length - 1]);

        FileOutputStream out = new FileOutputStream(downloadPath);

        try {
            System.out.println("\nDownloading file...");
            FileMetadata downloadFile = client.files().download(filePathOnDbxServer).download(out);
            System.out.println("Downloaded file: " + downloadPath);
        } finally {
            out.close();
        }
    }

    public void uploadFile(DbxClientV2 client, String filePath) throws IOException, DbxException {
        File inputFile = new File(filePath);

        FileInputStream inputStream = new FileInputStream(inputFile);

        try {
            String fileOnServer = String.format("/%s", inputFile.getName());
            System.out.println("\nUploading file...");
            FileMetadata uploadedFile = client.files().upload(fileOnServer).uploadAndFinish(inputStream);
            System.out.println("Uploaded file: " + uploadedFile.getPathDisplay());
        } finally {
            inputStream.close();
        }
    }

    public String auth(DbxRequestConfig config, String appInfoFile) throws IOException {

        // Get the resource URL e.g. URL of the file which contains app key and app secret
        URL resource = this.getClass().getClassLoader().getResource(appInfoFile);

        // Read app info file (contains app key and app secret)
        DbxAppInfo appInfo;
        try {
            appInfo = DbxAppInfo.Reader.readFully(resource.openStream());
        } catch (JsonReadException ex) {
            System.err.println("Error reading 'app-info.json': " + ex.getMessage());
            System.exit(1);
            return null;
        }

        // Constructs the auth request for authorization
        DbxWebAuth webAuth = new DbxWebAuth(config, appInfo);
        DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder()
                .withNoRedirect()
                .build();

        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.authorize(webAuthRequest);
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        System.out.print("Enter the authorization code here: ");

        // Read the authorization code from user input
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
        if (code == null) {
            System.exit(1);
            return null;
        }
        code = code.trim();

        DbxAuthFinish authFinish;
        try {
            // This will fail if the user enters an invalid authorization code.
            authFinish = webAuth.finishFromCode(code);
        } catch (DbxException ex) {
            System.err.println("Error in DbxWebAuth.authorize: " + ex.getMessage());
            System.exit(1);
            return null;
        }

        System.out.println("\nAuthorization complete.");
        System.out.println("- User ID: " + authFinish.getUserId());
        System.out.println("- Access Token: " + authFinish.getAccessToken());

        return authFinish.getAccessToken();
    }
}
package com.upwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;




	public class DropboxUpload {
	private static final String ACCESS_TOKEN = "GN8jMnlMlYAAAAAAAAAAb2oxT6seRkNo_cHrwlZ4gkjZvMoClbzco08DeNHt1ePV";
	private static final String APP_KEY ="74089qoyjk49q7d";
	private static final String APP_SECRET= "gmqd1l0i6iavpxd";
	
    public static void main(String args[]) throws DbxException, IOException{
       
    	// Create Dropbox client

    	DbxRequestConfig config = new DbxRequestConfig("sdkfjksdjfk");// look at deep of DbxRequestConfig
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN); //look at deep of DbxClientV2
        
        
        
        // Shows the linked account displayed name, email and other informations.
        System.out.println("\nBasic Informations logged in account");
        System.out.println("------------------------------------------------------------------");
        System.out.println("\nLinked account:");
        System.out.println("- Name: " + client.users().getCurrentAccount().getName().getDisplayName());
        System.out.println("- Email: " + client.users().getCurrentAccount().getEmail());
        System.out.println("- Porifle Photo URL: " + client.users().getCurrentAccount().getProfilePhotoUrl());
        
       
        
        						//Upload parameters
        					//File path to upload	
        String filePath ="C:\\Users\\Samsuddin\\Desktop\\sikder_samsuddin.jpg";
        //String filePath ="C:\\Users\\Samsuddin Sikder\\Desktop\\dropbox.FOLDER";
        	File inputFile = new File(filePath);

          FileInputStream inputStream = new FileInputStream(inputFile);

         
          try {
              String fileOnServer = String.format("/%s", inputFile.getName());
              System.out.println("\nUploading file...");
              FileMetadata uploadedFile = client.files().upload(fileOnServer).uploadAndFinish(inputStream);
              System.out.println("Uploaded file: " + uploadedFile.getPathDisplay());
              System.out.println();
          } finally {
              inputStream.close();
          }
          
          
          
      }
    
    
	}
	
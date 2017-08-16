package com.upwork;

import java.io.FileOutputStream;
import java.io.IOException;
import com.dropbox.core.v2.users.Account;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.Name;

public class DropboxDownload {

	private static final String ACCESS_TOKEN = "GN8jMnlMlYAAAAAAAAAAb2oxT6seRkNo_cHrwlZ4gkjZvMoClbzco08DeNHt1ePV";
	private static final String APP_KEY ="74089qoyjk49q7d";
	private static final String APP_SECRET= "gmqd1l0i6iavpxd";
	
    public static void main(String args[]) throws DbxException, IOException{
       
    	// Create Dropbox client
    	//Name name= new Name("myName","","","","");
    	//Account ac = new Account("1213", name,"email@gmail.com",false, false);

    	DbxRequestConfig config = new DbxRequestConfig("sdkfjksdjfk");// look at deep of DbxRequestConfig
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN); //look at deep of DbxClientV2
        
        //create client 
        // Our Dropbox client
        DropboxClient dropboxClient = new DropboxClient();
        
        // Shows the linked account displayed name, email and other informations.
        System.out.println("\nBasic Informations logged in account");
        System.out.println("------------------------------------------------------------------");
        System.out.println("\nLinked account:");
        System.out.println("- Name: " + client.users().getCurrentAccount().getName().getDisplayName());
        System.out.println("- Email: " + client.users().getCurrentAccount().getEmail());
        System.out.println("- Porifle Photo URL: " + client.users().getCurrentAccount().getProfilePhotoUrl());
        System.out.println("--------------------------------------------------------------------------------------");
     
        
        //String downloadpath = "/house.jpg";
        //String downloadpath = "/sadik/house.jpg";
        String downloadpath = "/test.txt";
        
        //String downloadpath = "/sikder_samsuddin.jpg";
        // Download file
    	dropboxClient.downloadFile(client, downloadpath);
    	
    	
    	
    	//String home = System.getProperty("user.home");
    	
    	
    	 
       
       
    	
    }
    
  
}

package com.dev.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Google Directory SDK configuration
 */
@Configuration
public class GoogleApiConfig
{
  private static final String APPLICATION_NAME =
      "User Service";

  /**
   * Directory to store user credentials for this application.
   */
  private static final java.io.File DATA_STORE_DIR = new java.io.File(
      System.getProperty("user.home"), ".credentials/admin-directory_v1-java-service");

  /**
   * Global instance of the {@link FileDataStoreFactory}.
   */
  private static FileDataStoreFactory DATA_STORE_FACTORY;

  /**
   * Global instance of the JSON factory.
   */
  private static final JsonFactory JSON_FACTORY =
      JacksonFactory.getDefaultInstance();

  /**
   * Global instance of the HTTP transport.
   */
  private static HttpTransport HTTP_TRANSPORT;

  /**
   * Global instance of the scopes required by this quickstart.
   * <p>
   * If modifying these scopes, delete your previously saved credentials
   * at ~/.credentials/admin-directory_v1-java-quickstart
   */
  private static final List<String> SCOPES =
      Arrays.asList(DirectoryScopes.ADMIN_DIRECTORY_USER_READONLY);

  static
  {
    try
    {
      HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
    }
    catch (Throwable t)
    {
      t.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Build and return an authorized Admin SDK Directory client service.
   *
   * @return an authorized Directory client service
   * @throws IOException
   */
  public static Directory getDirectoryService() throws IOException
  {
    Credential credential = authorize();
    return new Directory.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }

  /**
   * Creates an authorized Credential object.
   *
   * @return an authorized Credential object.
   * @throws IOException
   */
  public static Credential authorize() throws IOException
  {
    // Load client secrets.
    InputStream in =
        GoogleApiConfig.class.getResourceAsStream("/client_secret.json");
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow =
        new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(DATA_STORE_FACTORY)
            .setAccessType("offline")
            .build();
    Credential credential = new AuthorizationCodeInstalledApp(
        flow, new LocalServerReceiver()).authorize("user");
    return credential;
  }

  //TODO for test only, will be removed soon
  public static void main(String[] args) throws IOException
  {
    // Build a new authorized API client service.
    Directory service = getDirectoryService();
    service.groups().list().execute();

    // Print the first 10 users in the domain.
//    Users result = service.users().list().execute();
//    List<User> users = result.getUsers();
//    if (users == null || users.size() == 0)
//    {
//      System.out.println("No users found.");
//    }
//    else
//    {
//      System.out.println("Users:");
//      for (User user : users)
//      {
//        System.out.println(user.getName().getFullName());
//      }
//    }
  }

}

package com.jesusnear.versemapper.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class JDBCUtilities {
      public String dbms;
  public String jarFile;
  public String dbName; 
  public String userName;
  public String password;
  public String urlString;
  
  private String driver;
  private String serverName;
  private int portNumber;
  private Properties prop;

  private void setProperties(String fileName) throws FileNotFoundException,
                                                     IOException,
                                                     InvalidPropertiesFormatException {
    this.prop = new Properties();
    FileInputStream fis = new FileInputStream(fileName);
    prop.loadFromXML(fis);

    this.dbms = this.prop.getProperty("dbms");
    this.jarFile = this.prop.getProperty("jar_file");
    this.driver = this.prop.getProperty("driver");
    this.dbName = this.prop.getProperty("database_name");
    this.userName = this.prop.getProperty("user_name");
    this.password = this.prop.getProperty("password");
    this.serverName = this.prop.getProperty("server_name");
    this.portNumber = Integer.parseInt(this.prop.getProperty("port_number"));

    System.out.println("Set the following properties:");
    System.out.println("dbms: " + dbms);
    System.out.println("driver: " + driver);
    System.out.println("dbName: " + dbName);
    System.out.println("userName: " + userName);
    System.out.println("serverName: " + serverName);
    System.out.println("portNumber: " + portNumber);

  }

  public Connection getConnectionToDatabase() throws SQLException {
    {
      Connection conn = null;
      Properties connectionProps = new Properties();
      connectionProps.put("user", this.userName);
      connectionProps.put("password", this.password);

      // Using a driver manager:

      if (this.dbms.equals("postgresql")) {
        String currentUrlString = "jdbc:" + this.dbms + "://" + this.serverName +
                                        ":" + this.portNumber + "/" + this.dbName;
        conn =
            DriverManager.getConnection(currentUrlString,
                                        connectionProps);
    } else if (this.dbms.equals("derby")) {
//        DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        conn =
            DriverManager.getConnection("jdbc:" + dbms + ":" + dbName, connectionProps);
      }
      System.out.println("Connected to database");
      return conn;
    }
  }

  public Connection getConnection() throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);
    
    String currentUrlString = null;
    System.out.println("Connecting to database..." + this.dbms);
    if (this.dbms.equals("postgresql")) {
      currentUrlString = "jdbc:" + this.dbms + "://" + this.serverName +
                                      ":" + this.portNumber + "/" + this.dbName;
      conn =
          DriverManager.getConnection(currentUrlString,
                                      connectionProps);
      
      this.urlString = currentUrlString;
    } else if (this.dbms.equals("derby")) {
      this.urlString = "jdbc:" + this.dbms + ":" + this.dbName;
      
      conn =
          DriverManager.getConnection(this.urlString + 
                                      ";create=true", connectionProps);
      
    }
    System.out.println("Connected to database");
    return conn;
  }

  public Connection getConnection(String userName,
                                  String password) throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", userName);
    connectionProps.put("password", password);
     if (this.dbms.equals("postgresql")) {
      String currentUrlString = "jdbc:" + this.dbms + "://" + this.serverName +
                                      ":" + this.portNumber + "/" + this.dbName;
      conn =
          DriverManager.getConnection(currentUrlString,
                                      connectionProps);
    } else if (this.dbms.equals("derby")) {
      conn =
          DriverManager.getConnection("jdbc:" + this.dbms + ":" + this.dbName +
                                      ";create=true", connectionProps);
    }
    return conn;
  }


  public JDBCUtilities(String propertiesFileName) throws FileNotFoundException, IOException, InvalidPropertiesFormatException {
    super();
    this.setProperties(propertiesFileName);
  }


}

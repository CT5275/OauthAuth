package com.proj.local.sample;

import com.proj.local.sample.db.DBConnection;
import com.proj.local.sample.db.DBConnectionService;
import com.proj.local.sample.oauth.OauthService;
import com.proj.local.sample.service.ReadProperties;
import com.proj.local.sample.service.SendingEmailService;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main
{
  @Autowired
  private static OauthService service;
  public static Logger log1 = null;
  static SendingEmailService sendEmailService = null;
  static String profileName = "";

  
  public static void main(String[] args) throws SQLException, ParseException {
    ReadProperties.initialAppConfig();
    PropertyConfigurator.configure("log4j.properties");
    log1 = Logger.getLogger("rootLogger");
    sendEmailService = new SendingEmailService();
    String teestString="";
            try {
            	testFunction(teestString);
            } catch (Exception e) {
              e.printStackTrace();
            } 
        
  }
  
  public static void testFunction(String testString) throws Exception {
      AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(new String[] { "com.proj.local.sample.config" });
      service = (OauthService)annotationConfigApplicationContext.getBean("ServiceImpl");
      String url ="APIURL";
      String param ="something";
      HashMap<String, List<String>> test1 = service.testAPI(url, param);
      HashMap<String, List<String>> test2 = service.testAPI1(url);
      HashMap<String, List<String>> test3 = service.testAPI2(url);
  }
}

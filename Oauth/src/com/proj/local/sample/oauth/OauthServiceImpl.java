package com.proj.local.sample.oauth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;

import com.proj.local.sample.Main;

@Service("ServiceImpl")
public class OauthServiceImpl implements OauthService
{
  @Autowired
  private OAuth2RestOperations oAuth2restTemplate;
  
  public HashMap<String, String> testAPI(String api, String frequency) {
    String url = String.valueOf(api) + frequency;
    String obj = "";
    if (this.oAuth2restTemplate != null) {
      try {
        obj = (String)this.oAuth2restTemplate.getForObject(url, String.class, new Object[0]);
      } catch (OAuth2AccessDeniedException e) {
    	  logger.error(e.getMessage());
      } 
    }
    return getResponse(obj);
  }

  
  public HashMap<String, List<String>> testAPI1(String api) {
    String obj = "";
    if (this.oAuth2restTemplate != null) {
      try {
        obj = (String)this.oAuth2restTemplate.getForObject(api, String.class, new Object[0]);
      } catch (OAuth2AccessDeniedException e) {
    	  logger.error(e.getMessage());
      } 
    }
    return getResponse("sample", obj);
  }
  public HashMap<String, List<String>> testAPI2(String api) {
    String obj = "";
    if (this.oAuth2restTemplate != null) {
      try {
        obj = (String)this.oAuth2restTemplate.getForObject(api, String.class, new Object[0]);
      } catch (OAuth2AccessDeniedException e) {
    	  logger.info(e.getMessage());
      } 
    }
    return getResponse("sample", obj);
  }
  
  public HashMap<String, List<String>> getResponse(String name, String response) {
	  HashMap<String,List<String>> objReturn=new HashMap<String,List<String>>();
    if (!response.equals("") && response != null) {
      String otherthing = "";
      JSONObject info = null;
      try {
        JSONArray responseJson = new JSONArray(response);
        for (int i = 0; i < responseJson.length(); i++) {
        	info = responseJson.getJSONObject(i).getJSONObject("sample");
        	otherthing = String.valueOf(info.getString("otherthing"));
        } 
      } catch (JSONException e) {
        e.printStackTrace();
      } 
    } else {
      
      logger.info("oauth server error");
    } 
    return objReturn;
  }
}
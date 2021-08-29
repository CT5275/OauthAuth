package com.proj.local.sample.oauth;

import java.util.HashMap;
import java.util.List;

public interface OauthService {
  HashMap<String, String> testAPI(String param1, String param2);
  
  HashMap<String, List<String>> testAPI1(String param);
  
  HashMap<String, List<String>> testAPI2(String param);
  
  HashMap<String, List<String>> getResponse(String param1, String param2);
}

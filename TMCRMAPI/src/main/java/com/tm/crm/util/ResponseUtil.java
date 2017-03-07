package com.tm.crm.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseUtil {
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getResponseAsMap(String responseBody)  {

		Map<String, Object> values = new HashMap<String, Object> ();
		ObjectMapper mapper = new ObjectMapper();

		try {
			values = mapper.readValue(responseBody, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			//log.error(e.getMessage(), e);
			e.printStackTrace();
			// TODO Auto-generated catch block
			//log.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//log.error(e.getMessage(), e);
			e.printStackTrace();
		}		
		return values;
	}

}

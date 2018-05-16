package com.rd.wrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import com.rd.constants.ConstantsCommon;

public class ApiWrapper {

	public JSONObject doGet(String symbol, List<String> toConvert, Boolean fullPrice) throws Exception {

		JSONObject jOResponse = null;

		String url = "";
		if(!fullPrice)
			 url = this.generateUrl(ConstantsCommon.URL_CRYPTOCOMPARE, symbol, toConvert, false);
		else
			 url = this.generateUrl(ConstantsCommon.URL_CRYPTOCOMPARE_FULL, symbol, toConvert, true);

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod(ConstantsCommon.HTTP_GET_METHOD);
		
		con.setRequestProperty("User-Agent", ConstantsCommon.USER_AGENT);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		jOResponse = new JSONObject(response.toString());
		return jOResponse;

	}

	private String generateUrl(String url, String symbol, List<String> toConvert, Boolean full) {
		String params = "";
		for (int i = 0; i < toConvert.size(); i++) {
			if (i == toConvert.size() - 1)
				params += toConvert.get(i);
			else
				params += toConvert.get(i) + ConstantsCommon.COMMA;
		}
		if(!full)
			return url + ConstantsCommon.URL_SYMBOL + symbol + ConstantsCommon.URL_TO + params;
		else
			return url + ConstantsCommon.URL_SYMBOL_FULL + symbol + ConstantsCommon.URL_TO + params;
	}

}

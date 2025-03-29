package com.kipl.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;

public class URLConnect {
	
    
    @SuppressWarnings("unused")
	@Autowired
	private Environment appConfig;

    public static String send(String url)
    {
    	System.out.println("url==>"+url);
        InputStream inputStream = null;
        try
        {
            URL encodedUrl = new URL(url.replace(" ", "%20"));
            // LOG.info("Connecting to url : " + encodedUrl);
            HttpURLConnection connection = (HttpURLConnection) encodedUrl.openConnection();
            inputStream = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer out = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null)
            {
                out.append(line);
            }
            connection.disconnect();
            return out.toString();
        }
        catch (Exception e)
        {
           
            return "URL Connection Exception.";
        }
        finally
        {
            try
            {
                if (inputStream != null) inputStream.close();
            }
            catch (Exception e)
            {
               // LOG.info(e.getCause(), e);
            }
        }
    }

    public static String connect(String urlString)
    {
        String response = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.connect();
            urlConnection.setReadTimeout(20 * 1000);
            urlConnection.setConnectTimeout(20 * 1000);
            inputStream = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer out = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null)
            {
                out.append(line);
            }

            response = out.toString();
            in.close();

            return response;
        }
        catch (Exception e)
        {
            
            return null;
        }
        finally
        {
            try
            {
                if (inputStream != null) inputStream.close();
                if (urlConnection != null) urlConnection.disconnect();
            }
            catch (Exception e)
            {
               // LOG.info(e.getCause(), e);
            }
        }
    }
    
    
    
    public static String sendAsPost(String urlOnly, String postData)
    {
        try
        {
        	URL url = new URL(urlOnly);
    		HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
    		urlconnection.setRequestMethod("POST");
    		urlconnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
    		urlconnection.setDoOutput(true);
    		OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream());
    		out.write(postData);
    		out.close();
    		BufferedReader in = new BufferedReader(	new InputStreamReader(urlconnection.getInputStream()));
    		StringBuffer response = new StringBuffer();
    		String decodedString;
    		while ((decodedString = in.readLine()) != null) {
    			response.append(decodedString);
    		}
    		in.close();

            return response.toString();
        }
        catch (Exception e)
        {
           
            return "URL Connection Exception.";
        }
    }
    
    public static String sendAsPostForCropDiagnosis(String urlOnly, String postData)
    {
        try
        {
        	URL url = new URL(urlOnly);
    		HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
    		urlconnection.setRequestMethod("POST");
    		urlconnection.setRequestProperty("Content-Type","application/json");
    		urlconnection.setDoOutput(true);
    		urlconnection.setConnectTimeout(3000);
    		urlconnection.setReadTimeout(3000);
    		OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream());
    		out.write(postData);
    		out.close();
    		BufferedReader in = new BufferedReader(	new InputStreamReader(urlconnection.getInputStream()));
    		StringBuffer response = new StringBuffer();
    		String decodedString;
    		while ((decodedString = in.readLine()) != null) {
    			response.append(decodedString);
    		}
    		in.close();

            return response.toString();
        }
        catch (Exception e)
        {
          
            return "URL Connection Exception.";
        }
    }
    
    
    public static String apiCallWithJsonBody(String apiURl,String json) 
    {
    	try {
    		
			URL url = new URL (apiURl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			
			OutputStream os = con.getOutputStream();
			os.write(json.getBytes());
			os.flush();
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			String output;
			StringBuffer response = new StringBuffer();
			while ((output = br.readLine()) != null) {
				
				response.append(output);
			}
			return response.toString();
		} catch (IOException e) {
			//LOG.info(" URL Connection Exception by Whats app."+e.getCause(), e);
			return "URL Connection Exception.";
		}
    }
}
package com.road.crawler.meizitu.exec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class DownloadAction extends Action {

	private String url;
	private String localPath;
	private CloseableHttpClient httpClient;

	{
		RequestConfig clientConfig = RequestConfig.custom()
				.setRedirectsEnabled(false).build();
		PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager();
		syncConnectionManager.setMaxTotal(1000);
		syncConnectionManager.setDefaultMaxPerRoute(50);
		httpClient = HttpClientBuilder.create()
				.setDefaultRequestConfig(clientConfig)
				.setConnectionManager(syncConnectionManager).build();
	}

	public DownloadAction(ActionQueue queue, String url, String localPath) {
		super(queue);
		this.url = url;
		this.localPath = localPath;
	}

	@Override
	protected void execute() {
		try{
		// TODO Auto-generated method stub
		 // 构造URL  
	       // URL url = new URL(urlString);  
	        // 打开连接  
	        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
	        //设置请求超时为5s  
	        con.setConnectTimeout(5*1000);
	        con.setReadTimeout(5000);
	        con.setRequestMethod("GET");
	        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");//访止403 
	        // 输入流  
	        InputStream is = con.getInputStream();  
	      
	        // 1K的数据缓冲  
	        byte[] bs = new byte[1024];  
	        // 读取到的数据长度  
	        int len;  
	        // 输出的文件流  
//	       File sf=new File(localPath);  
//	       if(!sf.exists()){  
//	           sf.mkdirs();  
//	       }  
	       OutputStream os = new FileOutputStream(localPath);  
	        // 开始读取  
	        while ((len = is.read(bs)) != -1) {  
	          os.write(bs, 0, len);  
	        }  
	        // 完毕，关闭所有链接  
	        os.close();  
	        is.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*@Override
	protected void execute() {
		HttpRequestBase request = new HttpGet(url);
		try {
			HttpClientContext context = HttpClientContext.create();
			org.apache.http.HttpResponse response = httpClient.execute(request,
					context);
			FileUtils.copyInputStreamToFile(response.getEntity().getContent(),
					new File(localPath));
		} catch (Exception e) {
			Log.error("Download error,  " + e.toString());
		} finally {
			request.releaseConnection();
		}
	}*/

}

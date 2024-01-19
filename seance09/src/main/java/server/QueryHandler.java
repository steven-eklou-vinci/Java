package server;

import domaine.Query;
import domaine.Query.QueryMethod;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;

public class QueryHandler extends Thread {
	
	private Query query;

	public QueryHandler(Query query) {
		this.query = query;
	}

	@Override
	public void run() {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			ClassicHttpRequest request = null;
			if (this.query.getMethod() == QueryMethod.GET) {
				request = ClassicRequestBuilder.get(this.query.getUrl()).build();
			}
			httpclient.execute(request, response -> {
				System.out.println(response.getCode() + " " + response.getReasonPhrase());
				HttpEntity entity = response.getEntity();
				System.out.println(EntityUtils.toString(entity));
				return null;
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package domaine;

class QueryImpl implements Query {
	
	private String url;
	private QueryMethod method;

	public QueryImpl() {
	}
	
	public QueryImpl(String url, QueryMethod method) {
		this.url = url;
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public QueryMethod getMethod() {
		return method;
	}

	public void setMethod(QueryMethod method) {
		this.method = method;
	}

	@Override
	public void setMethod(String method){
		this.method = QueryMethod.valueOf(method);
	}

}

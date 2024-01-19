package domaine;

public class QueryFactoryImpl implements QueryFactory {
	
	/* (non-Javadoc)
	 * @see domaine.QueryFactory#getQuery()
	 */
	@Override
	public Query getQuery() {
		return new QueryImpl();
	}

}

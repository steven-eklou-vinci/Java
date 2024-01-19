package blacklist;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import domaine.Query;

public class BlacklistServiceImpl implements BlacklistService {
	
	private static Set<String> blacklistedDomains;

	static {
		try (FileInputStream in = new FileInputStream("blacklist.properties")) {
			Properties props = new Properties();
			props.load(in);
			blacklistedDomains = Set.of(props.getProperty("blacklistedDomains").split(";"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean check(Query query) {
		return !blacklistedDomains.stream().anyMatch(d -> query.getUrl().contains(d));
	}
	
}

package sk.demo.domain.store;

public interface PersonStore {
	
	public void set(String key, Object value);	
	public Object get(String key);
	public void registerUser();		

}

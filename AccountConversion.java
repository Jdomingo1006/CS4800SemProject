import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class AccountConversion {
	public static final DBObject toDBObject(Account account) {
		return new BasicDBObject("username", account.getUsername())
						.append("password", account.getPassword());
	}
}
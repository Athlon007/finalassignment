import junit.framework.Assert;
import nl.inholland.konradfigura.finalassignment.Database.Database;
import nl.inholland.konradfigura.finalassignment.Model.User;
import org.junit.jupiter.api.Test;

public class TestDatabase {

    @Test
    public void UserLoadWorks() {
        Database db = new Database();
        User user = db.getUser("gordon", "password1");
        Assert.assertNotNull(user);
    }

    @Test
    public void UserIdCheckWorks() {
        Database db = new Database();
    }
}

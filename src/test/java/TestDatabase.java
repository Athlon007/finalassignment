import junit.framework.Assert;
import nl.inholland.konradfigura.finalassignment.Database.UserDatabase;
import nl.inholland.konradfigura.finalassignment.Model.User;
import org.junit.jupiter.api.Test;

public class TestDatabase {

    @Test
    public void UserLoadWorks() {
        UserDatabase db = new UserDatabase();
        User user = db.getUser("gordon", "password1");
        Assert.assertNotNull(user);
    }

    @Test
    public void UserIdCheckWorks() {
        UserDatabase db = new UserDatabase();
    }
}

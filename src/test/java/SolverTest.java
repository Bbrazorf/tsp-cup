import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class SolverTest {

    @Test(timeout = 181000)
    public void ch130() throws FileNotFoundException {
        String[] args = {"ch130", "1556035720118"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void d198() throws FileNotFoundException {
        String[] args = {"d198", "1556193502804"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void eil76() throws FileNotFoundException {
        String[] args = {"eil76", "1556035555990"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void fl1577() throws FileNotFoundException {
        String[] args = {"fl1577", "1556109604049"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void kroA100() throws FileNotFoundException {
        String[] args = {"kroA100", "1556036126807"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void lin318() throws FileNotFoundException {
        String[] args = {"lin318", "1556295075895"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void pcb442() throws FileNotFoundException {
        String[] args = {"pcb442", "1556531024918"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void pr439() throws FileNotFoundException {
        String[] args = {"pr439", "1556311370436"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void rat783() throws FileNotFoundException {
        String[] args = {"rat783", "1556200401666"};
        Main.main(args);
    }

    @Test(timeout = 181000)
    public void u1060() throws FileNotFoundException {
        String[] args = {"u1060", "1556111957373"};
        Main.main(args);
    }
}


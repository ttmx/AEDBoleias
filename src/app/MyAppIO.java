package app;

import java.io.*;

/**
 * @author Rodrigo Rosa
 */
public class MyAppIO {
    private App app;
    private String fileName;

    public MyAppIO(String theFileName) {
        app = null;
        load(theFileName);
        if (app == null)
            app = new AppImp();
    }

    public void load(String fileName) {
        try {
            ObjectInputStream file = new ObjectInputStream(new FileInputStream(fileName));

            // Compiler gives warning
            app = (App) file.readObject();
            file.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void store() {
        try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(fileName));
            file.writeObject(app);
            file.flush();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

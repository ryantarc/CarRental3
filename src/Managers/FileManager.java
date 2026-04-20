package Managers;


import java.util.ArrayList;
import java.io.*;
public class FileManager {
    //use generics <T> so that it can accept multiple arraylist types (car,reservation,report etcetc)

    public <T> void saveToFile(ArrayList<T> array,String filename){
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> ArrayList<T> loadFromFile(String filename) { //returns an arraylist of loaded objects
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            return (ArrayList<T>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}


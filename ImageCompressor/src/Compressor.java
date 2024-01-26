import java.awt.*;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Used for compressing an image with the help of RGB_Manager
public class Compressor {
    //Receives a color bi-dimensional array and the name of the file to compress
    //Produces a ".compressed" file with 2/3 the size of the original 24 bits bitmap.
    public static void compress(Color[][] pixelMatrix, String filename) throws IOException {
        int height = pixelMatrix.length;
        int width = pixelMatrix[0].length;

        //A list is created from the compressed colors of the matrix
        List<Short> shortList = GetColors(pixelMatrix);

        FileOutputStream fos = new FileOutputStream(filename);
        DataOutputStream dos = new DataOutputStream(fos);

        //The first 8 bytes of the file are used for saving the width and length of the original image
        dos.writeInt(width);
        dos.writeInt(height);

        //For each color on the list, 2 bytes of information are saved into the compressed file
        for (int i = 0; i < shortList.size(); i++) {
            dos.writeShort(shortList.get(i));
        }
        dos.close();
    }
    //Returns a list formed by the compressed values of a received bi-dimensional color array
    private static List<Short> GetColors(Color[][] pixelMatrix){
        List<Short> shortList = new ArrayList<>();
        for(int x = 0; x < pixelMatrix.length; x++){
            for(int y = 0; y < pixelMatrix[0].length; y++){
                Color c = pixelMatrix[x][y];

                int b = c.getBlue();
                int g = c.getGreen();
                int r = c.getRed();

                shortList.add(RGB_Manager.compressRGB(r,g,b));
            }
        }
        return shortList;
    }
}

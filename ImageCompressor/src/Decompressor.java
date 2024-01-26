import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//Used for decompressing a compressed file, generating a 24 bit lossy bitmap with the help of RGB_Manager
public class Decompressor {
    //In charge of generating the decompressed bitmap
    public static void decompress(String sourceFileName, String filename) throws IOException {
        File sourceFile = new File(sourceFileName);
        FileInputStream fis = new FileInputStream(sourceFile);
        DataInputStream dis = new DataInputStream(fis);

        /*As the first 8 bytes of the file are dedicated to width and height, these are the first
        bits that are read and saved*/
        int height = dis.readInt();
        int width = dis.readInt();

        BufferedImage decompressedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //Each pixel of the decompressed image is written through a nested cycle
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                //Since each color is saved into two bytes, a short is read for each color
                short binaryColors = dis.readShort();
                //RGB_Manager.decompressRGB() returns an array of integers
                //The integers are obtained from the saved binary values
                /*Since a proportion is used again after compressing (this time in reverse), some
                information is lost*/
                int[] colorsArray = RGB_Manager.decompressRGB(binaryColors);
                int r = colorsArray[0];
                int g = colorsArray[1];
                int b = colorsArray[2];
                Color colorRGB = new Color(r, g, b);
                decompressedImage.setRGB(x, y, colorRGB.getRGB());
            }
        }
        dis.close();
        try{
            ImageIO.write(decompressedImage, "bmp", new File(filename));
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
}

//In charge of compressing and decompressing RGB values.
public class RGB_Manager {
    //Compresses RGB values from 3 to 2 bytes using a proportion
    public static short compressRGB(int r, int g, int b){
        //Calls CrossMultiplication to get the appropriate proportion
        //5 bytes are used for each; red and blue
        //6 bytes are used for green since it is the color we are most sensible to
        //Since 31 is the max. value that can be represented with 5 bytes, a proportion between 255 and 31 is used
        //63 is the max. value that can be represented with 6 bytes
        int r2 = CrossMultiplication(255, r, 31);
        int g2 = CrossMultiplication(255, g, 63);
        int b2 = CrossMultiplication(255, b, 31);

        //A short variable stores 2 bytes: 1 short for one compressed color.
        short compressedRGB;

        //Red is the first value assigned
        compressedRGB = (short) r2;
        //The assigned red value is moved 6 bits to the left and green takes the 6 bits lef to the right
        compressedRGB = (short) ((compressedRGB << 6) | g2);
        //The assigned red and green values are moved 5 bits to the left, which are used for storing blue
        compressedRGB = (short) ((compressedRGB << 5) | b2);

        return compressedRGB;
    }
    //Returns an array of integers decompressed from a short value.
    //The array has three values: red, green and blue
    public static int[] decompressRGB(short color){
        int r2 = Short.toUnsignedInt(color);
        //Moving the bytes 11 bits leaves only the last 5 bits, which represent the red color
        r2 = (r2 >> 11);

        int g2 = Short.toUnsignedInt(color);
        //2016 = 0000011111100000 Leaving only 6 bits in the middle for the comparison to obtain the green color
        g2 = (g2 & 2016);
        g2 = (g2 >> 5);

        int b2 = Short.toUnsignedInt(color);
        //31 = 0000000000011111 Rendering the first 11 bytes useless through the comparison
        b2 = (b2 & 31);

        int r1 = CrossMultiplication(31, 255, r2);
        int g1 = CrossMultiplication(63, 255, g2);
        int b1 = CrossMultiplication(31, 255, b2);

        return new int[]{r1, g1, b1};
    }

    //Applies cross multiplication to get a proportionate value.
    public static int CrossMultiplication(int n1, int n2, int proportion1){
        return Math.round((float) n2 * proportion1 / n1);
    }
}

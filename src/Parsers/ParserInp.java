package Parsers;

import CAoT.Triangle;
import CAoT.Triangulation;
import CAoT.Vertex;
import java.io.*;
import java.util.*;

/**
 *
 * @author Алексей Евсеев
 */
public class ParserInp {
    public static Triangulation load (String filename) throws FileNotFoundException, IOException {
        // Open file
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
        }
        catch (IOException ex) {
            try {
                br.close();
            }
            catch (IOException ex2) {
            }
            br = null;
            throw ex;
        }

        //Reading file
        String s;
        String[] split;
        double x, y, z;
        int v1, v2, v3, index;
        boolean node = false, elements = false;
        ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
        ArrayList<Triangle> triangles = new ArrayList<Triangle>();
        try {
            while ((s = br.readLine()) != null) {
                if (!s.startsWith("**")) {
                    if (!node && s.startsWith("*NODE")) node = true;
                    else
                    if (!elements && s.startsWith("*ELEMENT, TYPE=S3R")) { node = false; elements = true; }
                    else
                    if (node) {
                        split = s.split(",");
                        if (split.length != 4) throw new IOException("Wrong number of arguments in node section.");
                        try {
                            index = Integer.parseInt(split[0].trim());
                            x = Double.parseDouble(split[1]);
                            y = Double.parseDouble(split[2]);
                            z = Double.parseDouble(split[3]);
                        }
                        catch (NumberFormatException ex) {
                            throw new IOException("Conversion error from string to number");
                        }
                        vertexes.add(new Vertex(x, y, z, index-1));
                        //System.out.println(s + " <--> " + index + " " + x +" "+y+" "+z);
                    }
                    else
                    if (elements) {
                        split = s.split(",");
                        if (split.length != 4) throw new IOException("Wrong number of arguments in elements section.");
                        try {
                            index = Integer.parseInt(split[0].trim());
                            v1 = Integer.parseInt(split[1].trim());
                            v2 = Integer.parseInt(split[2].trim());
                            v3 = Integer.parseInt(split[3].trim());
                        }
                        catch (NumberFormatException ex) {
                            throw new IOException("Conversion error from string to number");
                        }
                        triangles.add(new Triangle(vertexes.get(v1 - 1), vertexes.get(v2 - 1), vertexes.get(v3 - 1), index-1));
                        //System.out.println(s + " <--> " + index + " " + v1 +" "+v2+" "+v3);
                    }
                }
            }

        }
        catch (IOException ex) {
            try {
                br.close();
            }
            catch (IOException ex2) {
            }
            br = null;
            throw ex;            
        }

        //Close file
        if (br != null) {
            try {
                br.close();
            }
            catch (IOException ex) { }
        }
        if (!elements) throw new IOException("File have bad format");

        return new Triangulation (vertexes, triangles);
    }
}

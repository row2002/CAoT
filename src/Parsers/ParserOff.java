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
public class ParserOff {
    public static Triangulation load (String filename) throws IOException, FileNotFoundException {
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
        int v1, v2, v3, index, num, vertexesCount = 0, trianglesCount = 0;
        boolean node = true, elements = false;
        ArrayList<Vertex> vertexes;
        ArrayList<Triangle> triangles;

        try {
            s = br.readLine();
            if (s == null || !s.equals("OFF")) throw new IOException("The file must begin with the string \"OFF\".");

            while ((s = br.readLine()) != null) {
                if (!s.startsWith("#")) {
                    split = s.split(" ");
                    if (split.length != 3) throw new IOException("Wrong number of arguments in the line containing the number of vertices and triangles.");
                    try {
                        vertexesCount  = Integer.parseInt(split[0].trim());
                        trianglesCount = Integer.parseInt(split[1].trim());
                        index          = Integer.parseInt(split[2].trim());

                        break;
                    }
                    catch (NumberFormatException ex) {
                        throw new IOException("Conversion error from string to number");
                    }
                }
            }

            vertexes = new ArrayList<Vertex>(vertexesCount);
            triangles = new ArrayList<Triangle>(trianglesCount);
            index = -1;
            while ((s = br.readLine()) != null) {
                if (!s.startsWith("#")) {
                    if (node && index == (vertexesCount-1)) {elements = true; node = false; index = -1;}
                    if (node) {
                        split = s.split(" ");
                        if (split.length != 3) throw new IOException("Wrong number of arguments in node section. Line "+index+".");
                        try {
                            x = Double.parseDouble(split[0]);
                            y = Double.parseDouble(split[1]);
                            z = Double.parseDouble(split[2]);
                            index++;
                        }
                        catch (NumberFormatException ex) {
                            throw new IOException("Conversion error from string to number");
                        }
                        vertexes.add(new Vertex(x, y, z, index));
                        //System.out.println(s + " <--> " + index + " " + x +" "+y+" "+z);
                    }
                    else
                    if (elements) {
                        split = s.split(" ");
                        if (split.length != 4) throw new IOException("Wrong number of arguments in elements section. Line "+index+".");
                        try {
                            num = Integer.parseInt(split[0].trim());
                            if (num != 3) throw new IOException("Supported only triangulation");
                            v1  = Integer.parseInt(split[1].trim());
                            v2  = Integer.parseInt(split[2].trim());
                            v3  = Integer.parseInt(split[3].trim());
                            index++;
                        }
                        catch (NumberFormatException ex) {
                            throw new IOException("Conversion error from string to number");
                        }
                        triangles.add(new Triangle(vertexes.get(v1), vertexes.get(v2), vertexes.get(v3), index));
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
            catch (IOException ex) {}
        }
        if (!elements) throw new IOException("File have bad format");

        return new Triangulation (vertexes, triangles);

    }
}

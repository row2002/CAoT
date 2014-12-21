package Visualization;

import CAoT.*;
import Enums.PresentedType;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author Алексей Евсеев
 */
public class Visualization {

    private Canvas3D canvas;
    private SimpleUniverse su;
    private BranchGroup sceneBG;
    private BoundingSphere bounds;
    private boolean is3D = false;
    private Point3d max = new Point3d();
    private Point3d min = new Point3d();

    private CellularArray cellularArray;
    private Colors colorSettings;
    private static GridVisualization gridVisualization;
    private TimeBehavior timeBehavior;

    private Screen3D on, off;
    private Canvas3D shot;

    public Visualization() {
        if (hasJ3D()) {
            GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            canvas = new Canvas3D(config);
//            canvas.setFocusable(true);
//            canvas.requestFocus();
            su = new SimpleUniverse(canvas);

            //For taking Snapshot
            on = canvas.getScreen3D();

            shot = new Canvas3D(canvas.getGraphicsConfiguration(), true);
            //canvas.getView().stopView();
            //canvas.getView().addCanvas3D(shot);
            //canvas.getView().startView();

            off = shot.getScreen3D();
            off.setSize(on.getSize());
            off.setPhysicalScreenHeight(on.getPhysicalScreenHeight());
            off.setPhysicalScreenWidth(on.getPhysicalScreenWidth());
            shot.setOffScreenLocation(canvas.getLocationOnScreen());
        }
        else {
            System.out.println("Java3D not installed");
            System.out.println("Visit https://java3d.dev.java.net/");
            System.exit(1);
        }
    }

    private boolean hasJ3D() {
        try {
            Class.forName("com.sun.j3d.utils.universe.SimpleUniverse");
            return true;
        }
        catch(ClassNotFoundException e) {
            System.err.println("Java 3D not installed");
            return false;
        }
    }
    public Canvas3D getCanvas() {
        return canvas;
    }
    public void createScene(CellularArray cellularArray, Colors colorSettings) {
        this.cellularArray = cellularArray;
        this.colorSettings = colorSettings;

        Triangulation tr = cellularArray.getTriangulation();
        max.set(tr.getVertex(0));
        min.set(tr.getVertex(0));
        for (int i=1; i< tr.getVertexCount(); i++) {
            if (!is3D && Math.abs(tr.getVertex(i).z) > 1e-7) is3D = true;
            //max coords
            if (tr.getVertex(i).x > max.x) max.x = tr.getVertex(i).x;
            if (tr.getVertex(i).y > max.y) max.y = tr.getVertex(i).y;
            if (tr.getVertex(i).z > max.z) max.z = tr.getVertex(i).z;
            //min coords
            if (tr.getVertex(i).x < min.x) min.x = tr.getVertex(i).x;
            if (tr.getVertex(i).y < min.y) min.y = tr.getVertex(i).y;
            if (tr.getVertex(i).z < min.z) min.z = tr.getVertex(i).z;
        }

        createSceneGraph();

        initUserPosition();
        orbitControls();

        // depth-sort transparent objects on a per-geometry basis
//        View view = su.getViewer().getView();
//        view.setTransparencySortingPolicy(View.TRANSPARENCY_SORT_GEOMETRY);

        su.addBranchGraph(sceneBG);
    }

    private void initUserPosition() {
        Point3d userPos = new Point3d(-2, 5, 10);
        if (!is3D) userPos.set(0, 0, -10);

        ViewingPlatform vp = su.getViewingPlatform();
        TransformGroup steerTG = vp.getViewPlatformTransform();
        Transform3D t3d = new Transform3D( );
        steerTG.getTransform( t3d );
        t3d.lookAt( userPos, new Point3d(0,0,0), new Vector3d(0,1,0));
        // args are: viewer posn, where looking, up direction
        t3d.invert();
        steerTG.setTransform(t3d);
    }
    private void orbitControls() {
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds);
        ViewingPlatform vp = su.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
    }
    private void createSceneGraph() {
        sceneBG = new BranchGroup();
        double boundSize = Math.max(Math.abs(max.x - min.x), Math.max(Math.abs(max.y-min.y), Math.abs(max.z-min.z)));
        bounds = new BoundingSphere(new Point3d(0, 0, 0), boundSize);

        //lightScene();
        addBackground();
        addGrid();
        autoScale();

        sceneBG.compile();
    }
    private void lightScene() {
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

        AmbientLight ambientLightNode = new AmbientLight(white);
        ambientLightNode.setInfluencingBounds(bounds);
        sceneBG.addChild(ambientLightNode);

        // Set up the directional lights
        Vector3f light1Direction = new Vector3f(-1.0f, 1.0f, -1.0f);
        // light coming from left, up, and back quadrant
        Vector3f light2Direction = new Vector3f(1.0f, 1.0f, 1.0f);
        // light coming from right, up, and front quadrant
        DirectionalLight light1 = new DirectionalLight(white, light1Direction);
        light1.setInfluencingBounds(bounds);
        sceneBG.addChild(light1);
        DirectionalLight light2 = new DirectionalLight(white, light2Direction);
        light2.setInfluencingBounds(bounds);
        sceneBG.addChild(light2);
    }
    private void addBackground() {
        Background back = new Background();
        back.setApplicationBounds( bounds );
        back.setColor(0.95f, 0.95f, 0.95f);
        //back.setColor(0.f, 0.f, 0.f);
        sceneBG.addChild( back );
    }
    private void addGrid() {
        gridVisualization = new GridVisualization(cellularArray, colorSettings);
        sceneBG.addChild(gridVisualization.getBaseTG());

        timeBehavior = new TimeBehavior(200, this);
        timeBehavior.setSchedulingBounds(bounds);
        timeBehavior.setEnable(false);
        sceneBG.addChild(timeBehavior);
    }
    public void update() {
        gridVisualization.update();
    }
    public void doSnapshot(String filename) {
       BufferedImage myImage = getBufImage();
       try {
         OutputStream out = new FileOutputStream(filename);
         javax.imageio.ImageIO.write(myImage, "png", out);
         //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
         //encoder.encode(myImage);
         out.close();
       }
       catch (Exception e) {
         System.out.println(e);
       }
    }
    private BufferedImage getBufImage(){
        canvas.getView().stopView();
        canvas.getView().addCanvas3D(shot);
        canvas.getView().startView();

        BufferedImage bi = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ImageComponent2D buffer = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bi);
        //BufferedImage bi = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        //ImageComponent2D buffer = new ImageComponent2D(ImageComponent.FORMAT_RGB, bi);
        shot.setOffScreenBuffer(buffer);
        shot.renderOffScreenBuffer();
        shot.waitForOffScreenRendering();
        BufferedImage res = shot.getOffScreenBuffer().getImage();
        canvas.getView().removeCanvas3D(shot);
        return res;
    }
    private void autoScale() {
        Transform3D autoScale = new Transform3D();

        // scale
        double screen = 6.583333333333333333333; // Magic number for my notebook
        //double screenRadius = 5.0f * Math.tan(su.getViewer().getView().getFieldOfView() / 2.0);
        //!!!!! Mine formula:  bounds * (1/scale) ----> screen
        double scaleValue = 1./(bounds.getRadius()/screen);
        autoScale.setScale(scaleValue);
        // translate
        double translateX, translateY, translateZ;
        translateX = -(max.x-min.x)/2;
        translateY = -(max.y-min.y)/2;
        translateZ = -(max.z-min.z)/2;
        translateX *= scaleValue; translateY *= scaleValue; translateZ *= scaleValue;

        Vector3d v = new Vector3d(translateX, translateY, translateZ);
        autoScale.setTranslation(v);

        gridVisualization.getBaseTG().setTransform(autoScale);
    }
    public void start() {
        timeBehavior.setEnable(true);
    }
    public void stop() {
        timeBehavior.setEnable(false);
    }
    public void setPresentedType(PresentedType presentedType) {
        gridVisualization.setPresentedType(presentedType);
    }
    public void setColorSettings(Colors colorSettings) {
        this.colorSettings = colorSettings;
        gridVisualization.setColorSettings(colorSettings);
    }
}

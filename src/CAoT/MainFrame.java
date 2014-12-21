/*
 * MainFrame.java
 *
 * Created on 21.08.2010, 0:21:53
 */

package CAoT;

import Automats.*;
import Enums.PresentedType;
import Parsers.*;
import Visualization.Visualization;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Алексей
 */
public class MainFrame extends javax.swing.JFrame {
    
    private String filename;
    private boolean init = true; //initialization status
    private static int selectedCA = 0;
    private static Triangulation triangulation;
    private static CellularArray cellularArray;
    private static CellularArray cellularArrayCopy;
    public static int iterationsCounter = 0;
    private static PresentedType presentedType = PresentedType.booleanValues;

    // Automats
    private CADiffusion caDiffusion;
    private CADLA caDLA;
    private CAFrontPropagation caFrontPropagation;
    private CAPattern caPattern;
    private CAPatternByCircles caPatternByCircles;
    private CAMargolusDiffusion caMargolusDiffusion;
    private CAGameOfLife caGameOfLife;

    private static ArrayList<Automaton> automats = new ArrayList<Automaton>(4);
    private DefaultComboBoxModel caNames = new DefaultComboBoxModel();

    private Visualization visualization;

    /** Creates new form MainFrame */
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add canvas on jPanel
        visualization = new Visualization();
        jPanel1.add(visualization.getCanvas(), BorderLayout.CENTER);
}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfFilename = new javax.swing.JTextField();
        bBrowse = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        bParse = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jComboBox1 = new javax.swing.JComboBox();
        bInitialState = new javax.swing.JButton();
        bClear = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        rbBoolean = new javax.swing.JRadioButton();
        rbAveragedValues = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        bStart = new javax.swing.JButton();
        bSingleStep = new javax.swing.JButton();
        bStop = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        bProperties = new javax.swing.JButton();
        tfIterations = new javax.swing.JTextField();
        bUpdate = new javax.swing.JButton();
        bSnapshot = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cellular Automata on Triangulation");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 16));
        jLabel1.setText("Select file");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14));
        jLabel2.setText("Your file:");

        tfFilename.setEditable(false);
        tfFilename.setFont(new java.awt.Font("Verdana", 0, 14));

        bBrowse.setFont(new java.awt.Font("Verdana", 0, 11));
        bBrowse.setText("Browse...");
        bBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBrowseActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 14));
        jLabel3.setText("Parse Your File:");

        bParse.setFont(new java.awt.Font("Verdana", 0, 11));
        bParse.setText("Parse");
        bParse.setEnabled(false);
        bParse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bParseActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Verdana", 0, 12));
        jComboBox1.setModel(caNames);
        jComboBox1.setEnabled(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        bInitialState.setFont(new java.awt.Font("Verdana", 0, 11));
        bInitialState.setText("Initial State");
        bInitialState.setEnabled(false);
        bInitialState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInitialStateActionPerformed(evt);
            }
        });

        bClear.setFont(new java.awt.Font("Verdana", 0, 11));
        bClear.setText(" Clear ");
        bClear.setEnabled(false);
        bClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 14));
        jLabel4.setText("Type of values:");

        buttonGroup1.add(rbBoolean);
        rbBoolean.setFont(new java.awt.Font("Verdana", 0, 12));
        rbBoolean.setSelected(true);
        rbBoolean.setText("Boolean");
        rbBoolean.setEnabled(false);
        rbBoolean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbBooleanActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbAveragedValues);
        rbAveragedValues.setFont(new java.awt.Font("Verdana", 0, 12));
        rbAveragedValues.setText("Averaged Values");
        rbAveragedValues.setEnabled(false);
        rbAveragedValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAveragedValuesActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 14));
        jLabel5.setText("Iterations:");

        bStart.setFont(new java.awt.Font("Verdana", 0, 11));
        bStart.setText("Start");
        bStart.setEnabled(false);
        bStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStartActionPerformed(evt);
            }
        });

        bSingleStep.setFont(new java.awt.Font("Verdana", 0, 11));
        bSingleStep.setText("Single Step");
        bSingleStep.setEnabled(false);
        bSingleStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSingleStepActionPerformed(evt);
            }
        });

        bStop.setFont(new java.awt.Font("Verdana", 0, 11));
        bStop.setText("Stop");
        bStop.setEnabled(false);
        bStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStopActionPerformed(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(600, 600));
        jPanel1.setLayout(new java.awt.BorderLayout());

        bProperties.setFont(new java.awt.Font("Verdana", 0, 11));
        bProperties.setText("Properties");
        bProperties.setEnabled(false);
        bProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPropertiesActionPerformed(evt);
            }
        });

        tfIterations.setFont(new java.awt.Font("Verdana", 0, 14));
        tfIterations.setText("0");

        bUpdate.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        bUpdate.setText("Update");
        bUpdate.setEnabled(false);
        bUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateActionPerformed(evt);
            }
        });

        bSnapshot.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        bSnapshot.setText("Snapshot");
        bSnapshot.setEnabled(false);
        bSnapshot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSnapshotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbAveragedValues)
                    .addComponent(rbBoolean)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfFilename, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bBrowse))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bParse, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bInitialState)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bProperties))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfIterations, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox1, 0, 317, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(bUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bStop, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSingleStep, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bStart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                        .addComponent(bSnapshot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(4, 4, 4))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(bBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfFilename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(bParse))
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bInitialState)
                    .addComponent(bClear)
                    .addComponent(bProperties))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(14, 14, 14)
                .addComponent(rbBoolean)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbAveragedValues)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(tfIterations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(bStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bSingleStep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bStop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bSnapshot)
                .addContainerGap(93, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBrowseActionPerformed
        jProgressBar1.setValue(0);
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File f) {
                return (f.isDirectory() || f.getName().endsWith(".inp") || f.getName().endsWith(".off"));
            }

            @Override
            public String getDescription() {
                return "INP and OFF files";
            }
        };

        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Select file with triangulation");

        int returnVal = chooser.showOpenDialog(bBrowse);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getAbsolutePath();
            tfFilename.setText(chooser.getSelectedFile().getName());
            bParse.setEnabled(true);
        }
    }//GEN-LAST:event_bBrowseActionPerformed

    private void bParseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bParseActionPerformed
        File f = new File(filename);
        if (!f.exists()) {
            JOptionPane.showMessageDialog(null, "File not found\n" , "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        if (!(filename.endsWith(".inp") || filename.endsWith(".off"))) {
            JOptionPane.showMessageDialog(null, "Unsupported file format\n" , "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }

        jProgressBar1.setIndeterminate(true);
        System.out.println("Loading triangulation...");
        try {
            if (filename.endsWith(".inp")) triangulation = ParserInp.load(filename);
            else
            if (filename.endsWith(".off")) triangulation = ParserOff.load(filename);
        }
        catch (FileNotFoundException ex) {
            jProgressBar1.setIndeterminate(false);
            jProgressBar1.setValue(0);
            System.out.println("Error: file "+filename+" not found");
            JOptionPane.showMessageDialog(null, "File not found\n" , "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        catch (IOException ex) {
            jProgressBar1.setIndeterminate(false);
            jProgressBar1.setValue(0);
            System.out.println("Error during reading file");
            if (!ex.getMessage().isEmpty()) System.out.println("Details: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error during reading file\n" , "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }

        bBrowse.setEnabled(false);
        bParse.setEnabled(false);
        System.out.println("Calculating Neighbors...");
        triangulation.calculateNeighbors();
        System.out.println("Filling Triangle Chooser...");
        triangulation.fillTriangleChooser();
        System.out.println("Creating Cellular Automaton...");
        cellularArray = new CellularArray(triangulation);
        System.out.println("Making Copy of Cellular Automaton...");
        cellularArrayCopy = cellularArray.copy();
        System.out.println("Initialization...");
        System.gc();
        initialization();
        jProgressBar1.setIndeterminate(false);
        jProgressBar1.setValue(100);
        System.out.println("All Done");
    }//GEN-LAST:event_bParseActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        selectedCA = jComboBox1.getSelectedIndex();
        if (!automats.get(selectedCA).canBeAveraged())
            rbAveragedValues.setEnabled(false);
        else
            rbAveragedValues.setEnabled(true);
        rbBoolean.setSelected(true);
        presentedType = PresentedType.booleanValues;

        if (!automats.get(selectedCA).haveProperties())
            bProperties.setEnabled(false);
        else
            bProperties.setEnabled(true);

        if (!init) {
            visualization.setColorSettings(automats.get(selectedCA).getColorSettings());
            visualization.setPresentedType(presentedType);
            visualization.update();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void bStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStartActionPerformed
        bStop.setEnabled(true);
        jComboBox1.setEnabled(false);
        bSingleStep.setEnabled(false);
        bInitialState.setEnabled(false);
        bClear.setEnabled(false);
        bProperties.setEnabled(false);
        bStart.setEnabled(false);
        jProgressBar1.setIndeterminate(true);
        visualization.start();
    }//GEN-LAST:event_bStartActionPerformed

    private void bStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStopActionPerformed
        jComboBox1.setEnabled(true);
        bSingleStep.setEnabled(true);
        bInitialState.setEnabled(true);
        bClear.setEnabled(true);
        bStop.setEnabled(false);
        if (automats.get(selectedCA).haveProperties()) bProperties.setEnabled(true);
        bStart.setEnabled(true);
        jProgressBar1.setIndeterminate(false);
        visualization.stop();
    }//GEN-LAST:event_bStopActionPerformed

    private void bInitialStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInitialStateActionPerformed
        cellularArray.clear();
        cellularArrayCopy.clear();
        automats.get(selectedCA).initialState();
        average();
        iterationsCounter = 0;
        tfIterations.setText(Integer.toString(iterationsCounter));
        visualization.update();
    }//GEN-LAST:event_bInitialStateActionPerformed

    private void bSingleStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSingleStepActionPerformed
        CAOperation();
        average();
        visualization.update();
    }//GEN-LAST:event_bSingleStepActionPerformed

    private void bClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearActionPerformed
        cellularArray.clear();
        cellularArrayCopy.clear();
        visualization.update();
        iterationsCounter = 0;
        tfIterations.setText(Integer.toString(iterationsCounter));
    }//GEN-LAST:event_bClearActionPerformed

    private void rbAveragedValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAveragedValuesActionPerformed
        presentedType = PresentedType.averagedValues;
        visualization.setPresentedType(PresentedType.averagedValues);
        average();
        visualization.update();
    }//GEN-LAST:event_rbAveragedValuesActionPerformed

    private void rbBooleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbBooleanActionPerformed
        presentedType = PresentedType.booleanValues;
        visualization.setPresentedType(presentedType);
        visualization.update();
    }//GEN-LAST:event_rbBooleanActionPerformed

    private void bPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPropertiesActionPerformed
        if (automats.get(selectedCA).haveProperties()) {
            if (automats.get(selectedCA).showProperties()) visualization.update();
        }
    }//GEN-LAST:event_bPropertiesActionPerformed

    private void bUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateActionPerformed
        visualization.update();
    }//GEN-LAST:event_bUpdateActionPerformed

    private void bSnapshotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSnapshotActionPerformed
        StringBuilder sb = new StringBuilder();
        sb.append("../Snapshots/");
        sb.append(automats.get(selectedCA).getName()).append(" ");
        sb.append(iterationsCounter).append(".png");
        visualization.doSnapshot(sb.toString());
    }//GEN-LAST:event_bSnapshotActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBrowse;
    private javax.swing.JButton bClear;
    private javax.swing.JButton bInitialState;
    private javax.swing.JButton bParse;
    private javax.swing.JButton bProperties;
    private javax.swing.JButton bSingleStep;
    private javax.swing.JButton bSnapshot;
    private javax.swing.JButton bStart;
    private javax.swing.JButton bStop;
    private javax.swing.JButton bUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rbAveragedValues;
    private javax.swing.JRadioButton rbBoolean;
    private javax.swing.JTextField tfFilename;
    private static javax.swing.JTextField tfIterations;
    // End of variables declaration//GEN-END:variables

    public static void CAOperation() {
        Automaton automaton = automats.get(selectedCA);
        int nextRandomTriangle;

        switch (automaton.getModeOperation()) {
            case asynchronous:
                for (int i=0; i<triangulation.getTrianglesCount(); i++) {
                    nextRandomTriangle = triangulation.getNextRandomTriangle();
                    automaton.rule(cellularArray.getCell(nextRandomTriangle));
                }
            break;

            case other:
                automaton.rule(null);
            break;

            case asynchronous_all:
                for (int i=0; i<triangulation.getTrianglesCount(); i++) {
                    nextRandomTriangle = (int) (Math.random()*triangulation.getTrianglesCount());
                    automaton.rule(cellularArray.getCell(nextRandomTriangle));
                }
            break;

            case synchronous:
                for (int i=0; i<triangulation.getTrianglesCount(); i++) {
                    automaton.rule(cellularArray.getCell(i)); //changes cellularArrayCopy, gettin data from cellularArray
                }
                cellularArray.copy(cellularArrayCopy);
            break;
        }
        iterationsCounter++;
        tfIterations.setText(Integer.toString(iterationsCounter));
    }
    public static void average() {
        if (presentedType == PresentedType.averagedValues) automats.get(selectedCA).average();
    }

    private void initialization() {
        caDiffusion = new CADiffusion(cellularArray);
        caMargolusDiffusion = new CAMargolusDiffusion(cellularArray);
        caFrontPropagation = new CAFrontPropagation(cellularArray);
        caDLA = new CADLA(cellularArray);
        //caPattern = new CAPattern(cellularArray, cellularArrayCopy, 1., 1., 0.5, -0.5, -0.3);
        caPattern = new CAPattern(cellularArray, cellularArrayCopy, 2., 1., 1., 0., -1.);
        //caPattern = new CAPattern(cellularArray, cellularArrayCopy, 1., 1., 1., -2.5);
        caPatternByCircles = new CAPatternByCircles(cellularArray, cellularArrayCopy);
        caPatternByCircles.setLevel(1, 10., 1.); caPatternByCircles.setLevel(2, 20., -0.5);
        caPatternByCircles.setLevel(3, 30., -0.2);
        
        int weightsLive[] = {2, 4};
        int weightsBorn[] = {4, 6};
        caGameOfLife = new CAGameOfLife(cellularArray, cellularArrayCopy, weightsLive, weightsBorn);

        automats.add(caDiffusion);
        automats.add(caFrontPropagation);
        automats.add(caDLA);
        automats.add(caPattern);
        automats.add(caPatternByCircles);
        automats.add(caMargolusDiffusion);
        automats.add(caGameOfLife);

        for (Automaton a: automats)
            caNames.addElement(a.getName());

        // Generating initial state
        automats.get(selectedCA).initialState();

        // Creating scene
        visualization.createScene(cellularArray, automats.get(selectedCA).getColorSettings());
        
        jComboBox1.setEnabled(true);
        bInitialState.setEnabled(true);
        bClear.setEnabled(true);
        rbAveragedValues.setEnabled(true);
        rbBoolean.setEnabled(true);
        bStart.setEnabled(true);
        bSingleStep.setEnabled(true);
        bProperties.setEnabled(true);
        bUpdate.setEnabled(true);
        bSnapshot.setEnabled(true);

        // Putting focus on Start button
        bStart.requestFocus();

        // End of initialization block
        init = false;
    }

}
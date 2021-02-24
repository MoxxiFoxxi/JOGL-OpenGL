/*
* Author: Jessie Romero 
* Filename: Shapes.java
* Date: 12 July 2020
* Sources: Code template used provided by instructor
* Color Codes: community.khronos.org
*/


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Main extends GLJPanel implements GLEventListener, KeyListener
{

    // Rotation along the X axis
    private double rX = 90; 
    // Rotation along the Y axis
    private double rY = 90; 
    // Rotation along the Z axis
    private double rZ = 0; 
    // Translation along the X axis
    private double tX = 0; 
    // Translation along the Y axis
    private double tY = 0; 
    // Translation along the Z axis
    private double tZ = 0; 
    // Scale shape
    private double scale = 1;

    public Main()
    {
        super(new GLCapabilities(null));
        // Size of Scene, it is set to 720p
        setPreferredSize(new Dimension(1280, 720));
        addGLEventListener(this);
        addKeyListener(this);
    }

    public static void main(String[] args) 
    {
        // Title of frame
        JFrame window = new JFrame("CMSC 405 Project 2");
        Main panel = new Main();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(50, 50);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
    }

    private void drawObj(GL2 gl2, Shapes shape, double scale, double x, double y, double z) 
    {
        gl2.glPushMatrix();
        gl2.glScaled(scale, scale, scale);  
        gl2.glTranslated(x, y, z);

        for (int i = 0; i < shape.faces.length; i++) 
        {
            gl2.glPushMatrix();
            double r = shape.rgb[i][0];
            double g = shape.rgb[i][1];
            double b = shape.rgb[i][2];

            // Build faces of shapes
            gl2.glColor3d(r, g, b);
            gl2.glBegin(GL2.GL_TRIANGLE_FAN);
            for (int j = 0; j < shape.faces[i].length; j++) 
            {
                int v = shape.faces[i][j];
                gl2.glVertex3dv(shape.vertices[v], 0);
            }
            gl2.glEnd();

            // Outlines shapes
            gl2.glColor3d(0, 0, 0);
            gl2.glBegin(GL2.GL_LINE_LOOP);
            for (int j = 0; j < shape.faces[i].length; j++) 
            {
                int v = shape.faces[i][j];
                gl2.glVertex3dv(shape.vertices[v], 0);
            }
            gl2.glEnd();
            gl2.glPopMatrix();
        }
        gl2.glPopMatrix();
    }

    // Input for shape transformation
    public void keyTyped(KeyEvent e) 
    {
    }

    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) 
            rY -= 20;

        else if (key == KeyEvent.VK_RIGHT) 
            rY += 20;

        else if (key == KeyEvent.VK_DOWN) 
            rX -= 20;

        else if (key == KeyEvent.VK_UP) 
            rX += 20;

        else if (key == KeyEvent.VK_PLUS || key == KeyEvent.VK_EQUALS) 
            scale += .2;

        else if (key == KeyEvent.VK_MINUS || key == KeyEvent.VK_UNDERSCORE) 
            scale -= .2;

        else if (key == KeyEvent.VK_W) 
            tY += .2;

        else if (key == KeyEvent.VK_A) 
            tX -= .2;

        else if (key == KeyEvent.VK_S) 
            tY -= .2;

        else if (key == KeyEvent.VK_D) 
            tX += .2;

        else if (key == KeyEvent.VK_HOME || key == KeyEvent.VK_SPACE) 
        {
            rX = rY = 60;
            rZ = tX = tY = tZ = 0;
            scale = 1.5;
        }

        else
            JOptionPane.showMessageDialog(this, "Following inputs are accepted: ← → ↓ ↑ W A S D + -", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        repaint();
    }

    public void keyReleased(KeyEvent e) 
    {
    }
    
    public void display(GLAutoDrawable toDraw) 
    {
        
        GL2 gl2 = toDraw.getGL().getGL2();

        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl2.glLoadIdentity();
        // Rotat on Z axis
        gl2.glRotated(rZ, 0, 0, 1);           
        // Rotate on Y axis
        gl2.glRotated(rY, 0, 1, 0);            
        // Rotate on X axis
        gl2.glRotated(rX, 1, 0, 0);            
        // Scale scene
        gl2.glScaled(scale, scale, scale);
        gl2.glTranslated(tX, tY, tZ);
        // Background Color
        gl2.glClearColor(1f,1f,1f,1); 
        // Draw Objects
        drawObj(gl2, Shapes.tetragonalGem, 0.125, 0, -0.05, 0);
        drawObj(gl2, Shapes.diamondShape, 0.125, 0, 1.5, 0);
        drawObj(gl2, Shapes.cubicSquare, 0.125, 1.5, 1.5, 1.5);
        drawObj(gl2, Shapes.goldIcosahedron, 0.125, -1.5, 1.5, -1.5);
        drawObj(gl2, Shapes.hexagonalGem, 0.125, 1.5, 1.5, -1.5);
        drawObj(gl2, Shapes.triclinicGem, 0.125, -1.5, 1.5, 1.5);

    }

    public void dispose(GLAutoDrawable arg0) 
    {
    }

    public void init(GLAutoDrawable drawable) 
    {

        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-1, 1, -1, 1, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor(0, 0, 0, 1);
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glLineWidth(2);

    }

    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) 
    {
    }	
}
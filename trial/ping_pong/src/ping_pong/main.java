package ping_pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main extends JFrame {

private static final long serialVersionUID = 1L; // Eclipse added this automatically

private JPanel jContentPane = null;

private PanelPelota panel = null; // This is the panel of the game class

private PanelPelota getPanel() {
if (panel == null) {
panel = new PanelPelota(); // The panel is created
}
return panel;
}

/**
* This is the default constructor
*/
public main() {
super();
initialize();
// Listeners for the keyboard
this.addKeyListener(new KeyAdapter() {
//Method for the key pressed
public void keyPressed(KeyEvent evt) {
formKeyPressed(evt);
}
// Method for the key released
public void keyReleased(KeyEvent evt) {
formKeyReleased(evt);
}
});

}

// Here i'm stating the method that will send the key pressed to the game class
private void formKeyPressed(KeyEvent evt)
{
panel.keyPressed(evt);
}

// Here i'm stating the method that will send the key released to the game class
private void formKeyReleased(KeyEvent evt)
{
panel.keyReleased(evt);
}

/**
* This method initializes this
*
* @return void
*/
private void initialize() {
this.setResizable(false);
this.setBounds(new Rectangle(312, 184, 250, 250)); // Position on the desktop
this.setMinimumSize(new Dimension(250, 250));
this.setMaximumSize(new Dimension(250, 250));
this.setContentPane(getJContentPane());
this.setTitle("Pong");
}

/**
* This method initializes jContentPane
*
* @return javax.swing.JPanel
*/
private JPanel getJContentPane() {
if (jContentPane == null) {
jContentPane = new JPanel();
jContentPane.setLayout(new BorderLayout());
jContentPane.add(getPanel(), BorderLayout.CENTER);
}
return jContentPane;
}

public static void main(String[] args) {
// TODO Auto-generated method stub
SwingUtilities.invokeLater(new Runnable() {
public void run() {
main thisClass = new main();
thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
thisClass.setVisible(true);
}
});
}
}

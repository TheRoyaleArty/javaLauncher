package fr.thearty;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

public class LauncherFrame extends JFrame {
	  private static LauncherFrame instance;
	  
	  private LauncherPanel LauncherPanel;
	  
	  public LauncherFrame() {
	    setTitle("Tryallium Launcher");
	    setSize(755, 490);
	    setDefaultCloseOperation(3);
	    setLocationRelativeTo((Component)null);
	    setUndecorated(true);
	    setIconImage(Swinger.getResource("logo.png"));
	    setContentPane(this.setLauncherPanel(new LauncherPanel()));
	    WindowMover mover = new WindowMover(this);
	    addMouseListener((MouseListener)mover);
	    addMouseMotionListener((MouseMotionListener)mover);
		setVisible(true);
		JFrame
	  }
	  
	  public static void main(String[] args) {
	    if (hasJava64Bits()) {
	      System.out.println("64");
	    } else {
	      System.out.println("32");
	    } 
	    Swinger.setSystemLookNFeel();
	    Swinger.setResourcePath("/fr/thearty/ressources");
	    Launcher.TR_CRASHS_DIR.mkdirs();
	    instance = new LauncherFrame();
	  }
	  
	  private static boolean hasJava64Bits() {
	    return System.getProperty("sun.arch.data.model").contains("64");
	  }
	  
	  public static LauncherFrame getInstance() {
	    return instance;
	  }
	  
	  public LauncherPanel getLauncherPanel() {
	    return this.LauncherPanel;
	  }

	public LauncherPanel setLauncherPanel(LauncherPanel launcherPanel) {
		LauncherPanel = launcherPanel;
		return launcherPanel;
	}
	}



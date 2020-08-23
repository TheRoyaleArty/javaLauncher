package fr.thearty;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import re.alwyn974.openlauncherlib.LaunchException;
import re.alwyn974.openlauncherlib.util.Saver;
import re.alwyn974.openlauncherlib.util.ramselector.RamSelector;

public class LauncherPanel extends JPanel implements SwingerEventListener {
	  private Image background = Swinger.getResource("background.png");
	  
	  private Saver saver = new Saver(new File(Launcher.TR_DIR, "launcher.properties, rame.txt"));
	  
	  private JTextField usernameField = new JTextField(this.saver.get("username"));
	  
	  private JPasswordField passwordField = new JPasswordField();
	  
	  private STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"));
	  
	  private STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit.png"));
	  
	  private STexturedButton hideButton = new STexturedButton(Swinger.getResource("hide.png"));
	  
	  private STexturedButton ramButton = new STexturedButton(Swinger.getResource("rambuton.png"));
	  
	  private STexturedButton infos = new STexturedButton(Swinger.getResource("discord.png"));
	  
	  private STexturedButton hidepassword = new STexturedButton(Swinger.getResource("eye.png"));
	  
	  private STexturedButton hidenpassword = new STexturedButton(Swinger.getResource("disableeye.png"));
	  
	  private RamSelector ramSelector = new RamSelector(new File(Launcher.TR_DIR, "ram.txt"));
	  
	  private SColoredBar progressBar = new SColoredBar(Swinger.getTransparentWhite(175));
	  
	  private JLabel infoLabel = new JLabel("");

	  private JOptionPane test = new JOptionPane("");

	  
	  public LauncherPanel() {
	    setLayout((LayoutManager)null);
	    this.infos.setBounds(55, 325, 64, 64);
	    this.infos.addEventListener(this);
	    add((Component)this.infos);
	    this.hidepassword.setBounds(695, 265, 20, 20);
	    this.hidepassword.addEventListener(this);
	    add((Component)this.hidepassword);
	    this.hidenpassword.setBounds(690, 290, 30, 30);
	    this.hidenpassword.addEventListener(this);
	    add((Component)this.hidenpassword);
	    this.usernameField.setHorizontalAlignment(0);
	    this.usernameField.setForeground(Color.WHITE);
	    this.usernameField.setFont(this.usernameField.getFont().deriveFont(25F));
	    this.usernameField.setOpaque(false);
	    this.usernameField.setBorder((Border)null);
	    this.usernameField.setCaretColor(Color.WHITE);
	    this.usernameField.setBounds(430, 178, 245, 46);
	    add(this.usernameField);
	    this.passwordField.setHorizontalAlignment(0);
	    this.passwordField.setForeground(Color.WHITE);
	    this.passwordField.setFont(this.passwordField.getFont().deriveFont(25F));
	    this.passwordField.setOpaque(false);
	    this.passwordField.setBorder((Border)null);
	    this.passwordField.setCaretColor(Color.WHITE);
	    this.passwordField.setBounds(430, 260, 245, 46);
	    add(this.passwordField);
	    this.infoLabel.setBounds(200, 409, 735, 69);
	    this.infoLabel.setForeground(Color.WHITE);
	    this.infoLabel.setFont(this.usernameField.getFont());
		add(this.infoLabel);
		this.test.setBounds(200, 409, 735, 69);
		add(this.test)
	    this.playButton.setBounds(370, 330, 64, 64);
	    this.playButton.addEventListener(this);
	    add((Component)this.playButton);
	    this.quitButton.setBounds(710, 22, 20, 20);
	    this.quitButton.addEventListener(this);
	    add((Component)this.quitButton);
	    this.hideButton.setBounds(680, 22, 20, 20);
	    this.hideButton.addEventListener(this);
	    add((Component)this.hideButton);
	    this.progressBar.setBounds(5, 470, 744, 18);
	    add((Component)this.progressBar);
	    this.ramButton.setBounds(670, 418, 50, 50);
	    this.ramButton.addEventListener(this);
	    add((Component)this.ramButton);
	  }
	  
	  public void onEvent(SwingerEvent e) {
	    if (e.getSource() == this.playButton) {
	      this.ramSelector.save();
	      setFieldEnabled(false);
	      if (this.usernameField.getText().replaceAll(" ", "").length() == 0 || this.passwordField.getText().length() == 0) {
	        JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un pseudo ou un mot de passe valide.", "Erreur", 0);
	        setFieldEnabled(true);
	        return;
	      } 
	      Thread t = new Thread() {
	          public void run() {
	            try {
	              Launcher.auth(LauncherPanel.this.usernameField.getText(), LauncherPanel.this.passwordField.getText());
	            } catch (AuthenticationException e1) {
	              e1.printStackTrace();
	            } 
	            try {
	              Launcher.update();
	            } catch (Exception e) {
	              Launcher.interruptThread();
	              Launcher.getCrashReporter().catchError(e, "Impossible de mettre jour Tryallium !");
	              LauncherPanel.this.setFieldEnabled(true);
	              return;
	            } 
	            LauncherPanel.this.saver.set("username", LauncherPanel.this.usernameField.getText());
	            LauncherPanel.this.saver.set("password", LauncherPanel.this.passwordField.getText());
	            try {
	              Launcher.launch();
	            } catch (LaunchException e) {
	              Launcher.getCrashReporter().catchError((Exception)e, "Impossible de lancer Tryallium !");
	              LauncherPanel.this.setFieldEnabled(true);
	            } 
	          }
	        };
	      t.start();
	    } else if (e.getSource() == this.quitButton) {
	      System.exit(0);
	    } else if (e.getSource() == this.hideButton) {
	      LauncherFrame.getInstance().setState(1);
	    } else if (e.getSource() == this.hideButton) {
	    } else if (e.getSource() == this.hidepassword){
	      LauncherPanel.this.passwordField.setEchoChar((char)0);
	    } else if (e.getSource() == this.hidenpassword){
		      LauncherPanel.this.passwordField.setEchoChar((char)'*');
	   
	    } else if (e.getSource() == this.ramButton) {
	      this.ramSelector.display();
	      this.ramSelector.save();
	    } else if (e.getSource() == this.infos) {
	      try {
	        Desktop.getDesktop().browse(new URI("https://discord.gg/53wTVd5"));
	      } catch (IOException e1) {
	        e1.printStackTrace();
	      } catch (URISyntaxException e1) {
	        e1.printStackTrace();
	      } 
	    } 
	  }
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(this.background, 0, 0, getWidth(), getHeight(), this);
	  }
	  
	  public void setFieldEnabled(boolean enabled) {
	    this.usernameField.setEnabled(enabled);
	    this.playButton.setEnabled(enabled);
	    this.ramButton.setEnabled(enabled);

	  }

	  public SColoredBar getprogressBar() {
	    return this.progressBar;
	  }
	  
	  public void setInfoText(String text) {
	    this.infoLabel.setText(text);
	  }
	  
	  public RamSelector getRamSelector() {
	    return this.ramSelector;
	  }
	}



package fr.thearty;

import java.io.File;
import java.util.Arrays;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.Application;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;
import re.alwyn974.openlauncherlib.LaunchException;
import re.alwyn974.openlauncherlib.external.ExternalLaunchProfile;
import re.alwyn974.openlauncherlib.external.ExternalLauncher;
import re.alwyn974.openlauncherlib.minecraft.AuthInfos;
import re.alwyn974.openlauncherlib.minecraft.GameFolder;
import re.alwyn974.openlauncherlib.minecraft.GameInfos;
import re.alwyn974.openlauncherlib.minecraft.GameTweak;
import re.alwyn974.openlauncherlib.minecraft.GameType;
import re.alwyn974.openlauncherlib.minecraft.GameVersion;
import re.alwyn974.openlauncherlib.minecraft.MinecraftLauncher;
import re.alwyn974.openlauncherlib.util.CrashReporter;

public class Launcher {
	 public static final GameVersion TR_VERSION = new GameVersion("1.12.2", GameType.V1_8_HIGHER);
	  
	  public static final GameInfos TR_INFOS = new GameInfos("Tryallium", TR_VERSION, new GameTweak[] { GameTweak.FORGE });

	  public static final File TR_DIR = TR_INFOS.getGameDir();
	  public static final File TR_CRASHS_DIR = new File(TR_DIR, "crashs");
	  
	  public static final File TR_LOG_DIR = new File(TR_DIR, "logs");
	  
	  private static AuthInfos authInfos;
	  
	  private static Thread updateThread;
	  
	  private static int val;
	  
	  private static int max;
	  
	  public static CrashReporter crashReporter = new CrashReporter("crashs", TR_CRASHS_DIR);
	  
	  public static void auth(String username, String password) throws AuthenticationException {
	    authInfos = new AuthInfos(username, "sry", "nope");
	  }
	  
	  public static void update() throws Exception {
	    SUpdate su = new SUpdate("http://137.74.91.190/app/webroot/files/Launcher1/", TR_DIR);
	    su.addApplication((Application)new FileDeleter());
	    updateThread = new Thread() {
	        public void run() {
	          while (!isInterrupted()) {
	            if (BarAPI.getNumberOfFileToDownload() == 0) {
	              Launcher.InfoLoad();
	              continue;
	            } 
	            Launcher.val = (int)(BarAPI.getNumberOfTotalDownloadedBytes() / 1000L);
	            Launcher.max = (int)(BarAPI.getNumberOfTotalBytesToDownload() / 1000L);
	            LauncherFrame.getInstance().getLauncherPanel().getprogressBar().setMaximum(Launcher.max);
	            LauncherFrame.getInstance().getLauncherPanel().getprogressBar().setValue(Launcher.val);
	            Launcher.InfoDL();
	          } 
	        }
	      };
	    updateThread.start();
	    su.start();
	    updateThread.interrupt();
	  }
	  
	  public static void launch() throws LaunchException {
	    ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(TR_INFOS, GameFolder.BASIC, authInfos);
	    profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getInstance().getLauncherPanel().getRamSelector().getRamArguments()));
	    ExternalLauncher launcher = new ExternalLauncher(profile);
	    LauncherFrame.getInstance().setVisible(false);
	    Thread.interrupted();
	    Process p = launcher.launch();
	    try {
	      p.waitFor();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    System.exit(0);
	  }
	  
	  public static void interruptThread() {
	    updateThread.interrupt();
	  }
	  
	  public static CrashReporter getCrashReporter() {
	    return crashReporter;
	  }
	  
	  /*private static void InfoLoad() {
	    try {
	      Thread.sleep(370L);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Recherche de mise jour");
	    try {
	      Thread.sleep(370L);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Recherche de mise jour.");
	    try {
	      Thread.sleep(370L);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Recherche de mise jour..");
	    try {
	      Thread.sleep(370L);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Recherche de mise jour...");
	  }
	  
	  private static void InfoDL() {
	    try {
	      Thread.sleep(370L);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers    " + 
	        BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " + 
	        Swinger.percentage(val, max) + "%");
	    try {
	      Thread.sleep(370L);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers.   " + 
	        BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " + 
	        Swinger.percentage(val, max) + "%");
	    try {
	      Thread.sleep(370L);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers..  " + 
	        BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " + 
	        Swinger.percentage(val, max) + "%");
	    try {
	      Thread.sleep(370L);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } 
	    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers... " + 
	        BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " + 
	        Swinger.percentage(val, max) + "%");
	        */
	  private static void InfoLoad() {
		    try {
		      Thread.sleep(370L);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } 
		    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Recherche de mise jour");
		    try {
		      Thread.sleep(370L);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } 
		    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Recherche de mise jour .");
		    try {
		      Thread.sleep(370L);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } 
		    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Recherche de mise jour ..");
		    try {
		      Thread.sleep(370L);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } 
		    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Recherche de mise jour ...");
		  }
		  
		  private static void InfoDL() {
		    try {
		      Thread.sleep(370L);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } 
		    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers    " + 
		        BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " + 
		        Swinger.percentage(val, max) + "%");
		    try {
		      Thread.sleep(370L);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } 
		    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers.   " + 
		        BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " + 
		        Swinger.percentage(val, max) + "%");
		    try {
		      Thread.sleep(370L);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } 
		    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers..  " + 
		        BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " + 
		        Swinger.percentage(val, max) + "%");
		    try {
		      Thread.sleep(370L);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } 
		    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Telechargement des fichiers... " + 
		        BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " + 
		        Swinger.percentage(val, max) + "%");
		  }
	  }
     



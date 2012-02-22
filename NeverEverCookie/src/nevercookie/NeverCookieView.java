/*
 * NeverCookieView.java
 */

package nevercookie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * The application's main frame.
 */
public class NeverCookieView extends FrameView {

    private String prefixDir = "";

    // SafariVars
    private String safariPrefix = "";
    private String safaricookie = "";
    private String safaridb = "";
    private String safarilocalstore = "";

    private String safariFlashPrefix="";
    private String safariFlashLso="";
    private String safariFlashLso2="";

    private String safariSilverPrefix="";
    private String safariSilverlso="";
    private dataModel model = new dataModel();

    private String[] safariAll = null;
    private String[] safariFlash = null;
    private String[] safariCookie = null;
    private String[] safariSilverLight = null;

    protected enum cookieTypes {COOKIE,FLASH,SILVERLIGHT};
    
    ArrayList<fileObj> items = new ArrayList<fileObj>();
        
    public NeverCookieView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        setEnviron();

        this.tblResults.setModel(model);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = NeverCookieApp.getApplication().getMainFrame();
            aboutBox = new NeverCookieAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        NeverCookieApp.getApplication().show(aboutBox);
    }

    private void setEnviron(){
        String os = System.getProperty("os.name");
        lbCurrentUser.setText(System.getProperty("user.name"));
        lbCurrentOS.setText(os);

        if (os.compareTo("Mac OS X") == 0){
            String homeDir = System.getProperty("user.home");
            this.prefixDir = String.format("%s/Library/",homeDir);
            this.safariPrefix = String.format("%sSafari/",this.prefixDir);
            this.safaricookie = String.format("%sCookies", this.prefixDir);
            this.safaridb = String.format("%sDatabases", this.safariPrefix);
            this.safarilocalstore = String.format("%sLocalStorage/", this.safariPrefix);
            this.safariFlashPrefix = String.format("%sPreferences/Macromedia/Flash Player/",this.prefixDir);
            this.safariFlashLso = String.format("%s#SharedObjects",this.safariFlashPrefix);
            this.safariFlashLso2 = String.format("%smacromedia.com/support/flashplayer/sys",this.safariFlashPrefix);
            this.safariSilverPrefix = String.format("%sApplication Support/Microsoft/Silverlight/",this.prefixDir);
            this.safariSilverlso = String.format("%sis",this.safariSilverPrefix);
            this.safariCookie = new String[]{safaricookie,safaridb,safarilocalstore,};
            this.safariFlash = new String[]{safariFlashLso,safariFlashLso2,};
            this.safariSilverLight = new String[]{safariSilverPrefix,safariSilverlso};
        } else {
            JOptionPane.showMessageDialog(null, "Operating System [" + os +"] not supported");
            System.exit(1);
        }
    }

    private void dirlist(String fname,cookieTypes type) {
        File dir = new File(fname);
        String[] chld = dir.list();
        if (dir.isFile()) {
            this.items.add(new fileObj(dir.getAbsolutePath(), dir.getName(),type,cbxSelectAll.isSelected()));
        System.out.println("" + dir.getName());
        return;

        } else if (dir.isDirectory()) {
            System.out.println(fname.substring(fname.lastIndexOf("/")));
            for (int i = 0; i < chld.length; i++) {
                dirlist(fname + "/" + chld[i],type);
            }
        }
    }

    private void getSubDir(String fname,ArrayList<String> foundDirs) {
        File dir = new File(fname);
        String[] chld = dir.list();
        if (dir.isDirectory()) {
            System.out.println(fname.substring(fname.lastIndexOf("/")));
            for (int i = 0; i < chld.length; i++) {
                foundDirs.add(fname + "/" + chld[i]);
                getSubDir(fname + "/" + chld[i],foundDirs);
            }
        }
    }

    private void genSafariListing(){
        
        dataModel m = (dataModel)tblResults.getModel();
        this.items.clear();
        m.clear();
        for (String path:safariCookie){
            dirlist(path,cookieTypes.COOKIE);
        }
        for (String path:safariFlash){
            dirlist(path,cookieTypes.FLASH);
        }
        for (String path:safariSilverLight){
            dirlist(path,cookieTypes.SILVERLIGHT);
        }
        for (fileObj f:this.items){
            m.addRow(f);
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cbxDeleteCookies = new javax.swing.JCheckBox();
        cbxPreventFlashCookie = new javax.swing.JCheckBox();
        cbxPreventSilverLightCookie = new javax.swing.JCheckBox();
        cbxDeleteDirs = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        btnGo = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lbCurrentUser = new javax.swing.JLabel();
        lbCurrentOS = new javax.swing.JLabel();
        lbSelectBrowser = new javax.swing.JLabel();
        cbxBrowser = new javax.swing.JComboBox();
        cbxSelectAll = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(nevercookie.NeverCookieApp.class).getContext().getResourceMap(NeverCookieView.class);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        cbxDeleteCookies.setText(resourceMap.getString("cbxDeleteCookies.text")); // NOI18N
        cbxDeleteCookies.setName("cbxDeleteCookies"); // NOI18N

        cbxPreventFlashCookie.setText(resourceMap.getString("cbxPreventFlashCookie.text")); // NOI18N
        cbxPreventFlashCookie.setName("cbxPreventFlashCookie"); // NOI18N

        cbxPreventSilverLightCookie.setText(resourceMap.getString("cbxPreventSilverLightCookie.text")); // NOI18N
        cbxPreventSilverLightCookie.setName("cbxPreventSilverLightCookie"); // NOI18N

        cbxDeleteDirs.setText(resourceMap.getString("cbxDeleteDirs.text")); // NOI18N
        cbxDeleteDirs.setName("cbxDeleteDirs"); // NOI18N
        cbxDeleteDirs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDeleteDirsActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(cbxDeleteCookies)
                        .add(39, 39, 39)
                        .add(cbxDeleteDirs))
                    .add(cbxPreventFlashCookie)
                    .add(cbxPreventSilverLightCookie))
                .addContainerGap(295, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbxDeleteCookies)
                    .add(cbxDeleteDirs))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbxPreventFlashCookie)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbxPreventSilverLightCookie)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2"); // NOI18N

        btnExit.setText(resourceMap.getString("btnExit.text")); // NOI18N
        btnExit.setName("btnExit"); // NOI18N

        btnGo.setText(resourceMap.getString("btnGo.text")); // NOI18N
        btnGo.setName("btnGo"); // NOI18N
        btnGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(btnGo)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 547, Short.MAX_VALUE)
                .add(btnExit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnExit)
                    .add(btnGo))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel5.border.title"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        lbCurrentUser.setText(resourceMap.getString("lbCurrentUser.text")); // NOI18N
        lbCurrentUser.setName("lbCurrentUser"); // NOI18N

        lbCurrentOS.setText(resourceMap.getString("lbCurrentOS.text")); // NOI18N
        lbCurrentOS.setName("lbCurrentOS"); // NOI18N

        lbSelectBrowser.setText(resourceMap.getString("lbSelectBrowser.text")); // NOI18N
        lbSelectBrowser.setName("lbSelectBrowser"); // NOI18N

        cbxBrowser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- None --", "Apple Safari" }));
        cbxBrowser.setName("cbxBrowser"); // NOI18N
        cbxBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxBrowserActionPerformed(evt);
            }
        });

        cbxSelectAll.setSelected(true);
        cbxSelectAll.setText(resourceMap.getString("cbxSelectAll.text")); // NOI18N
        cbxSelectAll.setName("cbxSelectAll"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, lbCurrentUser)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel5Layout.createSequentialGroup()
                        .add(lbCurrentOS)
                        .add(52, 52, 52)
                        .add(lbSelectBrowser)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cbxBrowser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 194, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 220, Short.MAX_VALUE)
                        .add(cbxSelectAll)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(lbCurrentUser)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lbCurrentOS)
                    .add(lbSelectBrowser)
                    .add(cbxBrowser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cbxSelectAll))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblResults.setName("tblResults"); // NOI18N
        tblResults.setRowHeight(24);
        jScrollPane2.setViewportView(tblResults);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(nevercookie.NeverCookieApp.class).getContext().getActionMap(NeverCookieView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 661, Short.MAX_VALUE)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel))
                .add(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void cbxBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxBrowserActionPerformed
        ((dataModel)this.tblResults.getModel()).clear();
        if (cbxBrowser.getSelectedIndex() == 1){
            this.genSafariListing();
        }
    }//GEN-LAST:event_cbxBrowserActionPerformed

    public String getFileContent(String path){
        String line ="";
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
            //InputStream is = getClass().getResourceAsStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String buffer;
            while ((buffer = br.readLine()) != null) {
                line = line + buffer;
            }
            br.close();
            isr.close();
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(NeverCookieView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }

    public void writeFileContent(String path, String content){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(content);
            out.close();
        } catch (IOException e) {
            System.out.println("Failed saving file");
        }
    }

    private void setFlashPref(){
        writeFileContent(safariFlashLso2 + "/settings.sol",getFileContent("nevercookie/resources/allsettings.sol"));
    }

    private void setSilverShiteDisabled(String path){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write("");
            out.close();
        } catch (IOException e) {
            System.out.println("Failed saving file");
        }
    }

    private void btnGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoActionPerformed
        if (cbxDeleteCookies.isSelected()){
            for (Object o:((dataModel)tblResults.getModel()).getData()){
                if (((fileObj)o).getSelected()){
                    File f = new File(((fileObj)o).getLocation());
                    System.out.println("deleting "+((fileObj)o).getLocation());
                    f.delete();
                }
            }
        }

        if (cbxDeleteDirs.isSelected()){
            ArrayList<String> dirs = new ArrayList<String>();
            getSubDir(safariFlashLso, dirs);
            getSubDir(safariFlashLso2, dirs);
         
            for(String path:dirs){
                File f = new File(path);
                f.delete();
            }
        }

        if (cbxPreventFlashCookie.isSelected()){
            setFlashPref();
        }

        if (cbxPreventSilverLightCookie.isSelected()){
            setSilverShiteDisabled(safariSilverPrefix+"disabled.dat");
        }

        this.genSafariListing();
    }//GEN-LAST:event_btnGoActionPerformed

    private void cbxDeleteDirsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDeleteDirsActionPerformed
        cbxDeleteCookies.setSelected(cbxDeleteDirs.isSelected());
    }//GEN-LAST:event_cbxDeleteDirsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnGo;
    private javax.swing.JComboBox cbxBrowser;
    private javax.swing.JCheckBox cbxDeleteCookies;
    private javax.swing.JCheckBox cbxDeleteDirs;
    private javax.swing.JCheckBox cbxPreventFlashCookie;
    private javax.swing.JCheckBox cbxPreventSilverLightCookie;
    private javax.swing.JCheckBox cbxSelectAll;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbCurrentOS;
    private javax.swing.JLabel lbCurrentUser;
    private javax.swing.JLabel lbSelectBrowser;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTable tblResults;
    // End of variables declaration//GEN-END:variables



    private JDialog aboutBox;


    public class fileObj{
        private String location = "";
        private String name = "";
        private ImageIcon type = null;
        private boolean selected = false;
        private boolean actioned = false;

        URL flashIconUrl = this.getClass().getClassLoader().getResource("nevercookie/resources/flash.png");
        URL silverShiteIconUrl = this.getClass().getClassLoader().getResource("nevercookie/resources/silverlight.png");
        URL cookieIconUrl = this.getClass().getClassLoader().getResource("nevercookie/resources/cookie.png");

        public fileObj(String location, String name, cookieTypes type, boolean selected){
            this.location = location;
            this.name = name;
            switch (type){
                case FLASH: {
                        this.type = new ImageIcon(flashIconUrl);
                        break;
                    }
                case COOKIE: {
                        this.type = new ImageIcon(cookieIconUrl);
                        break;
                    }
                case SILVERLIGHT: {
                        this.type = new ImageIcon(silverShiteIconUrl);
                        break;
                    }
            }
            this.selected = selected;
        }

        @Override
        public String toString(){
            return this.location;
        }

        public String getName(){
            return this.name;
        }

        public String getLocation(){
            return this.location;
        }

        public ImageIcon getType(){
            return this.type;
        }

        public boolean getSelected(){
            return this.selected;
        }

        public void setSelected(boolean value){
            this.selected = value;
        }

        public boolean getAction(){
            return this.actioned;
        }
    }

    public class dataModel extends DefaultTableModel{
        protected String[] columnNames = null;
        protected Class[] types = null;
        protected ArrayList<Object> data = new ArrayList<Object>();

        
        public dataModel(){
            super();
            this.columnNames = (new String[]{"Type","Path", "Name","Select",});
            
            this.setTypes(new Class [] {
                javax.swing.ImageIcon.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.Boolean.class,
            });
        }
        
        public void setTypes(Class[] types){
            this.types = types;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            fileObj row = (fileObj)this.data.get(rowIndex);
            if (row == null){
                return null;
            }
            switch (columnIndex){
                case 0: return row.getType();
                case 1: return row.getLocation();
                case 2: return row.getName();
                case 3: return row.getSelected();
                
                default: return null;
            }
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            fileObj item = (fileObj)this.data.get(row);
            switch (col){
                case 3: item.setSelected((Boolean)value);
            }
            fireTableCellUpdated(row, col);
        }

        public void addRow(Object item) {
            this.data.add((fileObj)item);
            fireTableDataChanged();
        }
    
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public int getColumnCount() {
            return this.columnNames.length;
        }

        @Override
        public int getRowCount() {
            if (this.data == null){
                return 0;
            }
            return this.data.size();
        }

        public void clear(){
            this.data.clear();
            fireTableDataChanged();
        }

        public ArrayList<Object> getData(){
            return this.data;
        }
    }


}

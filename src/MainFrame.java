import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

/**
 * This class makes the main frame of the application
 * @author shiva
 * @since 11/05/2020
 */
public class MainFrame extends JFrame {

    //The three main panels
    private RequestsListPanel requestsList;
    private RequestsPanel requests;
    private ResponsesPanel responses;

    //The menubar at the top of the frame
    private JMenuBar menuBar;

    //Tools for hiding in the system tray
    private TrayIcon trayIcon;
    private SystemTray tray;
    private boolean sysTray = false;


    /**
     * MainFrame constructor sets the components and add them to the frame
     */
    private MainFrame() {

        //Update the UI preferences
        UIControl.UIManagerControl();

        //Make the whole components
        requestsList = new RequestsListPanel();
        requests = new RequestsPanel();
        responses = new ResponsesPanel();

        //Add the components to the frame
        setLayout(new BorderLayout(1, 0));
        add(requestsList, BorderLayout.WEST);
        add(requests, BorderLayout.CENTER);
        add(responses, BorderLayout.EAST);

        //Make and add the menu
        setMenuBar();
        setJMenuBar(menuBar);

        setTitle("Insomnia");
        setSize(1100, 550);
        setLocation(150, 250);
        setVisible(true);

        //Make hiding in system tray option for the application
        setSystemTray();

        //Just for testing
        responses.addHeader("key", "value");
        responses.setStatusLabel(200);
        responses.setSizeLabel(560);
        responses.setTimeLabel(12);
    }

        /**
         * Make and set the menubar
         */
        private void setMenuBar () {
            menuBar = new JMenuBar();

            JMenu application = new JMenu("Application");
            application.setMnemonic(KeyEvent.VK_A);
            JMenuItem options = new JMenuItem("Options");
            options.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame();
                    JDialog optionDialog = new JDialog(frame,"Options");
                    optionDialog.setLayout(new FlowLayout(FlowLayout.LEADING,50,10));
                    optionDialog.setLocationRelativeTo(null);
                    optionDialog.setSize(200,150);
                    optionDialog.setVisible(true);

                    JCheckBox followRedirectCheck = new JCheckBox("Follow Redirect");
                    followRedirectCheck.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            updateProperty("followRedirect");
                        }
                    });
                    optionDialog.add(followRedirectCheck);
                    JCheckBox hideInSysTrayCheck = new JCheckBox("Hide in system tray");
                    hideInSysTrayCheck.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            updateProperty("systemTray");
                        }
                    });
                    optionDialog.add(hideInSysTrayCheck);
                    JCheckBox darkTheme = new JCheckBox("Dark Theme");
                    followRedirectCheck.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            updateProperty("darkTheme");
                            UIControl.updateDarkTheme();
                            repaint();
                            revalidate();
                        }
                    });
                    optionDialog.add(darkTheme);
                }
            });
            options.setMnemonic(KeyEvent.VK_O );
            options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
            application.add(options);

            JMenuItem exit = new JMenuItem("Exit");
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateSysTray();
                    if (sysTray) {
                        try {
                            tray.add(trayIcon);
                            setVisible(false);
                        } catch (AWTException ex) {
                            System.out.println("unable to add to tray");
                        }
                    }
                    else
                        System.exit(0);
                }
            });
            exit.setMnemonic(KeyEvent.VK_X);
            exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
            application.add(exit);

            JMenu view = new JMenu("View");
            view.setMnemonic(KeyEvent.VK_V);
            JMenuItem fullScreen = new JMenuItem("Toggle Full Screen");
            fullScreen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(getExtendedState() == JFrame.NORMAL)
                        setExtendedState(JFrame.MAXIMIZED_BOTH);
                    else setExtendedState(JFrame.NORMAL);
                }
            });
            fullScreen.setMnemonic(KeyEvent.VK_F11);
            fullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, ActionEvent.CTRL_MASK));
            view.add(fullScreen);
            JMenuItem sideBar = new JMenuItem("Toggle Sidebar");
            sideBar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(requestsList.isVisible())
                        requestsList.setVisible(false);
                    else
                        requestsList.setVisible(true);
                }
            });
            sideBar.setMnemonic(KeyEvent.VK_F5);
            sideBar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
            view.add(sideBar);

            JMenu help = new JMenu("Help");
            help.setMnemonic(KeyEvent.VK_H);
            JMenuItem about = new JMenuItem("About");
            about.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame infoFrame = new JFrame();
                    JOptionPane.showMessageDialog(infoFrame,
                            "<html><b>Shiva Shahrokhi</b>\n" +
                                    "Student Number : 9722023\n" +
                                    "Gmail : shivashahrokhi22@gmail.com\n",
                            "About",
                            JOptionPane.INFORMATION_MESSAGE);                }
            });
            about.setMnemonic(KeyEvent.VK_A);
            about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
            help.add(about);
            JMenuItem helpItem = new JMenuItem("Help");
            helpItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame helpFrame = new JFrame();
                    JDialog dialog = new JDialog(helpFrame,"Help",true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
            });
            helpItem.setMnemonic(KeyEvent.VK_H);
            helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
            help.add(helpItem);

            menuBar.add(application);
            menuBar.add(view);
            menuBar.add(help);

        }


        private void setSystemTray() {
            if (SystemTray.isSupported()) {

                tray = SystemTray.getSystemTray();

                Image image = Toolkit.getDefaultToolkit().getImage("src/resources/InsomniaIcon.jpg");
                ActionListener exitListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                };

                PopupMenu popup = new PopupMenu();
                MenuItem defaultItem = new MenuItem("Exit");
                defaultItem.addActionListener(exitListener);
                popup.add(defaultItem);
                defaultItem = new MenuItem("Open");
                defaultItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setVisible(true);
                        setExtendedState(JFrame.NORMAL);
                        tray.remove(trayIcon);
                    }
                });
                popup.add(defaultItem);
                trayIcon = new TrayIcon(image, "Insomnia", popup);
                trayIcon.setImageAutoSize(true);
            }
            else {
                System.out.println("system tray not supported");
            }

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    updateSysTray();
                    if (sysTray) {
                        try {
                            tray.add(trayIcon);
                            setVisible(false);
                        } catch (AWTException ex) {
                            System.out.println("unable to add to tray");
                        }
                    } else
                        System.exit(0);
                }

            });
            setIconImage(Toolkit.getDefaultToolkit().getImage("src/resources/InsomniaIcon.jpg"));
        }

        private void updateProperty(String property) {
            try {
                File configFile = new File("src/resources/config.properties");
                FileReader reader = new FileReader(configFile);
                Properties props = new Properties();
                props.load(reader);
                reader.close();

                FileWriter writer = new FileWriter(configFile);
                if (props.getProperty(property).equals("inactive"))
                    props.setProperty(property,"active");
                else
                    props.setProperty(property,"inactive");
                props.store(writer,null);
                writer.close();
            }
            catch (Exception e) {
                System.out.println("File not found");
            }
        }

        private void updateSysTray() {
            try {
                File configFile = new File("src/resources/config.properties");
                FileReader reader = new FileReader(configFile);
                Properties props = new Properties();
                props.load(reader);
                if (props.getProperty("systemTray").equals("active"))
                    sysTray = true;
                else
                    sysTray = false;
                reader.close();
            }
            catch (Exception e) {
                System.out.println("File not found");
            }
        }

        public static void main (String[]args){
            new MainFrame();
        }
    }


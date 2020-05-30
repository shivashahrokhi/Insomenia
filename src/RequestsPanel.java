import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import java.io.File;

/**
 * This class makes the second panel of the main frame where the user adds a new request
 * @author shiva
 * @since 12/05/2020
 */
public class RequestsPanel extends JPanel {

    //The components on the the top of panel
    private JComboBox comboBoxMethod;
    private JTextField address;
    private JButton sendBtn;
    //The second part of Request panel is a tabbed pane consists of Body, Auth, Query and Header panels
    private BodyTypes bodyPanel;
    private JPanel noBodyPanel;
    private JPanel formPanel;
    private JPanel jsonPanel;
    private JPanel binaryFilePanel;
    private JPanel authPanel;
    private JPanel headerPanel;


    /**
     * RequestsPanel constructor makes the request panel
     */
    public RequestsPanel() {

        setLayout(new BorderLayout(1, 0));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));

        setComboBoxMethod();
        setAddress();
        setSendBtn();

        topPanel.add(comboBoxMethod, BorderLayout.WEST);
        topPanel.add(address, BorderLayout.CENTER);
        topPanel.add(sendBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setPreferredSize(new Dimension(50, 50));

        setBodyPanel();
        setAuthPanel();
        setHeaderPanel();

        tabbedPane.addTab("Body", bodyPanel);
        tabbedPane.addTab("Auth", authPanel);
        tabbedPane.addTab("Header", headerPanel);

        add(tabbedPane, BorderLayout.CENTER);

    }

    /**
     * This method makes the combobox that shows the method
     */
    private void setComboBoxMethod() {
        String[] comboBoxMethodsItems = {"GET", "POST", "PUT", "PATCH", "DELETE" };
        comboBoxMethod = new JComboBox(comboBoxMethodsItems);
        comboBoxMethod.setPreferredSize(new Dimension(100, 50));
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        comboBoxMethod.setRenderer(listRenderer);
    }

    /**
     * This method makes the text field which is gets the address
     */
    private void setAddress() {
        address = new JTextField();
    }

    /**
     * This method makes the send button
     */
    private void setSendBtn() {
        sendBtn = new JButton("Send");
        sendBtn.setPreferredSize(new Dimension(65, 50));
    }

    /**
     * This method makes the body panel details
     */
    private void setBodyPanel() {

        bodyPanel = new BodyTypes();

        setNoBodyPanel();
        setFormPanel();
        setJsonPanel();
        setBinaryDataPanel();

        bodyPanel.addType("No Body",noBodyPanel);
        bodyPanel.addType("Form Body", formPanel);
        bodyPanel.addType("JSON", jsonPanel);
        bodyPanel.addType("Binary Data", binaryFilePanel);


    }

    /**
     * This method makes the body panel which has not a specific type
     */
    private void setNoBodyPanel() {
        noBodyPanel = new JPanel(new BorderLayout(0, 0));
        JLabel label = new JLabel("Select a body type from above");
        label.setHorizontalAlignment(0);
        noBodyPanel.add(label, BorderLayout.CENTER);

    }

    /**
     * This method makes the form body panel
     */
    private void setFormPanel() {
        formPanel = new JPanel(new BorderLayout(0, 0));
        JTextArea bodyMessage = new JTextArea();
        formPanel.add(bodyMessage, BorderLayout.CENTER);
    }

    /**
     * This method makes the JSON body panel
     */
    private void setJsonPanel() {
        jsonPanel = new JPanel(new BorderLayout(0, 0));
        JTextArea jsonBodyMessage = new JTextArea();
        jsonPanel.add(jsonBodyMessage, BorderLayout.CENTER);
    }

    /**
     * This method makes the binary data body panel
     */
    private void setBinaryDataPanel() {
        binaryFilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
        JLabel message = new JLabel("SELECTED FILE");
        JTextField fileAddress = new JTextField("No file selected");
        JButton chooseFileBtn = new JButton("Choose File");
        chooseFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                binaryFilePanel.add(message);
                binaryFilePanel.add(fileAddress);
                binaryFilePanel.add(chooseFileBtn);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(binaryFilePanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileAddress.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        binaryFilePanel.add(message);
        binaryFilePanel.add(fileAddress);
        binaryFilePanel.add(chooseFileBtn);
    }

    /**
     * This method makes the auth panel
     */
    private void setAuthPanel() {
        authPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,20,10));
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(70, 25));
        label.setText("TOKEN");
        authPanel.add(label);
        JTextField tokenText = new JTextField();
        tokenText.setPreferredSize(new Dimension(250, 25));
        authPanel.add(tokenText);
        JLabel preLabel = new JLabel();
        preLabel.setPreferredSize(new Dimension(70, 25));

        preLabel.setText("PREFIX");
        authPanel.add(preLabel);
        JTextField prefixText = new JTextField();
        prefixText.setPreferredSize(new Dimension(250, 25));
        authPanel.add(prefixText);
        JLabel enableLabel = new JLabel();
        enableLabel.setText("ENABLE");
        authPanel.add(enableLabel);
        JCheckBox checkBox = new JCheckBox();
        authPanel.add(checkBox);

    }


    /**
     * This method makes the header panel
     */
    private void setHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.add(makeHeader());
    }

    /**
     * This method makes a single header
     */
    private JPanel makeHeader() {
        JPanel retPanel = new JPanel();
        JTextField key = new JTextField("New header");
        key.setPreferredSize(new Dimension(150,25));
        key.addFocusListener(makeFocusListener());
        JTextField value = new JTextField("New value");
        value.setPreferredSize(new Dimension(150,25));
        value.addFocusListener(makeFocusListener());
        retPanel.add(key);
        retPanel.add(value);
        return retPanel;
    }

    /**
     * This method makes a focus listener for JTexts
     */
    private FocusListener makeFocusListener() {
        FocusListener retListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                Container singleHeaderPanel = e.getComponent().getParent();
                if (singleHeaderPanel.getComponents().length == 2) {
                    for (Component component : singleHeaderPanel.getComponents()) {
                        ((JTextField)component).setText("");
                    }
                    singleHeaderPanel.add(maKeCheckBox());
                    singleHeaderPanel.add(makeDeleteBtn());
                    singleHeaderPanel.getParent().add(makeHeader());
                    singleHeaderPanel.getParent().revalidate();
                }
            }
        };
        return retListener;
    }


    /**
     * This method makes the check box for each header
     */
    private JCheckBox maKeCheckBox() {
        JCheckBox checkBox = new JCheckBox();
        return checkBox;
    }

    /**
     * This method makes the delete button for each header
     */
    private JButton makeDeleteBtn() {
        JButton deleteBtn = new JButton();
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container singleHeader = deleteBtn.getParent();
                headerPanel.remove(singleHeader);
                headerPanel.validate();
            }
        });
        try {
            ImageIcon originalIcon = new ImageIcon("src/resources/delete.png");
            int width = originalIcon.getIconWidth() / 10;
            int height = originalIcon.getIconHeight() / 10;
            Image scaled = scaleImage(originalIcon.getImage(), width, height);
            ImageIcon scaledIcon = new ImageIcon(scaled);
            deleteBtn.setIcon(scaledIcon);

        }
        catch (Exception e) {
            System.out.println("Icon not set");
        }
        return deleteBtn;
    }

    /**
     * This method sets the scale of icon image
     */
    private Image scaleImage(Image image, int w, int h) {
        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return scaled;
    }
}

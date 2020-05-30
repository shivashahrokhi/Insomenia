import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

/**
 * This class makes the third panel of the main frame where the response will show
 * @author shiva
 * @since 12/05/2020
 */
public class ResponsesPanel extends JPanel {

    //There is a panel consist of three label in the top panel on the top of Responses panel
    JPanel statusPanel;
    private JLabel statusLabel;
    private JLabel timeLabel;
    private JLabel sizeLabel;
    //The combo box in the top panel
    private JComboBox responseHistory;

    //The second part of Responses panel is a tabbed pane consists of Body and Header panels
    private JTabbedPane tabbedPane;
    private BodyTypes bodyPanel;
    private JPanel rawBody;
    private JPanel previewBody;
    private JPanel jsonBody;
    private JPanel headerPanel;
    private JPanel headers;


    /**
     * ResponsesPanel constructor makes the response panel
     */
    public ResponsesPanel() {

        setLayout(new BorderLayout(0, 1));
        setPreferredSize(new Dimension(430, 550));

        setTopPanel();
        setBodyPanel();
        setHeaderPanel();

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setPreferredSize(new Dimension(50, 50));
        tabbedPane.addTab("Body", bodyPanel);
        tabbedPane.addTab("Header", headerPanel);
        add(tabbedPane, BorderLayout.CENTER);

    }

    /**
     * This method makes the panel one the top of Responses panel
     */
    private void setTopPanel() {

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        topPanel.setPreferredSize(new Dimension(430, 50));
        statusPanel = new JPanel(new FlowLayout(FlowLayout.LEADING,20,12));
        statusLabel = new JLabel();
        statusLabel.setPreferredSize(new Dimension(90, 25));
        statusLabel.setHorizontalAlignment(0);
        statusLabel.setOpaque(true);
        statusLabel.setForeground(Color.WHITE);

        timeLabel = new JLabel();
        timeLabel.setPreferredSize(new Dimension(65, 25));
        timeLabel.setHorizontalAlignment(0);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(Color.lightGray);

        sizeLabel = new JLabel();
        sizeLabel.setPreferredSize(new Dimension(65, 25));
        sizeLabel.setHorizontalAlignment(0);
        sizeLabel.setOpaque(true);
        sizeLabel.setBackground(Color.lightGray);

        statusPanel.add(statusLabel);
        statusPanel.add(timeLabel);
        statusPanel.add(sizeLabel);

        responseHistory = new JComboBox();
        responseHistory.setPreferredSize(new Dimension(100, 50));

        topPanel.add(statusPanel, BorderLayout.CENTER);
        topPanel.add(responseHistory, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
    }

    /**
     * This method makes the body panel for body response
     */
    private void setBodyPanel() {
        bodyPanel = new BodyTypes();
        setRawBody();
        setPreviewBody();
        setJsonPanel();
        bodyPanel.addType("Raw",rawBody);
        bodyPanel.addType("Preview",previewBody);
        bodyPanel.addType("JSON",jsonBody);

    }

    /**
     * This method makes the raw body panel for body response
     */
    private void setRawBody() {

        rawBody = new JPanel(new BorderLayout(0,0));
        JTextArea bodyMessage = new JTextArea();
        rawBody.add(bodyMessage, BorderLayout.CENTER);
    }

    /**
     * This method makes the preview body panel for body response
     */
    private void setPreviewBody() {

        previewBody = new JPanel(new BorderLayout(0,0));
        JLabel label = new JLabel();
        previewBody.add(label,BorderLayout.CENTER);
    }

    /**
     * This method makes the JSON body panel for body response
     */
    private void setJsonPanel() {

        jsonBody = new JPanel(new BorderLayout(0,0));
        JTextArea bodyMessage = new JTextArea();
        jsonBody.add(bodyMessage, BorderLayout.CENTER);
    }

    /**
     * This method makes the header panel for response
     */
    private void setHeaderPanel() {

        headerPanel = new JPanel(new BorderLayout(0,0));
        headers = new JPanel();

        JButton cpyBtn = new JButton("Copy to clipboard");
        cpyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpyString = "";
                for (Component header : headers.getComponents()) {
                    try {
                        cpyString += ((JTextField)header).getText() + "\n";
                    }
                    catch (Exception exp) {
                        System.out.println(exp.getMessage());
                    }
                    StringSelection stringSelection = new StringSelection(cpyString);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
            }
        });

        headerPanel.add(headers,BorderLayout.CENTER);
        headerPanel.add(cpyBtn,BorderLayout.SOUTH);

    }

    /**
     * This method adds a new header to header panel
     * @param key the key of a header
     * @param value the value of a header
     */
    public void addHeader(String key, String value) {

        JTextField header = new JTextField(key  + "    :     " + value);
        header.setPreferredSize(new Dimension(300,25));
        header.setEditable(false);

        headers.add(header);
    }

    /**
     * This method get the status code and set the proper colour and text in the status label
     * @param statusCode status code of response
     */
    public void setStatusLabel(int statusCode) {
        //Add new cases later
        switch (statusCode) {
            case (200):
                statusLabel.setBackground(new Color(0x14AE33));
                statusLabel.setText("200 OK");
                break;
            case (404):
                statusLabel.setBackground(new Color(0xC89E20));
                statusLabel.setText("404 Not Found");
                break;
            default:
                statusLabel.setBackground(new Color(0xC2161B));
                statusLabel.setText("Error");
        }
    }

    /**
     * This method add the time to the time label
     * @param time the time of process in millisecond
     */
    public void setTimeLabel(int time) {
        timeLabel.setText(time + " ms");
    }

    /**
     * This method add the size of data to the size label
     * @param sizeOfData the size of data in byte
     */
    public void setSizeLabel(int sizeOfData) {
        if (sizeOfData < 1000)
            sizeLabel.setText(sizeOfData + " B");
        else  if (sizeOfData < 1000000)
            sizeLabel.setText(sizeOfData/1000 + " KB");
        else
            sizeLabel.setText(sizeOfData/1000000 + " MB");
    }
}

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * This class makes the first panel of the main frame where the list of requests are added
 * @author shiva
 * @since 12/05/2020
 */
public class RequestsListPanel extends JPanel {

    private JTree methodReqTree;

    /**
     * RequestsListPanel constructor makes the request list panel
     */
    public RequestsListPanel() {

        setLayout(new BorderLayout(1,0));
        setPreferredSize(new Dimension(230, 550));

        JLabel label = new JLabel("Insomnia", JLabel.CENTER);
        label.setPreferredSize(new Dimension(230, 50));
        label.setFont(new Font("Monaco", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        label.setBackground(Color.pink);
        label.setOpaque(true);
        add(label,BorderLayout.NORTH);

        setMethodReqTree();
        add(methodReqTree,BorderLayout.CENTER);
    }

    /**
     * This method makes the JTree of the methods
     */
    private void setMethodReqTree() {
        DefaultMutableTreeNode method = new DefaultMutableTreeNode("Method");
        DefaultMutableTreeNode get = new DefaultMutableTreeNode("GET");
        DefaultMutableTreeNode put = new DefaultMutableTreeNode("PUT");
        DefaultMutableTreeNode post = new DefaultMutableTreeNode("POST");
        DefaultMutableTreeNode delete = new DefaultMutableTreeNode("DELETE");
        DefaultMutableTreeNode patch = new DefaultMutableTreeNode("PATCH");
        method.add(get);
        method.add(post);
        method.add(put);
        method.add(delete);
        method.add(patch);

        methodReqTree = new JTree(method);

    }

}

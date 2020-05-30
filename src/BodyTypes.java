import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class is made to show different types of body by using a combobox and a panel with card layout manager
 * @author shiva
 * @since 15/05/2020
 */
public class BodyTypes extends JPanel {

    private JPanel cards;
    private JComboBox comboBox;

    public BodyTypes() {

        setLayout(new BorderLayout(0, 0));

        cards = new JPanel();
        cards.setLayout(new CardLayout(0, 0));

        comboBox = new JComboBox();
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cl.show(cards,e.getItem().toString());
                }
            }
        });

        add(comboBox, BorderLayout.NORTH);
        add(cards,BorderLayout.CENTER);
    }

    /**
     * This method adds different type of body
     * @param name The name of the panel which is added
     * @param typePanel The panel consists of the body type message
     */
    public void addType(String name, JPanel typePanel) {
        comboBox.addItem(name);
        cards.add(typePanel,name);
    }
}

import net.miginfocom.swing.MigLayout;
import javax.swing.*;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class UISwingResponsif extends JFrame {

    private JPanel mainPanel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;

    private static final int COMPACT_VIEW_MAX = 576;
    private static final int SPLIT_VIEW_MAX = 768;
    private static final int INTERMEDIATE_VIEW_MAX = 992;
    private static final int DESKTOP_SMALL_MAX = 1200;
    private static final int DESKTOP_STANDARD_MAX = 1400;

    public UISwingResponsif() {        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 600));

        panel1 = createColoredPanel("Panel 1 - Header", new Color(70, 130, 180));
        panel2 = createColoredPanel("Panel 2 - Sidebar", new Color(60, 179, 113));
        panel3 = createColoredPanel("Panel 3 - Content", new Color(255, 206, 86));
        panel4 = createColoredPanel("Panel 4 - Secondary", new Color(147, 112, 219));
        panel5 = createColoredPanel("Panel 5 - Footer", new Color(220, 20, 60));

        mainPanel = new JPanel();
        add(mainPanel);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayoutAndTitle();
            }

            @Override
            public void componentMoved(ComponentEvent e) {            
                updateLayoutAndTitle();
            }
        });
        
        updateLayoutAndTitle();

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void updateLayoutAndTitle() {
        Dimension size = getSize();  // ukuran aktual termasuk border
        Insets insets = getInsets(); // border + title bar
        int innerWidth  = size.width  - insets.left - insets.right;
        int innerHeight = size.height - insets.top  - insets.bottom;
        
        String category = getDeviceCategory(innerWidth);
        setTitle(category + " | " + innerWidth + " x " + innerHeight + " px");        

        rebuildLayout(innerWidth);
    }

    private String getDeviceCategory(int width) {
        if (width <= COMPACT_VIEW_MAX) return "Compact View (≤576px)";
        else if (width <= SPLIT_VIEW_MAX) return "Split View (576-768px)";
        else if (width <= INTERMEDIATE_VIEW_MAX) return "Intermediate View (768-992px)";
        else if (width <= DESKTOP_SMALL_MAX) return "Desktop Small (992-1200px)";
        else if (width <= DESKTOP_STANDARD_MAX) return "Desktop Standard (1200-1400px)";
        else return "Desktop Large (>1400px)";
    }

    private void rebuildLayout(int width) {
        mainPanel.removeAll();
        mainPanel.setLayout(new MigLayout("fill, insets 0, gap 0"));

        if (width <= COMPACT_VIEW_MAX) {            
            mainPanel.add(panel1, "grow, wrap");
            mainPanel.add(panel2, "grow, wrap");
            mainPanel.add(panel3, "grow, push, h 300::, wrap");
            mainPanel.add(panel4, "grow, wrap");
            mainPanel.add(panel5, "grow, wrap");

        } else if (width <= SPLIT_VIEW_MAX) {
            mainPanel.add(panel1, "span 2, growx, wrap");
            mainPanel.add(panel2, "w 200!, growy");
            mainPanel.add(panel3, "grow, push, wrap");
            mainPanel.add(panel4, "span 2, growx, wrap");
            mainPanel.add(panel5, "span 2, growx, wrap");

        } else if (width <= INTERMEDIATE_VIEW_MAX) {
            mainPanel.add(panel1, "span 2, growx, wrap");
            mainPanel.add(panel2, "w 280!, growy");
            mainPanel.add(panel3, "grow, push, wrap, spany 2");
            mainPanel.add(panel4, "growx, h 180!, wrap");
            mainPanel.add(panel5, "span 2, growx, wrap");

        } else if (width <= DESKTOP_SMALL_MAX) {
            mainPanel.add(panel1, "span 3, growx, wrap");
            mainPanel.add(panel2, "growy, span 1 2, w 300!, h 100%"); // span 1 kolom × 2 baris
            mainPanel.add(panel3, "grow, push, wrap");                   
            mainPanel.add(panel4, "h 25%, growx, wrap");
            mainPanel.add(panel5, "span 3, growx");

        } else if (width <= DESKTOP_STANDARD_MAX) {
            mainPanel.add(panel1, "span 3, growx, wrap");
            mainPanel.add(panel2, "w 300!, growy");
            mainPanel.add(panel3, "grow, pushx 2, pushy");
            mainPanel.add(panel4, "grow, wrap");
            mainPanel.add(panel5, "span 3, growx, wrap");

        } else {
            mainPanel.add(panel1, "span 3, growx, wrap");
            mainPanel.add(panel2, "w 340!, growy");
            mainPanel.add(panel3, "grow, pushx 3, pushy");
            mainPanel.add(panel4, "grow, wrap");
            mainPanel.add(panel5, "span 3, growx, wrap");
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JPanel createColoredPanel(String title, Color background) {
        JPanel panel = new JPanel(new MigLayout("fill, insets 0")); 
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Inter", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        panel.add(label, "grow, align center"); 
        
        return panel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {            
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new UISwingResponsif().setVisible(true);
        });
    }
}
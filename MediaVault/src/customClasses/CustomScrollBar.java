package customClasses;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBar extends BasicScrollBarUI {
	private final int scrollSize = 10;
    private boolean hover = false;
    private boolean press = false;
    private final MouseAdapter mouseEvent;

    public static ComponentUI createUI(JComponent c) {
        return new CustomScrollBar();
    }

    public CustomScrollBar() {
        mouseEvent = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                press = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                if(scrollbar!=null) {
                	scrollbar.repaint();                	
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                if(scrollbar!=null) {
                	scrollbar.repaint();                	
                }
            }
        };
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setPreferredSize(new Dimension(scrollSize, scrollSize));
        c.addMouseListener(mouseEvent);
        c.setForeground(new Color(150, 150, 150));
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createInvisibleButton();
    }

    private JButton createInvisibleButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setVisible(false);
        return button;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(scrollbar.getForeground().brighter());
        g2.fill(new Rectangle2D.Double(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height));
        g2.dispose();
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (!scrollbar.isEnabled() || thumbBounds.width > thumbBounds.height) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = scrollbar.getForeground();
        if (press) {
            color = new Color(110, 110, 110);
        } else if (hover) {
            color = new Color(130, 130, 130);
        }

        g2.setColor(color);

        int arc = scrollSize;
        int x = thumbBounds.x + 2;
        int y = thumbBounds.y + 2;
        int width = thumbBounds.width - 4;
        int height = thumbBounds.height - 4;

        g2.fill(new RoundRectangle2D.Double(x, y, width, height, arc, arc));
        g2.dispose();
    }
}

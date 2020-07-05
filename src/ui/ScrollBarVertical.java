/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author kjcar
 */
public class ScrollBarVertical extends BasicScrollBarUI {
      private final Dimension d = new Dimension();
      @Override
      protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            private static final long serialVersionUID = -3592643796245558676L;
            @Override
              public Dimension getPreferredSize() {
                return d;
              }
            };
      }

      @Override
      protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            private static final long serialVersionUID = 1L;
        @Override
          public Dimension getPreferredSize() {
            return d;
          }
        };
      }

      @Override
      protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
      }

      @Override
      protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color color;
        JScrollBar sb = (JScrollBar) c;
        if (!sb.isEnabled() || r.width > r.height) {
          return;
        } else if (isDragging) {
          color = Color.DARK_GRAY; // change color
        } else if (isThumbRollover()) {
          color = Color.LIGHT_GRAY; // change color
        } else {
          color = Color.GRAY; // change color
        }
        g2.setPaint(color);
        g2.fillRoundRect(r.x+1+(r.width)/2, r.y, (r.width)/2, r.height, 2, 2);
        g2.setPaint(Interfaz.color3);
        g2.drawRoundRect(r.x+1+(r.width)/2, r.y, (r.width)/2, r.height, 2, 2);
        g2.dispose();
      }

      @Override
      protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
      }
    }

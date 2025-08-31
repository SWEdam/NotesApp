package com.snope.notes_app.assets.windows.main_windows.home.managers.ui;

import javax.swing.*;
import java.awt.*;

/*
   Warning: This class is an implementation for the Home class.
   - Do not use directly. Use Home's public methods instead.

 */

public class LayoutManager {

    public static Dimension forcePanelColumnLimit(JPanel panel, int limit) {

        if (!(panel.getLayout() instanceof FlowLayout)) {
            System.out.println("The 'forcePanelColumnLimit' function accepts FlowLayouts only.");
            return panel.getPreferredSize();
        }

        final int DEFAULT_HEIGHT = 100;

        if (panel.getParent() instanceof JViewport) {

            if (panel.getComponentCount() == 0) {
                return new Dimension(panel.getParent().getWidth(), DEFAULT_HEIGHT);
            }

            FlowLayout layout = (FlowLayout) panel.getLayout();
            int rows = (int) Math.ceil((double) panel.getComponentCount() / limit);
            int totalHeight = layout.getVgap() +
                    (rows * (panel.getComponent(0).getPreferredSize().height + layout.getVgap()));

            return new Dimension(panel.getParent().getWidth(), totalHeight);

        }

        return panel.getPreferredSize();
    }

}
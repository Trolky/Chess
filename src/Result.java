import jdk.jfr.Category;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Result extends JPanel {

    public Result(String winner, ArrayList<Double> wTime,ArrayList<Double> bTime, int chessTypePieces){
        JLabel label = new JLabel("The winner is "+winner+" player");
        JButton restart = new JButton("Restart");
        JFreeChart chart = ChartFactory.createBarChart("Time spend by player on moves","move","time (s)",createDataset(wTime,bTime));
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.restart(chessTypePieces);
            }
        });
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer)categoryPlot.getRenderer();
        renderer.setItemMargin(0);
        ChartPanel panel = new ChartPanel(chart);
        panel.setSize(new Dimension(100,100));
        this.setLayout(new GridLayout(3,0));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);
        this.add(panel);
        this.add(restart);
    }

    private CategoryDataset createDataset( ArrayList<Double> wTime, ArrayList<Double> bTime){
        final String white = "white";
        final String black = "black";

        final DefaultCategoryDataset dateset = new DefaultCategoryDataset();

        int max = Math.max(wTime.size(),bTime.size());
        for(int i = 0;i<max;i++){
            if(i>wTime.size()){
                dateset.addValue(0,white,""+(i+1));
                dateset.addValue(bTime.get(i),black,""+(i+1));
            }
            else if(i>bTime.size()){
                dateset.addValue(wTime.get(i),white,""+(i+1));
                dateset.addValue(0,black,""+(i+1));
            }
            else if(i<wTime.size()&&i<bTime.size()){
                dateset.addValue(wTime.get(i), white, "" + (i + 1));
                dateset.addValue(bTime.get(i), black, "" + (i + 1));
            }
        }
        return dateset;
    }
}

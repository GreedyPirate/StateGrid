package cn.sina.junit.jfreechart;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart {
	public static void main(String[] args) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("北京", 15);
		dataset.setValue("上海", 51);
		dataset.setValue("深圳", 35);
		dataset.setValue("杭州", 26);
		//不管是什么统计图,都是用ChartFactory来创建的,返回的都是JFreeChart
		JFreeChart chart = ChartFactory.createPieChart3D(
				"用户统计图(所属单位)", //上方的主标题
				dataset, //要显示的数据的集合
				true, //是否子标题(在最下面)
				true, //是否在图形上生成提示工具 
				true); //是否生成URL
		
		//处理标题乱码
		chart.getTitle().setFont(new Font("楷体", Font.BOLD, 20));
		//处理子标题乱码
		chart.getLegend().setItemFont(new Font("楷体", Font.BOLD, 18));
		
		//获取图形区域对象
		PiePlot3D piePlot3d = (PiePlot3D) chart.getPlot();
		piePlot3d.setLabelFont(new Font("楷体", Font.BOLD, 14));//这里处理的是北京,上海...等的乱码
		
		//显示数字和百分比
		String labelFormat = "{0} {1} ({2})";
		piePlot3d.setLabelGenerator(new StandardPieSectionLabelGenerator(labelFormat));
		
		//JFreeChart也是要用一个Frame来显示的
		ChartFrame frame = new ChartFrame("第一个柱状图", chart, true);
		frame.setLocation(500, 500);
		//设置可见
		frame.setVisible(true);
		//显示图形
		frame.pack();
	}
}

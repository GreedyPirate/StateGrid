package cn.sina.junit.jfreechart;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class LinearChart {
	public static void main(String[] args) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(20, "中国", "北京");
		dataset.addValue(5, "中国1", "北京");
		dataset.addValue(10, "中国", "上海");
		dataset.addValue(18, "中国1", "上海");
		dataset.addValue(22, "中国", "深圳");
		dataset.addValue(11, "中国1", "深圳");
		dataset.addValue(18, "中国", "杭州");
		dataset.addValue(30, "中国1", "杭州");
		
		dataset.addValue(15, "美国", "西雅图");
		dataset.addValue(18, "美国", "佛罗里达");
		dataset.addValue(2, "美国", "名古屋");
		
		//不管是什么统计图,都是用ChartFactory来创建的,返回的都是JFreeChart
		JFreeChart chart = ChartFactory.createLineChart(
				"用户统计图(所属单位)", //上方的主标题
				"所属单位", //X轴名称
				"人数",	//Y轴名称
				dataset, //要显示的数据的集合
				PlotOrientation.VERTICAL, //以垂直的柱状图显示
				true, //是否子标题(在最下面)
				true, //是否在图形上生成提示工具 
				true); //是否生成URL
		
		//处理标题乱码
		chart.getTitle().setFont(new Font("楷体", Font.BOLD, 20));
		//处理子标题乱码
		chart.getLegend().setItemFont(new Font("楷体", Font.BOLD, 18));
		
		//获取图形区域对象
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		//处理X轴名称乱码:获取X轴对象
		CategoryAxis axis = (CategoryAxis) plot.getDomainAxis();
		axis.setLabelFont((new Font("楷体", Font.BOLD, 16)));
		//这是处理X轴上元素的乱码
		axis.setTickLabelFont((new Font("楷体", Font.BOLD, 14)));
		
		//处理Y轴名称乱码
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		numberAxis.setLabelFont((new Font("楷体", Font.BOLD, 16)));
		//这是处理X轴上元素的乱码
		numberAxis.setTickLabelFont((new Font("楷体", Font.BOLD, 14)));
		
		//设置Y轴刻度为1,不然统计人数时出现0.5的情况
		numberAxis.setAutoRange(false);//取消自动设置刻度
		NumberTickUnit unit = new NumberTickUnit(1);
		numberAxis.setTickUnit(unit);
		
		//获取绘图区域对象
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		//图形上生成数字
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelFont(new Font("楷体", Font.BOLD, 14));
		renderer.setBaseItemLabelsVisible(true);
		/**
		 * 在拐点添加方块
		 */
		Shape shape = new Rectangle(10,10);
		renderer.setSeriesShape(0, shape);//设置第一条线的形状
		renderer.setSeriesShapesVisible(0, true);//设置为可见
		renderer.setSeriesShape(1, shape);//设置第一条线的形状
		renderer.setSeriesShapesVisible(1, true);//设置为可见
		renderer.setSeriesShape(2, shape);//设置第一条线的形状
		renderer.setSeriesShapesVisible(2, true);//设置为可见

		
		
		//JFreeChart也是要用一个Frame来显示的
		ChartFrame frame = new ChartFrame("第一个柱状图", chart, true);
		frame.setLocation(500, 500);
		//设置可见
		frame.setVisible(true);
		//显示图形
		frame.pack();
	}
}

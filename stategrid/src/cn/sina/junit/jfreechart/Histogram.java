package cn.sina.junit.jfreechart;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 柱状图
 * @author yj
 * @date 2015-12-6 下午9:26:15
 */
public class Histogram {
	public static void main(String[] args) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(20, "中国", "北京");
		dataset.addValue(10, "中国", "上海");
		dataset.addValue(22, "中国", "深圳");
		dataset.addValue(18, "中国", "杭州");
		
		dataset.addValue(15, "美国", "西雅图");
		dataset.addValue(18, "美国", "佛罗里达");
		dataset.addValue(2, "日本", "名古屋");
		
		//不管是什么统计图,都是用ChartFactory来创建的,返回的都是JFreeChart
		JFreeChart chart = ChartFactory.createBarChart3D(
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
		CategoryAxis3D axis3d = (CategoryAxis3D) plot.getDomainAxis();
		axis3d.setLabelFont(new Font("楷体", Font.BOLD, 16));
		//这是处理X轴上元素的乱码
		axis3d.setTickLabelFont(new Font("楷体", Font.BOLD, 14));
		
		//处理Y轴名称乱码
		NumberAxis3D numberAxis3D = (NumberAxis3D) plot.getRangeAxis();
		numberAxis3D.setLabelFont((new Font("楷体", Font.BOLD, 16)));
		//这是处理X轴上元素的乱码
		numberAxis3D.setTickLabelFont(new Font("楷体", Font.BOLD, 14));
		
		//设置Y轴刻度为1,不然统计人数时出现0.5的情况
		numberAxis3D.setAutoRange(false);//取消自动设置刻度
		NumberTickUnit unit = new NumberTickUnit(1);
		numberAxis3D.setTickUnit(unit);
		
		//获取绘图区域对象
		BarRenderer3D renderer3d = (BarRenderer3D) plot.getRenderer();
		renderer3d.setMaximumBarWidth(0.1);
		//让在图形上生成数字
		renderer3d.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer3d.setBaseItemLabelsVisible(true);
		renderer3d.setBaseItemLabelFont(new Font("宋体", Font.BOLD, 15));

		
		
		//JFreeChart也是要用一个Frame来显示的
		ChartFrame frame = new ChartFrame("第一个柱状图", chart, true);
		//设置可见
		frame.setVisible(true);
		//显示图形
		frame.pack();
		//生成图片
		File file = new File("G:/chart/demo.jpg");
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, 1024, 768);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

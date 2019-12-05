package THUgame.subevents;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import THUgame.datapack.DataPack;
import THUgame.event.EventBase;
import THUgame.main.EventManager;
import THUgame.tool.ImagePanel;
import javax.swing.SwingConstants;



public class EventEnd2050_1 extends EventBase{
	public void actOn(DataPack oldDataPack) {
		oldDataPack.count++;
		oldDataPack.stateA="<html>2050年9月20日，中国第二次核聚变发电5兆兆千瓦3000小时压力测试顺利结束，"
				+ "全国的火力、水力以及裂变发电站等等从此成为历史。目前，全国的传统发电站已经被取代90%。剩余10%也"
				+ "在有序地拆除，预计仅保留5%的核裂变发电站作为战略安全所需。可控核聚变的技术的掌握，尤其是小型化聚"
				+ "变反应堆的诞生，让中国一举成为世界上的超级强国，在航空航天、高能工业，军事技术等领域，我国已经拥"
				+ "有了绝对的话语权。可控核聚变的诞生使得人类进入无限能源的时代，彻底解决了世界上愈发激烈的经济矛盾"
				+ "和生产力矛盾，使得人类开始往自动化大生产方向发展，传统的生产关系受到巨大的冲击。\n" + 
				"</html>";
		oldDataPack.stateB="<html>"+oldDataPack.name+"。出生于1999年6月，中国可控核聚变项目总负责人，总工程师，安全总监，"
				+ "保密总监，在21世纪的新冷战局面下，带领着中国核能研究院的最顶尖的技术团队，上演了一出新时代的"
				+ "《马兰花开》。在四川绵阳地下4km处，他们隔绝天日，隐姓埋名20年，最终完成了这一项壮举。毕业于华"
				+ "清大学工程物理系的"+oldDataPack.name+"在大学生活中就潜心科研，本科的时候发表了多篇高水平论文。其丰富的课外经历"
				+ "更使其具有了丰富的团队管理经验。因此，大学博士毕业的时候，"+oldDataPack.name+"就已经有了一支经验丰富的科技团队，"
				+ "甚至已经承包了一座裂变核电站的核心建设工作。\n" + 
				"     在和本报记者聊到大学生活的时候，"+oldDataPack.name+"感叹的说：“如果不是当年本科的科研训练的帮助，"
				+ "我也不会有如此成就”。谈起将来的打算，他想：“将来有机会的话，准备给华清大学建"
				+ "一座微型可控剧变站点。这样冬天的夜里就不用断电了”</html>";
		
	}
}

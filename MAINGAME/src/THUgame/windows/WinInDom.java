package THUgame.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Font;

import THUgame.Game.MazeGame;
import THUgame.Game.RememberGame;
import THUgame.Game.ShootGame;
import THUgame.datapack.DataPack;
import THUgame.main.EventManager;
import THUgame.tool.ImagePanel;
import THUgame.tool.StateList;

/*
 * 【宿舍界面】
 * 
 * --DIALOG--
 * update:20191114
 * via：林逸晗
 * 更新：加入存档方法于基类，加入存档操作于宿舍
 * 
 * update:20191030
 * via：林逸晗
 * 更新：加入safeGuardCount
 * 
 * update:20191028 01:07
 * via：林逸晗
 * 更新：更改了界面UI，使之适配MAP，加入了游戏
 * 
 * update:20191018 01:07
 * via：余冬杰
 * 更新：加入了打呼噜的子事件
 * 
 * update:20191014 18:30
 * via：林逸晗
 * 更新：加入了游戏界面（在morning class的窗口里）
 * 		游戏panel直接添加就可以运行，但是需要注册一下数据包和控制进程（maingame）具体用法见morning
 * 		游戏panel在tool里有，在Game里有一个可运行的例程，现已更新完第一个game
 * 
 * update:20191010 18:30
 * via：林逸晗
 * 更新：加入了UI及使用方法
 * 		更新了按钮的UI和对话框的UI，设置UI的方法可以看本类中【按钮】和【对话框】的部分
 * 
 * update:20191010 18:30
 * via：林逸晗
 * 更新：加入对话框
 * 		加入对话的逻辑
 * 
 * update:20191006 18:30
 * via：林逸晗
 * 更新：加入了按钮的可见／不可见
 * 		加入了时钟（简陋）
 * 		鼠标事件响应不需要再写set game了，使用方法和dataPack的传递一样
 * 
 * update:20190930 18:30
 * via：林逸晗
 * 更新：解决了界面闪烁的问题
 * 		跟新了一些类的结构和注释的问题
 * 		注释中：//¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥***¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥表示最重要的事情
 * 		蓝色的部分则是表示对代码块的解释
 * 		细节用单句注释阐述
 * 		推荐收起后再看代码
 * 		推荐看WinInDom.java的注释
 * 
 **/


public class WinInDom extends WinBase{
	
	static JPanel studychoice;
	static JPanel messagePanel;
	static boolean showStudychoices=false;
	
	/*************************************************************	
	 *【内部的事件响应类】
	 *************************************************************/
	static private class demoMouseListener extends BaseMouseListener{
		static public DataPack dataPackage;
		static public EventManager mainGame;
		static public JFrame frame;
		private JButton button;
		private int mode;
		
		public demoMouseListener(int i){
			this.mode=i;
		}

		public void setButton(JButton button) {
			this.button=button;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			/*允许多次点击*/
			if(mode ==1){
				if(showStudychoices) {
					showStudychoices=false;
				}else {
					showStudychoices=true;
				}
				studychoice.setVisible(showStudychoices);//TODO:不知道为什么收不起来
				return;
			}
			if(mode ==8){
				if(showToDoList) {
					showToDoList=false;
					button.setText("查看更多");
				}else {
					showToDoList=true;
					button.setText("收起");
				}
				todolistPanel.setVisible(showToDoList);//TODO:不知道为什么收不起来
				return;
			}
			
			/*只允许单次点击*/
			if(safeGuardCount==0) {
				safeGuardCount++;
				if(mode==0) {
					dataPackage.choiceA="sleep";	//点按钮0（睡觉按钮）返回sleep
				}
				else if(mode ==2){
					dataPackage.choiceA="gooutside";//点按钮2（上课按钮）返回gotoclass
				}
				else if(mode ==3){
					dataPackage.choiceA="wakehimup";//点按钮3（唤醒按钮）返回wakehimup
				}
				else if(mode ==4){
					dataPackage.choiceA="stayup";//点按钮4（待着按钮）返回stayup
				}
				else if(mode ==5){
					if(dataPackage.stateA.equals("期末考")){
						dataPackage.choiceA="takeExam";//点按钮4（待着按钮）返回stayup
					}else if(dataPackage.stateA.equals("科研报名")){
						dataPackage.choiceA="readMessage_research_login";//点按钮4（待着按钮）返回stayup
					}else if(dataPackage.stateA.equals("报名结果")){
						dataPackage.choiceA="readMessage_research_result";//点按钮4（待着按钮）返回stayup
					}else if(dataPackage.stateA.equals("选课")){
						dataPackage.choiceA="need_course_reg";//点按钮4（待着按钮）返回stayup
					}else if(dataPackage.stateA.equals("每周报告")){
						messagePanel.setVisible(false);
						dataPackage.stateA=null;
					}
				}
				else if(mode ==6){
					dataPackage.choiceA="dohomework";//点按钮1（自习按钮）返回selfstudy
				}
				else if(mode ==7){
					dataPackage.choiceA="readpaper";//点按钮1（自习按钮）返回selfstudy
				}
				/*		END OF YOUR CODE		*/
				//¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥要刷新事件这部分一定要加¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥
				EventManager.dataPackage=dataPackage;
				synchronized(mainGame) {
					mainGame.notify();
				}
			}
			//¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥要刷新事件这部分一定要加¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	/*************************************************************
	 * 【构造函数】
	 *************************************************************/
	public WinInDom(EventManager mainGame,JFrame frame) {
		DataPack o=dataPackage;
		dataPackage.stateE=StateList.getEventNotification(o.term,o.week,o.date,
							o.joinResearch,o.joinClub,o.joinSTA,
							o.joinChallengeCup>0,o.joinSA);//生成提醒
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		this.mainGame=mainGame;
		/*************************************************************	
		 *【背景镶板】
		 *************************************************************/
		JPanel backgroundPanel=new JPanel();
		backgroundPanel.setBackground(Color.WHITE);
		backgroundPanel.setBounds(0, 0, 1080, 720);
		backgroundPanel.setLayout(null);
		
		/*************************************************************	
		 *【右下侧按钮】
		 *************************************************************/
		
		studychoice = new JPanel();
		studychoice.setBounds(660, 470, 150, 200);
		backgroundPanel.add(studychoice);
		studychoice.setOpaque(false);
		studychoice.setLayout(null);
		studychoice.setVisible(showStudychoices);
		
		JPanel choicepanel = new JPanel();
		choicepanel.setBounds(0, 0, 150, 200);
		studychoice.add(choicepanel);
		choicepanel.setOpaque(false);
		choicepanel.setLayout(null);
		
		JButton homeWork = new JButton();
		homeWork.setBorderPainted(false);
		homeWork.setBounds(25, 10, 75, 50);
		homeWork.setContentAreaFilled(false);
		setIcon("/imgsrc/Windom/homework.png",homeWork);
		setSelectedIcon("/imgsrc/Windom/homework_un.png",homeWork);
		choicepanel.add(homeWork);
		
		JButton paper = new JButton();
		paper.setBorderPainted(false);
		paper.setBounds(25, 70, 75, 50);
		paper.setContentAreaFilled(false);
		setIcon("/imgsrc/Windom/paper.png",paper);
		setSelectedIcon("/imgsrc/Windom/paper_un.png",paper);
		choicepanel.add(paper);
		
		JPanel imgpanel = new ImagePanel("imgsrc//Windom/choices.png",0, 0, 150, 200);
		imgpanel.setBounds(0, 0, 150, 200);
		studychoice.add(imgpanel);
		imgpanel.setOpaque(false);
		
		JButton sleepButton = new JButton();
		sleepButton.setBorderPainted(false);
		sleepButton.setBounds(819, 477, 150, 50);
		sleepButton.setContentAreaFilled(false);
		setIcon("/imgsrc/Windom/sleep.png",sleepButton);
		setSelectedIcon("/imgsrc/Windom/sleepUn.png",sleepButton);
		backgroundPanel.add(sleepButton);

		JButton selfstudyButton = new JButton();
		selfstudyButton.setBorderPainted(false);
		selfstudyButton.setBounds(819, 544, 150, 50);
		setIcon("/imgsrc/Windom/study.png",selfstudyButton);
		setSelectedIcon("/imgsrc/Windom/studyUn.png",selfstudyButton);
		backgroundPanel.add(selfstudyButton);
		
		JButton OutButton = new JButton();
		OutButton.setBorderPainted(false);
		OutButton.setBounds(819, 611, 150, 50);
		setIcon("/imgsrc/Windom/out.png",OutButton);
		setSelectedIcon("/imgsrc/Windom/out_press.png",OutButton);
		backgroundPanel.add(OutButton);
		
		
		
		/*************************************************************	
		 * 【小事件】 
		 *************************************************************/
		JPanel SnorePanel = new JPanel();	//1.跟打呼噜相关的小事件在这里触发
		SnorePanel.setOpaque(false);
		SnorePanel.setBounds(254, 129, 531, 363);
		backgroundPanel.add(SnorePanel);
		SnorePanel.setLayout(null);
		
		JPanel upperlevel = new JPanel();
		upperlevel.setOpaque(false);
		upperlevel.setBounds(0, 0, 531, 363);
		upperlevel.setLayout(null);
		
		JPanel background = new ImagePanel("imgsrc//对话框.png",0, 0, 531, 363);
		background.setOpaque(false);
		background.setBounds(0, 0, 531, 363);
		background.setLayout(null);
		
			JLabel label_1 = new JLabel("你被舍友的呼噜吵醒了，睡眠质量大跌");
			label_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			label_1.setBounds(95, 130, 285, 16);
		
			JLabel label_2 = new JLabel("好烦啊，要不要叫醒他");
			label_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			label_2.setBounds(95, 173, 388, 16);
			
			JButton wakeButton = new JButton();
			wakeButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			wakeButton.setBorderPainted(true);
			wakeButton.setBounds(130, 250, 120, 50);
			wakeButton.setText("叫醒舍友");
			
			JButton stayButton = new JButton();
			stayButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			stayButton.setBorderPainted(true);
			stayButton.setBounds(260, 250, 120, 50);
			stayButton.setText("保持沉默");
		
		upperlevel.add(label_1);
		upperlevel.add(label_2);
		upperlevel.add(wakeButton);
		upperlevel.add(stayButton);
			
		SnorePanel.add(upperlevel);
		SnorePanel.add(background);
		
		messagePanel = new JPanel();//2.跟转场相关的小事件在这里触发
		messagePanel.setOpaque(false);
		messagePanel.setBounds(254, 129, 531, 363);
		backgroundPanel.add(messagePanel);
		messagePanel.setLayout(null);
		
		JPanel messageupperlevel = new JPanel();
		messageupperlevel.setOpaque(false);
		messageupperlevel.setBounds(0, 0, 531, 363);
		messageupperlevel.setLayout(null);
		
		JPanel messagebackground = new ImagePanel("imgsrc//对话框.png",0, 0, 531, 363);
		messagebackground.setOpaque(false);
		messagebackground.setBounds(0, 0, 531, 363);
		messagebackground.setLayout(null);
		
			JLabel messagelabel_1 = new JLabel();
			messagelabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			messagelabel_1.setBounds(100, 50, 300, 200);

			JButton messageButton = new JButton();
			messageButton.setBorderPainted(true);//TODO：GUI还没设计
			messageButton.setBounds(197, 250, 120, 50);		
		
		messageupperlevel.add(messagelabel_1);
		messageupperlevel.add(messageButton);	
		messagePanel.add(messageupperlevel);
		messagePanel.add(messagebackground);
		
		RememberGame.mainGame=mainGame;//3.Game 注意这里！不然没办法结束游戏！
		RememberGame.dataPackage=dataPackage;//注意这里！不然没办法结束游戏！
		MazeGame.mainGame=mainGame;//3.Game 注意这里！不然没办法结束游戏！
		MazeGame.dataPackage=dataPackage;//注意这里！不然没办法结束游戏！
		
		JPanel Maze = new MazeGame(254, 134);
		Maze.setBounds(254, 134, 536, 398);
		Maze.setLayout(null);
		Maze.setOpaque(false);//注意要设成透明的
		backgroundPanel.add(Maze);
		
		JPanel Remember = new RememberGame(254, 134);
		Remember.setBounds(254, 134, 536, 398);
		Remember.setLayout(null);
		Remember.setOpaque(false);//注意要设成透明的
		backgroundPanel.add(Remember);
		
		messagePanel.setVisible(false); 
		SnorePanel.setVisible(false); 
		Remember.setVisible(false);
		Maze.setVisible(false);
		
		if(dataPackage.week==1 && dataPackage.date==1 && dataPackage.course_selected==false) {
			dataPackage.trigSubEvent=true;
			dataPackage.stateA="选课";
			dataPackage.course_selected=true;
		}
		if (dataPackage.trigSubEvent){ // 触发子事件，小事情可见。。  d
			if(dataPackage.stateA.equals("game")) {
				Random r = new Random();
				int dice = r.nextInt(10);
				if(dice<4) {
					Remember.setVisible(true);
				}
				else {
					Maze.setVisible(true);
				}
			}else if(dataPackage.stateA.equals("被吵醒")){
				SnorePanel.setVisible(true);
			}else if(dataPackage.stateA.equals("期末考")){
				messageButton.setText("出发去考点");
				messagelabel_1.setText("<html>天哪期末考要开始了！差点就睡过头了，快点赶去考试！</html>");
				messagePanel.setVisible(true);
			}else if(dataPackage.stateA.equals("每周报告")){
				messageButton.setText("关闭");
				messagelabel_1.setText("<html>这周又是精彩的一周</html>");
				messagePanel.setVisible(true);
			}else if(dataPackage.stateA.equals("科研报名")){
				messageButton.setText("查看消息");
				messagelabel_1.setText("<html>咦？收到了一条消息</html>");
				messagePanel.setVisible(true);
			}else if(dataPackage.stateA.equals("报名结果")){
				messageButton.setText("查看消息");
				messagelabel_1.setText("<html>咦？收到了一条消息</html>");
				messagePanel.setVisible(true);
			}else if(dataPackage.stateA.equals("选课")){
				messageButton.setText("选课系统");
				messagelabel_1.setText("<html>今天是新学期的第1天，我需要去选课系统选一下课程。9102年的选课系统真不赖，一选完课就能出结果了哦～</html>");
				messagePanel.setVisible(true);
			}
			sleepButton.setVisible(false);
			selfstudyButton.setVisible(false);
			OutButton.setVisible(false);
			studychoice.setVisible(false);
		}
		
		/*************************************************************	
		 * 【镶时钟】
		 *************************************************************/
		JPanel timePack = new JPanel();
		timePack.setLayout(null);
		timePack.setOpaque(false);//注意要设成透明的
		timePack.setBounds(66, 32, 195, 90);
		
			JPanel timePanel = new JPanel();
			timePanel.setBounds(0, 0, 195, 90);
			JPanel timeBackgoundPanel = new ImagePanel("imgsrc//taili.png",0, 0, 195, 90);	
			timeBackgoundPanel.setBounds(0, 0, 195, 90);
			
			timeBackgoundPanel.setOpaque(false);//注意要设成透明的
			timePanel.setOpaque(false);//注意要设成透明的
			timePanel.setLayout(null);
			
			JLabel timeText = new JLabel("当前时间为："+String.valueOf(dataPackage.time)+" 时");
			timeText.setBounds(6, 60, 172, 16);
			timePanel.add(timeText);
			timeText.setFont(new Font("STFangsong", Font.BOLD, 14));
			
			JLabel dateText = new JLabel("今天是：第"+String.valueOf(dataPackage.term)+"学期"+String.valueOf(dataPackage.week)+"周"+String.valueOf(dataPackage.date)+"日");
			dateText.setBounds(6, 35, 172, 16);
			timePanel.add(dateText);
			dateText.setFont(new Font("STFangsong", Font.BOLD, 14));
			
		timePack.add(timePanel);
		timePack.add(timeBackgoundPanel);
		backgroundPanel.add(timePack);
		/*************************************************************	
		 * 【镶属性】
		 *************************************************************/

		JPanel panel = new JPanel();
		panel.setBounds(64, 140, 197, 290);
		backgroundPanel.add(panel);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panel.setLayout(null);
		
		JLabel StudentIDLable = new JLabel("学号：");
		StudentIDLable.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		StudentIDLable.setBounds(10, 78, 48, 16);
		panel.add(StudentIDLable);
		
		JLabel nameShow = new JLabel();
		nameShow.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		nameShow.setBounds(70, 45, 114, 20);
		panel.add(nameShow);
		
		JLabel nameLable = new JLabel("姓名：");
		nameLable.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		nameLable.setBounds(10, 42, 48, 24);
		panel.add(nameLable);
		
		JLabel IDshow = new JLabel();
		IDshow.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		IDshow.setBounds(70, 76, 114, 20);
		panel.add(IDshow);
		
		JProgressBar healthBar = new JProgressBar();
		healthBar.setBounds(70, 119, 114, 20);
		panel.add(healthBar);
		
		JProgressBar Bar_progress = new JProgressBar();
		Bar_progress.setBounds(70, 140, 114, 20);
		panel.add(Bar_progress);
		
		JProgressBar Bar_Energy = new JProgressBar();
		Bar_Energy.setBounds(70, 165, 114, 20);
		panel.add(Bar_Energy);
		
		JProgressBar Bar_happiness = new JProgressBar();
		Bar_happiness.setBounds(70, 187, 114, 20);
		panel.add(Bar_happiness);
		
		JLabel label_workProgress = new JLabel("学习进度");
		label_workProgress.setBounds(10, 142, 60, 16);
		label_workProgress.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		panel.add(label_workProgress);
		
		JLabel label_Energy = new JLabel("体力值");
		label_Energy.setBounds(10, 166, 52, 16);
		label_Energy.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		panel.add(label_Energy);
		
		JLabel label_health = new JLabel("健康值");
		label_health.setBounds(10, 118, 52, 16);
		label_health.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		panel.add(label_health);
		
		JLabel label_happy = new JLabel("心   情");
		label_happy.setBounds(10, 189, 52, 16);
		label_happy.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		panel.add(label_happy);
		
		JLabel label_social = new JLabel("社交能力:");
		label_social.setBounds(10, 219, 92, 16);
		label_social.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		panel.add(label_social);
		
		
		JLabel label_Art = new JLabel("才艺能力:");
		label_Art.setBounds(10, 245, 92, 16);
		label_Art.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		panel.add(label_Art);
		
		JLabel label_IQ = new JLabel("智商:");
		label_IQ.setBounds(100, 219, 84, 16);
		label_IQ.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		panel.add(label_IQ);
		
		JLabel label_lucky = new JLabel("幸运值:");
		label_lucky.setBounds(100, 245, 84, 16);
		label_lucky.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		panel.add(label_lucky);
		
		/*************************************************************	
		 * 【镶对话框】
		 *************************************************************/
		
		JPanel dialogPack = new JPanel();
		dialogPack.setBounds(66, 475, 689, 189);
		dialogPack.setOpaque(false);//注意要设成透明的
		dialogPack.setLayout(null);
		
			JPanel dialogPanel = new JPanel();//第1个subPanel，放控件
			dialogPanel.setBounds(0, 0, 689, 189);//(0, 0, 宽, 高);
			
			JPanel dialogBackgoundPanel = new ImagePanel("imgsrc//Dialog.png",0, 0, 689, 189);	//第2个subPanel，放图
			dialogBackgoundPanel.setBounds(0, 0, 689, 189);//(0, 0, 宽, 高);
			dialogBackgoundPanel.setOpaque(false);//注意要设成透明的
			dialogPanel.setOpaque(false);		//注意要设成透明的
			dialogPanel.setLayout(null);
			
			JLabel dialogName = new JLabel();
			dialogName.setBounds(17, 6, 89, 40);
			dialogPanel.add(dialogName);
			dialogName.setText(dataPackage.name);
			
			JLabel dialogContent = new JLabel();
			dialogName.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			dialogContent.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			dialogContent.setBounds(15, 42, 677, 141);
			dialogPanel.add(dialogContent);
			
			if (!dataPackage.notification.equals(""))//设置对话内容
				dialogContent.setText(dataPackage.notification);
			else
				dialogContent.setText("回到了宿舍～");//设置默认对话内容
		
		dialogPack.add(dialogPanel);//注意：先放的在上层，所以先放带控件的
		dialogPack.add(dialogBackgoundPanel);
		backgroundPanel.add(dialogPack);
		
		/*************************************************************	
		 * 镶待办事项 这一部分按照流程做的话就会自然消失的
		 *************************************************************/
		
		todolistPanel = new JPanel();//2.跟转场相关的小事件在这里触发
		todolistPanel.setBounds(360, 120, 215, 337);
		backgroundPanel.add(todolistPanel);
		todolistPanel.setVisible(showToDoList);
		todolistPanel.setOpaque(false);//注意要设成透明的
		todolistPanel.setLayout(null);
			
			JPanel todolistPanelText = new JPanel();//2.跟转场相关的小事件在这里触发
			todolistPanelText.setBounds(0, 0, 215, 337);
			todolistPanel.add(todolistPanelText);
			todolistPanelText.setLayout(null);
			todolistPanelText.setOpaque(false);//注意要设成透明的
			
			JLabel todolistExtra = new JLabel();
			todolistExtra.setForeground(Color.BLACK);
			todolistExtra.setBounds(40, 25, 150, 250);
			todolistPanelText.add(todolistExtra);
			todolistExtra.setFont(new Font("STFangsong", Font.BOLD, 16));
			todolistExtra.setText(dataPackage.stateE);
			
			JPanel todolistPanelBackground =  new ImagePanel("imgsrc//Windom//dbsx.png",0, 0,215, 337);//2.跟转场相关的小事件在这里触发
			todolistPanelBackground.setBounds(0, 0, 215, 337);
			todolistPanel.add(todolistPanelBackground);
			todolistPanelBackground.setLayout(null);
			todolistPanelBackground.setOpaque(false);//注意要设成透明的
			
		backgroundPanel.add(todolistPanel);
			
		JPanel todoList = new JPanel();
		todoList.setLayout(null);
		todoList.setOpaque(false);	
		todoList.setBounds(752, 35, 263, 189);
			
			JLabel label = new JLabel("待办事项");
			label.setForeground(Color.WHITE);
			label.setBounds(20, 25, 100, 18);
			todoList.add(label);
			label.setFont(new Font("STFangsong", Font.PLAIN, 16));
				
			JLabel label2 = new JLabel("1.上午课:"+dataPackage.todayMorningClass);
			label2.setForeground(Color.WHITE);
			label2.setBounds(20, 55, 200, 18);
			todoList.add(label2);
			label2.setFont(new Font("STFangsong", Font.PLAIN, 16));
				
			JLabel label3 = new JLabel("2.下午课:"+dataPackage.todayAfternoonClass);
			label3.setForeground(Color.WHITE);
			label3.setBounds(20, 85, 200, 18);
			todoList.add(label3);
			label3.setFont(new Font("STFangsong", Font.PLAIN, 16));
				
			JLabel label4 = new JLabel("3.");
			label4.setForeground(Color.WHITE);
			label4.setBounds(20, 115, 20, 20);
			todoList.add(label4);
			label4.setFont(new Font("STFangsong", Font.PLAIN, 16));
			
			JButton readToDoList = new JButton("查看更多");
			readToDoList.setBounds(35, 113, 100, 34);
			todoList.add(readToDoList);
			backgroundPanel.add(todoList);
			
			JPanel dbsxBackgruond = new ImagePanel("imgsrc//todoList.png",0, 0, 263, 189);
			dbsxBackgruond.setOpaque(false);	
			dbsxBackgruond.setBounds(0, 0, 263, 189);
		
		todoList.add(dbsxBackgruond);
		dbsxBackgruond.setLayout(null);
		
		/*************************************************************	
		 * 【放背景图】
		 * 		最后放。
		 *************************************************************/
		
		JPanel Background=new ImagePanel("imgsrc//Windom/dom3.jpg",0, 0, 1080, 720);
		if(dataPackage.choiceA.equals("sleep")) {
			Background=new ImagePanel("imgsrc//Windom/dom2.jpg",0, 0, 1080, 720);
		}else if(dataPackage.choiceA.equals("readpaper") || dataPackage.choiceA.equals("dohomework")) {
			Background=new ImagePanel("imgsrc//Windom/dom1.jpg",0, 0, 1080, 720);
		}
		Background.setBounds(0, 0, 1080, 720);
		backgroundPanel.add(Background);
		Background.setLayout(null);
		/*****************************************************************				
		 * 
		 * 【细节设定】
		 * 在用插件绘制完界面之后进行界面内数值设定
		 * 利用数据包进行显示控件的输出的设置
		 * 
		 *****************************************************************/
		/*		START OF YOUR CODE		*/
		
		Bar_progress.setValue(dataPackage.studyProgress);
		Bar_Energy.setValue(dataPackage.characterEnergy);
		Bar_happiness.setValue(dataPackage.characterHappiness);
		healthBar.setValue(dataPackage.characterHealth);//进度条设置进度
		Bar_progress.setStringPainted(true);
		Bar_Energy.setStringPainted(true);//开启进度条显示字
		Bar_happiness.setStringPainted(true);
		healthBar.setStringPainted(true);
		Bar_progress.setString(String.format("%d",dataPackage.studyProgress));
		Bar_Energy.setString(String.format("%d",dataPackage.characterEnergy));
		Bar_happiness.setString(String.format("%d",dataPackage.characterHappiness));
		healthBar.setString(String.format("%d",dataPackage.characterHealth));//进度条显示字
		IDshow.setText(dataPackage.studentID);//显示学号
		nameShow.setText(dataPackage.name);//显示名字
		
		label_social.setText("社交能力:"+dataPackage.characterEQ);
		label_Art.setText("才艺能力:"+dataPackage.characterArt);
		label_IQ.setText("智商:"+dataPackage.characterIQ);
		label_lucky.setText("幸运值:"+dataPackage.characterlucky);

		/*********************************************			
		 * 【鼠标动作的设置】
		 ********************************************/
		demoMouseListener.dataPackage=dataPackage;//数据包注册，不需要改
		demoMouseListener.mainGame=mainGame;
		
		demoMouseListener clicksleep=new demoMouseListener(0);//设置鼠标监听器，发生0号事件
		demoMouseListener clickselfstudy=new demoMouseListener(1);//设置鼠标监听器，发生1号事件
		demoMouseListener clickOut=new demoMouseListener(2);//设置鼠标监听器，发生2号事件
		demoMouseListener clickwake=new demoMouseListener(3);//设置鼠标监听器，发生3号事件
		demoMouseListener clickstay=new demoMouseListener(4);//设置鼠标监听器，发生4号事件
		demoMouseListener clickexam = new demoMouseListener(5);//设置鼠标监听器，发生4号事件
		demoMouseListener clickhomework=new demoMouseListener(6);//设置鼠标监听器，发生4号事件
		demoMouseListener clickpaper = new demoMouseListener(7);//设置鼠标监听器，发生4号事件
		demoMouseListener clickToDoList = new demoMouseListener(8);//设置鼠标监听器，发生4号事件

		clickexam.setButton(messageButton);
		clicksleep.setButton(sleepButton);
		clickselfstudy.setButton(selfstudyButton);
		clickOut.setButton(OutButton);
		clickwake.setButton(wakeButton);
		clickstay.setButton(stayButton);
		clickhomework.setButton(homeWork);
		clickpaper.setButton(paper);
		clickToDoList.setButton(readToDoList);
		
    	sleepButton.addMouseListener(clicksleep);//0号事件是 睡觉按钮 被点击
		selfstudyButton.addMouseListener(clickselfstudy);//1号事件是 去自习按钮 被点击
		OutButton.addMouseListener(clickOut);//2号事件是 去上课按钮 被点击
		wakeButton.addMouseListener(clickwake);//3号事件是 叫醒舍友 被点击
		stayButton.addMouseListener(clickstay);//4号事件是 按兵不动 被点击
		messageButton.addMouseListener(clickexam);//5号事件是 出发考试 被点击
		homeWork.addMouseListener(clickhomework);//6号事件是 出发考试 被点击
		paper.addMouseListener(clickpaper);//7号事件是 出发考试 被点击
		readToDoList.addMouseListener(clickToDoList);//7号事件是 出发考试 被点击
		/*		END OF YOUR CODE		*/
    	    	
    	/*****************************************************************				
		 * 【恢复显示】
		 * 必须存在的四行代码！！！！
		 ******************************************************************/
		//¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥这部分不允许改¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥
		frame.getContentPane().removeAll();
		frame.getContentPane().add(backgroundPanel);
		frame.getContentPane().repaint();
    	frame.setVisible(true);
    	//¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥这部分不允许改¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥
	}
}
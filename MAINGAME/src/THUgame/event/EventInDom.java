package THUgame.event;
import java.util.Random;

import javax.swing.JOptionPane;

import THUgame.datapack.DataPack;
import THUgame.tool.StateList;


/*
 * 宿舍事件 * 
 * 
 * --DIALOG--
 * update:20191114
 * via：林逸晗
 * 更新：加入自动存档
 * 
 *  update:20191018
 * via：余冬杰
 * 更新：加入打呼噜
 * 
 * update:20191014
 * via：林逸晗
 * 更新：加入游戏逻辑
 * 
 * update:20191010
 * via：林逸晗
 * 更新：加入对话逻辑，使用notification存储对话的信息 
 * 
 * update:20191006 
 * via：林逸晗
 * 更新：把事件切换移除，统一在主线程里管理
 *	    计数器更换为时间，更合理
 *      完善注释
 *      增加了主要循环的事件分支，完善了 上午课-下午课-睡觉的主要循环
 *  
 * update:20190930 30
 * via：林逸晗
 * 更新：添加了“是否要让小事件显示”的属性的判断
 * 
 * */

public class EventInDom extends EventBase{

	public void actOn(DataPack oldDataPack) {
		this.oldDataPack=oldDataPack;
		/*******************************************
		 * 事件结束
		 * 		转换一个标记，必要时存储一些信息
		 *******************************************/
		if (oldDataPack.choiceA.equals("gooutside")) {
			oldDataPack.eventFinished=true;			
			return;									//直接返回，避免属性乱变
		}
		if (oldDataPack.choiceA.equals("takeExam")) {
			oldDataPack.eventFinished=true;			
			return;									//直接返回，避免属性乱变
		}
		if (oldDataPack.choiceA.equals("readMessage_research_login")) {
			oldDataPack.eventFinished=true;			
			return;									//直接返回，避免属性乱变
		}
		if (oldDataPack.choiceA.equals("readMessage_research_result")) {
			oldDataPack.eventFinished=true;			
			return;									//直接返回，避免属性乱变
		}
		if (oldDataPack.choiceA.equals("need_course_reg")) {
			oldDataPack.eventFinished=true;			
			return;									//直接返回，避免属性乱变
		}
		if(oldDataPack.term==5) {
			oldDataPack.eventFinished=true;			
			oldDataPack.choiceA="endgame";
			return;
		}
		if(oldDataPack.term<4 && oldDataPack.date==7 && oldDataPack.week==4) {
			oldDataPack.course_selected=false;
		}
		else if(oldDataPack.term==4) {
			oldDataPack.course_selected=true;
		}
		
		/*		START OF YOUR CODE		*/	
		Random r = new Random();
		int randomSnore = r.nextInt(10) + 1;  // 生成被吵醒的随机数
		int randomGame = r.nextInt(10) + 1;  // 生成被吵醒的随机数
		boolean isNewday=false;
		
		switch(oldDataPack.choiceA) {
			case "sleep":
				oldDataPack.notification="<html>转眼过去了3个小时，睡醒了！头脑有些不清醒，但是健康和体力都增加了，人的心情也变好了。";
				if(oldDataPack.time<24 && oldDataPack.time>6){
					oldDataPack.time+=3;		//小憩一会儿，时间+2
					oldDataPack.characterEnergy+=20;
					oldDataPack.characterHealth+=5;
					oldDataPack.characterHappiness+=5;
					oldDataPack.notification += "<br>心情值+2，健康值+2，体力回复10点</html>";
				}else if(randomSnore <= 5 && oldDataPack.time<3 ) {// 0点睡觉，50%触发被呼噜打醒
					oldDataPack.time=3;			//3点被吵醒
					oldDataPack.trigSubEvent = true; // 触发子事件
					oldDataPack.stateA="被吵醒";
					oldDataPack.notification += "<br>天哪！！！现在是三点！！</html>";
				}else { // 一日之际在于晨
					oldDataPack.trigSubEvent = false;  // 吵醒之后睡着，取消子事件
					oldDataPack.time=8;	
					oldDataPack.characterEnergy=100;
					oldDataPack.characterHealth+=5;
					oldDataPack.characterHappiness+=5;
					oldDataPack.notification += "<br>新的一天开始了，快看看今天有什么要做的事儿吧~<br>健康+10，心情+5，体力回复满。[游戏已保存]</html>";
					isNewday=true;
				}
				break;
			case "dohomework":
				if(oldDataPack.characterEnergy<5) {
					oldDataPack.notification="我没有力气读书了。";
					break;
				}if(oldDataPack.characterHealth<10) {
					oldDataPack.notification="我身体很不舒服。";
					break;
				}if(3<=oldDataPack.time && oldDataPack.time<8) {
					oldDataPack.notification="时间不早了，舍友早已睡着，我还是熄灯吧";
					break;
				}else{
					oldDataPack.time+=3;		//自习需要耗时，时间+2（原本的版本是计数器+2）
					oldDataPack.notification="<html>写了3个小时作业，身体变得有些疲劳";
					oldDataPack.characterEnergy-=20;
					oldDataPack.characterHappiness-=4;
					oldDataPack.studyProgress+=2;
					oldDataPack.notification += "<br>学习进度+2，心情值-4，体力消耗20点</html>";
					if(randomGame <= 3) {
						oldDataPack.stateA="game";	
						oldDataPack.trigSubEvent = true; // 触发子事件
						saveGame();
					}
					break;
				}
			case "wakehimup":
				oldDataPack.trigSubEvent = false;  // 吵醒之后叫醒，取消子事件
				oldDataPack.notification="<html>虽然感觉安静了些，但还是觉得有些尴尬，社交力和心情稍稍下降了";
				oldDataPack.characterHappiness -= 2;
				oldDataPack.characterEQ -= 1;
				oldDataPack.characterHealth -= 5;
				oldDataPack.characterEnergy += 5;
				oldDataPack.notification += "<br>社交力-1，健康值-5，心情值-2，体力回复5点</html>";
				break;
			case "stayup":
				oldDataPack.trigSubEvent = false;  // 吵醒之后待着，取消子事件
				oldDataPack.notification = "<html>后面睡得都不太好，白天告诉了舍友，舍友非常抱歉";
				oldDataPack.characterEQ += 1;
				oldDataPack.characterHealth -= 5;
				oldDataPack.characterHappiness -= 5;
				oldDataPack.characterEnergy += 5;	
				oldDataPack.notification += "<br>社交力+1，健康值-5，心情值+5，体力回复5点</html>";
				break;
			case "readpaper":
				if(3<=oldDataPack.time && oldDataPack.time<8) {
					oldDataPack.notification="时间不早了，舍友早已睡着，我还是熄灯吧";
					break;
				}
				if(oldDataPack.joinResearch)
					oldDataPack.eventFinished=true;
				else {
					oldDataPack.characterEnergy -= 5;	
					oldDataPack.notification = "<html>我还没有参加SRT（student research training），没有老师指导，实在是看不懂论文";
					oldDataPack.notification += "<br>体力白白浪费了5点</html>";
				}
				break;
		}
		
		if(isNewday) {
			if(oldDataPack.week==1 && oldDataPack.date==4) {
				oldDataPack.stateA="招新报名";
				//oldDataPack.trigSubEvent = true; // 触发子事件
			}
			if(oldDataPack.date==7) {
				oldDataPack.stateA="每周报告";
				oldDataPack.trigSubEvent = true; // 触发子事件
			}
			if(oldDataPack.week==4 && oldDataPack.date==6) {
				oldDataPack.stateA="期末考";
				oldDataPack.trigSubEvent = true; // 触发子事件
			}
			if(oldDataPack.term==3 && oldDataPack.week==1 && oldDataPack.date==2) {
				oldDataPack.stateA="科研报名";
				oldDataPack.trigSubEvent = true; // 触发子事件
			}
			if(oldDataPack.term==3 && oldDataPack.week==1 && oldDataPack.date==4 && oldDataPack.joinResearch){
				oldDataPack.stateA="报名结果";
				oldDataPack.trigSubEvent = true; // 触发子事件
			}
			saveGame();
		}

		if (oldDataPack.characterHealth<=0)
			JOptionPane.showMessageDialog(null, "你猝死了", "", JOptionPane.ERROR_MESSAGE);//弹出猝死界面
		
		DataPack o=oldDataPack;
		oldDataPack.stateE=StateList.getEventNotification(o.term,o.week,o.date,
							o.joinResearch,o.joinClub,o.joinSTA,
							o.joinChallengeCup>0,o.joinSA);//生成提醒
		
		/*		END OF YOUR CODE		*/
		return;
	}
}

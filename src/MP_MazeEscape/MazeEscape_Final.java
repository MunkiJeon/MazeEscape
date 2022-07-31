package MP_MazeEscape;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class MazeEscape_Final extends JFrame implements KeyListener {
	JPanel titleBar, Item_State, Player, Feature, Map, Char_ax;
	JLabel HP_lb, Item_lb,key_lb,speed_lb,heart_lb, player_img, map_lb, FT_LB, Time_lb,
	item_boot1,item_boot2,item_key,item_Stime1,item_Stime2,item_Stime3, finish, Potal;
	JButton restartBtn, startBtn, finishBtn, WinBtn, restartBtnOnPopup;
	
	int Pan_X = 800, Pan_Y = 600, Limit =180, AddTime=20, 
		Map_X = -1014, Map_Y = -1625, MapSize_X = 2000,MapSize_Y = 2400,
		Speed = 5, i=0, t=0,k=0;
	
	boolean gameState = false, timeAddCheck = false, UD_Break, LR_Break;
	
	String [] Player_img= {"Player/stop_L.gif","Player/stop_R.gif",
						"Player/walk_L.gif","Player/walk_R.gif",
						"Player/proneStab_L.gif","proneStab_R.gif",
						"Player/rope.gif","Player/dead.gif"};
	ImageIcon player_IC = new ImageIcon("MazeEscape_img/"+Player_img[0]);
	
	int player_Ic_X=player_IC.getIconWidth()+5,player_Ic_Y=player_IC.getIconHeight()+5,
		player_X = Pan_X/2-player_Ic_X, player_Y = Pan_Y/2-player_Ic_Y;
	
	Timer timeRun = new Timer();
	
	String [] block_img = {"block/yellow_block_new.png","block/yellow_block.png",
			"block/purple_block.png","block/big_purple_block.png","block/Base.png"
			};
	
	String [] ladder_img = {"ladder/blue_ladder_7.png","ladder/blue_ladder_9.png",
			"ladder/blue_ladder_13.png","ladder/yellow_ladder_5.png",
			"ladder/yellow_ladder_7.png","ladder/red_ladder_11.png"
			};

	String [] dcr_images = {
			"decobox/onebox_1.png","decobox/onebox_2.png","decobox/onebox_3.png",
			"decobox/twoBox.png","decobox/threebox.png"
			};
	
	String [] Map_img= {"MazeEscape_img/Map/Dark_map_00.png",
			};
	
	String [] Potal_gif = {"MazeEscape_img/Map/potal.gif"};
	
	Object [][] feature_Block= {
			{0,1920,block_img[4]},{0,1420,block_img[4]},{0,910,block_img[4]},{0,395,block_img[4]},
			{1200,1735,block_img[0]},{310,1735,block_img[1]},{854,1500,block_img[0]},{1190,1630,block_img[0]},
			{400,1280,block_img[0]},{400,1032,block_img[2]},{700, 990, block_img[2]},{950,1230,block_img[1]},
			{925,1095,block_img[0]},{1435,1095,block_img[2]},{830,751,block_img[1]},{300,519,block_img[3]},
			{1418,519,block_img[2]}
	};
	Object [][] feature_ladder= {
			{1205,1735,ladder_img[0]},{369,1735,ladder_img[0]},{890,1495,ladder_img[1]},{1194,1500,ladder_img[3]},
			{1540,1410,ladder_img[1]},{560,1280,ladder_img[3]},{475,1032,ladder_img[5]},{757,990,ladder_img[2]},
			{1260,1227,ladder_img[4]},{1000,1095,ladder_img[3]},{1530,1095,ladder_img[3]},{1200,900,ladder_img[4]},
			{1000,751,ladder_img[3]},{830,509,ladder_img[1]},{1430,515,ladder_img[1]},{383,385,ladder_img[3]}
	};
	
	Object [][] feature_dcr= {
			{1535,1695,dcr_images[0]},{369,1735,ladder_img[0]},{420, 953 ,dcr_images[4]},{755, 955, dcr_images[2]},
			{1450, 1055, dcr_images[1]},{1512, 870, dcr_images[2]},{1484, 441, dcr_images[3]}
	};
	
	
	ImageIcon im	= new ImageIcon("MazeEscape_img/Item/boot.png"),
			  im2 	= new ImageIcon("MazeEscape_img/Item/key.png"),
			  im3 	= new ImageIcon("MazeEscape_img/Item/SandTimer.png"),
			  potal_ic = new ImageIcon("MazeEscape_img/Map/potal.gif");;
	
	class Feature_lb extends JLabel{
		int x,y;
		String img;
		public Feature_lb(Object x, Object y, Object img) {
			this.img = (String)(img);
			this.x = (int)(x);
			this.y = (int)(y);
			ImageIcon ICon = new ImageIcon("MazeEscape_img/Map/feature/"+this.img);
			
			int IC_X=ICon.getIconWidth()+5,IC_Y=ICon.getIconHeight()+5;
			setIcon(ICon);
			setBounds(this.x,this.y,IC_X,IC_Y);
			setOpaque(false);
		}
	}
	
	public MazeEscape_Final() {	//기본 배경 설정
		super("Potato MazeEscape");
		
		JDialog StartPopup = new JDialog(this, "Potato MazeEscape", true);
		StartPopup.setBounds(Pan_X/2, Pan_Y/2, 340, 150);
		StartPopup.setLayout(new FlowLayout());
		
		StartPopup.add(new JLabel("스피드 아이템: 스피드+5 | 시간 아이템: 시간+20초"));
		StartPopup.add(new JLabel("★중요★ <열쇠 아이템을 찾아야지만 탈출 할 수 있습니다> "));
		StartPopup.add(new JLabel("게임을 시작하려면 아래 버튼을 눌러주세요."));
		startBtn = new JButton("게임 시작");
		StartPopup.add(startBtn);
		
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameState = true;
				StartPopup.dispose();
				Timer timeRun = new Timer();
				timeRun.start();
				Judgmunt J_Munt = new Judgmunt(); 
				J_Munt.start();
			}
		});
		
		StartPopup.setLocationRelativeTo(null);
		StartPopup.setVisible(true);
		//--------시작 팝업 끝
		
		setBounds(0, 0, Pan_X, Pan_Y);
		setLayout(null);
		
		//--------타이틀바 영역 시작
		titleBar = new JPanel();
		titleBar.setBounds(0,532, Pan_X, 30);
		getContentPane().add(titleBar);
		titleBar.setBackground(new Color(107,102,255,200));
		titleBar.setLayout(null);
		
		// ------------프로그램 종료
		finishBtn = new JButton("프로그램 종료");
		finishBtn.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameState = false;
				System.exit(0);
			}
		});
		finishBtn.setFocusable(false);
		
		///성공시 프로그램 종료
		WinBtn = new JButton("성공");
		WinBtn.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		Item_State = new JPanel();
		Item_State.setBounds(210, 0, 364, 30);
		Item_State.setBackground(new Color(178,204,255));
		titleBar.add(Item_State);
		Item_State.setLayout(null);
		
		speed_lb = new JLabel("X ");
		speed_lb.setIcon(new ImageIcon("MazeEscape_img/Item/boot.png"));
		speed_lb.setBounds(70, 0, 80, 30);
		Item_State.add(speed_lb);
		
		key_lb = new JLabel("X ");
		key_lb.setIcon(new ImageIcon("MazeEscape_img/Item/key.png"));
		key_lb.setBounds(170, 0, 67, 30);
		Item_State.add(key_lb);
		
		Time_lb = new JLabel("> " + Limit+"초 <");
		Time_lb.setIcon(new ImageIcon("MazeEscape_img/Item/SandTimer.png"));
		Time_lb.setBounds(270, 0, 123, 30);
		Item_State.add(Time_lb);

		//--------타이틀바 영역 끝
		
		//--------캐릭터 영역 시작
		Player = new JPanel();
		Player.setBounds(player_X, player_Y, player_Ic_X,player_Ic_Y);
		Player.setBackground(new Color(255,0,0,0));
		Player.setOpaque(false);
		player_img = new JLabel(player_IC);
		Player.add(player_img);
		add(Player);
	
		//--------캐릭터 영역 끝
	
		//--------맵 영역 시작
		Map = new JPanel();
		Map.setBounds(Map_X, Map_Y, MapSize_X, MapSize_Y);
		Map.setLayout(getLayout());
		
		Char_ax = new JPanel();
		Char_ax.setBackground(new Color(255,0,0,0));
		Char_ax.setBounds(1375+player_Ic_Y/4,1925-player_Ic_Y, 10, player_Ic_Y);
		Map.add(Char_ax);
		
		map_lb = new JLabel();
		map_lb.setIcon(new ImageIcon(Map_img[0]));
 		
		//--------지형지물 추가 영역 시작
		
		Potal = new JLabel(potal_ic);
		Potal.setBounds(1300,200, potal_ic.getIconWidth(), potal_ic.getIconHeight());
		
		item_boot1 = new JLabel(im);//부츠1
		item_boot1.setBounds(400, 970, 100, 100);
		item_boot2 = new JLabel(im);//부츠2
		item_boot2.setBounds(1250, 450, 100, 100);

		item_Stime1 = new JLabel(im3);//모래시계1
		item_Stime1.setBounds(1535, 1680, 100, 100);
		item_Stime2 = new JLabel(im3);//모래시계2
		item_Stime2.setBounds(1450, 1030, 100, 100);
		item_Stime3 = new JLabel(im3);//모래시계3
		item_Stime3.setBounds(400, 840, 100, 100);
		
		item_key = new JLabel(im2);//열쇠
		item_key.setBounds(1484, 460, 100, 100);
		
		Item_lb = new JLabel("Item :");
		Item_lb.setBounds(10, 0, 35, 30);
		Item_State.add(Item_lb);
		
		Map.add(Potal);
		Map.add(item_boot1);
		Map.add(item_boot2);
		Map.add(item_Stime1);
		Map.add(item_Stime2);
		Map.add(item_Stime3);
		Map.add(item_key);
		
		for (int i = 0; i < feature_ladder.length; i++) {
			Map.add(new Feature_lb(feature_ladder[i][0],feature_ladder[i][1],feature_ladder[i][2]));
		}
		for (int i = 0; i < feature_dcr.length; i++) {
			Map.add(new Feature_lb(feature_dcr[i][0],feature_dcr[i][1],feature_dcr[i][2]));
		}
		for (int i = 0; i < feature_Block.length; i++) {
			Map.add(new Feature_lb(feature_Block[i][0],feature_Block[i][1],feature_Block[i][2]));
		}
		
		//--------지형지물 추가 영역 끝
		
		Map.add(map_lb);
		add(Map);
		//--------맵 영역 끝
		
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Item1 tm1 = new Item1();
		Item2 tm2 = new Item2();
		Item3 tm3 = new Item3();
		Item4 tm4 = new Item4();
		Item5 tm5 = new Item5();
		Item6 tm6 = new Item6();
		
		finish fs = new finish(); 
		fs.start();
		tm1.start();
		tm2.start();
		tm3.start();
		tm4.start();
		tm5.start();
		tm6.start();
		
		addKeyListener(this);
	}
	
	class Item1 extends Thread{
		@Override
		public void run() {
			while(true) {
				try {
					sleep(10);
					if(item_Stime1.getBounds().x <= Char_ax.getBounds().x+5&&
					item_Stime1.getBounds().x+item_Stime1.getBounds().width >= Char_ax.getBounds().x&&
					item_Stime1.getBounds().y <= Char_ax.getBounds().y+player_Ic_Y&&
					item_Stime1.getBounds().y+item_Stime1.getBounds().height >= Char_ax.getBounds().y+player_Ic_Y-20
					) {
						Map.remove(item_Stime1);
						t+=AddTime;
						break;
					}
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class Item2 extends Thread{
		@Override
		public void run() {
			while(true) {
				try {
					sleep(10);
					if(item_Stime2.getBounds().x <= Char_ax.getBounds().x+5&&
					item_Stime2.getBounds().x+item_Stime2.getBounds().width >= Char_ax.getBounds().x&&
					item_Stime2.getBounds().y <= Char_ax.getBounds().y+player_Ic_Y&&
					item_Stime2.getBounds().y+item_Stime2.getBounds().height >= Char_ax.getBounds().y+player_Ic_Y-20
					) {
						Map.remove(item_Stime2);
						t+=AddTime;
						break;
					}
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class Item3 extends Thread{
		@Override
		public void run() {
			while(true) {
				try {
					sleep(10);
					if(item_Stime3.getBounds().x <= Char_ax.getBounds().x+5&&
					item_Stime3.getBounds().x+item_Stime3.getBounds().width >= Char_ax.getBounds().x&&
					item_Stime3.getBounds().y <= Char_ax.getBounds().y+player_Ic_Y&&
					item_Stime3.getBounds().y+item_Stime3.getBounds().height >= Char_ax.getBounds().y+player_Ic_Y-20
					) {
						Map.remove(item_Stime3);
						t+=AddTime;
						break;
					}
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class Item4 extends Thread{
		@Override
		public void run() {
			while(true) {
				try {
					sleep(10);
					if(item_boot1.getBounds().x <= Char_ax.getBounds().x+5&&
					item_boot1.getBounds().x+item_boot1.getBounds().width >= Char_ax.getBounds().x&&
					item_boot1.getBounds().y <= Char_ax.getBounds().y+player_Ic_Y&&
					item_boot1.getBounds().y+item_boot1.getBounds().height >= Char_ax.getBounds().y+player_Ic_Y-20
					) {
						Map.remove(item_boot1);
						i+=1;
						speed_lb.setText("X "+i);
						Speed =+(Speed+5);
						break;
					}
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class Item5 extends Thread{
		@Override
		public void run() {
			while(true) {
				try {
					sleep(10);
					if(item_boot2.getBounds().x <= Char_ax.getBounds().x+5&&
					item_boot2.getBounds().x+item_boot2.getBounds().width >= Char_ax.getBounds().x&&
					item_boot2.getBounds().y <= Char_ax.getBounds().y+player_Ic_Y&&
					item_boot2.getBounds().y+item_boot2.getBounds().height >= Char_ax.getBounds().y+player_Ic_Y-20
					) {
						Map.remove(item_boot2);
						i+=1;
						speed_lb.setText("X "+i);
						Speed =+(Speed+5);
						break;
					}
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}	
	class Item6 extends Thread{
		@Override
		public void run() {
			while(true) {
				try {
					sleep(10);
					if(item_key.getBounds().x <= Char_ax.getBounds().x+5&&
					item_key.getBounds().x+item_key.getBounds().width >= Char_ax.getBounds().x&&
					item_key.getBounds().y <= Char_ax.getBounds().y+player_Ic_Y&&
					item_key.getBounds().y+item_key.getBounds().height >= Char_ax.getBounds().y+player_Ic_Y-20
					) {
						Map.remove(item_key);
						k+=1;
						key_lb.setText("X"+k);
						break;
					}
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class finish extends Thread{
		@Override
		public void run() {
			Dialog WinPopup = new JDialog();
            WinPopup.setBounds(750, 450, 400, 100);
            WinPopup.setLayout(new FlowLayout());
        
			while(true) {
				if(Map.getX()>-1114&&Map.getX()<-1039&&Map.getY()<=-95&&Map.getY()>=-100&&k==1) {
					k = 0;
	                WinPopup.add(new JLabel("용사여 탈출을 축하드립니다. 빅토리아 아일랜드로 떠나보아요!"));
	                WinPopup.add(WinBtn);
						
	                WinPopup.setVisible(true);
				}	
				try {
					sleep(10);
					
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class Judgmunt extends Thread{
			
			public void run() {
				try {
					while(gameState){
						sleep(100);
						LR_Break = false;
						UD_Break = false;
						for (int i = 0; i < feature_Block.length; i++) {
							ImageIcon Block_ic = new ImageIcon("MazeEscape_img/Map/feature/"+(String)feature_Block[i][2]);
							if(	((int)feature_Block[i][1]  <= Char_ax.getBounds().y+player_Ic_Y) &&
								((int)feature_Block[i][1]+5 >= Char_ax.getBounds().y+player_Ic_Y)&&
								((int)feature_Block[i][0]+10 < Char_ax.getBounds().x) &&
								((int)feature_Block[i][0]+Block_ic.getIconWidth() >= Char_ax.getBounds().x)){
//								System.out.println("LR_Break OFF");
								LR_Break = true;
								break;
							}
						}
						for (int i = 0; i < feature_ladder.length; i++) {
							ImageIcon Block_ic = new ImageIcon("MazeEscape_img/Map/feature/"+(String)feature_ladder[i][2]);
							if(((int)feature_ladder[i][0]+5 <= Char_ax.getBounds().x) &&
								((int)feature_ladder[i][0]+35 >= Char_ax.getBounds().x)&&
								((int)feature_ladder[i][1]-20 <= Char_ax.getBounds().y+player_Ic_X)&&
								((int)feature_ladder[i][1]+Block_ic.getIconHeight()+10 >= Char_ax.getBounds().y+player_Ic_X)
								) {
//								System.out.println("UD_Break OFF");
								UD_Break = true;
								break;
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	
	int nowtime=0;
	class Timer extends Thread{			//게임 내부의 시간
		@Override
		public void run() {
			String fns = "";
			//닫기 팝업
			Dialog finishPopup = new JDialog();
			finishPopup.setBounds(750, 450, 400, 100);
			finishPopup.setLayout(new FlowLayout());
			while(gameState) {	
				Time_lb = new JLabel();
				for ( t = Limit; t >= 0; t--) {
					if(timeAddCheck==true) {
						t += AddTime;
					}
					timeAddCheck=false;
					
					Time_lb.setText((t)+"초");
					
					if (t==0) {
						fns = "끝";	
						
						finishPopup.add(new JLabel("시간 초과! 프로그램 종료버튼을 눌러 종료해주세요"));
						finishPopup.setVisible(true);
						
						finishPopup.add(finishBtn);
						
						finishPopup.setVisible(true);
						
					}
					
					try {
						sleep(1000);
						if (fns=="끝") {
							break;
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}	
			gameState = false;
			finishPopup.dispose();	
		}
	}
	

//--------------------------------------------------------------
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
//		System.out.println(key);
		switch(key){
		case 37:
			if(LR_Break) {
				if(Map_X<=-4){
					Map_X +=Speed;
					Char_ax.setLocation(Char_ax.getBounds().x-Speed,Char_ax.getBounds().y);
					player_img.setIcon(new ImageIcon("MazeEscape_img/"+Player_img[2]));
				}
			}
			
//			System.out.println("왼쪽");
			break;
		case 39:
			if(LR_Break) {
				if(Map_X>=-1215){
					Map_X -=Speed;
					Char_ax.setLocation(Char_ax.getBounds().x+Speed,Char_ax.getBounds().y);
					player_img.setIcon(new ImageIcon("MazeEscape_img/"+Player_img[3]));
				}
			}
//			System.out.println("오른쪽");
			break;
		case 38:
			if(UD_Break) {
				if(Map_Y <=-95){
					player_img.setIcon(new ImageIcon("MazeEscape_img/"+Player_img[6]));
					Map_Y+=5;
					Char_ax.setLocation(Char_ax.getBounds().x,Char_ax.getBounds().y-5);
				}
			}
//			System.out.println("위");
			break;
		case 40:
			if(UD_Break) {
				if(Map_Y >= -1620){
					player_img.setIcon(new ImageIcon("MazeEscape_img/"+Player_img[6]));
					Map_Y-=5;
					Char_ax.setLocation(Char_ax.getBounds().x,Char_ax.getBounds().y+5);
				}
			}
//			System.out.println("아래");
			break;
		}
		Map.setLocation(Map_X,Map_Y);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(player_img.getIcon().equals("MazeEscape_img/"+Player_img[3])){
			player_img.setIcon(new ImageIcon("MazeEscape_img/"+Player_img[1]));
		}else {
			player_img.setIcon(new ImageIcon("MazeEscape_img/"+Player_img[0]));
		}
	}
		
//--------------------------------------------------------------
	public static void main(String[] args) {
		
		new MazeEscape_Final();
	}
}

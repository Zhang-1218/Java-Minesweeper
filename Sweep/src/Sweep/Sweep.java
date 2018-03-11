package Sweep;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.applet.*;
import java.net.*;
import java.io.*;
public class Sweep extends JFrame implements ActionListener,MouseListener,Runnable
{
	JFrame frame = new JFrame("扫雷游戏");
	int x=1;  //是否启动线程
	int flagcount=0;//标记数量
	int wanchenglei=0;//成功标记雷数
	String dengji;//等级
	JButton start;
	int width=50;
	int height=50;
	int flag=0;//完成雷数
	 int N=5;//雷数
	Graphics g;
	Boolean Gaming=false; //游戏是否开始
	int weibu=0;  //布雷变量
	int totalscore;//得分
	int totaltime=200;//时间
	Thread t=new Thread(this);
	int A[][]=new int[10][10];//雷的标记
	int B[][]=new int[10][10];//标记该格子是否已经点过
	int C[][]=new int[10][10];
	JLabel score=new JLabel("总分：0");
	JLabel time=new JLabel("时间：0");
	JLabel leishu=new JLabel("雷数：");
	Image im,qi; //两个图片
	JMenuBar jb=new JMenuBar();  //菜单条
	JMenu jm=new JMenu("选择难度");
	JMenuItem cainiao,zhongdeng,gaoshou,paihang;
	AudioClip au,aau,aaau;  //声音变量
	URL backurl,xiaziurl,baozhaurl;//声音路径
	Sweep(){
	ImageIcon i=new ImageIcon("start.gif");

	try
	{
		xiaziurl=getClass().getResource("ding.wav");
		baozhaurl=getClass().getResource("bob.wav");	//加载声音
		backurl=getClass().getResource("music.mid");
 
		au=JApplet.newAudioClip(backurl);
		aau=JApplet.newAudioClip(xiaziurl);
		aaau=JApplet.newAudioClip(baozhaurl);
		au.loop();
   }
	catch(Exception e)
	{
		System.err.println("e.getMessage()");
	}

	dengji="菜鸟";

	Border border=BorderFactory.createTitledBorder("游戏状态");
	JPanel p=new JPanel();
	start=new JButton(i);
	p.setBorder(border);
	p.setLayout(null);
	p.add(score);
	p.add(time);
	p.add(leishu);
	p.add(start);
	frame.setLayout(null);
	score.setBounds(0,10,80,40);
	time.setBounds(100,10,80,40);
	leishu.setBounds(200,10,40,40);
	start.setBounds(300,10,40,40);             //游戏界面代码
	start.addActionListener(this);
	frame.addMouseListener(this);
	frame.add(p);

	p.setBounds(100,10,400,40);
	cainiao=new JMenuItem("菜鸟级别");
	zhongdeng=new JMenuItem("中等级别");
	gaoshou=new JMenuItem("高手级别");
	paihang=new JMenuItem("排行榜");
	cainiao.addActionListener(this);
	zhongdeng.addActionListener(this);
	gaoshou.addActionListener(this);
	paihang.addActionListener(this);

	jm.add(cainiao);
	jm.addSeparator();
	jm.add(zhongdeng);
	jm.addSeparator();
	jm.add(gaoshou);
	jm.addSeparator();
	jb.add(jm);
	jb.add(paihang);


	frame.setJMenuBar(jb);
	frame.setLocation(200,10);
	frame.setSize(540,650);
	frame.setVisible(true);

	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	Image back=getToolkit().getImage("lei.gif");
	frame.setIconImage(back);
	g=frame.getGraphics();
	im=getToolkit().getImage("lei.gif");    // 获取图片
	qi=getToolkit().getImage("qi.gif");
	leishu.setForeground(Color.red);
	time.setForeground(Color.red);          //设置标签背景色
	score.setForeground(Color.red);

}
	public void start()
	{
		t.start();
	}
	public void run()
	{
		x=0;
		for(;;)
		{
			if(Gaming)
			{          //无限循环
		
			try
			{
				t.sleep(1000);
				totaltime-=1;
				time.setText("时间:"+totaltime);  //倒记时
				if(flag==100 && wanchenglei<N)      //判断格子用完 且 完成雷数小于N  就结束游戏
				{ 
					Gaming=false;
					lei();
					end("你输了!!!");
					defen();
				}
			}
			catch(Exception e)
			{}
		}
	
		if(totaltime<=0)
		{
			Gaming=false;
			lei();
			end("时间到！");   //时间为零  结束游戏
			qingkong();}
		}
	}

	void draw()//绘制窗格
	{                
	
		g.setColor(frame.getBackground());
		g.fillRect(0,100,600,540);
		g.setColor(Color.red);
		int i,j;
	
		for(i=0;i<10;i++)
		{
	
			for(j=0;j<10;j++)      
			{
				g.draw3DRect(i*50+10,j*50+130,width,height,true);
			}
		}
	}

	void randbulei(int n)     //随即布雷
	{
	
		Random r=new Random();
	
		for(int i=0;i<n;i++)
		 {
			int t=Math.abs(r.nextInt()%10);
			int f=Math.abs(r.nextInt()%10);
	
			A[t][f]=1;
		 }
	
		if(isfull())
		Gaming=true;
		else
		randbulei(weibu);
	}


	Boolean isfull()//判断布雷数量是否已经达到N
	{             
		int t=0;
	
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				if(A[i][j]==1)
					t+=1;
			}
		}
	
		if(t<N)
		{
			weibu=N-t;
			return false;
		}
		else
			return true;
	
	
	
	}

	void playback()
	{                //背景音乐播放
		au.play();
	}






	int leicount(int i,int j){
	
	if(i>=0 && j>=0 && i<=9 && j<=9)
	{     //判断鼠标点中的格子是否有雷  如果雷数为 0  就染色并标记b  数组 ...  如果为1 或2...  就显示数字
		if(B[i][j]!=1 && A[i][j]!=1)
		{
	
			if(shuzi(i,j)==0 )
			{
		
				g.setColor(new Color(109,30,100));
		    	g.fillRect(i*50+10,j*50+130,width,height);
		    	g.setColor(Color.black);
		    	g.drawRect(i*50+10,j*50+130,width,height);
		    	B[i][j]=1;
		        flag+=1;
		    	return 0;
		
			}
			else
			{
				g.setFont(new Font("宋体",Font.BOLD,28));
				g.setColor(new Color(240,150,150));
			
				g.fillRect(i*50+10,j*50+130,width,height);
				g.setColor(Color.red);
				g.drawString(String.valueOf(shuzi(i,j)),i*50+30,j*50+160);
				g.setColor(Color.red);
			    g.drawRect(i*50+10,j*50+130,width,height);
			    B[i][j]=1;
			
			   	flag+=1;
			
			    return  shuzi(i,j);
			}
		
		
		}
	
	}
	return -1;
	
	
	}
	
	int shuzi(int i,int j)
	{    //雷数
	
		int shu=0;
		if(i>0 && i<9 && j>0 && j<9)
	
		 shu=count(i-1,j-1)+count(i-1,j)+count(i-1,j+1)+count(i,j-1)+count(i,j+1)+count(i+1,j-1)+count(i+1,j)+count(i+1,j+1);
	
		if(i==0 && j==0)
	
		shu=count(i,j+1)+count(i+1,j+1)+count(i+1,j);
	
		if(i==0 && j==9)
		shu=count(i,j-1)+count(i+1,j-1)+count(i+1,j);
	
		if(i==9 && j==0)
		shu=count(i-1,j)+count(i-1,j+1)+count(i,j+1);
	
		if(i==9 && j==9)
		shu=count(i-1,j)+count(i-1,j-1)+count(i,j-1);
	
		return shu;
	
	}
	
	int count(int i,int j)
	{
		if(A[i][j]==1)
			return 1;
		else
			return 0;
	}
	
	void qingkong()
	{
	
		for(int i=0;i<10;i++)   //数组清空  
		{
		
			for(int j=0;j<10;j++)
			{	A[i][j]=0;
				B[i][j]=0;
			    C[i][j]=0;
			}
	
		}
	}
	
	void end(String s)
	{              //游戏结束
		g.setFont(new Font("宋体",Font.BOLD,72));
		g.setColor(Color.green);
	
		g.drawString(s,100,330);

	}
	
	
	void play()
	{
		aau.play();    //鼠标单机声音	
	}
	
	
	public void mouseClicked(MouseEvent e)
	{
	
		int x1=0,y1=0;                //判断鼠标单机范围 确定数组下标
		if(e.getX()>60)
		{
			x1=(e.getX()-10)/50;
		}
		else
		{x1=0;}
		if(e.getY()>180)
		{y1=(e.getY()-130)/50;	}
		else
		{
			if(e.getY()>150)
			y1=0;	
		}
		if(e.getButton()==3)
			youji(x1,y1);
		else
		{  
			if(Gaming)
			{
				if(A[x1][y1]==1)                  //该格子有雷  炸死
		   		{      
					g.setColor(Color.red);
					g.fillRect(x1*50+10,y1*50+130,width,height);
			
			
			
				 Gaming=false;
			     aaau.play();
			     lei();
			
			     end("你被炸死了！！！");
			     defen();
				 qingkong();
		
		   		}
				else
				{
					int i=leicount(x1,y1);
					if(i==0)
					{
						jiance(x1,y1);               
					}
				}
		     }
		}
		
		play();
	}
		
		
	void jiance(int x1,int y1)
	{
		                                     //该格子无雷 递归调用
	     if(leicount(x1-1,y1)==0 )
	       	 jiance(x1-1,y1);
	     if(leicount(x1-1,y1-1)==0 )
	     	 jiance(x1-1,y1-1);
	
		 if(leicount(x1-1,y1+1)==0)
		 	jiance(x1-1,y1+1);
	
	      if(leicount(x1,y1-1)==0)
	      	jiance(x1,y1-1);
	
	       if(leicount(x1,y1+1)==0)
	       	jiance(x1,y1+1);
	
		   if(leicount(x1+1,y1-1)==0)
		   	 jiance(x1+1,y1-1);
	
	       if(leicount(x1+1,y1)==0)
	       	jiance(x1+1,y1);
	
	     if(leicount(x1+1,y1+1)==0)
	     	jiance(x1+1,y1+1);
	
		}
	
	
	void lei()
	{
	                                //雷暴炸后显示所有雷

		for(int i=0;i<10;i++)
		{
	
			for(int j=0;j<10;j++)
			{	
				if(A[i][j]==1)
				g.drawImage(im,i*50+20,j*50+140,this);
		
			}
	
		}
	
	}
	void defen()
	{
		for(int i=0;i<10;i++)
		{
	
			for(int j=0;j<10;j++)
			{     
	
	
	
				if((C[i][j]==1 ) && ( A[i][j]==1))    //计算得分
				{	
					totalscore+=1000/(200-totaltime);
	
					score.setText("总分:"+totalscore);

				}
			}
	    
		}
	
	write();
	}
	public void mousePressed(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	
	void youji(int i,int j)
	{            //鼠标右机判断
	
		if(B[i][j]!=1)
		{
		
			g.drawImage(qi,i*50+20,j*50+140,this);
			if(A[i][j]==1)
				wanchenglei+=1;

			flagcount+=1;
		  
			B[i][j]=1;	
			C[i][j]=1;
		      flag+=1;
		}
		if(flagcount==N && wanchenglei<N)
		{
			lei();
			end("Game Over");
			defen();
			Gaming=false;
		}
		
		
		if(wanchenglei==N)
		{
		lei();
		end("You Win");
		defen();
		Gaming=false;		
		}
	
	}
	
	public void actionPerformed(ActionEvent e){     //菜单和按纽事件
	
		if(e.getSource()==cainiao)
		{totaltime=200;	N=5;leishu.setText("雷数"+N);dengji="菜鸟";}
		if(e.getSource()==zhongdeng)
		{totaltime=150;	N=15;leishu.setText("雷数"+N);dengji="中等";  }
		if(e.getSource()==gaoshou)
		{totaltime=100;	N=25;leishu.setText("雷数"+N);dengji="高手";}
	
	if(e.getSource()==paihang)
	read();
	
	
		if(e.getSource()==start)
		{	draw();
	      flag=0;
		qingkong();
		randbulei(N);
	totalscore=0;
	wanchenglei=0;
	flagcount=0;
		score.setText("总分:"+totalscore);
		if(dengji=="菜鸟")
		totaltime=200;
		if(dengji=="中等")
		totaltime=150;
		if(dengji=="高手")
			totaltime=100;
	
	     if(x==1)
	     	start();
	
	
		}
		}
	
	void write()
	{    //记录保存

		try
		{
			InputStream fo1=new FileInputStream("record.txt");
			DataInputStream  fi1=new DataInputStream(fo1);
			 
			if(fi1.readInt()<totalscore)
			{
				fo1.close();
				String s=JOptionPane.showInputDialog(frame,"纪录","请输入您的姓名");
				OutputStream fi=new FileOutputStream("record.txt");
				DataOutputStream fo=new DataOutputStream(fi);
			
				fo.writeInt(totalscore);
				fo.writeUTF(s);
				fi.close();
				JOptionPane.showMessageDialog(frame,"记录保存成功");
			
			}
			fo1.close();
			
		}
		catch(Exception e){}
	
	}                                     
			
	void read()
	{
		int fen=0;                          //读取记录
		String s="";
		try
		{
			InputStream fo=new FileInputStream("record.txt");
			DataInputStream  fi=new DataInputStream(fo);
			 
			fen=fi.readInt();
			s=fi.readUTF();	
				
			fo.close();
		}
		catch(Exception e){}
		JOptionPane.showMessageDialog(frame,"目前记录保持着是："+s+"\n"+"总分是："+fen);

	}

	public static void main(String args[])
	{
		new Sweep();
	}
	
}

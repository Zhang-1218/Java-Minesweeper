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
	JFrame frame = new JFrame("ɨ����Ϸ");
	int x=1;  //�Ƿ������߳�
	int flagcount=0;//�������
	int wanchenglei=0;//�ɹ��������
	String dengji;//�ȼ�
	JButton start;
	int width=50;
	int height=50;
	int flag=0;//�������
	 int N=5;//����
	Graphics g;
	Boolean Gaming=false; //��Ϸ�Ƿ�ʼ
	int weibu=0;  //���ױ���
	int totalscore;//�÷�
	int totaltime=200;//ʱ��
	Thread t=new Thread(this);
	int A[][]=new int[10][10];//�׵ı��
	int B[][]=new int[10][10];//��Ǹø����Ƿ��Ѿ����
	int C[][]=new int[10][10];
	JLabel score=new JLabel("�ܷ֣�0");
	JLabel time=new JLabel("ʱ�䣺0");
	JLabel leishu=new JLabel("������");
	Image im,qi; //����ͼƬ
	JMenuBar jb=new JMenuBar();  //�˵���
	JMenu jm=new JMenu("ѡ���Ѷ�");
	JMenuItem cainiao,zhongdeng,gaoshou,paihang;
	AudioClip au,aau,aaau;  //��������
	URL backurl,xiaziurl,baozhaurl;//����·��
	Sweep(){
	ImageIcon i=new ImageIcon("start.gif");

	try
	{
		xiaziurl=getClass().getResource("ding.wav");
		baozhaurl=getClass().getResource("bob.wav");	//��������
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

	dengji="����";

	Border border=BorderFactory.createTitledBorder("��Ϸ״̬");
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
	start.setBounds(300,10,40,40);             //��Ϸ�������
	start.addActionListener(this);
	frame.addMouseListener(this);
	frame.add(p);

	p.setBounds(100,10,400,40);
	cainiao=new JMenuItem("���񼶱�");
	zhongdeng=new JMenuItem("�еȼ���");
	gaoshou=new JMenuItem("���ּ���");
	paihang=new JMenuItem("���а�");
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
	im=getToolkit().getImage("lei.gif");    // ��ȡͼƬ
	qi=getToolkit().getImage("qi.gif");
	leishu.setForeground(Color.red);
	time.setForeground(Color.red);          //���ñ�ǩ����ɫ
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
			{          //����ѭ��
		
			try
			{
				t.sleep(1000);
				totaltime-=1;
				time.setText("ʱ��:"+totaltime);  //����ʱ
				if(flag==100 && wanchenglei<N)      //�жϸ������� �� �������С��N  �ͽ�����Ϸ
				{ 
					Gaming=false;
					lei();
					end("������!!!");
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
			end("ʱ�䵽��");   //ʱ��Ϊ��  ������Ϸ
			qingkong();}
		}
	}

	void draw()//���ƴ���
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

	void randbulei(int n)     //�漴����
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


	Boolean isfull()//�жϲ��������Ƿ��Ѿ��ﵽN
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
	{                //�������ֲ���
		au.play();
	}






	int leicount(int i,int j){
	
	if(i>=0 && j>=0 && i<=9 && j<=9)
	{     //�ж������еĸ����Ƿ�����  �������Ϊ 0  ��Ⱦɫ�����b  ���� ...  ���Ϊ1 ��2...  ����ʾ����
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
				g.setFont(new Font("����",Font.BOLD,28));
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
	{    //����
	
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
	
		for(int i=0;i<10;i++)   //�������  
		{
		
			for(int j=0;j<10;j++)
			{	A[i][j]=0;
				B[i][j]=0;
			    C[i][j]=0;
			}
	
		}
	}
	
	void end(String s)
	{              //��Ϸ����
		g.setFont(new Font("����",Font.BOLD,72));
		g.setColor(Color.green);
	
		g.drawString(s,100,330);

	}
	
	
	void play()
	{
		aau.play();    //��굥������	
	}
	
	
	public void mouseClicked(MouseEvent e)
	{
	
		int x1=0,y1=0;                //�ж���굥����Χ ȷ�������±�
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
				if(A[x1][y1]==1)                  //�ø�������  ը��
		   		{      
					g.setColor(Color.red);
					g.fillRect(x1*50+10,y1*50+130,width,height);
			
			
			
				 Gaming=false;
			     aaau.play();
			     lei();
			
			     end("�㱻ը���ˣ�����");
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
		                                     //�ø������� �ݹ����
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
	                                //�ױ�ը����ʾ������

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
	
	
	
				if((C[i][j]==1 ) && ( A[i][j]==1))    //����÷�
				{	
					totalscore+=1000/(200-totaltime);
	
					score.setText("�ܷ�:"+totalscore);

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
	{            //����һ��ж�
	
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
	
	public void actionPerformed(ActionEvent e){     //�˵��Ͱ�Ŧ�¼�
	
		if(e.getSource()==cainiao)
		{totaltime=200;	N=5;leishu.setText("����"+N);dengji="����";}
		if(e.getSource()==zhongdeng)
		{totaltime=150;	N=15;leishu.setText("����"+N);dengji="�е�";  }
		if(e.getSource()==gaoshou)
		{totaltime=100;	N=25;leishu.setText("����"+N);dengji="����";}
	
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
		score.setText("�ܷ�:"+totalscore);
		if(dengji=="����")
		totaltime=200;
		if(dengji=="�е�")
		totaltime=150;
		if(dengji=="����")
			totaltime=100;
	
	     if(x==1)
	     	start();
	
	
		}
		}
	
	void write()
	{    //��¼����

		try
		{
			InputStream fo1=new FileInputStream("record.txt");
			DataInputStream  fi1=new DataInputStream(fo1);
			 
			if(fi1.readInt()<totalscore)
			{
				fo1.close();
				String s=JOptionPane.showInputDialog(frame,"��¼","��������������");
				OutputStream fi=new FileOutputStream("record.txt");
				DataOutputStream fo=new DataOutputStream(fi);
			
				fo.writeInt(totalscore);
				fo.writeUTF(s);
				fi.close();
				JOptionPane.showMessageDialog(frame,"��¼����ɹ�");
			
			}
			fo1.close();
			
		}
		catch(Exception e){}
	
	}                                     
			
	void read()
	{
		int fen=0;                          //��ȡ��¼
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
		JOptionPane.showMessageDialog(frame,"Ŀǰ��¼�������ǣ�"+s+"\n"+"�ܷ��ǣ�"+fen);

	}

	public static void main(String args[])
	{
		new Sweep();
	}
	
}

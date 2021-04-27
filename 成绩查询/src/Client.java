import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void testClient(){
		System.out.println("正在向服务器请求连接。。。");
		Socket socket = null;
		Scanner keybordscanner = null;
		Scanner keybordscanner1 = null;
		Scanner keybordscanner2 = null;
		Scanner inScanner = null;
		PrintWriter pwtoserver = null;
		
		try {
			socket = new Socket("127.0.0.1", 6666);
			inScanner = new Scanner(socket.getInputStream()); 
			System.out.println(inScanner.nextLine());
			pwtoserver = new PrintWriter(socket.getOutputStream());
			System.out.print("请输入用户名:");	
			
			//先读取键盘录入方可向服务端发送消息
			keybordscanner = new Scanner(System.in);
			
			while(keybordscanner.hasNextLine()){
				
				String sno = keybordscanner.nextLine();
				//展示到己方的控制台
				System.out.println("用户名："+sno);
				//写到服务端的的控制台
				pwtoserver.println(sno);
				pwtoserver.flush();
				//阻塞等待接收服务端的消息


				    
				    if(inScanner.nextLine().equals("用户名输入正确")) {

				    	System.out.print("请输入密码：");	
				    	
				    	
				    	keybordscanner1 = new Scanner(System.in);
				    	
				    	while(keybordscanner1.hasNextLine()){
				    		
				    	
				    	String password = keybordscanner1.nextLine();
					    //展示到己方的控制台
					    System.out.println("密码："+password);
					    //写到服务端的的控制台
					    pwtoserver.println(password);
					    pwtoserver.flush();
					    //阻塞等待接收服务端的消息
					
					    String indata2 = inScanner.nextLine();
	
					    if(indata2.equals("登陆成功!")) {
					    	
						    System.out.println("登陆成功!");
							System.out.print("请输入您要查询的课程名或平均成绩：");
						    
						    
							keybordscanner2 = new Scanner(System.in);
						    while(keybordscanner2.hasNextLine()){
								
						        String course = keybordscanner2.nextLine();

						        //展示到己方的控制台

						        //写到服务端的的控制台
						        pwtoserver.println(course);
						        pwtoserver.flush();
						        //阻塞等待接收服务端的消息
						    						
					    	    String indata3 = inScanner.nextLine();
					    	    
					    	    
					    	    if(indata3.equals("无此课程!"))
					    	    {
					    	    	System.out.println("错误的科目号或您没选该课程!");	
					    	    	}
					    	    else if(indata3.equals("BYE")){
					    	    	System.out.println("GOOD BYE");	
//					    	    	socket.close();
					    	    	System.exit(0);					    	    	
					    	    }
					    	    else {
					    	    	System.out.println(course+":"+indata3);
					        	}
					    	    
					    	    System.out.print("请输入您要查询的课程名或平均成绩：");
						        }
					        }else {
					        	System.out.println("密码错误，请重新输入!");					            
					    	}
					    System.out.print("请输入密码：");
				    	}
					    }else {
					    	System.out.println("无此用户,请重新运行此程序!");
					    	System.exit(0);
					    }
				}
				

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			keybordscanner.close();
			pwtoserver.close();
			inScanner.close();
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		testClient();
	}
}

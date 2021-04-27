import java.io.IOException;
import java.io.PrintWriter;
//import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	//服务器
	public static void testServer() throws SQLException{
		//连接数据库
		
	    final String DBDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	    //数据库地址
	    final String DBURL = "jdbc:sqlserver://localhost:1434;DataBaseName=testdatabase";
	    //数据库登录用户名
	    final String DBUSER = "sa";
	    //数据库用户密码
	    final String DBPASSWORD = "123456";
	    Connection conn = null;
	    //数据库操作
	    Statement stmt1 = null;
	    Statement stmt2 = null;
	    Statement stmt3 = null;
	    Statement stmt4 = null;
	    
	    //数据库查询结果集
	    ResultSet rs1 = null;
	    ResultSet rs2 = null;
	    ResultSet rs3 = null;
	    ResultSet rs4 = null;
	    
	    
	    //程序入口函数
		//创建一个服务器
		System.out.println("等待客户端连接。。。");
		PrintWriter pwtoclien = null;
		Scanner inScanner = null;
		Scanner inScanner1 = null;
		Scanner inScanner2 = null;
		ServerSocket ss = null;

		
		try {
			//建立一个服务器Socket绑定一个端口并开始监听
			ss = new ServerSocket(6666);
			//创建一个接收连接客户端的对象,使用accept()方法阻塞等待监听，获得新的连接
			Socket socket = ss.accept();

			System.out.println(socket.getInetAddress()+"已成功连接到此台服务器上。");
			//字符输出流
			pwtoclien = new PrintWriter(socket.getOutputStream());
			pwtoclien.println("连接成功！");
			pwtoclien.flush();
			inScanner = new Scanner(socket.getInputStream());
			//阻塞等待客户端发送消息过来
			while(inScanner.hasNextLine()){

				String Sno = inScanner.nextLine();
				System.out.println("用户名："+Sno);

			    try {
			    	//加载驱动程序
			        Class.forName(DBDRIVER);
			        //连接数据库
			        conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);

			        //实例化Statement对象
			        stmt1 = conn.createStatement();
			        stmt2 = conn.createStatement();
			        stmt3 = conn.createStatement();
			        stmt4 = conn.createStatement();
			        String sql1="select Sno from xxx where Sno='"+Sno+"'";
			        rs1= stmt1.executeQuery(sql1);			        
			        if(rs1.next()){    	
			        	if(Sno.equals(rs1.getString(1))) {

			        		pwtoclien.println("用户名输入正确");			        			
			        	    pwtoclien.flush();

			        	    inScanner1 = new Scanner(socket.getInputStream());

			            	while(inScanner1.hasNextLine()){

			            		String Password = inScanner1.nextLine();

							    System.out.println("密码："+Password);
							    String sql2= "select Password from xxx where Sno='"+Sno+"'";
							    rs2= stmt2.executeQuery(sql2);							    
							    while(rs2.next()){		
							    	if(Password.equals(rs2.getString(1))){							    		
							    		pwtoclien.println("登陆成功!");						        		
						        		pwtoclien.flush();
						        		
						        		//查询成绩
						        		inScanner2 = new Scanner(socket.getInputStream());
						        		while(inScanner2.hasNextLine()){
						        			String Course = inScanner2.nextLine();
						        		    
						        		    System.out.println("操作："+Course);

						        		    if(Course.equals("语文")) {
						        		    	String sql3= "select 语文 from xxx where Sno='"+Sno+"'";
							        		    rs3= stmt3.executeQuery(sql3);
									            while(rs3.next()){
									            	
									            	pwtoclien.println(rs3.getInt(1));
									        	    pwtoclien.flush();
									        	    System.out.println(Course+"成绩:"+rs3.getInt(1));
									        	    }
									            } 
						        		    else if(Course.equals("数学")) {
						        		    	String sql3= "select 数学 from xxx where Sno='"+Sno+"'";
							        		    rs3= stmt3.executeQuery(sql3);
									            while(rs3.next()){
									        	    pwtoclien.println(rs3.getInt(1));
									        	    pwtoclien.flush();
									        	    System.out.println(Course+"成绩:"+rs3.getInt(1));
									        	    }
									            }
						        		    else if(Course.equals("英语")) {
							        		    String sql3= "select 英语 from xxx where Sno='"+Sno+"'";
							        		    rs3= stmt3.executeQuery(sql3);
									            while(rs3.next()){
									        	    pwtoclien.println(rs3.getInt(1));
									        	    pwtoclien.flush();
									        	    System.out.println(Course+"成绩:"+rs3.getInt(1));
									        	    }
									            } 
						        		    else if(Course.equals("平均成绩")) {
						        		    	String sql4= "select 语文,数学,英语 from xxx where Sno='"+Sno+"'";
						        		    	
						        		    	rs4= stmt4.executeQuery(sql4);
						        		    	
									            while(rs4.next()){
									            	int a = rs4.getInt(1)+rs4.getInt(2)+rs4.getInt(3);
									            	 DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
									            	 String Avg = df.format((float)a/3);
									        	    pwtoclien.println(Avg);									        	    
									        	    pwtoclien.flush();									        	    
									        	    System.out.println("平均成绩:"+Avg);
									        	    }	
									            }	
						        		    else if(Course.equals("BYE")) {
						        		    	System.out.println("GOOD BYE");
						        		    	pwtoclien.println("BYE");
						        		    	
						        		    	pwtoclien.flush();
						        		    	System.exit(0);							        		    	
						        		    	} 
						        		    else {
						        		    	pwtoclien.println("无此课程!");	
						        		    	System.out.println("无此课程!");
						        		    	pwtoclien.flush();
						        		    	}
						        		    pwtoclien.flush();
						        		    }
						        		}
						        	//查询成绩结束
						        	else {
						        			pwtoclien.println("密码错误!");	
						        			System.out.println("密码错误!");
						        			pwtoclien.flush();
						        			}
							    	}
							    pwtoclien.flush();
							    }
			            	}
			        	pwtoclien.flush();
			        	}
			        else {
		            		pwtoclien.println("无此用户!");
		            		System.out.println("无此用户!");
		            		pwtoclien.flush();
		            		System.exit(0);
		            		}	
			        
			        }catch (Exception e) {
			        	e.printStackTrace();
			        	}
			    
			    
			    //数据库操作结束
//			    System.out.print("用户名：");
				pwtoclien.flush();
				}						
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}finally {
					pwtoclien.close();
					//keybordscanner.close();
					inScanner.close();
					try {
						ss.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							}
					}
		}
	public static void main(String[] args) throws SQLException {
		testServer();
		}
	}

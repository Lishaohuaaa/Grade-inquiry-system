import java.io.IOException;
import java.io.PrintWriter;
//import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	//������
	public static void testServer() throws SQLException{
		//�������ݿ�
		
	    final String DBDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	    //���ݿ��ַ
	    final String DBURL = "jdbc:sqlserver://localhost:1434;DataBaseName=testdatabase";
	    //���ݿ��¼�û���
	    final String DBUSER = "sa";
	    //���ݿ��û�����
	    final String DBPASSWORD = "123456";
	    Connection conn = null;
	    //���ݿ����
	    Statement stmt1 = null;
	    Statement stmt2 = null;
	    Statement stmt3 = null;
	    Statement stmt4 = null;
	    
	    //���ݿ��ѯ�����
	    ResultSet rs1 = null;
	    ResultSet rs2 = null;
	    ResultSet rs3 = null;
	    ResultSet rs4 = null;
	    
	    
	    //������ں���
		//����һ��������
		System.out.println("�ȴ��ͻ������ӡ�����");
		PrintWriter pwtoclien = null;
		Scanner inScanner = null;
		Scanner inScanner1 = null;
		Scanner inScanner2 = null;
		ServerSocket ss = null;

		
		try {
			//����һ��������Socket��һ���˿ڲ���ʼ����
			ss = new ServerSocket(6666);
			//����һ���������ӿͻ��˵Ķ���,ʹ��accept()���������ȴ�����������µ�����
			Socket socket = ss.accept();

			System.out.println(socket.getInetAddress()+"�ѳɹ����ӵ���̨�������ϡ�");
			//�ַ������
			pwtoclien = new PrintWriter(socket.getOutputStream());
			pwtoclien.println("���ӳɹ���");
			pwtoclien.flush();
			inScanner = new Scanner(socket.getInputStream());
			//�����ȴ��ͻ��˷�����Ϣ����
			while(inScanner.hasNextLine()){

				String Sno = inScanner.nextLine();
				System.out.println("�û�����"+Sno);

			    try {
			    	//������������
			        Class.forName(DBDRIVER);
			        //�������ݿ�
			        conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);

			        //ʵ����Statement����
			        stmt1 = conn.createStatement();
			        stmt2 = conn.createStatement();
			        stmt3 = conn.createStatement();
			        stmt4 = conn.createStatement();
			        String sql1="select Sno from xxx where Sno='"+Sno+"'";
			        rs1= stmt1.executeQuery(sql1);			        
			        if(rs1.next()){    	
			        	if(Sno.equals(rs1.getString(1))) {

			        		pwtoclien.println("�û���������ȷ");			        			
			        	    pwtoclien.flush();

			        	    inScanner1 = new Scanner(socket.getInputStream());

			            	while(inScanner1.hasNextLine()){

			            		String Password = inScanner1.nextLine();

							    System.out.println("���룺"+Password);
							    String sql2= "select Password from xxx where Sno='"+Sno+"'";
							    rs2= stmt2.executeQuery(sql2);							    
							    while(rs2.next()){		
							    	if(Password.equals(rs2.getString(1))){							    		
							    		pwtoclien.println("��½�ɹ�!");						        		
						        		pwtoclien.flush();
						        		
						        		//��ѯ�ɼ�
						        		inScanner2 = new Scanner(socket.getInputStream());
						        		while(inScanner2.hasNextLine()){
						        			String Course = inScanner2.nextLine();
						        		    
						        		    System.out.println("������"+Course);

						        		    if(Course.equals("����")) {
						        		    	String sql3= "select ���� from xxx where Sno='"+Sno+"'";
							        		    rs3= stmt3.executeQuery(sql3);
									            while(rs3.next()){
									            	
									            	pwtoclien.println(rs3.getInt(1));
									        	    pwtoclien.flush();
									        	    System.out.println(Course+"�ɼ�:"+rs3.getInt(1));
									        	    }
									            } 
						        		    else if(Course.equals("��ѧ")) {
						        		    	String sql3= "select ��ѧ from xxx where Sno='"+Sno+"'";
							        		    rs3= stmt3.executeQuery(sql3);
									            while(rs3.next()){
									        	    pwtoclien.println(rs3.getInt(1));
									        	    pwtoclien.flush();
									        	    System.out.println(Course+"�ɼ�:"+rs3.getInt(1));
									        	    }
									            }
						        		    else if(Course.equals("Ӣ��")) {
							        		    String sql3= "select Ӣ�� from xxx where Sno='"+Sno+"'";
							        		    rs3= stmt3.executeQuery(sql3);
									            while(rs3.next()){
									        	    pwtoclien.println(rs3.getInt(1));
									        	    pwtoclien.flush();
									        	    System.out.println(Course+"�ɼ�:"+rs3.getInt(1));
									        	    }
									            } 
						        		    else if(Course.equals("ƽ���ɼ�")) {
						        		    	String sql4= "select ����,��ѧ,Ӣ�� from xxx where Sno='"+Sno+"'";
						        		    	
						        		    	rs4= stmt4.executeQuery(sql4);
						        		    	
									            while(rs4.next()){
									            	int a = rs4.getInt(1)+rs4.getInt(2)+rs4.getInt(3);
									            	 DecimalFormat df=new DecimalFormat("0.00");//���ñ���λ��
									            	 String Avg = df.format((float)a/3);
									        	    pwtoclien.println(Avg);									        	    
									        	    pwtoclien.flush();									        	    
									        	    System.out.println("ƽ���ɼ�:"+Avg);
									        	    }	
									            }	
						        		    else if(Course.equals("BYE")) {
						        		    	System.out.println("GOOD BYE");
						        		    	pwtoclien.println("BYE");
						        		    	
						        		    	pwtoclien.flush();
						        		    	System.exit(0);							        		    	
						        		    	} 
						        		    else {
						        		    	pwtoclien.println("�޴˿γ�!");	
						        		    	System.out.println("�޴˿γ�!");
						        		    	pwtoclien.flush();
						        		    	}
						        		    pwtoclien.flush();
						        		    }
						        		}
						        	//��ѯ�ɼ�����
						        	else {
						        			pwtoclien.println("�������!");	
						        			System.out.println("�������!");
						        			pwtoclien.flush();
						        			}
							    	}
							    pwtoclien.flush();
							    }
			            	}
			        	pwtoclien.flush();
			        	}
			        else {
		            		pwtoclien.println("�޴��û�!");
		            		System.out.println("�޴��û�!");
		            		pwtoclien.flush();
		            		System.exit(0);
		            		}	
			        
			        }catch (Exception e) {
			        	e.printStackTrace();
			        	}
			    
			    
			    //���ݿ��������
//			    System.out.print("�û�����");
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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void testClient(){
		System.out.println("������������������ӡ�����");
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
			System.out.print("�������û���:");	
			
			//�ȶ�ȡ����¼�뷽�������˷�����Ϣ
			keybordscanner = new Scanner(System.in);
			
			while(keybordscanner.hasNextLine()){
				
				String sno = keybordscanner.nextLine();
				//չʾ�������Ŀ���̨
				System.out.println("�û�����"+sno);
				//д������˵ĵĿ���̨
				pwtoserver.println(sno);
				pwtoserver.flush();
				//�����ȴ����շ���˵���Ϣ


				    
				    if(inScanner.nextLine().equals("�û���������ȷ")) {

				    	System.out.print("���������룺");	
				    	
				    	
				    	keybordscanner1 = new Scanner(System.in);
				    	
				    	while(keybordscanner1.hasNextLine()){
				    		
				    	
				    	String password = keybordscanner1.nextLine();
					    //չʾ�������Ŀ���̨
					    System.out.println("���룺"+password);
					    //д������˵ĵĿ���̨
					    pwtoserver.println(password);
					    pwtoserver.flush();
					    //�����ȴ����շ���˵���Ϣ
					
					    String indata2 = inScanner.nextLine();
	
					    if(indata2.equals("��½�ɹ�!")) {
					    	
						    System.out.println("��½�ɹ�!");
							System.out.print("��������Ҫ��ѯ�Ŀγ�����ƽ���ɼ���");
						    
						    
							keybordscanner2 = new Scanner(System.in);
						    while(keybordscanner2.hasNextLine()){
								
						        String course = keybordscanner2.nextLine();

						        //չʾ�������Ŀ���̨

						        //д������˵ĵĿ���̨
						        pwtoserver.println(course);
						        pwtoserver.flush();
						        //�����ȴ����շ���˵���Ϣ
						    						
					    	    String indata3 = inScanner.nextLine();
					    	    
					    	    
					    	    if(indata3.equals("�޴˿γ�!"))
					    	    {
					    	    	System.out.println("����Ŀ�Ŀ�Ż���ûѡ�ÿγ�!");	
					    	    	}
					    	    else if(indata3.equals("BYE")){
					    	    	System.out.println("GOOD BYE");	
//					    	    	socket.close();
					    	    	System.exit(0);					    	    	
					    	    }
					    	    else {
					    	    	System.out.println(course+":"+indata3);
					        	}
					    	    
					    	    System.out.print("��������Ҫ��ѯ�Ŀγ�����ƽ���ɼ���");
						        }
					        }else {
					        	System.out.println("�����������������!");					            
					    	}
					    System.out.print("���������룺");
				    	}
					    }else {
					    	System.out.println("�޴��û�,���������д˳���!");
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

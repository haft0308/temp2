package project;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class MovieMain{

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		login();
	}
	
	static void login() throws IOException
	{
		System.out.println("�����ϼ̽��ϴ�. ������ [1], �̿��� [2], ��ü���� [3]");
		Scanner mainsc = new Scanner(System.in);
		int number = mainsc.nextInt();
		
		switch(number)
		{
			case 1:
				Manager admin =new Manager();
				admin.login();
				break;
			case 2:		
				Member member = new Member();
				member.login();
				break;
			case 3:
				System.out.println("��ü �����մϴ�.");
				break;
		}
	}

}

class Manager extends AbstractMenu{
	
	private String adminid = "admin";
	private String password = "1234";
	boolean administer = true;
	ArrayList<String> movielists;
	
	File filemovies;
	File filetheaterseats;
	Scanner sc;
	String dataStr;
	
	Manager()
	{
		filemovies = new File("src/project/movies.txt");
		filetheaterseats = new File("src/project/theaterseats");
	}
	
	void managerMenu() throws IOException
	{
		System.out.println("������ �޴��Դϴ�. [1]�α��� [2]��ȭ ��� [3]��ȭ �߰� [4]��ȭ ��� [5]��ȭ ���� [6]��ü ���� [7]�¼� ���� [8]�޴��� ���ư���");
		sc=new Scanner(System.in);
		int number=sc.nextInt();
		
		switch(number)
		{
			case 1: login();
					break;
			
			case 2: getMovie();
					break;
			
			case 3: addMovie();
					break;
			
			case 4: seeList();
					break;
			
			case 5: delMovie();
					break;
			
			case 6: delAll();
					break;
			
			case 7: getSeat();
					break;
			
			case 8: MovieMain.login();
					System.out.println("�ȳ��� �輼��. ����ʪ顣");
					break;
					
		}
		
	}
	
	private void setMovie() throws IOException{
		
		FileWriter fwmovies = new FileWriter(filemovies);
		BufferedWriter bwmovies = new BufferedWriter(fwmovies);
		
		int i=5;
		while (i>0)
		{
			System.out.println("��ȭ ����, �帣, ���ɴ븦 5�� �ۼ����ּ���. ���� "+i+"�� ���ҽ��ϴ�. �ۼ��� [1] ����� [2]");
			
			int tmp=sc.nextInt();
			if(tmp==1)
			{	
				sc = new Scanner(System.in);
				System.out.print("��ȭ�� ����մϴ�. ��");
				String title=sc.nextLine();
				System.out.println("=======��ȭ�� ����߽��ϴ�.=========");				
				System.out.print("�帣�� �Է����ּ���. ��");
				String genre=sc.nextLine();
				System.out.println("=======�帣�� ����߽��ϴ�.=========");
				System.out.print("���ɴ븦 �Է����ּ���. ��");
				String age=sc.nextLine();
				System.out.println("=======���ɴ븦 ����߽��ϴ�.=========");
									
				bwmovies.write(System.currentTimeMillis()+", "+title+", "+genre+", "+age);
				bwmovies.newLine();
			}
			else if(tmp==0)
			{
				System.out.println("��ȭ����� ��ҵǾ����ϴ�.");
			}
			i--;
		}
		bwmovies.close();
		fwmovies.close();
		
		System.out.println("��ȭ ��� ����");
		System.out.println("==========================================================");
		managerMenu();
		
	}
	
	void getMovie() throws IOException
	{
		setMovie();
	}
	
	void seeList() throws IOException
	{
		try
		{
			FileReader fr=new FileReader(filemovies);
			int data=0;
			while((data=fr.read())!=-1)
			{
				System.out.print((char)data);
			}
			fr.close();
		}catch(FileNotFoundException e) {e.printStackTrace();}
		
		System.out.println("==========================================================");
		managerMenu();
	}
	
	void addMovie() throws IOException
	{
		int data=0;
		System.out.println("===================");
		System.out.println("��ȭ�� �߰��Ͻðڽ��ϱ�?");
		
		FileReader frmovies=new FileReader(filemovies);
		BufferedReader brmovies=new BufferedReader(frmovies);
		
		FileWriter fwmovies = new FileWriter(filemovies, true);
		BufferedWriter bwmovies = new BufferedWriter(fwmovies);
		int i=1;
		while(i>0)
		{	
			sc=new Scanner(System.in);
			System.out.print("��ȭ�� ����մϴ�. ��");
			String movie=sc.nextLine();
			System.out.println("======��ȭ�� ����߽��ϴ�.========");
			
			System.out.print("�帣�� �Է����ּ���. ��");
			String genre=sc.nextLine();
			System.out.println("======��Ҹ� ����߽��ϴ�.========");
			
			System.out.print("���ɴ븦 �Է��մϴ�. ��");
			String age=sc.nextLine();
			System.out.println("======���ɴ븦 ����߽��ϴ�.========");
			
			bwmovies.write(System.currentTimeMillis()+", "+movie+", "+genre+", "+age);
			bwmovies.newLine();
			i--;
		}
		bwmovies.close();
		fwmovies.close();
		System.out.println("��ȭ�� �߰��߽��ϴ�.");
		System.out.println("==========================================================");
		managerMenu();
	}
	
	void delMovie() throws IOException
	{
		System.out.println("������ ��ȭ�� �������ּ���.\n===================");
		
		movielists=new ArrayList<String>();
		FileReader frmovies = new FileReader(filemovies);
		BufferedReader brmovies = new BufferedReader(frmovies);
		
		int index=0;
		int num=1;
		
		while((dataStr=brmovies.readLine())!=null)
		{
			movielists.add(dataStr);
			System.out.println("["+(num++)+"]�� "+movielists.get(index));
			index++;
		}
		System.out.println("��ȭ�� �� "+movielists.size()+"���Դϴ�.");
		System.out.println("������ ��ȣ�� �Է����ּ���.\n==================");
		
		int movienum=sc.nextInt();
		movielists.remove(movienum-1);
		
		FileWriter fwmovies = new FileWriter(filemovies);
		BufferedWriter bwmovies = new BufferedWriter(fwmovies);
		
		for(int i=0; i<movielists.size(); i++)
		{
			if(filemovies.canWrite())
			{
				bwmovies.write(movielists.get(i));
				bwmovies.newLine();
			}
		}
		System.out.println("��ȭ ���� ����\n===================================");
		for(int i=0; i<movielists.size(); i++)
		{
			System.out.println(movielists.get(i));
		}
		bwmovies.flush();
		bwmovies.close();
		fwmovies.close();
		managerMenu();
	}
	
	void delAll() throws IOException
	{	
		//this.movies=movies;
		filemovies.delete();
		System.out.println("������ �����߽��ϴ�.\n=====================");
		managerMenu();
	}
	
	void getSeat() throws IOException
	{
		setSeat();
	}
	
	private void setSeat() throws IOException
	{	//2�� �迭�� ���� �¼��� �����. ���� ���ĺ� ���� ���ڷ� �����.
		System.out.println("�¼��� ��� ���� �������ּ���.");

		char seatrow=' ';
		int sum=1;
		System.out.println("A=1, B=2,...Z=26�Դϴ�.");
		
		int row=sc.nextInt();
		System.out.println((char)(row+64)+"���� �Է��߽��ϴ�.\n���� �Է����ּ���.");
		
		int column=sc.nextInt();
		System.out.println(column+"����� �Է��߽��ϴ�.");
		
		if(0<row&&row<27)
		{
			System.out.println("�ùٸ� ���Դϴ�.");
		}
		else{
			System.out.println("�ٽ� �Է����ּ���.");
			setSeat();
		}
		
		int[][] seat=new int[row][column];
		
		FileWriter fwtheaterseats=new FileWriter(filetheaterseats);
		BufferedWriter bwtheaterseats = new BufferedWriter(fwtheaterseats);
		
		for(int i=0; i<seat.length; i++)
		{
			int j;
			for(j=0; j<seat[i].length; j++)
			{
				seat[i][j]=sum+j;
			}
			seatrow=(char)(i+65);
			String str = seatrow+Arrays.toString(seat[i]);
			bwtheaterseats.write(str);
			bwtheaterseats.newLine();
		}
		bwtheaterseats.close();
		fwtheaterseats.close();
		
		FileReader fwtheater=new FileReader(filetheaterseats);
		BufferedReader bwtheater=new BufferedReader(fwtheater);
		
		while((dataStr=bwtheater.readLine())!=null)
		{
			System.out.println(dataStr);
		}
		
		managerMenu();
	}
	public void login()
	{
		while(administer)
		{
			System.out.println("������ �α��� ȭ���Դϴ�.");
			System.out.print("������ ID�� �Է����ּ����");
			sc=new Scanner(System.in);
			String inputid=sc.nextLine();
			System.out.println("========================");
			System.out.print("������ ��й�ȣ�� �Է����ּ���.��");
			String inputpass=sc.nextLine();
			System.out.println("========================");
			
			if(inputid.equals(adminid)&&inputpass.equals(password))
			{
				System.out.println("���ӵǾ����ϴ�.");
				try
				{
					managerMenu();
					break;
				}
				catch(IOException e) {e.printStackTrace();}
			}
			else
			{
				System.out.println("ID �Ǵ� ��й�ȣ�� �߸� �Է��ϼ̽��ϴ�.");
				try
				{
					MovieMain.login();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class Member extends AbstractMenu{
	
	File filemovies;
	File filereservations;
	File filelogindata;
	File fileseat;
	
	ArrayList<String> moviearrays=new ArrayList<String>();
	ArrayList<String> reservearrays = new ArrayList<String>();
	ArrayList<String> seatarrays = new ArrayList<String>();
	
	String data=null;
	Scanner sc = new Scanner(System.in);
	
	Member()throws IOException
	{
		filemovies = new File("src/project/movies.txt");
		filereservations = new File("src/project/reservation.txt");
		filelogindata = new File("src/project/logindata.txt");
		fileseat = new File("src/project/theaterseats");
	}
	
	void memberMenu() throws IOException
	{
		System.out.println("�޴��� �������ּ���. [1] ��ȭ���� [2] ���� ��Ȳ [3] ���� �߰� [4] ���Ż��� [5] �޴����ư���");
		int selectnum=sc.nextInt();
		switch(selectnum)
			{
			case 1:{
				getReservation();
				break;
			}
			case 2:
			{
				seeReservation();
				break;
			}
			case 3:
			{
				addReservation();
				break;
			}
			case 4:
			{
				delReservation();
				break;
			}
			
			case 5:
			{
				MovieMain.login();
				System.out.println("�ȳ��� �輼��! ����ʪ顣");
				break;
			}
		}
	}
	
	private void setReservation() throws IOException
	{
		try
		{
			FileReader frmovies = new FileReader(filemovies);
			BufferedReader brmovies = new BufferedReader(frmovies);
			String str=null;
			String data=null;
			
			while((data=brmovies.readLine())!=null)
			{
				moviearrays.add(data);
			}
			
			FileWriter fwreservations = new FileWriter(filereservations);
			BufferedWriter bwreservations = new BufferedWriter(fwreservations);
			int j=0;
			int i=0;
			
			do
			{
				System.out.println("��ȭ�� �����Ͻðڽ��ϱ�? [1] ���� [2] ���");
				System.out.println("===================================");
				
				int selection = sc.nextInt();
				if(selection==1)
				{
					System.out.print("��Ҹ� �������ּ���. ����� ���� �����ּ���.��");
					String place=sc.next();
					
					for(i=0; i<moviearrays.size(); i++)
					{
						System.out.println("["+(i+1)+"��]"+moviearrays.get(i));
					}
					int purchase=sc.nextInt();
					String movienum=moviearrays.get(purchase-1);
					System.out.println("["+purchase+"��] ��ȭ�� �����߽��ϴ�.\n=========================");
					
					FileReader frseat = new FileReader(fileseat);
					BufferedReader brseats = new BufferedReader(frseat);
					
					
					while((str=brseats.readLine())!=null)
					{
						seatarrays.add(str);
						System.out.println(str);
					}
					System.out.println("==========================");
					System.out.println("��ȭ �¼��� �������ּ���.");
					System.out.println("���ϴ� �¼� ���� �������ּ���. A-Z ������ ��ȣ�� �Է����ּ���.");
					
					int row=sc.nextInt();
					String row2=seatarrays.get(row-1);
					char alphabet=row2.charAt(0);
					System.out.println(alphabet+"���� �����ϼ̽��ϴ�.");
					System.out.println("���Ͻô� ���� �������ּ���.");
					
					int column=sc.nextInt();
					char column2=row2.charAt(3*column-1);
					System.out.println(column+"���� �����߽��ϴ�.");
					String seatnumber=alphabet+"-"+column2;
					
					bwreservations.write(place+", "+System.currentTimeMillis()+", "+movienum+", "+seatnumber);
					bwreservations.newLine();
					System.out.println("��ȭ���Ÿ� �����մϴ�.");
				}
				else if(selection==2)
				{
					System.out.println("��ȭ ���Ÿ� ����߽��ϴ�.");
					break;
				}
				
			}while((j++)<moviearrays.size());
			bwreservations.close();
			fwreservations.close();
			System.out.println("��ȭ���Ÿ� �����մϴ�.");
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("������ ã�� ���� �����ϴ�.");
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("������ �ʰ��߽��ϴ�. �ٽ� �������ּ���.");
		}
		catch(IOException e)
		{
			
		}
		memberMenu();
	}
	
	void getReservation() throws IOException
	{
		setReservation();
	}
	
	void addReservation() throws IOException
	{
		int i=0;
		int selection;
		System.out.println("�߰� ���Ÿ� �Ͻðڽ��ϱ�? ��[1] �ƴϿ�[2]");
		selection = sc.nextInt();
		if(selection==1)
		{
			try
			{
				FileReader frmovies = new FileReader(filemovies);
				BufferedReader brmovies = new BufferedReader(frmovies);
				String str=null;
				String data=null;
				/*
				while((data=brmovies.readLine())!=null)
				{
					moviearrays.add(data);
				}
				*/
				FileWriter fwreservations = new FileWriter(filereservations, true);
				BufferedWriter bwreservations = new BufferedWriter(fwreservations);
				do
				{
					System.out.print("��Ҹ� �������ּ���. ����� ���� �����ּ���.��");
					String place=sc.next();
					
					for(i=0; i<moviearrays.size(); i++)
					{
						System.out.println("["+(i+1)+"��]"+moviearrays.get(i));
					}
					int purchase=sc.nextInt();
					String movienum=moviearrays.get(purchase-1);
					System.out.println("["+purchase+"��] ��ȭ�� �����߽��ϴ�.\n=========================");
					
					FileReader frseat = new FileReader(fileseat);
					BufferedReader brseats = new BufferedReader(frseat);
					
					
					while((str=brseats.readLine())!=null)
					{
						seatarrays.add(str);
						System.out.println(str);
					}
					System.out.println("==========================");
					System.out.println("��ȭ �¼��� �������ּ���.");
					System.out.println("���ϴ� �¼� ���� �������ּ���. A-Z ������ ��ȣ�� �Է����ּ���.");
						
					int row=sc.nextInt();
					String row2=seatarrays.get(row-1);
					char alphabet=row2.charAt(0);
					System.out.println(alphabet+"���� �����ϼ̽��ϴ�.");
					System.out.println("���Ͻô� ���� �������ּ���.");
						
					int column=sc.nextInt();
					char column2=row2.charAt(3*column-1);
					String seatnumber=alphabet+"-"+column2;
						
					bwreservations.write(place+", "+System.currentTimeMillis()+", "+movienum+", "+seatnumber);
					bwreservations.newLine();
					System.out.println("��ȭ���Ÿ� �����մϴ�.");
					i++;				
				}while(i<1);
				bwreservations.close();
				fwreservations.close();
				System.out.println("��ȭ���Ÿ� �����մϴ�.");
				
			}catch(FileNotFoundException e)
			{
				e.printStackTrace();
				System.out.println("������ ã�� ���� �����ϴ�.");
			}
			catch(IndexOutOfBoundsException e)
			{
				System.out.println("������ �ʰ��߽��ϴ�. �ٽ� �������ּ���.");
			}
			catch(IOException e)
			{
				
			}
			memberMenu();
		}
		else if(selection==2)
		{
			System.out.println("�޴��� ���ư��ϴ�.");
			memberMenu();
		}
		
		
	}
	
	void delReservation() throws IOException
	{
		FileReader frreservations = new FileReader(filereservations);
		BufferedReader brreservations = new BufferedReader(frreservations);
		
		int i=0;
		System.out.println("����� ���Ÿ� �������ּ���.\n=================");
		
		while((data=brreservations.readLine())!=null)
		{
			System.out.println("["+(++i)+"]"+data);
			reservearrays.add(data);
		}
		
		int delnumber=sc.nextInt();
		reservearrays.remove(delnumber-1);
		System.out.println("���Ÿ� ����߽��ϴ�.");
		
		
		FileWriter fwreserve = new FileWriter(filereservations);
		BufferedWriter bwreserve = new BufferedWriter(fwreserve);
		
		while(filereservations.canWrite())
		{
			for(i=0; i<reservearrays.size(); i++) {
				bwreserve.write(reservearrays.get(i));
				bwreserve.newLine();
			}
			break;
		}
		bwreserve.close();
		fwreserve.close();
		
		System.out.println("���� �� ���ο� ��ȭ ���� �ϼ�");
		memberMenu();
	}
	
	void seeReservation() throws IOException
	{
		FileReader frreservations = new FileReader(filereservations);
		int i;
		while((i=frreservations.read())!=-1)
		{
			System.out.print((char)i);
		}
		System.out.println("=============================");
		memberMenu();
	}
	
	public void login()
	{
		int yn;
		String mname=null;
		String mpass=null;
		boolean memberid=true;
		String buf=null;
		String[] member= {};
		try {
			System.out.println("��ȸ���̸� [1] ȸ���̸� [2]�� �Է����ּ���.");
			yn=sc.nextInt();
			
				if(yn==1)
				{
					FileWriter fwlogindata = new FileWriter(filelogindata);
					BufferedWriter bwlogindata = new BufferedWriter(fwlogindata);
					
					bwlogindata.write(register());
					bwlogindata.newLine();
							
					bwlogindata.close();
					fwlogindata.close();
					login();
						
				}
				else if(yn==2)
				{
						System.out.println("=====�α��� ȭ������ �̵��մϴ�.=====");
						FileReader frlogindata = new FileReader(filelogindata);
						BufferedReader brlogindata = new BufferedReader(frlogindata);
						String id=null;
						String pw=null;
						Scanner loginsc=new Scanner(System.in);
						
						while((buf=brlogindata.readLine())!=null)
						{
							member=buf.split(", ");
							System.out.print("���̵� �Է����ּ���.��");
							
							id=loginsc.nextLine();
							
							System.out.println("��й�ȣ�� �Է����ּ���.");
							pw=loginsc.nextLine();
							
							if(id.equals(member[0])&&pw.equals(member[1]))
							{
								System.out.println("\n"+"������"+id+"�� ȯ���մϴ�."+"������");
								memberid=false;
							}
							if(memberid!=false)
							{
								System.out.println("ȸ�� ������ ��ġ���� �ʽ��ϴ�. ȸ�� ������ �ٽ� �Է��ϼ���.");
								login();
							}
						}
						memberMenu();
				}
				else 
				{
					System.out.println("ȸ�������� �������ּ���. �α��� �� ȸ������ �޴��� �̵��մϴ�.");
					register();
				}						
					

		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	static String register()
	{
		String information=null;
		String name = null;
		String phone = null;
		String password = null;
		Scanner membersc = new Scanner(System.in);
		
		System.out.print("�̸��� �Է��ϼ��� ��");
		name = membersc.next();
		System.out.println("=======================");
		
		System.out.print("��ȭ��ȣ�� �Է��ϼ���(-����)��");
		phone = membersc.next();
		System.out.println("=======================");
		
		System.out.print("��й�ȣ�� �Է��ϼ���.(���ڸ�)��");
		password = membersc.next();
		System.out.println("ȸ������ �Է��� ���ƽ��ϴ�.");
		
		information= name+", "+password+", "+phone;
		System.out.println(information);
		return information= name+", "+password+", "+phone;
	}
	
}

interface Menu{
	public abstract void login();
}

abstract class AbstractMenu implements Menu{
	public abstract void login();
	
}
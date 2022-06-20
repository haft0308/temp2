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
		System.out.println("접속하셨습니다. 관리자 [1], 이용자 [2], 전체종료 [3]");
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
				System.out.println("전체 종료합니다.");
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
		System.out.println("관리자 메뉴입니다. [1]로그인 [2]영화 등록 [3]영화 추가 [4]영화 목록 [5]영화 삭제 [6]전체 삭제 [7]좌석 설정 [8]메뉴로 돌아가기");
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
					System.out.println("안녕히 계세요. さよなら。");
					break;
					
		}
		
	}
	
	private void setMovie() throws IOException{
		
		FileWriter fwmovies = new FileWriter(filemovies);
		BufferedWriter bwmovies = new BufferedWriter(fwmovies);
		
		int i=5;
		while (i>0)
		{
			System.out.println("영화 제목, 장르, 연령대를 5번 작성해주세요. 현재 "+i+"번 남았습니다. 작성은 [1] 종료는 [2]");
			
			int tmp=sc.nextInt();
			if(tmp==1)
			{	
				sc = new Scanner(System.in);
				System.out.print("영화를 등록합니다. →");
				String title=sc.nextLine();
				System.out.println("=======영화를 등록했습니다.=========");				
				System.out.print("장르를 입력해주세요. →");
				String genre=sc.nextLine();
				System.out.println("=======장르를 등록했습니다.=========");
				System.out.print("연령대를 입력해주세요. →");
				String age=sc.nextLine();
				System.out.println("=======연령대를 등록했습니다.=========");
									
				bwmovies.write(System.currentTimeMillis()+", "+title+", "+genre+", "+age);
				bwmovies.newLine();
			}
			else if(tmp==0)
			{
				System.out.println("영화등록이 취소되었습니다.");
			}
			i--;
		}
		bwmovies.close();
		fwmovies.close();
		
		System.out.println("영화 등록 종료");
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
		System.out.println("영화를 추가하시겠습니까?");
		
		FileReader frmovies=new FileReader(filemovies);
		BufferedReader brmovies=new BufferedReader(frmovies);
		
		FileWriter fwmovies = new FileWriter(filemovies, true);
		BufferedWriter bwmovies = new BufferedWriter(fwmovies);
		int i=1;
		while(i>0)
		{	
			sc=new Scanner(System.in);
			System.out.print("영화를 등록합니다. →");
			String movie=sc.nextLine();
			System.out.println("======영화를 등록했습니다.========");
			
			System.out.print("장르를 입력해주세요. →");
			String genre=sc.nextLine();
			System.out.println("======장소를 등록했습니다.========");
			
			System.out.print("연령대를 입력합니다. →");
			String age=sc.nextLine();
			System.out.println("======연령대를 등록했습니다.========");
			
			bwmovies.write(System.currentTimeMillis()+", "+movie+", "+genre+", "+age);
			bwmovies.newLine();
			i--;
		}
		bwmovies.close();
		fwmovies.close();
		System.out.println("영화를 추가했습니다.");
		System.out.println("==========================================================");
		managerMenu();
	}
	
	void delMovie() throws IOException
	{
		System.out.println("삭제할 영화를 선택해주세요.\n===================");
		
		movielists=new ArrayList<String>();
		FileReader frmovies = new FileReader(filemovies);
		BufferedReader brmovies = new BufferedReader(frmovies);
		
		int index=0;
		int num=1;
		
		while((dataStr=brmovies.readLine())!=null)
		{
			movielists.add(dataStr);
			System.out.println("["+(num++)+"]번 "+movielists.get(index));
			index++;
		}
		System.out.println("영화는 총 "+movielists.size()+"개입니다.");
		System.out.println("삭제할 번호를 입력해주세요.\n==================");
		
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
		System.out.println("영화 삭제 종료\n===================================");
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
		System.out.println("파일을 삭제했습니다.\n=====================");
		managerMenu();
	}
	
	void getSeat() throws IOException
	{
		setSeat();
	}
	
	private void setSeat() throws IOException
	{	//2차 배열로 극장 좌석을 만든다. 행은 알파벳 열은 숫자로 만든다.
		System.out.println("좌석의 행과 열을 지정해주세요.");

		char seatrow=' ';
		int sum=1;
		System.out.println("A=1, B=2,...Z=26입니다.");
		
		int row=sc.nextInt();
		System.out.println((char)(row+64)+"행을 입력했습니다.\n열을 입력해주세요.");
		
		int column=sc.nextInt();
		System.out.println(column+"행까지 입력했습니다.");
		
		if(0<row&&row<27)
		{
			System.out.println("올바른 값입니다.");
		}
		else{
			System.out.println("다시 입력해주세요.");
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
			System.out.println("관리자 로그인 화면입니다.");
			System.out.print("관리자 ID를 입력해주세요→");
			sc=new Scanner(System.in);
			String inputid=sc.nextLine();
			System.out.println("========================");
			System.out.print("관리자 비밀번호를 입력해주세요.→");
			String inputpass=sc.nextLine();
			System.out.println("========================");
			
			if(inputid.equals(adminid)&&inputpass.equals(password))
			{
				System.out.println("접속되었습니다.");
				try
				{
					managerMenu();
					break;
				}
				catch(IOException e) {e.printStackTrace();}
			}
			else
			{
				System.out.println("ID 또는 비밀번호를 잘못 입력하셨습니다.");
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
		System.out.println("메뉴를 선택해주세요. [1] 영화예매 [2] 예매 현황 [3] 예매 추가 [4] 예매삭제 [5] 메뉴돌아가기");
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
				System.out.println("안녕히 계세요! さよなら。");
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
				System.out.println("영화를 예매하시겠습니까? [1] 예매 [2] 취소");
				System.out.println("===================================");
				
				int selection = sc.nextInt();
				if(selection==1)
				{
					System.out.print("장소를 선택해주세요. 띄어쓰기는 하지 말아주세요.→");
					String place=sc.next();
					
					for(i=0; i<moviearrays.size(); i++)
					{
						System.out.println("["+(i+1)+"번]"+moviearrays.get(i));
					}
					int purchase=sc.nextInt();
					String movienum=moviearrays.get(purchase-1);
					System.out.println("["+purchase+"번] 영화를 선택했습니다.\n=========================");
					
					FileReader frseat = new FileReader(fileseat);
					BufferedReader brseats = new BufferedReader(frseat);
					
					
					while((str=brseats.readLine())!=null)
					{
						seatarrays.add(str);
						System.out.println(str);
					}
					System.out.println("==========================");
					System.out.println("영화 좌석을 선택해주세요.");
					System.out.println("원하는 좌석 행을 선택해주세요. A-Z 순으로 번호를 입력해주세요.");
					
					int row=sc.nextInt();
					String row2=seatarrays.get(row-1);
					char alphabet=row2.charAt(0);
					System.out.println(alphabet+"행을 선택하셨습니다.");
					System.out.println("원하시는 열을 선택해주세요.");
					
					int column=sc.nextInt();
					char column2=row2.charAt(3*column-1);
					System.out.println(column+"열을 선택했습니다.");
					String seatnumber=alphabet+"-"+column2;
					
					bwreservations.write(place+", "+System.currentTimeMillis()+", "+movienum+", "+seatnumber);
					bwreservations.newLine();
					System.out.println("영화예매를 종료합니다.");
				}
				else if(selection==2)
				{
					System.out.println("영화 예매를 취소했습니다.");
					break;
				}
				
			}while((j++)<moviearrays.size());
			bwreservations.close();
			fwreservations.close();
			System.out.println("영화예매를 종료합니다.");
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("파일을 찾을 수가 없습니다.");
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("범위를 초과했습니다. 다시 실행해주세요.");
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
		System.out.println("추가 예매를 하시겠습니까? 예[1] 아니오[2]");
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
					System.out.print("장소를 선택해주세요. 띄어쓰기는 하지 말아주세요.→");
					String place=sc.next();
					
					for(i=0; i<moviearrays.size(); i++)
					{
						System.out.println("["+(i+1)+"번]"+moviearrays.get(i));
					}
					int purchase=sc.nextInt();
					String movienum=moviearrays.get(purchase-1);
					System.out.println("["+purchase+"번] 영화를 선택했습니다.\n=========================");
					
					FileReader frseat = new FileReader(fileseat);
					BufferedReader brseats = new BufferedReader(frseat);
					
					
					while((str=brseats.readLine())!=null)
					{
						seatarrays.add(str);
						System.out.println(str);
					}
					System.out.println("==========================");
					System.out.println("영화 좌석을 선택해주세요.");
					System.out.println("원하는 좌석 행을 선택해주세요. A-Z 순으로 번호를 입력해주세요.");
						
					int row=sc.nextInt();
					String row2=seatarrays.get(row-1);
					char alphabet=row2.charAt(0);
					System.out.println(alphabet+"행을 선택하셨습니다.");
					System.out.println("원하시는 열을 선택해주세요.");
						
					int column=sc.nextInt();
					char column2=row2.charAt(3*column-1);
					String seatnumber=alphabet+"-"+column2;
						
					bwreservations.write(place+", "+System.currentTimeMillis()+", "+movienum+", "+seatnumber);
					bwreservations.newLine();
					System.out.println("영화예매를 종료합니다.");
					i++;				
				}while(i<1);
				bwreservations.close();
				fwreservations.close();
				System.out.println("영화예매를 종료합니다.");
				
			}catch(FileNotFoundException e)
			{
				e.printStackTrace();
				System.out.println("파일을 찾을 수가 없습니다.");
			}
			catch(IndexOutOfBoundsException e)
			{
				System.out.println("범위를 초과했습니다. 다시 실행해주세요.");
			}
			catch(IOException e)
			{
				
			}
			memberMenu();
		}
		else if(selection==2)
		{
			System.out.println("메뉴로 돌아갑니다.");
			memberMenu();
		}
		
		
	}
	
	void delReservation() throws IOException
	{
		FileReader frreservations = new FileReader(filereservations);
		BufferedReader brreservations = new BufferedReader(frreservations);
		
		int i=0;
		System.out.println("취소할 예매를 선택해주세요.\n=================");
		
		while((data=brreservations.readLine())!=null)
		{
			System.out.println("["+(++i)+"]"+data);
			reservearrays.add(data);
		}
		
		int delnumber=sc.nextInt();
		reservearrays.remove(delnumber-1);
		System.out.println("예매를 취소했습니다.");
		
		
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
		
		System.out.println("삭제 후 새로운 영화 파일 완성");
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
			System.out.println("비회원이면 [1] 회원이면 [2]를 입력해주세요.");
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
						System.out.println("=====로그인 화면으로 이동합니다.=====");
						FileReader frlogindata = new FileReader(filelogindata);
						BufferedReader brlogindata = new BufferedReader(frlogindata);
						String id=null;
						String pw=null;
						Scanner loginsc=new Scanner(System.in);
						
						while((buf=brlogindata.readLine())!=null)
						{
							member=buf.split(", ");
							System.out.print("아이디를 입력해주세요.→");
							
							id=loginsc.nextLine();
							
							System.out.println("비밀번호를 입력해주세요.");
							pw=loginsc.nextLine();
							
							if(id.equals(member[0])&&pw.equals(member[1]))
							{
								System.out.println("\n"+"♥♥♥"+id+"님 환영합니다."+"♥♥♥");
								memberid=false;
							}
							if(memberid!=false)
							{
								System.out.println("회원 정보가 일치하지 않습니다. 회원 정보를 다시 입력하세요.");
								login();
							}
						}
						memberMenu();
				}
				else 
				{
					System.out.println("회원가입을 진행해주세요. 로그인 및 회원가입 메뉴로 이동합니다.");
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
		
		System.out.print("이름을 입력하세요 →");
		name = membersc.next();
		System.out.println("=======================");
		
		System.out.print("전화번호를 입력하세요(-제외)→");
		phone = membersc.next();
		System.out.println("=======================");
		
		System.out.print("비밀번호를 입력하세요.(숫자만)→");
		password = membersc.next();
		System.out.println("회원정보 입력을 마쳤습니다.");
		
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
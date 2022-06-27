package movie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ReservationProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReservationMain rm = new ReservationMain();
		rm.process();
//		NonMemberReserv nmr = new NonMemberReserv();
//		nmr.res();
	}

}
class ReservationMain{
	
	Scanner sc = new Scanner(System.in);
	public void process(){
		int select;
		boolean flag = true;
		while(flag) {
			System.out.println("Hello!  HoTTo Cinema!       ─ver1.0");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("1.[회원 예매]");
			System.out.println("2.[비회원 예매]");
			System.out.println("3.[종료]");
			select=sc.nextInt();
			switch(select) {
			case 1:
				membersReserv();
				flag=false;
				break;
			case 2:
				nonMembersReserv();
				flag=false;
				break;
			case 3:
				flag=false;
				break;
			case 1419:				//관리자모드로 접근하려면 관리자 고유번호를 입력해야함. 메인메뉴에는 보이지않게 설정.
				Admin a = new Admin();
				a.adminMenu();
				flag=false;
				break;
			default:
				System.out.println("정확한 번호를 입력해주세요.");
				break;
			}
		}
	}
	void membersReserv() {
		int select;
		boolean flag = true;
		while(flag) {
			System.out.println("1.[로그인]");
			System.out.println("2.[회원가입]");
			System.out.println("3.[뒤로가기]");
			select = sc.nextInt();
			switch(select) {
			case 1:
				signIn();
				flag=false;
				break;
			case 2:
				signUp();
				flag=false;
				break;
			case 3:
				process();
				flag=false;
				break;
			}
		}
	}
	void nonMembersReserv() {
		NonMemberReserv nmr = new NonMemberReserv();
		nmr.reservation();
	}
	void signIn() {
		boolean flag=true;
		while(flag) {
			System.out.println("───로그인───");
			System.out.print("[ID]: ");
			String id = sc.next();
			System.out.print("[PW]: ");
			String pw = sc.next();
			try {
				FileReader fr = new FileReader("src/movie/member.txt");
				BufferedReader br = new BufferedReader(fr);
				String buf;
				
				while((buf=br.readLine()) != null) {
					String[] arr=buf.split(" ");
					IdPw ip=new IdPw(arr[0],arr[1]);
					idpw.add(ip);
				}
				fr.close();
				br.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			int x=0;
			//id pw값 idpw에서 비교후 일치하면 예메 페이지 이동
			IdPw ip = new IdPw(id, pw);
			for(int i=0; i<idpw.size(); i++) {
				IdPw ip2=idpw.get(i);
				if(ip.getId().equals(ip2.getId())&&ip.getPw().equals(ip.getPw())) {
					idpwIndex = i;
					MemberReserv mr = new MemberReserv();  //MemberReserve 클래스 아래에 따로 존재
					mr.reservation();
					flag=false;
					x=1;
					break;
				}
			}
			if(x==0) {
				System.out.println("아이디/비밀번호가 일치하지 않습니다.");
			}
		}
	}
	static ArrayList<Members> privacy = new ArrayList<Members>();				//개인정보와 id pw값이 변하지 않게 static 처리
	static ArrayList<IdPw> idpw = new ArrayList<IdPw>();
	static int idpwIndex;
	void signUp() {
		Members m;
		IdPw ip = null;
		System.out.println("───회원가입───");
		System.out.print("[이름]: ");
		String name = sc.next();
		
		System.out.print("[생년월일]: ");
		String birth = sc.next();
		
		
		System.out.print("[e-mail]: ");
		String email = sc.next();
		
		String grade="등급";
		String point="0";
		
		m = new Members(name, birth, email, grade, point);
		
		System.out.println("회원가입 창으로 이동합니다...");
		try {
			int rand = (int)Math.random()*5000;			//Math.random, thread.sleep 으로 랜덤한시간동안 로딩처리
			Thread.sleep(rand);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean flag = true;
		while(flag) {	
			System.out.println("사용하실 아이디와 비밀번호를 입력해주세요.");
			System.out.print("[아이디]: ");
			String id = sc.next();
			ArrayList<String> ids = new ArrayList<String>();
			try {
				FileReader fr = new FileReader("src/movie/member.txt");
				BufferedReader br = new BufferedReader(fr);
				String buf;
				while((buf=br.readLine())!=null) {
					ids.add(buf.split(" ")[0]);
				}
				fr.close();
				br.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			for(int i=0; i<ids.size(); i++) {
				if(ids.get(i).equals(id)) {
					System.out.println("중복된 아이디입니다!");
				}else {
					System.out.print("[비밀번호]: ");
					String pw = sc.next();

					System.out.print("[비밀번호 재입력]: ");

					String pwAgain = sc.next();
					if(pw.equals(pwAgain)) {
						ip = new IdPw(id, pw);
						
						try {
							FileWriter memberFile = new FileWriter("src/movie/member.txt",true);
							FileReader memberRead = new FileReader("src/movie/member.txt");
							BufferedReader memberBR = new BufferedReader(memberRead);
							String txt = ip.toString() + m.toString() + "\n";
							memberFile.write(txt);
							memberFile.close();
							String buf;
							while((buf=memberBR.readLine())!=null) {
								String[] arr=buf.split(" ");
								IdPw ip2=new IdPw(arr[0],arr[1]);
								idpw.add(ip2);
							}
							memberRead.close();
							memberBR.close();
						}catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("HOTTO의 회원이 되어주셔서 감사합니다!");
						System.out.println("영화 예매 창으로 이동합니다.");
						for(int j=0; j<idpw.size(); j++) {
							IdPw ip2=idpw.get(j);
							if(ip.getId().equals(ip2.getId())&&ip.getPw().equals(ip.getPw())) {
								idpwIndex = j;
								MemberReserv mr = new MemberReserv();  //MemberReserve 클래스 아래에 따로 존재
								mr.reservation();
								flag=false;
								break;
							}
						}
						break;
					}
					else {
						System.out.println("비밀번호가 일치하지 않습니다.");
					}
				}
			}
		}
	}
}
class Members{										//회원 개인정보 저장클래스
	private String name;
	private String birth;
	private String email;
	public String grade;
	public String point;
	
	Members(){
		
	}
	Members(String name, String birth, String email, String grade, String point){
		this.name = name;
		this.birth = birth;
		this.email = email;
		this.grade = grade;
		this.point = point;
	}
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public String getBirth(){
		return birth;
	}
	public String getEmail() {
		return email;
	}
	@Override
	public String toString() {
		return " " + name + " " + birth + " " + email + " " + grade+ " " + point;
	}
	
}
class IdPw{
	private String id;
	private String pw;
	IdPw(){
		
	}
	IdPw(String id, String pw){
		this.id = id;
		this.pw = pw;
	}
	public String getId()
	{
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String toString() {
		return id + " " + pw;
	}
}
interface Reserv{
	void reservation();
	void res();
	void resCheck();
	void resCancel();
	void seatSelect(int select);
}
class NonMemberReserv implements Reserv{
//	ReservationMain rm = new ReservationMain();
//	String id=rm.idpw.get(rm.idpwIndex).getId();
	
	Scanner sc = new Scanner(System.in);
	public void reservation() {
		int select;
		boolean flag = true;
		while(flag) {
			System.out.println("───HOTTO Cinema 비회원 예매 페이지───");
			System.out.println("1.[예매 하기] \n2.[예매 확인] \n3.[예매 취소] \n4.[메인메뉴로 이동]");
			select = sc.nextInt();
			switch(select) {
			case 1:
				res();
				flag=false;
				break;
			case 2: 
				resCheck();
				flag=false;
				break;
			case 3:
				resCancel();
				flag=false;
				break;
			case 4:
				ReservationMain rm=new ReservationMain();
				rm.process();
				flag=false;
				break;
			}
		}
	}
	ArrayList<String> movie = new ArrayList<String>();
	int select;
	public void res() {
		
		try {
			FileReader fr = new FileReader("src/movie/movies.txt");
			BufferedReader br = new BufferedReader(fr);
			String buf;
			
			while((buf=br.readLine()) != null) {
				if(movie.size()==0) {
					movie.add(buf);
				}else {
					int num=0;
					for(int i=0; i<movie.size(); i++) {
						if(movie.get(i).equals(buf)) {
							num++;
						}
						if(i==movie.size()-1 && num==0) {
							movie.add(buf);
						}
					}
				}
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[][] seats = new String[6][10];
		for (int i=0; i<seats.length; i++) {
			seats[i][0]=Character.toString(65+i);
			seats[5][0]=" ";
			for (int j=1; j<seats[i].length; j++) {
				seats[i][j]="o";
				seats[5][j]=Integer.toString(j);
			}
		}
		
		System.out.println("[영화 선택]");
		for(int i=0; i<movie.size(); i++) {
			System.out.println(movie.get(i));
		}
		System.out.println("예매할 영화를 선택하세요:");
		select = sc.nextInt();								
		int select2 = select*7-6;
		switch(select) {										
		case 1:
			seatSelect(select);
			break;
		case 2:
			seatSelect(select2);
			break;
		case 3:
			seatSelect(select2);
			break;
		case 4:
			seatSelect(select2);
			break;
		case 5:
			seatSelect(select2);
			break;
		case 6:
			seatSelect(select2);
			break;
		case 7:
			seatSelect(select2);
			break;
		case 8:
			seatSelect(select2);
			break;
		case 9:
			seatSelect(select2);
			break;
		case 10:
			seatSelect(select2);
			break;
			}
		}
	public void resCheck() {
		try {
			FileReader fr = new FileReader("src/movie/reservation.txt");
			BufferedReader br = new BufferedReader(fr);
			String buf;
			System.out.print("발급번호를 입력하세요 :");
			String num=sc.next();
			int tf=0;
			while((buf=br.readLine())!=null) {
				if(buf.split(" ")[0].equals(num)) {
					System.out.println("고객님이 예약하신 내역입니다.");
					System.out.println(buf);
					tf=1;
					break;
				}
			}
			if(tf==0) {
				System.out.println("잘못된 발급번호입니다.");
			}
			fr.close();
			br.close();
			reservation();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void resCancel() {
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> al2 = new ArrayList<String>();
		String[][] seatarr = new String[6][10];
		try {
			FileReader fr = new FileReader("src/movie/reservation.txt");
			FileReader seat = new FileReader("src/movie/seats.txt");
			FileReader movie = new FileReader("src/movie/movies.txt");
			BufferedReader br = new BufferedReader(fr);
			BufferedReader seatbr = new BufferedReader(seat);
			BufferedReader moviebr = new BufferedReader(movie);
			String buf;
			System.out.print("발급번호를 입력하세요 :");
			String num=sc.next();
			String seatNum=null;
			String movieNum=null;
			String index=null;
			int idx=0;
			int tf=0;
			while((buf=br.readLine())!=null) {
				String[] arr = buf.split(" ");
				al2.add(buf);
				if(arr[0].equals(num)) {
					movieNum = arr[1];
					seatNum = arr[3];
					tf=1;
				}
			}
			if(tf==0) {
				System.out.println("잘못된 발급번호입니다.");
			}
			while((buf=moviebr.readLine())!= null) {
				String[] spl = buf.split(",")[0].split(" ");
				if(movieNum.equals(spl[1])) {
					index = spl[0];
				}
			}
			while((buf=seatbr.readLine())!=null) {
				al.add(buf);
			}
			for(int i=0; i<al.size(); i++) {
				if(al.get(i).equals(index)) {
					idx=i;
				}
			}
			for(int i=0; i<6; i++) {
				seatarr[i]=al.get(idx+i+1).split(" ");
			}
			String[] splarr = seatNum.split("-");
			for(int i=0; i<6; i++) {
				if(seatarr[i][0].equals(splarr[0])) {
					for(int j=1; j<10; j++) {
						if(seatarr[i][Integer.parseInt(splarr[1])].equals("x")) {
							seatarr[i][Integer.parseInt(splarr[1])]="o";
						}
					}
				}
			}
			String[] arr = {"","","","","",""}; 
			for(int i=0; i<5; i++) {
				for(int j=0; j<10; j++) {
					arr[i] += seatarr[i][j]+" ";
					arr[5] = "  1 2 3 4 5 6 7 8 9";
				}
			}
			int x=0;
			for(int i=idx+1; i<idx+7; i++) {
				al.set(i, arr[x]);
				x++;
			}
			FileWriter seatwrite = new FileWriter("src/movie/seats.txt");
			for(int i=0; i<al.size(); i++) {
				seatwrite.write(al.get(i)+"\n");
			}
			for(int i=0; i<al2.size(); i++) {
				if(al2.get(i).split(" ")[0].equals(num)) {
					al2.remove(i);
				}else {
					continue;
				}
			}
			FileWriter resWrite = new FileWriter("src/movie/reservation.txt");
			for(int i=0; i<al2.size(); i++) {
				resWrite.write(al2.get(i)+"\n");
			}
			System.out.println("취소가 완료되었습니다.");
			fr.close();
			seat.close();
			seatwrite.close();
			br.close();
			seatbr.close();
			moviebr.close();
			resWrite.close();
			reservation();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void seatSelect(int select) {   				
		ArrayList<String> al = new ArrayList<String>();
		String[][] seats = new String[6][10];
		try {
			FileReader fr = new FileReader("src/movie/seats.txt");
			BufferedReader br = new BufferedReader(fr);
			String buf;
			while((buf=br.readLine()) != null) {
				al.add(buf);
			}
			int a=0;
			for(int i=select; i<select+6; i++) {
				String[] split = al.get(i).split(" ");
				for(int j=0; j<10; j++) {
					seats[a][j] = split[j];
					if(i==6) {
						seats[5][0] = " ";
						seats[5][j] = Integer.toString(j);
					}
				}
				a++;
			}
			br.close();
			fr.close();
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("현재 상영중인 영화가 없습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
														//영화 선택후 저장되어있는 좌석현황을 불러와 seats배열에 재주입,
		String seatNum;									//좌석 선택하면 'o'인지 확인 후 맞으면 'x'변환, 아니면 "이미 예약된 좌석입니다"반환
		String x;										//영화마다 좌석상태를 받아와서 seats배열에 다시 담아서 표시
		String y;
		boolean flag=true;
		while(flag) {
			System.out.println("───────────────────");
			System.out.println("    S C R E E N    ");
			System.out.println("───────────────────");
			for (int i=0; i<seats.length; i++) {
				for(int j=0; j<seats[i].length; j++) {
					System.out.print(seats[i][j]+" ");
				}	
				System.out.println("");
			}
			System.out.println("[좌석 선택]  (예: E-9)");
			seatNum=sc.next();
			String[] seat_a = seatNum.split("-");
			x=seat_a[0];
			y=seat_a[1];
			for(int i=0; i<seats.length; i++) {
				if(seats[i][0].equals(x)) {
					for(int j=1; j<seats[i].length; j++) {
						if(seats[i][Integer.parseInt(y)].equals("o")) {
								seats[i][Integer.parseInt(y)]="x";
								System.out.println("예매가 완료되었습니다.");
								resWrite(seatNum);
								seatWrite(seats, al);
								flag=false;
								reservation();
								break;
							}
						else if(seats[i][Integer.parseInt(y)].equals("x")) {
								System.out.println("이미 예매완료된 좌석입니다.");
								break;
							}
						}
					break;
					}
				else if(i==5) {
					System.out.println("정확한 좌석번호를 입력하세요.");
				}
			}
		}
		
	}
	void seatWrite(String[][] seats, ArrayList<String> al) {
		try {
			String[] arr = {"","","","","",""}; 
			for(int i=0; i<5; i++) {
				for(int j=0; j<10; j++) {
					arr[i] += seats[i][j]+" ";
					arr[5] = "  1 2 3 4 5 6 7 8 9";
				}
			}
			int idx =0;
			for(int i=select; i<select+6; i++) {
				al.set(i, arr[idx]);
				idx++;
			}
			
			FileWriter fw = new FileWriter("src/movie/seats.txt");
			for(int i=0; i<al.size(); i++) {
				fw.write(al.get(i)+'\n');
			}
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	void resWrite(String seatNum){
		try {
			FileWriter fw = new FileWriter("src/movie/reservation.txt",true);
			String[] arr=this.movie.get(select-1).split(" ")[1].split(",");
			long num=System.currentTimeMillis();
			System.out.println("발급번호 : "+num+" "+arr[1]+" "+seatNum);
			System.out.println("발급번호가 없으면 에매내역을 출력하실 수 없으니 주의하세요!");
			fw.write(num+" "+arr[0]+" "+arr[1]+" "+seatNum+"\r\n");
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
class MemberReserv extends NonMemberReserv implements Reserv{						//회원전용 예매 페이지
	ReservationMain rm = new ReservationMain();
	String id=rm.idpw.get(rm.idpwIndex).getId();				
	
	public void reservation() {
												//회원 id값 받아옴 회원이 로그인하면 arrayList에서 값 비교해서 index값 찾아서 넣을 예
		String memresLine = "";
		int i = 0;
		int point = 0;
		String grade = "";
		String[] memArr = new String[6];
		try {
			FileInputStream memres=new FileInputStream("src/movie/memreservation.txt");
			BufferedReader memresbr=new BufferedReader(new InputStreamReader(memres));
			while((memresLine = memresbr.readLine())!=null) {
	
				if(memresLine.contains(id)){
					i++;
				}
			}
			point=i*100;
			if(point>=0 && point<500) {
				grade="일반";
			}
			else if(point>=500 && point<1000) {
				grade="gold";
			}
			else if(point>=1000 && point<2000) {
				grade="vip";
			}
			else if(point>=2000) {
				grade="vvip";
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader membr = new BufferedReader(new InputStreamReader(new FileInputStream("src/movie/member.txt")));
			
			String memline="";
			String newMem="";
			
			while((memline = membr.readLine())!=null) {
				for(int j=0; j<7; j++){
					memArr = memline.split(" ");
					if(memArr[0].equals(id)){
						memArr[5] = grade;
						memArr[6] = Integer.toString(point);
					}
				}
				if(memline.contains(id)) {
					break;
				}
				newMem += (memline+"\r\n");
				
			}
//			String trash="";
//			trash=membr.readLine();
			String dummy="";
			for(int j=0; j<7; j++) {
				dummy += memArr[j]+" ";
			}
				newMem += (dummy+ "\r\n");
			while((memline = membr.readLine())!=null) {
				newMem += (memline + "\r\n");
			}
			FileWriter memfw = new FileWriter("src/movie/member.txt");
			memfw.write(newMem);
			memfw.close();
			membr.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		int select;
		boolean flag = true;
		while(flag) {
			System.out.println("───HOTTO Cinema 회원 예매 페이지───");
			System.out.println(id+"님  ["+grade+" 등급]  잔여 포인트: "+point+"p");
			System.out.println("1.[예매 하기] \n2.[예매 확인] \n3.[예매 취소] \n4.[메인메뉴로 이동]");
			select = sc.nextInt();
			switch(select) {
			case 1:
				res();
				flag =false;
				break;
			case 2: 
				resCheck();
				flag =false;
				break;
			case 3:
				resCancel();
				flag =false;
				break;
			case 4:
				ReservationMain rm=new ReservationMain();
				rm.process();
				flag=false;
				break;
			}
		}
	}
	public void resCheck() {
		try {
			FileReader fr = new FileReader("src/movie/memreservation.txt");
			BufferedReader br = new BufferedReader(fr);
			String buf;
			int bool=0;
			while((buf=br.readLine())!=null) {
				String id=buf.split(" ")[0];
				if(id.equals(this.id)) {
					System.out.println(id+"님이 예약하신 내역입니다.");
					System.out.println(buf);
					bool=1;
				}
			}
			if(bool==0) {
				System.out.println("예매하신 내역을 찾을 수 없습니다.");
			}
			reservation();
			fr.close();
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void resCancel() {
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> al2 = new ArrayList<String>();
		String[][] seatarr = new String[6][10];
		try {
			FileReader res = new FileReader("src/movie/memreservation.txt");
			BufferedReader resbr = new BufferedReader(res);
			FileReader movie = new FileReader("src/movie/movies.txt");
			BufferedReader moviebr = new BufferedReader(movie);
			FileReader seat = new FileReader("src/movie/seats.txt");
			BufferedReader seatbr = new BufferedReader(seat);			
			String buf;
			String seatNum=null;
			String movieNum=null;
			String index=null;

			String movname;
			String seatname;
			int idx=0;
			int tf=0;
			while((buf=resbr.readLine())!=null) {
				String[] arr=buf.split(" ");
				al2.add(buf);
				if(id.equals(arr[0])) {
					tf=1;
				}
			}
			if(tf==1) {
				System.out.println(id+"님이 예매하신 내역입니다.");
				for(int i=0; i<al2.size(); i++) {
					if(al2.get(i).split(" ")[0].equals(id)) {
						System.out.println(al2.get(i));
					}
				}
				System.out.println("취소할 영화제목을 입력해주세요.");
				movname=sc.next();
				System.out.println("취소할 좌석을 입력해주세요.");
				seatname=sc.next();
				int ox=0;
				for(int i=0; i<al2.size(); i++) {
					String[] arr=al2.get(i).split(" ");
					if(arr[0].equals(id) && arr[2].equals(movname) && arr[3].equals(seatname)) {

						movieNum=arr[1];
						seatNum=arr[3];
						ox=1;
					}
				}
				if(ox==0) {
					System.out.println("내역을 찾을 수 없습니다.");
				}
				
				while((buf=moviebr.readLine())!=null) {
					String[] mov = buf.split(",")[0].split(" ");
					if(mov[1].equals(movieNum)) {
						index=mov[0];
					}
				}
				while((buf=seatbr.readLine())!=null) {
					al.add(buf);
				}
				for(int i=0; i<al.size(); i++) {
					if(al.get(i).equals(index)) {
						idx=i;
					}
				}
				for(int i=0; i<6; i++) {
					seatarr[i]=al.get(idx+i+1).split(" ");
				}
				for(int i=0; i<6; i++) {
					String[] spl=seatNum.split("-");
					if(seatarr[i][0].equals(spl[0]))
					for(int j=1; j<10; j++) {
						if(seatarr[i][Integer.parseInt(spl[1])].equals("x")) {
							seatarr[i][Integer.parseInt(spl[1])]="o";
						}
					}
				}
				String[] arr = {"","","","","",""}; 
				for(int i=0; i<5; i++) {
					for(int j=0; j<10; j++) {
						arr[i] += seatarr[i][j]+" ";
						arr[5] = "  1 2 3 4 5 6 7 8 9";
					}
				}
				int x=0;
				for(int i=idx+1; i<idx+7; i++) {
					al.set(i, arr[x]);
					x++;
				}
				FileWriter seatWrite = new FileWriter("src/movie/seats.txt");
				for(int i=0; i<al.size(); i++) {
					seatWrite.write(al.get(i)+"\n");
				}
				for(int i=0; i<al2.size(); i++) {
					String id=al2.get(i).split(" ")[0];
					String seatid=al2.get(i).split(" ")[3];
					if(id.equals(this.id) && seatid.equals(seatname)) {
						al2.remove(i);
					}
				}
				FileWriter resWrite = new FileWriter("src/movie/memreservation.txt");
				for(int i=0; i<al2.size(); i++) {
					resWrite.write(al2.get(i)+"\n");
				}
				System.out.println("취소가 완료되었습니다.");
				seatWrite.close();
				resWrite.close();
				reservation();
			}

			else {
				System.out.println("예매하신 내역을 찾을 수 없습니다.");
				reservation();
			}
			
			res.close();
			resbr.close();
			movie.close();
			moviebr.close();
			seat.close();
			seatbr.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}

	}
	void resWrite(String seatNum) {
		try {
			FileWriter fw = new FileWriter("src/movie/memreservation.txt",true);
			String[] arr=this.movie.get(select-1).split(" ")[1].split(",");
			System.out.println("예매 완료 : "+arr[1]+" "+seatNum);

			fw.write(id+" "+arr[0]+" "+arr[1]+" "+seatNum+"\n");
			fw.close();
		}catch(IOException e) {
				e.printStackTrace();
		}
	}
}
class Admin{						//관리자클래스
	Scanner sc = new Scanner(System.in);
	
	public void adminMenu() {
		int select;
		boolean flag = true;
		while(flag) {
			System.out.println("───관리자 메뉴───");
			System.out.println("1.[영화 등록] \n2.[영화 목록] \n3.[영화 삭제] \n4.[회원 정보] \n5.[메인메뉴로 이동]");
			select = sc.nextInt();
			switch(select) {
			case 1:
				addMovie();
				break;
			case 2:
				movieList();
				break;
			case 3: 
				delMovie();
				break;
			case 4:
				memberInfo();
				break;
			case 5:
				ReservationMain rm=new ReservationMain();
				rm.process();
				flag=false;
				break;
			default:
				System.out.println("잘못된 번호 입력");
				break;
				}
		}
	}
	void addMovie() {
		try {
			FileWriter fw = new FileWriter("src/movie/movies.txt",true);
			FileWriter fw2 = new FileWriter("src/movie/seats.txt",true);
			boolean flag = true;
			int exit;
			String movies;
			Scanner sc = new Scanner(System.in);
			
			while(flag) {
				sc.nextLine();
				System.out.println("번호입력");
				long movnum = System.currentTimeMillis()/1000;	
				String num = sc.nextLine();
				System.out.println("영화입력");
				String name = sc.nextLine();
				System.out.println("장르입력");
				String genre = sc.nextLine();
				movies = num + " " + Long.toString(movnum) + "," + name +","+genre;
				fw.write(movies+"\n");
				System.out.println("추가완료");
				System.out.println("종료하려면 0 입력, 계속 추가하려면 1입력");
				exit = sc.nextInt();
				
				if(exit == 0) {
					flag = false;
				}else {
					System.out.println("추가를 계속합니다.");
				}
				String[][] seats = new String[6][10];  			
				for (int i=0; i<seats.length; i++) {			
					seats[i][0]=Character.toString(65+i);	
					seats[5][0]=" ";
					for (int j=1; j<seats[i].length; j++) {
						seats[i][j]="o";
						seats[5][j]=Integer.toString(j);
					}
				}
				fw2.write(num+'\n');
				for (int i=0; i<seats.length; i++) {
					for(int j=0; j<seats[i].length; j++) {
						fw2.write(seats[i][j]+" ");
					}	
					fw2.write("\n");
				}
			}
			fw.close();
			fw2.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	void movieList() {
		File movieList = new File("src/movie/movies.txt");
		try {
			FileReader fr = new FileReader(movieList);
			BufferedReader br = new BufferedReader(fr);
			String listLine = "";
			while((listLine = br.readLine()) != null) {
				System.out.println(listLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("영화 목록 출력완료.");
		
	}
	void delMovie() {
		String[] arr = new String[0];
		File movieList = new File("src/movie/movies.txt");
		String newMovieList = "";
		File seat = new File("src/movie/seats.txt");
		String newseat = "";
		System.out.println("-------영화목록출력-------");
		try {
			FileReader moviefr = new FileReader(movieList);
			BufferedReader moviebr = new BufferedReader(moviefr);
			String listLine = "";
			while((listLine = moviebr.readLine()) != null) {
				System.out.println(listLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("삭제할 영화의 줄번호를 입력하세요. (뒤로 가려면 a 입력) : ");
		String delnum=sc.next();
		if(delnum.equals("a")) {
			System.out.println("[관리자 메뉴로 이동.]");
			adminMenu();
		}
		else {
			try {
				BufferedReader moviebr = new BufferedReader(new InputStreamReader(new FileInputStream(movieList)));
				BufferedReader seatbr = new BufferedReader(new InputStreamReader(new FileInputStream(seat)));
				
				String movieline;
				String seatLine;
				boolean bl=true;
				while((movieline = moviebr.readLine())!=null) {
					arr = movieline.split(" ");
					if(arr[0].equals(delnum)){
						bl=false;
						break;
					}
					newMovieList += (movieline+"\r\n");
				}
				if(bl) {
					System.out.println("잘못 입력.");
					delMovie();
				}
				while((movieline = moviebr.readLine())!=null) {
					newMovieList += (movieline + "\r\n");
				}
				FileWriter moviefw = new FileWriter("src/movie/movies.txt");
				moviefw.write(newMovieList);
				
				while((seatLine = seatbr.readLine())!=null) {
					arr = seatLine.split(" ");
					if(arr[0].equals(delnum)) {
						break;
					}
					newseat += (seatLine+"\r\n");
				}
				
				String byebye = null;
				
				for(int i=0; i<6; i++) {
					byebye = seatbr.readLine();
				}
				while((seatLine = seatbr.readLine())!=null) {
					newseat += (seatLine+"\r\n");
				}
				FileWriter seatfw = new FileWriter("src/movie/seats.txt");
				seatfw.write(newseat);
				
				
				
				seatfw.close();
				seatbr.close();
				moviefw.close();
				moviebr.close();
				System.out.println("영화 삭제 완료.");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	void memberInfo() {
		File member = new File("src/movie/member.txt");
		System.out.println("-------회원정보출력-------");
		try {
			FileReader memberfr = new FileReader(member);
			BufferedReader memberbr = new BufferedReader(memberfr);
			String listLine = "";
			while((listLine = memberbr.readLine()) != null) {
				System.out.println(listLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

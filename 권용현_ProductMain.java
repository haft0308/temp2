

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

public class ProductMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductProcess pp = new ProductProcess();
		pp.process();
	}

}

class Product
{
	private int num;
	private String name;
	private int price;
	
	Product(int a, String b, int c) {
		num=a;
		name=b;
		price=c;
		
	}
	Product(String a, int b){
		name=a;
		price=b;
	}
	public String toString() {
		return num+"/"+name+"/"+price;
	}
	int getNum() {
		return num;
	}
	void setNum(int num) {
		this.num = num;
	}
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	int getPrice() {
		return price;
	}
	void setPrice(int price) {
		this.price = price;
	}
	
}

class ProductProcess
{	
	Scanner sc = new Scanner(System.in);
	boolean Flag=true;
	ServiceImpl service=new ServiceImpl();
	
	void process()
	{
		System.out.println("==================================================");
		System.out.println("===================제품관리 프로그램===================");
		System.out.println("==================================================");
		while(Flag)
		{
			try {
			System.out.println("메뉴 선택");
			System.out.println("1. 종료   2.제품등록   3. 제품수정   4. 제품삭제   5. 제품검색   6. 전체목록");
		
			switch(sc.nextInt()) {
			case 1:
				Flag=false;
				service.close();
				System.out.println("서비스 종료");
				break;
			case 2:
				addProduct();
				break;
			case 3:
				modifyProduct();
				break;
			case 4:
				deleteProduct();
				break;
			case 5:
				searchProduct();
				break;
			case 6:
				searchAll();
				break;
			}
			}catch(Exception e) {
				System.out.println("잘못 입력했습니다. 오류코드 :");
				e.printStackTrace();
				sc = new Scanner(System.in);
			}
		}
	}
	
	void addProduct()
	{
		System.out.print("추가할 제품:");
		String name= sc.next();
		System.out.println("제품 가격 :");
		int price= sc.nextInt();
		Product p = new Product(name,price);
		if(service.insert(p))
			System.out.println("제품 추가 성공");
		else
			System.out.println("제품 추가 실패");
	}
	void modifyProduct()
	{
		searchAll();
		System.out.print("수정할 제품 번호 :");
		int num = sc.nextInt();
		if( service.select(num) == null) {
			System.out.println("없는 제품 번호 입니다");
			return;
		}
		System.out.print("제품명:");
		String name= sc.next();
		System.out.print("가격:");
		int price = sc.nextInt();
		
		if( service.update(new Product(num,name,price) ))
			System.out.println("수정 성공");
		else
			System.out.println("수정 실패");
	}
	
	void deleteProduct()
	{
		searchAll();
		System.out.print("삭제할 제품 번호 : ");
		int num= sc.nextInt();
		if ( service.select(num)==null) {
			System.out.println("없는 제품 번호 입니다");
			return;
		}
		if (service.delete(num))
			System.out.println("삭제 성공");
		else
			System.out.println("삭제 실패");
	}
	
	void searchAll()
	{
		System.out.println("-------------전체 제품 목록------------");
		ArrayList<Product> al = service.selectAll();
		
		Iterator<Product> iter = al.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println("------------------------------------");
	}
	void searchProduct()
	{
		searchAll();
		System.out.print("검색할 제품 번호 : ");
		Product p = service.select( sc.nextInt() );
		System.out.println("검색결과 : ");
		if(p==null)
			System.out.println("없는 제품번호 입니다");
		else
			System.out.println(p);
	}
}


interface Service
{
	boolean insert(Product p);
	boolean delete(int num);
	boolean update(Product p);
	ArrayList<Product> selectAll();
	Product select(int num);
	void close();
}
class ServiceImpl implements Service
{
	MysqlDao dao = new MysqlDao();
	@Override
	public boolean insert(Product p) {
		// TODO Auto-generated method stub
		return dao.insert(p);
	}

	@Override
	public boolean delete(int num) {
		// TODO Auto-generated method stub
		return dao.delete(num);
	}

	@Override
	public boolean update(Product p) {
		// TODO Auto-generated method stub
		return dao.update(p);
	}

	@Override
	public ArrayList<Product> selectAll() {
		// TODO Auto-generated method stub
		return dao.selectAll();
	}

	@Override
	public Product select(int n) {
		// TODO Auto-generated method stub
		return dao.select(n);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		dao.close();
	}
	
}


interface Dao
{
	boolean insert(Product p);
	boolean update(Product p);
	boolean delete(int num);
	Product select(int num);
	ArrayList<Product> selectAll();
	void close();
}
class MysqlDao implements Dao
{
	private Connection conn;
	PreparedStatement pstmt;
	String sql;
	
	MysqlDao()
	{
		DBConnect dbconn= DBConnect.getInstance();
		conn= dbconn.getConnection();
	}

	@Override
	public boolean insert(Product p) {
		// TODO Auto-generated method stub
		sql="insert into product (name,price) values(?,?)";
		int num=0;
		
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, p.getName());
			pstmt.setInt(2, p.getPrice());
			num = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(num<=0)
			return false;
		return true;
	}

	@Override
	public boolean update(Product p) {
		// TODO Auto-generated method stub
		sql="update product set name=?, price=? where num=?";
		int num=0;
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, p.getName());
			pstmt.setInt(2, p.getPrice());
			pstmt.setInt(3, p.getNum());
			num = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(num<=0)
			return false;
		return true;
	}

	@Override
	public boolean delete(int n) {
		// TODO Auto-generated method stub
		int num=0;
		sql="delete from product where num=?";
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, n);
			
			num= pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(num<=0)
			return false;
		return true;
	}

	@Override
	public Product select(int num) {
		// TODO Auto-generated method stub
		Product p=null;
		sql="select * from product where num=?";
		
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				int numData= rs.getInt("num");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				
				p = new Product(numData,name,price);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public ArrayList<Product> selectAll() {
		// TODO Auto-generated method stub
		ArrayList<Product> al = new ArrayList<Product>();
		sql = "select * from product";
		
		try {
			pstmt= conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int num= rs.getInt("num");
				String name= rs.getString("name");
				int price= rs.getInt("price");
				Product p = new Product(num,name,price);
				al.add(p);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	@Override
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


class DBConnect
{
	private Connection conn=null;
	private static DBConnect db = new DBConnect();
	static DBConnect getInstance(){
		return db;
	}
	
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return conn;
	}

	private DBConnect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Properties props = new Properties();
			props.setProperty("user", "root");
			props.setProperty("password", "iotiot");
			props.setProperty("autoReconnect", "true");
			
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306",props);
			
			Statement stmt=conn.createStatement();
			stmt.execute("create database if not exists addrdb");
			stmt.execute("use addrdb");
			stmt.execute("create table if not exists product (num int auto_increment primary key,name varchar(10), price int);");
			stmt.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
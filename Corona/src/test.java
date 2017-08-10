package Corona;
import java.util.*;
public class test {
	static String[][] seatField = new String[3][10];										// 자리 30개 2차 문자열배열로 생성
	static String userid;																// 사용자id 전역변수로 설정
	static String[] choosemenu = new String[3];											// 선택한 메뉴들을 저장하는 문자열 배열
	static int count=0, usernum = 0, menucount=0;										// 회원번호
	static String rootid = "root",rootpwd = "1234";
	static private String Password;
	static int seatNum=0;
	static String[][] userinform = new String[10][10];
	static ArrayList<String> review = new ArrayList<>();
	static ArrayList<String> menu = new ArrayList<>();
	static HashMap<String, String> inform = new HashMap<String, String>();
	static HashMap<String, Integer> confirm = new HashMap<String, Integer>();				
	static HashMap<Integer, Integer> countmenu = new HashMap<Integer, Integer>();			//회원번호에 대한 메뉴개수
	static HashMap<Integer, Integer> seatconfirm = new HashMap<Integer, Integer>();
	static Scanner scanner = new Scanner(System.in);
	
	
	public static void Login(){							//구현완료
		while(true){
			System.out.println("1.회원가입 2.로그인 3.종료");
			int num = scanner.nextInt();
			switch(num){
			case 1: 
				while(true){
					System.out.println("사용자 아이디 입력: ");
					String inputid = scanner.next();
					System.out.println("비밀번호 입력: ");
					String inputpd = scanner.next();
					if(inputid.equals(rootid))
					{
						System.out.println("관리자 아이디는 변경할 수 없습니다. 다시입력하세요.");
					}
					else{
						inform.put(inputid, inputpd);
						System.out.println("회원가입 완료");
						break;
					}
				}
				break;
			case 2:
				try{
					while(true){
						System.out.println("사용자 아이디 입력:");
						String id = scanner.next();
						System.out.println("비밀번호 입력 : ");
						String password = scanner.next();
						if(id.equals(rootid) && password.equals(rootpwd)){
							System.out.println("관리자 로그인 성공");
							chooseAdminmenu();
							break;
						}
						else if(inform.get(id).equals(password)){
							System.out.println("사용자 "+id+" 로그인 성공");
							userid = id;
							Password = password;
							chooseUsermenu(id);
							break;
						}
						System.out.println("로그인실패");
					}
				}catch(NullPointerException e){
					System.out.println("잘못 입력하셨습니다. ");
					Login();
				}finally{}
				break;
			case 3:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			}
			if(num==2)
				break;
		}
	}
	public static void chooseAdminmenu(){
		System.out.println("1.사용자 조회 2.메뉴업데이트 3.초기화면");
		int num2 = scanner.nextInt();
		if(num2 == 1)
		{
			Userlist();
		}
		else if(num2 == 2){
			MenuUpdate();								
		}
		else if(num2 == 3){
			Login();
		}
	}
	public static void Userlist(){
		Set key = inform.keySet();
		for(Iterator iterator = key.iterator();iterator.hasNext();){
			String keyName = (String) iterator.next();
			String valueName = (String) inform.get(keyName);
			System.out.println("사용자 id : "+keyName + " ,Password : " + valueName);
			System.out.println("예약한 좌석번호 : " + userinform[confirm.get(keyName)][2]);
			int num1 = confirm.get(keyName);
			for(int i = 0;i<=countmenu.get(num1);i++){
				System.out.println("예약한 메뉴 : " + userinform[confirm.get(keyName)][i+3]);
			}
			System.out.println("------------------------------------------");

		}
		chooseAdminmenu();
	}
	public static void chooseUsermenu(String id){
		try{
			System.out.println("1.예약 2.예약조회 3.예약취소 4.로그아웃");
			int num1 = scanner.nextInt();
			switch(num1)
			{
			case 1:
				TableSearching();
				break;
			case 2:
				Lookup(userid);
				chooseUsermenu(id);
				break;
			case 3:
				CancelReservation(userid);
				break;
			case 4:
				Logout();
				break;
			}
		}
		catch(NullPointerException e){
			System.out.println("잘못 입력하셨습니다. ");
			chooseUsermenu(id);
		}
	}
	public static void Lookup(String id){
		try{
			System.out.println("사용자 아이디 : " + userinform[confirm.get(id)][0]);
			System.out.println("사용자 비밀번호 : " + userinform[confirm.get(id)][1]);
			System.out.println("예약한 좌석번호 : " + userinform[confirm.get(id)][2]);
			int num1 = confirm.get(id);
			for(int i = 0;i<=countmenu.get(num1);i++){
				System.out.println("예약한 메뉴 : " + userinform[confirm.get(id)][i+3]);
			}
		}
		catch(NullPointerException e)
		{
			System.out.println("예약 내역이 없습니다.");
			chooseUsermenu(id);
		}
	}
	public static Boolean TableSearching(){				//구현완료
		System.out.println("자리를 탐색합니다..");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print("[");
				System.out.printf("%2s",((j+1 )+(i*10)));
				System.out.print("] " + seatField[i][j]);
			}
			System.out.println(" ");
		}
		while(true){
			System.out.println("자리를 예약하시겠습니까? 1.예 2.아니오");
			int num = scanner.nextInt();
			if(num==1){
				TableReservation(userid);
				break;
			}
			else if(num==2){ 
				System.out.println("초기화면으로 돌아갑니다.");
				chooseUsermenu(userid);
				break;
			}				
		}
		return true;
	}
	public static Boolean TableReservation(String Name){	//구현완료
		int go=1;
		while(go==1){
			try{
				System.out.println("몇번 자리를 예약하시겠습니까? ");
				seatNum = scanner.nextInt();
				if(seatNum == 10 && seatField[0][9] == "---"){
					seatField[0][9] = userid;
					go = 0;
					break;
				}
				if(seatNum == 20 && seatField[1][9] == "---"){
					seatField[1][9] = userid;
					go = 0;
					break;
				}
				if(seatNum == 30 && seatField[2][9] == "---"){
					seatField[2][9] = userid;
					go = 0;
					break;
				}
				else if (seatField[seatNum/10][(seatNum%10) - 1] == "---") {
					seatField[seatNum/10][(seatNum%10) - 1] = userid;
					go = 0;
					break;
				}
				else
					System.out.println("예약불가능한 자리입니다. 다시 선택하세요.");
			}catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
			}
		}
		ConfirmTableReservation();
		return true;
	} 
	public static Boolean ConfirmTableReservation(){			//구현완료
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print("[");
				System.out.printf("%2s",((j+1 )+(i*10)));
				System.out.print("] " + seatField[i][j]);
			}
			System.out.println(" ");
		}
		MenuSearching();
		return true;
	}
	public static void MenuSearching(){							//구현완료
		System.out.println("현재 메뉴 : " + menu);
		System.out.println("메뉴를 고르세요.");
		int menu = scanner.nextInt();
		MenuReservation(menu);
	}
	public static void MenuUpdate(){					//구현완료
		while(true){
			System.out.println("현재 메뉴 : " + menu);
			System.out.println("메뉴를 업데이트하시겠습니까? 1.등록  2.삭제 3.이전화면");
			int num = scanner.nextInt();
			if(num == 1)
			{
				System.out.println("메뉴를 입력하시오");
				String inputmenu = scanner.next();
				MenuAddDelete(num, inputmenu);
				break;
			}
			else if(num == 2){
				MenuAddDelete(num, null);
				break;
			}
			else if(num == 3){
				chooseAdminmenu();
				break;
			}
		}
	}
	public static Boolean MenuAddDelete(int num, String menuItem){			//구현완료
		if(num == 1){
			System.out.println(menuItem+"을 메뉴 등록하였습니다.");
			menu.add(menuItem);
		}
		else if(num == 2){
			System.out.println("몇번째 항목을 삭제하시겠습니까? ");
			int menunum = scanner.nextInt();
			System.out.println(menu.get(menunum-1).toString() + " 항목을 삭제하였습니다.");
			menu.remove(menunum-1);
		}
		MenuUpdate();
		return true;
	} 
	public static void MenuReservation(int menunum){			// 구현완료
		choosemenu[count] = menu.get(menunum-1);
		System.out.println(menu.get(menunum-1) + "를 선택하셨습니다.");
		System.out.println("다른 메뉴를 선택하시겠습니까? 1.예 2.아니오");
		int num = scanner.nextInt();
		if(num == 1)
		{
			count++;
			menucount++;
			MenuSearching();
		}
		else if(num ==2)
		{
			ConfirmMenuReservation();
		}
	} 
	public static void ConfirmMenuReservation(){				// 구현완료
		System.out.println("예약 메뉴 : ");
		for(int i = 0;i<=count;i++){
			System.out.println((i+1) + ". " + choosemenu[i]);
		}
		Logout();
	}
	public static void CancelReservation(String userid){
		try{
			System.out.println("예약을 취소하시겠습니까? 1.예 2.아니오");
			int num = scanner.nextInt();
			if(num == 1)
			{
				for(int i = 2;i<10;i++){
					userinform[confirm.get(userid)][i] = null;
				}
				int erseat = seatconfirm.get(confirm.get(userid));
				if(erseat == 10){
					seatField[0][9] = "---";
				}
				else if(erseat == 20){
					seatField[1][9] = "---";
				}
				else if(erseat == 30){
					seatField[2][9] = "---";
				}
				else{
					seatField[erseat/10][(erseat%10)-1]="---";
				}
				System.out.println("예약 취소 완료");
				chooseUsermenu(userid);
			}
			else{
				chooseUsermenu(userid);
			}
		}
		catch(NullPointerException e)
		{
			System.out.println("예약 내역이 없습니다.");
			chooseUsermenu(userid);
		}
	}
	public static void Logout(){
		System.out.println("로그아웃합니다.");
		confirm.put(userid, usernum);
		userinform[usernum][0] = userid;
		userinform[usernum][1] = Password;
		userinform[usernum][2] = Integer.toString(seatNum);
		seatconfirm.put(usernum, seatNum);
		for(int i = 0;i<choosemenu.length;i++){
			userinform[usernum][i + 3] = choosemenu[i];
			choosemenu[i] = null;
		}
		countmenu.put(usernum, menucount);
		count=0;
		menucount=0;
		usernum++;
		Login();
	}
	public static void main(String args[]){
		System.out.println("프로그램을 시작합니다.");
		menu.add("후라이드치킨");							//기본 메뉴 생성과정
		menu.add("간장치킨");
		menu.add("양념치킨");
		menu.add("양파치킨");
		for (int i = 0; i < 3; i++) {				//자리 설정
			for (int j = 0; j < 10; j++) {
				seatField[i][j] = "---";
			}
		}
		Login();
	}
}
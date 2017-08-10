package Corona;
import java.util.*;
public class test {
	static String[][] seatField = new String[3][10];										// �ڸ� 30�� 2�� ���ڿ��迭�� ����
	static String userid;																// �����id ���������� ����
	static String[] choosemenu = new String[3];											// ������ �޴����� �����ϴ� ���ڿ� �迭
	static int count=0, usernum = 0, menucount=0;										// ȸ����ȣ
	static String rootid = "root",rootpwd = "1234";
	static private String Password;
	static int seatNum=0;
	static String[][] userinform = new String[10][10];
	static ArrayList<String> review = new ArrayList<>();
	static ArrayList<String> menu = new ArrayList<>();
	static HashMap<String, String> inform = new HashMap<String, String>();
	static HashMap<String, Integer> confirm = new HashMap<String, Integer>();				
	static HashMap<Integer, Integer> countmenu = new HashMap<Integer, Integer>();			//ȸ����ȣ�� ���� �޴�����
	static HashMap<Integer, Integer> seatconfirm = new HashMap<Integer, Integer>();
	static Scanner scanner = new Scanner(System.in);
	
	
	public static void Login(){							//�����Ϸ�
		while(true){
			System.out.println("1.ȸ������ 2.�α��� 3.����");
			int num = scanner.nextInt();
			switch(num){
			case 1: 
				while(true){
					System.out.println("����� ���̵� �Է�: ");
					String inputid = scanner.next();
					System.out.println("��й�ȣ �Է�: ");
					String inputpd = scanner.next();
					if(inputid.equals(rootid))
					{
						System.out.println("������ ���̵�� ������ �� �����ϴ�. �ٽ��Է��ϼ���.");
					}
					else{
						inform.put(inputid, inputpd);
						System.out.println("ȸ������ �Ϸ�");
						break;
					}
				}
				break;
			case 2:
				try{
					while(true){
						System.out.println("����� ���̵� �Է�:");
						String id = scanner.next();
						System.out.println("��й�ȣ �Է� : ");
						String password = scanner.next();
						if(id.equals(rootid) && password.equals(rootpwd)){
							System.out.println("������ �α��� ����");
							chooseAdminmenu();
							break;
						}
						else if(inform.get(id).equals(password)){
							System.out.println("����� "+id+" �α��� ����");
							userid = id;
							Password = password;
							chooseUsermenu(id);
							break;
						}
						System.out.println("�α��ν���");
					}
				}catch(NullPointerException e){
					System.out.println("�߸� �Է��ϼ̽��ϴ�. ");
					Login();
				}finally{}
				break;
			case 3:
				System.out.println("���α׷��� �����մϴ�.");
				System.exit(0);
			}
			if(num==2)
				break;
		}
	}
	public static void chooseAdminmenu(){
		System.out.println("1.����� ��ȸ 2.�޴�������Ʈ 3.�ʱ�ȭ��");
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
			System.out.println("����� id : "+keyName + " ,Password : " + valueName);
			System.out.println("������ �¼���ȣ : " + userinform[confirm.get(keyName)][2]);
			int num1 = confirm.get(keyName);
			for(int i = 0;i<=countmenu.get(num1);i++){
				System.out.println("������ �޴� : " + userinform[confirm.get(keyName)][i+3]);
			}
			System.out.println("------------------------------------------");

		}
		chooseAdminmenu();
	}
	public static void chooseUsermenu(String id){
		try{
			System.out.println("1.���� 2.������ȸ 3.������� 4.�α׾ƿ�");
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
			System.out.println("�߸� �Է��ϼ̽��ϴ�. ");
			chooseUsermenu(id);
		}
	}
	public static void Lookup(String id){
		try{
			System.out.println("����� ���̵� : " + userinform[confirm.get(id)][0]);
			System.out.println("����� ��й�ȣ : " + userinform[confirm.get(id)][1]);
			System.out.println("������ �¼���ȣ : " + userinform[confirm.get(id)][2]);
			int num1 = confirm.get(id);
			for(int i = 0;i<=countmenu.get(num1);i++){
				System.out.println("������ �޴� : " + userinform[confirm.get(id)][i+3]);
			}
		}
		catch(NullPointerException e)
		{
			System.out.println("���� ������ �����ϴ�.");
			chooseUsermenu(id);
		}
	}
	public static Boolean TableSearching(){				//�����Ϸ�
		System.out.println("�ڸ��� Ž���մϴ�..");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print("[");
				System.out.printf("%2s",((j+1 )+(i*10)));
				System.out.print("] " + seatField[i][j]);
			}
			System.out.println(" ");
		}
		while(true){
			System.out.println("�ڸ��� �����Ͻðڽ��ϱ�? 1.�� 2.�ƴϿ�");
			int num = scanner.nextInt();
			if(num==1){
				TableReservation(userid);
				break;
			}
			else if(num==2){ 
				System.out.println("�ʱ�ȭ������ ���ư��ϴ�.");
				chooseUsermenu(userid);
				break;
			}				
		}
		return true;
	}
	public static Boolean TableReservation(String Name){	//�����Ϸ�
		int go=1;
		while(go==1){
			try{
				System.out.println("��� �ڸ��� �����Ͻðڽ��ϱ�? ");
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
					System.out.println("����Ұ����� �ڸ��Դϴ�. �ٽ� �����ϼ���.");
			}catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��ϼ���.");
			}
		}
		ConfirmTableReservation();
		return true;
	} 
	public static Boolean ConfirmTableReservation(){			//�����Ϸ�
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
	public static void MenuSearching(){							//�����Ϸ�
		System.out.println("���� �޴� : " + menu);
		System.out.println("�޴��� ������.");
		int menu = scanner.nextInt();
		MenuReservation(menu);
	}
	public static void MenuUpdate(){					//�����Ϸ�
		while(true){
			System.out.println("���� �޴� : " + menu);
			System.out.println("�޴��� ������Ʈ�Ͻðڽ��ϱ�? 1.���  2.���� 3.����ȭ��");
			int num = scanner.nextInt();
			if(num == 1)
			{
				System.out.println("�޴��� �Է��Ͻÿ�");
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
	public static Boolean MenuAddDelete(int num, String menuItem){			//�����Ϸ�
		if(num == 1){
			System.out.println(menuItem+"�� �޴� ����Ͽ����ϴ�.");
			menu.add(menuItem);
		}
		else if(num == 2){
			System.out.println("���° �׸��� �����Ͻðڽ��ϱ�? ");
			int menunum = scanner.nextInt();
			System.out.println(menu.get(menunum-1).toString() + " �׸��� �����Ͽ����ϴ�.");
			menu.remove(menunum-1);
		}
		MenuUpdate();
		return true;
	} 
	public static void MenuReservation(int menunum){			// �����Ϸ�
		choosemenu[count] = menu.get(menunum-1);
		System.out.println(menu.get(menunum-1) + "�� �����ϼ̽��ϴ�.");
		System.out.println("�ٸ� �޴��� �����Ͻðڽ��ϱ�? 1.�� 2.�ƴϿ�");
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
	public static void ConfirmMenuReservation(){				// �����Ϸ�
		System.out.println("���� �޴� : ");
		for(int i = 0;i<=count;i++){
			System.out.println((i+1) + ". " + choosemenu[i]);
		}
		Logout();
	}
	public static void CancelReservation(String userid){
		try{
			System.out.println("������ ����Ͻðڽ��ϱ�? 1.�� 2.�ƴϿ�");
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
				System.out.println("���� ��� �Ϸ�");
				chooseUsermenu(userid);
			}
			else{
				chooseUsermenu(userid);
			}
		}
		catch(NullPointerException e)
		{
			System.out.println("���� ������ �����ϴ�.");
			chooseUsermenu(userid);
		}
	}
	public static void Logout(){
		System.out.println("�α׾ƿ��մϴ�.");
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
		System.out.println("���α׷��� �����մϴ�.");
		menu.add("�Ķ��̵�ġŲ");							//�⺻ �޴� ��������
		menu.add("����ġŲ");
		menu.add("���ġŲ");
		menu.add("����ġŲ");
		for (int i = 0; i < 3; i++) {				//�ڸ� ����
			for (int j = 0; j < 10; j++) {
				seatField[i][j] = "---";
			}
		}
		Login();
	}
}
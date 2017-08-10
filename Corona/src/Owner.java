public class Owner {

	public String name;

	public String PhoneNumber;

	private String Password;

	public String TableNumber;

	public String MenuNumber;

	public void Login(String Name, String Password){

		if(this.name ==name && this.Password==Password)

		{

			System.out.println("로그인 성공");

		}

		else{

			System.out.println("로그아웃");

		}

	}

	public void ConfirmReservation(){

		

	}

}

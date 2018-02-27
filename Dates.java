
public class Dates {
		private String code_num, name,surname,install,savings, amount, total, date;
		
		public Dates(String code_num, String name,String surname,String install,String savings,String amount,String total, String date ){
			this.code_num = code_num;
			this.name = name;
			this.surname = surname;
			this.install = install;
			this.savings = savings;
			this.amount = amount;
			this.total = total;
			this.date = date;
			
		}
		
		public String getcode_num(){
			return code_num;
		}
		
		public String getname(){
			return name;
		}
		public String getsurname(){
			return surname;
		}
		public String getinstall(){
			return install;
		}
		public String getsavings(){
			return savings;
		}
		
		public String getamount(){
			return amount;
		}
		
		public String gettotal(){
			return total;
		}
		public String getdate(){
			return date;
		}
		
}


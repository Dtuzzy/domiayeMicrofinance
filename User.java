
public class User {
		private String code_num, name,surname,reg,install,doinstall,savings, phone, grp, amount,period, total, date;
		
		public User(String code_num, String name,String surname,String reg,String install,String doinstall,String savings,String phone,String amount, String grp, String period,String total, String date ){
			this.code_num = code_num;
			this.name = name;
			this.surname = surname;
			this.reg = reg;
			this.install = install;
			this.doinstall = doinstall;
			this.savings = savings;
			this.phone = phone;
			this.grp = grp;
			this.amount = amount;
			this.period = period;
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
		public String getreg(){
			return reg;
		}
		public String getinstall(){
			return install;
		}
		public String getdoinstall(){
			return doinstall;
		}
		public String getsavings(){
			return savings;
		}
		public String getphone(){
			return phone;
		}
		public String getgrp(){
			return grp;
		}
		public String getamount(){
			return amount;
		}
		public String getperiod(){
			return period;
		}
		public String gettotal(){
			return total;
		}
		public String getdate(){
			return date;
		}
		
}


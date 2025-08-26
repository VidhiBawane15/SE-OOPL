import java.util.Scanner;
public class ATM
{
    float balance;
    public ATM(float b)
    {
        balance=b;
    }

    public void check_balance()
    {
        System.out.println("the balance in your account is: "+balance);
    }

    public void deposite(float depamt)
    {
        try
        {
            if(depamt<0)
            {
                throw new IllegalArgumentException();
            }
            balance+=depamt;
            System.out.println("the new balance is : "+balance);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Error:invalid amount");
            System.out.println("Error:"+e.getMessage());

        }
        finally
        {
        System.out.println("your transaction has ended succefully");
        }
       
    }

    public void withdraw(float withamt)
    {
        try
        {
            if(withamt>balance)
            {
                throw new ArithmeticException("Error: not sufficient balance");
            }
        balance-=withamt;
        System.out.println("the new balance is: "+balance);
        }
        catch(ArithmeticException e)
        {
            System.out.println("ERROR: insufficient balance"+e.getMessage());
        }
        finally
        {
        System.out.println("your transaction has ended succefully");
        }
    }

    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        ATM obj=new ATM(500);
        System.out.println("choose");
        String cont="yes";
        while(cont.equalsIgnoreCase("yes"))
        {
        System.out.println("1:Check Balance");
        System.out.println("2:Deposite Money");
        System.out.println("3:Withdraw Money");
        int choice=sc.nextInt();

        switch(choice)
        {
            case 1:
                obj.check_balance();
                break;
            case 2:
                System.out.println("Enter the amount you want to deposite in your account");
                float deptamt=sc.nextFloat();
                obj.deposite(deptamt);
                break;
            case 3:
                System.out.println("Enter the amount you want to withdraw in your account");
                float Wamt=sc.nextFloat();
                obj.withdraw(Wamt);  
                break; 
            default:
                System.out.println("wrong input"); 
        }
        System.out.println("Do you want to continue: (yes/no)");
        cont=sc.next();
    }
        sc.close();
    }
}

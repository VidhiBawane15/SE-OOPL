import java.util.Scanner;
public class Calc
{
    public void calculate(int a, int b,int c)
    {
        switch(c)
        {
        case(1):
        System.out.println("The sum of these numbers is: "+ (a+b));
        break;

        case(2):
        System.out.println("the subtraction is: "+(a-b));
        break;

        case(3):
        System.out.println("The product is: "+a*b);
        break;

        case(4):
        System.out.println("The remainder is: "+a%b);
        break;
        
        case(5):
            try
            {
                System.out.println("The division is: "+a/b);
            }catch(ArithmeticException e)
            {
                System.out.println("Error can not divide by zero");
                System.out.println("Exception details: "+e.getMessage());
            }
        default:
        System.out.println("invalid choice");    
        }
    }

    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        Calc obj=new Calc();

            String ch="y";
        while(ch.equalsIgnoreCase("y"))
        {
        System.out.println("Enter two numbers: ");
        int a=sc.nextInt();
        int b=sc.nextInt();
        System.out.println("enter the operation you want to perform \n1:Addition\n2:Subtraction\n3:Multiplication\n4:Remainder\n5:Division");
        int c=sc.nextInt();

        obj.calculate(a,b,c);
        System.out.println("do u want to continue: (y/n)");
        ch=sc.next();
        }
        sc.close();
    }
    
}

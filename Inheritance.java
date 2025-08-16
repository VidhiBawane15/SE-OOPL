class parent
{
    String name;
    int age;
    public void info(String n, int a)
    {
        name=n;
        age=a;
    }
  public void display()
  {
    System.out.println("this is the parent class\nhere name and age is given\n\n");
  }
}

class child extends parent
{
    String cname;
    child(String c)
    {
        this.cname=c;
    }
  public void show()
  {
      System.out.println("inside child class");
      System.out.println("the name of the child is:"+cname);
      System.out.println("Parent Name: " + name + ", Parent Age: " + age);

  }
  
}

public class Inheritance
{
  public static void main(String[] args)
  {
    System.out.println("inside main class");
    child obj = new child("xyz");
    obj.info("ABCD",45);
    obj.display();
    obj.show();
  }
}

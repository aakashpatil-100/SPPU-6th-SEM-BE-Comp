import java.util.Scanner;

public class Scheduling {

    public static void main(String[] args) {

    	Scanner sc = new Scanner(System.in);

        System.out.println("Enter Number of Processes:");
        int n = sc.nextInt();
        int bt[]=new int[n];
	    int at[]=new int[n];
        int priority[] = new int[n];

	    for(int i=0;i<n;i++)
	    {
	    	
	    	
	    	System.out.println("P(" + (i + 1) + "):Enter Arrival Time, Burst time  & priority");
	    	at[i]=sc.nextInt();
	    	
	    	bt[i]=sc.nextInt();
	    	
	    	priority[i] = sc.nextInt();
	    	
	    }
	    
	    System.out.println("Enter Time Quantum for Round Robin:");
        int quantum = sc.nextInt(); 
    	
        System.out.println("============================================================================================");

        System.out.println("FCFS Scheduling Algorithm:");
         FCFS fcfs = new FCFS();
         fcfs.execute(n,at,bt);
         
         System.out.println("============================================================================================");

         
         System.out.println("SJF Scheduling Algorithm:");
	     SJF sjf = new SJF();
	     sjf.execute(n,at,bt);
	     
	        System.out.println("============================================================================================");

	         
	     System.out.println("Round Robin Scheduling Algorithm:");
	     RoundRobin rr = new RoundRobin();
	     rr.execute(n,at,bt,quantum);
	     
	        System.out.println("============================================================================================");

	     
	     System.out.println("Priority Scheduling Algorithm:");
	     Priority pr = new Priority();
	     pr.execute(n,at,bt,priority);
    }

}
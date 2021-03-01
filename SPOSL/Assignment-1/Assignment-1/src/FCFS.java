
 import java.util.Scanner;

public class FCFS {
	 
	    public void execute(int n, int arrival_time[] ,int burst_time[]) { 
	    Scanner sc = new Scanner(System.in);
	    
	    
	    
	    
	    int wt[] = new int[n], tat[] = new int[n];
	    
	    int service_time[] = new int[n]; 
	    service_time[0] = 0; 
	    wt[0] = 0; 
	 
	    
	    for (int i = 1; i < n ; i++) 
	    { 
	        
	        int wasted=0;
	        
	        service_time[i] = service_time[i-1] + burst_time[i-1]; 
	 
	        
	        wt[i] = service_time[i] - arrival_time[i]; 
	 
	        if (wt[i] < 0) {
	            wasted = Math.abs(wt[i]);
	            wt[i] = 0; 
	        }
	        
	        service_time[i] = service_time[i] + wasted;
	    } 
	    
	    for (int i = 0; i < n ; i++) 
	        tat[i] = burst_time[i] + wt[i]; 
	    
	    System.out.print("Processes " +  " Arrival Time "+" Burst Time "
		        + " Waiting Time " + " Turn-Around Time "
		        + " Completion Time \n"); 
		    int total_wt = 0, total_tat = 0; 
		    for (int i = 0 ; i < n ; i++) 
		    { 
		        total_wt = total_wt + wt[i]; 
		        total_tat = total_tat + tat[i]; 
		        int compl_time = tat[i] + arrival_time[i]; 
		        System.out.println(i+1 + "\t\t" + arrival_time[i] + "\t\t"
		            + burst_time[i] + "\t\t" + wt[i] + "\t\t "
		            + tat[i] + "\t\t " + compl_time); 
		    } 
		 
		    System.out.println("Average waiting time = "
		        + (float)total_wt / (float)n); 
		    System.out.println("\nAverage turn around time = "
		        + (float)total_tat / (float)n); 
	    
	    
	 
	    } 

}

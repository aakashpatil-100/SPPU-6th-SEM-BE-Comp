import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
	String name;
	int BT, WT, AT, CT, TAT, remBT, priority;
	boolean flag;

	public Process(String name, int burst, int AT) {
		this.name = name;
		BT = burst;
		this.AT = AT;
		WT = CT = TAT = 0;
		remBT = BT;
		priority = 0;
	}

	public Process(String name, int burst, int AT, int PR) {
		this.name = name;
		BT = burst;
		this.AT = AT;
		WT = CT = TAT = 0;
		remBT = BT;
		priority = PR;
		flag = false;
	}

	public void display() {
		System.out.println(name + "\t\t" + AT + "\t\t" + BT + "\t\t" + CT + "\t\t" + TAT + "\t\t" + WT + "\t\t" + priority);
	}
}

class SortByPriority implements Comparator<Process> {

	@Override
	public int compare(Process o1, Process o2) {

		if (o1.AT == o2.AT) {
			return o1.priority - o2.priority;
		} else {
			return o1.AT - o2.AT;
		}
	}

}



public class Priority {
    private Scanner sc;

    public void execute(int n, int at[], int bt[], int priority[]) {
        sc = new Scanner(System.in);

        
        Process[] process = new Process[n];

        for (int i = 0; i < n; i++) {
            

            process[i] = new Process("P" + (i + 1), bt[i], at[i], priority[i]);
        }
        Arrays.sort(process, new SortByPriority());

        int sum = 0;
        double avgWT = 0, avgTAT = 0;
        int serv[] = new int[n];

        serv[0] = process[0].AT;
        process[0].WT = 0;

        for (int i = 1; i < n; i++) {
            serv[i] = process[i - 1].BT + serv[i - 1];

            process[i].WT = serv[i] - process[i].AT;

            if (process[i].WT < 0) {
                process[i].WT = 0;
            }
        }
        for (int i = 0; i < n; i++) {
            process[i].TAT = process[i].BT + process[i].WT;
        }

        int ST[] = new int[n];
        ST[0] = process[0].AT;
        process[0].CT = ST[0] + process[0].TAT;

        for (int i = 1; i < n; i++) {
            ST[i] = process[i - 1].CT;
            process[i].CT = ST[i] + process[i].TAT - process[i].WT;
        }

        System.out.println("\n\nProcess No\tArrival Time\tBurst Time\tCompletion Time\tTurn Around Time\tWaiting Time\tPriority");
        for (int i = 0; i < n; i++) {
            avgWT = avgWT + process[i].WT;
            avgTAT = avgTAT + process[i].TAT;

            process[i].display();
        }

        avgTAT = (double) avgTAT / n;
        avgWT = (double) avgWT / n;
        System.out.println("Average Waiting Time = " + avgWT);
        System.out.println("Average TAT = " + avgTAT);
    }
}
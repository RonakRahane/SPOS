import static java.lang.System.exit;
import java.util.Scanner;
public class CPUScheduling 
{
    public static void main(String[] args) 
    {
        int NP, PBT[], SPBT[], RRPBT[], PPBT[], P[], choice;
        Scanner S = new Scanner(System.in);
        System.out.print("Enter Number of Processes: ");
        NP=S.nextInt();
        PBT = new int[NP];
        P = new int [NP];
        SPBT = new int[NP];
        RRPBT = new int[NP];
        PPBT = new int[NP];
        System.out.println("Enter Process Burst Time: ");
        for(int i=0;i<NP;i++)
        {
            P[i] = i+1;
            System.out.print("P[" + P[i] + "]: ");
            PBT[i] = S.nextInt();
            SPBT[i]=PBT[i];
            RRPBT[i]=PBT[i];
            PPBT[i]=PBT[i];            
        }
        while(true)
        {
            System.out.println("*****CPU Scheduling Algorithms*****");
            System.out.println("\n 1. FCFS(First Come First Serve)");
            System.out.println(" 2. SJF(Shortest Job First- Preemptive)");
            System.out.println(" 3. RR(Round Robin- Preemptive)");
            System.out.println(" 4. Priority Scheduling(Non-Preemptive)");
            System.out.println(" 5. Exit");
            System.out.print("\n Enter Your Choice: ");
            choice = S.nextInt();
       
            switch(choice)
            {
                case 1: System.out.println("*****FCFS*****");
                        fcfs(NP, PBT, P);
                        break;
                case 2: System.out.println("*****SJF*****");
                        sjf(NP, SPBT, P);
                        break;
                case 3: System.out.println("*****Round Robin*****");
                        round_robin(NP, RRPBT, P);
                        break;
                case 4: System.out.println("*****Priority Scheduling*****");
                        priority_sched(NP, PPBT, P);
                        break;
                case 5: exit(0);
                        break;
                default: System.out.println("Please Enter a Valid Choice...!");
                         break;
            }
        }
    }
    
    static void fcfs(int NP, int PBT[], int P[]) 
    {
        int WT[], TAT[], AVWT=0, AVTAT=0, i;
        WT = new int[NP];
        TAT = new int[NP];
        WT[0] = 0;
        for(i=1;i<NP;i++)
        {
            WT[i]=0;
            for(int j=0;j<i;j++)
                WT[i]+=PBT[j];
        }
        
        System.out.println("\nProcess\t\t\tBurst Time\t\tWaiting Time\t\tTurnaround Time");
        for(i=0;i<NP;i++)
        {
            TAT[i] = PBT[i]+WT[i];
            AVWT += WT[i];
            AVTAT += TAT[i];
            System.out.println("P[" + P[i] + "]" + "\t\t\t" + PBT[i] + "\t\t\t" + WT[i] + "\t\t\t" + TAT[i]);
        }
        AVWT /= i;
        AVTAT /= i;
        System.out.println("\n\nAverage Waiting Time:" + AVWT);
        System.out.println("Average Turn Around Time:" + AVTAT);
    }
    
    static void sjf(int NP, int PBT[], int P[]) 
    {
        int AT[], WT[], TAT[], F[], CT[], K[], i;
        float AVWT, AVTAT;
        AT = new int[NP];
        WT = new int[NP];
        TAT = new int[NP];
        F = new int[NP];
        CT = new int[NP];
        K = new int[NP];
        
        Scanner S=new Scanner(System.in);
        System.out.print("Enter Process Arrival Time: \n");
        for(i=0; i<NP; i++)
        {
            System.out.print("P[" + P[i] + "]: ");
            AT[i]=S.nextInt();
            K[i]=PBT[i];
            F[i]=0;
        }

        
        float avgwt=0, avgtat=0;
        int st=0, tot=0;
        int min, c;
        while(true)
        {
            min=99;
            c=NP;
            if (tot==NP)
                break;
    
            for ( i=0;i<NP;i++)
            {
                if ((AT[i]<=st) && (F[i]==0) && (PBT[i]<min))
                {
                    min=PBT[i];
                    c=i;
                }
            }
            if (c==NP)
                st++;
            else
            {
                PBT[c]--;
                st++;
                if (PBT[c]==0)
                {
                    CT[c]= st;
                    F[c]=1;
                    tot++;
                }
            }
        }
    
        for(i=0;i<NP;i++)
        {
            TAT[i] = CT[i] - AT[i];
            WT[i] = TAT[i] - K[i];
            avgwt+= WT[i];
            avgtat+= TAT[i];
        }
    
        System.out.println("Processes " + " Arrival Time " +  " Burst time " +" Waiting Time " + " Turn Around Time");
        for(i=0;i<NP;i++)
        {
            System.out.println(" " + P[i] + "\t\t" + AT[i] + "\t\t" + K[i] +"\t " + WT[i] +"\t\t " + TAT[i]);
        }
    
        System.out.println("\nAverage Waiting Time is "+ (float)(avgwt/NP));
        System.out.println("Average Turn Around Time is "+ (float)(avgtat/NP));      
    }  
    
    static void round_robin(int NP, int PBT[], int P[])
    {
	int TQ, WT[], TAT[], total_wt=0, total_tat=0, OPBT[];
        WT=new int[NP];
        TAT=new int[NP];
        OPBT=new int[NP];
        Scanner S=new Scanner(System.in);
        System.out.print("Enter Time Quantum: ");
        TQ=S.nextInt();
        int rem_bt[] = new int[NP];
	for (int i = 0 ; i < NP ; i++)
        {
            rem_bt[i] = PBT[i];
            OPBT[i]=PBT[i];
        }
	
	int t = 0;              // Current time	
        boolean done;
	while(true)
	{
            done = true;
            for (int i = 0 ; i < NP; i++)
            {
		if (rem_bt[i] > 0)
		{
                    done = false; // There is a pending process
	
                    if (rem_bt[i] > TQ)
                    {
			t += TQ;
			rem_bt[i] -= TQ;
                    }
                    else
                    {
                        t = t + rem_bt[i];
			WT[i] = t - PBT[i];
			rem_bt[i] = 0;
                    }
                }
            }
	
            if (done == true)
		break;
	}
        for(int i = 0; i < NP ; i++)
            TAT[i] = PBT[i] + WT[i];
        
        System.out.println("Processes " + " Burst time " +" Waiting time " + " Turn around time");
	
	for (int i=0; i<NP; i++)
	{
            total_wt = total_wt + WT[i];
            total_tat = total_tat + TAT[i];
            System.out.println(" " + P[i] + "\t\t" + OPBT[i] +"\t " + WT[i] +"\t\t " + TAT[i]);
	}

        System.out.println("\n\nAverage Waiting Time:" + (float)total_wt/NP);
        System.out.println("Average Turn Around Time:" + (float)total_tat/NP); 		
    }
    
    static void priority_sched(int NP, int PBT[], int P[])
    {
        Scanner S=new Scanner(System.in);
        int Priority[], AT[], WT[], TAT[], CT[], temp;
        Priority=new int[NP];
        AT=new int[NP];
        WT=new int[NP];
        TAT=new int[NP];
        CT=new int[NP];
        System.out.print("Enter Process Arrival Time: \n");
        for(int i=0; i<NP; i++)
        {
            System.out.print("P[" + P[i] + "]: ");
            AT[i]=S.nextInt();
        }
        System.out.print("\nEnter Priority of Each Process: \n");
        for(int i=0; i<NP; i++)
        {
            System.out.print("P[" + P[i] + "]: ");
            Priority[i]=S.nextInt();
            P[i]=i+1;
        }
        for (int i = 0; i < NP; i++) 
        {
            for (int j = 0; j < NP - i - 1; j++) 
            {
                if (AT[j] > AT[j + 1]) 
                {
                    temp = AT[j];
                    AT[j] = AT[j + 1];
                    AT[j + 1] = temp;

                    temp = PBT[j];
                    PBT[j] = PBT[j + 1];
                    PBT[j + 1] = temp;

                    temp = Priority[j];
                    Priority[j] = Priority[j + 1];
                    Priority[j + 1] = temp;

                    temp = P[j];
                    P[j] = P[j + 1];
                    P[j + 1] = temp;

                }
                if (AT[j] == AT[j + 1]) 
                {
                    if (Priority[j] > Priority[j + 1]) 
                    {
                        temp = AT[j];
                        AT[j] = AT[j + 1];
                        AT[j + 1] = temp;

                        temp = PBT[j];
                        PBT[j] = PBT[j + 1];
                        PBT[j + 1] = temp;

                        temp = Priority[j];
                        Priority[j] = Priority[j + 1];
                        Priority[j + 1] = temp;
                        
                        temp = P[j];
                        P[j] = P[j + 1];
                        P[j + 1] = temp;
                    }
                }
            }
        }
        CT[0] = AT[0] + PBT[0];
        TAT[0] = CT[0] - AT[0];
        WT[0] = TAT[0] - PBT[0];
        
        for (int i = 1; i < NP; i++) 
        {
            CT[i] = PBT[i] + CT[i - 1];
            TAT[i] = CT[i] - AT[i];
            WT[i] = TAT[i] - PBT[i];
        }
        float sum = 0;
        for (int n : WT) 
        {
            sum += n;
        }
        float AVWT = sum / NP;

        sum = 0;
        for (int n : TAT) 
        {
            sum += n;
        }
        float AVTAT = sum / NP;
        
        System.out.println("Processes " + " Arrival Time " +  " Burst time " +" Waiting Time " + " Turn Around Time");
        for(int i=0;i<NP;i++)
        {
            System.out.println(" " + P[i] + "\t\t" + AT[i] + "\t\t" + PBT[i] +"\t " + WT[i] +"\t\t " + TAT[i]);
        }
    
        System.out.println("\nAverage Waiting Time is "+ AVWT);
        System.out.println("Average Turn Around Time is "+ AVTAT);
    }   	
}


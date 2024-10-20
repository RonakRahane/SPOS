import java.util.*;
class fit {
  public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in); // Create a Scanner object for input
      // Input for block sizes
      System.out.print("Enter the number of memory blocks: ");
      int m = scanner.nextInt();
      int blockSize[] = new int[m];
      System.out.println("Enter the sizes of the blocks:");
      for (int i = 0; i < m; i++) {
        blockSize[i] = scanner.nextInt(); // Store block sizes from user
      }
      // Input for process sizes
      System.out.print("Enter the number of processes: ");
      int n = scanner.nextInt();
      int processSize[] = new int[n];
      System.out.println("Enter the sizes of the processes:");
      for (int i = 0; i < n; i++) {
        processSize[i] = scanner.nextInt(); // Store process sizes from user }
        while (true) {
          System.out.println("\nMemory Allocation Strategies:");
          System.out.println("1. First Fit");
          System.out.println("2. Best Fit");
          System.out.println("3. Worst Fit");
          System.out.println("4. Next Fit");
          System.out.println("5. Exit");
          System.out.print("Choose a strategy (1-5): ");
          int choice = scanner.nextInt();
          switch (choice) {
          case 1:
            implementFirstFit(blockSize.clone(), m, processSize, n);
            break;
          case 2:
            implementBestFit(blockSize.clone(), m, processSize, n);
            break;
          case 3:
            implementWorstFit(blockSize.clone(), m, processSize, n);
            break;
          case 4:
            implementNextFit(blockSize.clone(), m, processSize, n);
            break;
          case 5:
            System.out.println("Exiting...");
            scanner.close();
            return; // Exit the program
          default:
            System.out.println("Invalid choice! Please select again.");
          }
        }
      }
      // First Fit implementation
      static void implementFirstFit(int blockSize[], int blocks, int processSize[], int processes) {
        int allocate[] = new int[processes];
        for (int i = 0; i < allocate.length; i++)
          allocate[i] = -1;
        for (int i = 0; i < processes; i++) {
          for (int j = 0; j < blocks; j++) {
            if (blockSize[j] >= processSize[i]) {

              allocate[i] = j;
              blockSize[j] -= processSize[i];
              break;
            }
          }
        }
        printAllocationResults(processSize, allocate);
      }
      // Best Fit implementation
      static void implementBestFit(int blockSize[], int blocks, int processSize[], int processes) {
        int allocate[] = new int[processes];
        for (int i = 0; i < allocate.length; i++) {
          allocate[i] = -1;
        }
        for (int i = 0; i < processes; i++) {
          int bestIdx = -1;
          for (int j = 0; j < blocks; j++) {
            if (blockSize[j] >= processSize[i]) {
              if (bestIdx == -1 || blockSize[bestIdx] > blockSize[j]) {
                bestIdx = j;
              }
            }
          }
          if (bestIdx != -1) {
            allocate[i] = bestIdx;
            blockSize[bestIdx] -= processSize[i];
          }
        }
        printAllocationResults(processSize, allocate);
      }
      // Worst Fit implementation
      static void implementWorstFit(int blockSize[], int blocks, int processSize[], int processes) {
        int allocate[] = new int[processes];
        for (int i = 0; i < allocate.length; i++)

          allocate[i] = -1;
        for (int i = 0; i < processes; i++) {
          int worstIdx = -1;
          for (int j = 0; j < blocks; j++) {
            if (blockSize[j] >= processSize[i]) {
              if (worstIdx == -1 || blockSize[worstIdx] < blockSize[j]) {
                worstIdx = j;
              }
            }
          }
          if (worstIdx != -1) {
            allocate[i] = worstIdx;
            blockSize[worstIdx] -= processSize[i];
          }
        }
        printAllocationResults(processSize, allocate);
      }
      // Next Fit implementation
      static void implementNextFit(int blockSize[], int blocks, int processSize[], int processes) {
        int allocate[] = new int[processes];
        for (int i = 0; i < allocate.length; i++)
          allocate[i] = -1;
        int j = 0; // Starting index for next fit for (int i = 0; i < processes; i++)
        {
          while (j < blocks) {
            if (blockSize[j] >= processSize[i]) {
              allocate[i] = j;
              blockSize[j] -= processSize[i];
              j = (j + 1) % blocks; // Move to the next block break;
            }
            j = (j + 1) % blocks; // Move to the next block

          }
        }
        printAllocationResults(processSize, allocate);
      }
      // Method to print allocation results
      static void printAllocationResults(int processSize[], int allocate[]) {
        System.out.println("\nProcess No.\tProcess Size\tBlock no.\n");
        for (int i = 0; i < processSize.length; i++) {
          System.out.print(i + 1 + "\t\t\t" + processSize[i] + "\t\t\t");
          if (allocate[i] != -1)
            System.out.println(allocate[i] + 1); // Print block number (1-based) else
          System.out.println("Not Allocated"); // Indicate if not allocated }
        }
      }
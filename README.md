# **Network Testing Tool**  

## **Overview**  
The **Network Testing Tool** is a simple **Java Swing** application that allows users to execute network-related terminal/command prompt commands directly from a GUI. The tool provides real-time command output within the application and supports stopping an active process.  

## **Features**  
- **Execute terminal commands** (e.g., `ping`, `nslookup`, `tracert`, `netstat`).  
- **Real-time output display** in a styled text pane.  
- **Color-coded output**:  
  - **Blue:** Displays the executed command.  
  - **White:** Shows command output.  
  - **Red:** Displays errors or process termination messages.  
- **Stop Running Commands** with a single click.  
- **Simple GUI** built using Java Swing.  

## **Technologies Used**  
- **Java Swing** (Graphical User Interface)  
- **ProcessBuilder** (Executing system commands)  
- **Multithreading** (For handling command output in real-time)  

## **Installation & Setup**  
1. **Clone the repository**:  
   ```sh
   git clone https://github.com/UdithaPJ/Network-Testing-Tool.git
   cd Network-Testing-Tool
   ```
2. **Compile the Java file**:  
   ```sh
   javac Main.java
   ```
3. **Run the program**:  
   ```sh
   java Main
   ```

## **Usage**  
1. **Enter a command** (e.g., `ping google.com`) in the text field.  
2. **Click the "Run" button** to execute the command.  
3. **View the output** in the black console-style text pane.  
4. **Click the "Stop" button** to terminate the currently running command.  

## **Example Commands**  
- **Windows**:  
  - `ping google.com` (Check network connectivity)  
  - `ipconfig` (Show network configuration)  
  - `tracert google.com` (Trace network route)  
  - `netstat` (View active network connections)  

- **Linux/macOS**:  
  - `ping google.com`  
  - `ifconfig` (Show network configuration)  
  - `traceroute google.com` (Trace network route)  
  - `nslookup google.com` (DNS lookup)  

## **Example Output**  
```
Command - ping google.com
Pinging google.com [142.250.183.238] with 32 bytes of data:
Reply from 142.250.183.238: bytes=32 time=14ms TTL=117
Reply from 142.250.183.238: bytes=32 time=13ms TTL=117

Exited with error code: 0
```

## **Notes & Limitations**  
- Some commands require **admin/root privileges** to execute.  
- The **Stop button** may not immediately terminate all processes, depending on the system.  
- This tool executes system commands **as-is**, so ensure that input commands are **valid and safe**.  

## **License**  
This project is licensed under the **MIT License**.

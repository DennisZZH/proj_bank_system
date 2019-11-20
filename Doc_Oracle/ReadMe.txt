You have two optional choice to connect ORACL server:
1. Open terminal of CSIL machine and type 
sqlplus c##YourNetID/YourPerm@cs174a.cs.ucsb.edu/ORCL

E.g:sqlplus c##ranliu/0000000@cs174a.cs.ucsb.edu/ORCL

Then you should be able to connect ORACLE server

The other optional choice to connect ORACLE is as following:

1. Put this script in some folder
2. Run command "sh setup.sh"
3. Run command "source ~/.bashrc"
4. Check if environment variable is added. Run command "echo $TWO_TASK". You should be able see following. If not, go to Step 7:
=====================================================================
[ran_liu@csil-02 ~]$ echo $TWO_TASK
cs174a.cs.ucsb.edu/ORCL
=====================================================================
5. Open terminal and type sqlplus
6. Input your account name: c##YourNetID, password: YourPerm.
Remember to add c## in front of your YourNetID: C##YourNetID
You should be able see following:
=====================================================================
Connected to:
Oracle Database 19c Enterprise Edition Release 19.0.0.0.0 - Production
Version 19.3.0.0.0


SQL> 
=====================================================================

7. If you failed in Step 4, follow the steps below:
a. Open terminal and type vi .bashrc
b. Add the following sentence to the end of the file "export TWO_TASK=cs174a.cs.ucsb.edu/ORCL".
c. Run command "source ~/.bashrc".
d. Run command "echo $TWO_TASK" to see the output.

8. Open terminal and type sqlplus

9. Input your account name: c##YourNetID, password: YourPerm.
Remember to add c## in front of your YourNetID: C##YourNetID
You should be able see following:
=====================================================================
Connected to:
Oracle Database 19c Enterprise Edition Release 19.0.0.0.0 - Production
Version 19.3.0.0.0

SQL> 
=====================================================================
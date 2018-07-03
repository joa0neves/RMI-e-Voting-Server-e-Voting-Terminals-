# RMI-e-Voting-Server-e-Voting-Terminals-
RMI Server For e-voting + Admin Console (look at info for the Elections being held, setting up new voting locations and tallying up  the vote count)+ Voting terminal console + Authentication console(tcp Server) 

RMI Server:
  Parameters: RMI <self-port> <host> <port>
  
 Admin Console:
  Parameters: Admin <host>
  The admin console is made up of easy to use menu that only takes numbers as input.
 
 TCP Server:
  Parameters: TcpServer <host>
  The TCP Server starts by asking the name of the election, place where the electon is taking place and the maximun number of Voting booths are allowed to connect to the TCP Server. After the initial setup, this server enters a loop where:
      1-Asks for an ID number if there an available;
      2-If the person attached to the given Id number is allowed to vote in the election being held in this Tcp Server an Voting Booth
  
Voting Booth(client):
  Parameters: TcpClient <host> <port>
  The Voting Booth Terminal only accepts these types of commands:
    type|login;username|******;password|***** - This command lets the user login in to the terminal;
    type|list - After login in, this command lets the user see which parties the user can vote for;
    type|vote;list|****** - After picking which party the user wishes to vote for, this command allows the user to vote.
  
  After voting the user is autommaticly logged off the terminal and terminal locks it self until it is unlocked by the Tcp Server.
  



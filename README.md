# Transaction Searcher Bot
Transaction Searcher Bot is  a Telegram chat bot that informs about high-value transactions that are being broadcast to the Bitcoin blockchain.
 
 ##Used library
 I used the library from :
 ```
 blockchain/api-v1-client-java
 (https://github.com/blockchain/api-v1-client-java)
 ```
 
 ##Maven repository
 Add the following to pom.xml:
 ```
    ...
    <repositories>
        ...
        <repository>
            <id>api-v1-client-java-mvn-repo</id>
            <url>https://raw.githubusercontent.com/blockchain/api-v1-client-java/mvn-repo/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
        ...
    </repositories>

    <dependencies>
        ...
        <dependency>
            <groupId>info.blockchain</groupId>
            <artifactId>api</artifactId>
            <version>2.0.0</version>
        </dependency>

        <dependency>
            <groupId>org.telegram</groupId>
            <artifactId>telegrambots</artifactId>
            <version>4.9.1</version>
        </dependency>
        ...
    </dependencies>
 ```
 ##How does it work?
  The name of bot in the Telegram is
   ```Transaction_Searcher_Bot```
  The username of bot in the Telegram is 
  ```@TransactionSearcherBot```
  To get information about transactions with greater value of the determined threshold that are being broadcast to the Bitcoin Blockchain
  You should enter the command ```/start (amount of the threshold)```
  after entering the command, you can see ***the hash of the transaction***, ***the total value of output*** and ***the date and time of the transaction***.
  Information update evey 4000ms.
  for stopping the stream you should enter the command ````/stop````.
  
  
  

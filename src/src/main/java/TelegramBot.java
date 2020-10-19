import info.blockchain.api.APIException;
import info.blockchain.api.blockexplorer.entity.Transaction;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;


public class TelegramBot extends TelegramLongPollingBot {
    BlockExplorerWrapper blockExplorerWrapper;
    boolean stop = false;

    public TelegramBot(BlockExplorerWrapper blockExplorerWrapper) {
        this.blockExplorerWrapper = blockExplorerWrapper;
    }


    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            final long chat_id = update.getMessage().getChatId();

            if (message_text.equals("/stop")){
                stop = true;
            }else{
                //The format of input is : /start(space)number
                //Separate '/start' and number
                String[] messageTextSplited = message_text.split(" ");
                if(messageTextSplited.length == 2){
                    String command = messageTextSplited[0];

                    if(command.equals("/start")){
                        stop = false;
                        try{
                            final Long amountThreshold = Long.valueOf(messageTextSplited[1]);
                            Executors.newCachedThreadPool().execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        printNewCustomTransaction(chat_id,amountThreshold);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (APIException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }catch (NumberFormatException e){
                            //if after '/start' is not a number: send error
                            SendMessage message = new SendMessage()
                                    .setChatId(chat_id)
                                    .setText("Please enter a number!");
                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }else {
                        SendMessage message = new SendMessage() // Create a message object object
                                .setChatId(chat_id)
                                .setText("please enter a correct command : /start (number)");
                        try {
                            execute(message); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    SendMessage message = new SendMessage() // Create a message object object
                            .setChatId(chat_id)
                            .setText("please enter a correct command : /start' '(number) + or stop" );
                    try {
                        execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }



            }
        }

    }

    public String getBotUsername() {
        return "TransactionSearcherBot";
    }

    public String getBotToken() {
        return "1049190457:AAEVErg4kgwK3RiIrQJrx42v_P5hgn6fArc";
    }

    //converting timestamp to date and utc time
    public String getHumanReadableTime(Long timeStamp){
        Date date = new Date(timeStamp*1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        jdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String java_date = jdf.format(date);
        return java_date;
    }

    //print transactions there are more than the specific amount which entered with command /start
    public void printNewCustomTransaction(long chat_id, long amountThreshold) throws InterruptedException, APIException, IOException {

        long maxTimePreviousIteration = 0;

        while(!stop){
            List<Transaction> unconfirmedTransactionsList = blockExplorerWrapper.getTransactionsWithAmountMoreThan(amountThreshold);
            ListIterator<Transaction> listIterator = unconfirmedTransactionsList.listIterator(unconfirmedTransactionsList.size());
            while (listIterator.hasPrevious()){
                Transaction theTransaction = listIterator.previous();
                if(theTransaction.getTime() > maxTimePreviousIteration) {//preventing to repetition of printing transaction
                    maxTimePreviousIteration = theTransaction.getTime();
                    SendMessage message = new SendMessage()
                            .setChatId(chat_id)
                            .setText("Transfer of amount " + blockExplorerWrapper.getTotalOutputsValue(theTransaction) + " satoshi happened with hash " + theTransaction.getHash() + " on " +getHumanReadableTime(theTransaction.getTime()));
                    try {
                        execute(message); // Sending our message object to user
                    } catch (TelegramApiException e1) {
                        e1.printStackTrace();
                    }
                }

            }
            Thread.sleep(4000);


        }

    }
}

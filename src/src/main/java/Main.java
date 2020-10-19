
import info.blockchain.api.APIException;
import info.blockchain.api.blockexplorer.BlockExplorer;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws APIException, IOException {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot(new BlockExplorerWrapper()));

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        BlockExplorer blockExplorer = new BlockExplorer();
        //BlockExplorerWrapper BlockExplorerWrapper = new BlockExplorerWrapper();
//        System.out.println(BlockExplorerWrapper.getLatestBlock());

//        for (Transaction transaction : BlockExplorerWrapper.getUnconfirmedTransactions()){
//            System.out.println(transaction.getHash());
//            System.out.println(transaction.getTime());
//            System.out.println(transaction.getBlockHeight());
//        }

//        for (Transaction transaction : BlockExplorerWrapper.getUnconfirmedTransactions()) {
//            long totalValue = 0;
//            for (Output output : transaction.getOutputs()) {
//                totalValue = totalValue + output.getValue();
//            }
//            if (totalValue > 1000000){
//                System.out.println(totalValue);
//                System.out.println("Time = "+transaction.getTime());
//                System.out.println("Hash = " + transaction.getHash());
//                System.out.println("---------------------------------------");
//            }
//
//
//
//        }

//        for(SimpleBlock simpleBlock : blockExplorer.getBlocks()){
//           // System.out.println(simpleBlock.getHash());
//            System.out.println(blockExplorer.getBlock(simpleBlock.getHash()).getTransactions().size());
//
//        }

            //value of the last block transactions
//        for(Transaction transaction : blockExplorer.getBlock(blockExplorer.getLatestBlock().getHash()).getTransactions()){
//            for(Output output : transaction.getOutputs()) {
//                System.out.println(output.getValue());
//
//
//            }
//            System.out.println("________________________________________________");
//        }

//        for(Transaction transaction : blockExplorer.getBlock(blockExplorer.getLatestBlock().getHash()).getTransactions()){
//            for(Output output : transaction.getOutputs()) {
//                System.out.println(output.getValue());
//
//
//            }
//            System.out.println("________________________________________________");
//        }


        }

    }

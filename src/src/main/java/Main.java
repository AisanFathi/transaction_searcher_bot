
import info.blockchain.api.APIException;
import info.blockchain.api.blockexplorer.BlockExplorer;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot(new BlockExplorerWrapper()));

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        }

    }
